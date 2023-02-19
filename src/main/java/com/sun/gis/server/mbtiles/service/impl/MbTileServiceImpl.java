package com.sun.gis.server.mbtiles.service.impl;


import com.sun.gis.server.mbtiles.entity.model.MBTileInfo;
import com.sun.gis.server.mbtiles.entity.model.UpdateStatus;
import com.sun.gis.server.mbtiles.mbTileHandle.MBTilesReader;
import com.sun.gis.server.mbtiles.mbTileHandle.MbListBean;
import com.sun.gis.server.mbtiles.mbTileHandle.MetadataEntry;
import com.sun.gis.server.mbtiles.mbTileHandle.Tile;
import com.sun.gis.server.mbtiles.service.MbTileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MbTileServiceImpl implements MbTileService {

    private static final Logger log = LoggerFactory.getLogger(MbTileService.class);

    @Value("${mbdata.path}")
    private String mbPath;

    @Resource
    MbListBean mbListBean;

    @Override
    public String dataPath() {
        return String.format("{\"path\": \"%s\"}", mbPath);
    }

    @Override
    public MetadataEntry mbTilesMetadata(String layer) {

        MBTilesReader mbTilesReader = mbListBean.getMBReader(layer).get_mBTileReader();
        if (mbTilesReader != null) {
            try {
                return mbTilesReader.getMetadata();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public List<MBTileInfo> datalist() {
        return mbListBean.getAllMBFiles();
    }

    @Override
    public UpdateStatus refreshFolder() {

        UpdateStatus upStatus = new UpdateStatus();
        try {
            mbListBean.setMBTilesValue();
            upStatus.isSuc = true;
        } catch (Exception e) {
            upStatus.errorMsg = e.getMessage();
        }
        return upStatus;
    }

    @Override
    public ResponseEntity getTile(int x, int y, int zoom, String layer) {
        try {
            MBTilesReader mbTilesReader = mbListBean.getMBReader(layer).get_mBTileReader();
            if (mbTilesReader != null) {
                Tile tile = mbTilesReader.getTile(zoom, x, y);
                if (tile.getData() != null) {
                    int size = tile.getData().available();
                    byte[] bytes = new byte[size];
                    tile.getData().read(bytes);
                    HttpHeaders headers = new HttpHeaders();
                    String formatTypeString = mbListBean.getMBReader(layer).get_imageFormat();
                    // 获取矢量切片
                    if (formatTypeString.contains("pbf")) {
                        headers.add("Content-Type", "application/x-protobuf");
                        headers.add("Content-Encoding", "gzip");
                    } else { //获取影像切片
                        headers.add("Content-Type", String.format("image/%s", formatTypeString));
                    }
                    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到对象");
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("The tile with the parameters x=%d, y=%d & zoom=%d not found", x, y, zoom));
        }
    }
}
