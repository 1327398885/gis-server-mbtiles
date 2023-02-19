package com.sun.gis.server.mbtiles.service;


import com.sun.gis.server.mbtiles.entity.model.MBTileInfo;
import com.sun.gis.server.mbtiles.entity.model.UpdateStatus;
import com.sun.gis.server.mbtiles.mbTileHandle.MetadataEntry;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MbTileService {

    /**
     * 返回数据路径
     *
     * @return dataPath
     */
    String dataPath();

    /**
     * 图层信息详情
     *
     * @param layer 图层
     * @return MetadataEntry
     */
    MetadataEntry mbTilesMetadata(String layer);

    /**
     * 图层列表信息
     *
     * @return list
     */
    List<MBTileInfo> datalist();

    /**
     * 刷新图层列表信息
     *
     * @return UpdateStatus
     */
    UpdateStatus refreshFolder();

    /**
     * 根据获取实时切片数据
     *
     * @param x     x
     * @param y     y
     * @param zoom  层级
     * @param layer 图层
     * @return ResponseEntity
     */
    ResponseEntity getTile(int x, int y, int zoom, String layer);

}
