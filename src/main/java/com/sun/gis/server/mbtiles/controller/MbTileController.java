package com.sun.gis.server.mbtiles.controller;


import com.sun.gis.server.mbtiles.entity.model.MBTileInfo;
import com.sun.gis.server.mbtiles.entity.model.UpdateStatus;
import com.sun.gis.server.mbtiles.mbTileHandle.MetadataEntry;
import com.sun.gis.server.mbtiles.service.MbTileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "mbtiles相关接口")
@RestController
@RequestMapping("mbtiles")
public class MbTileController {

    @Resource
    MbTileService mbTileService;

    @ApiOperation(value = "返回数据路径")
    @GetMapping(path = "dataPath", produces = {"application/json;charset=UTF-8"})
    public String dataPath() {
        return mbTileService.dataPath();
    }

    @ApiOperation(value = "图层列表信息")
    @GetMapping(path = "datalist", produces = {"application/json;charset=UTF-8"})
    public List<MBTileInfo> datalist() {
        return mbTileService.datalist();
    }

    @ApiOperation(value = "图层信息详情")
    @GetMapping(path = "{layer}/metadata", produces = {"application/json;charset=UTF-8"})
    public MetadataEntry mbTilesMetadata(@PathVariable("layer") String layer) {
        return mbTileService.mbTilesMetadata(layer);
    }

    @ApiOperation(value = "刷新图层列表信息")
    @GetMapping(path = "refresh", produces = {"application/json;charset=UTF-8"})
    public UpdateStatus refreshFolder() {
        return mbTileService.refreshFolder();
    }

    @ApiOperation(value = "根据获取实时切片数据")
    @GetMapping("{layer}/{zoom}/{x}/{y}")
    public ResponseEntity getTile(@PathVariable("x") int x, @PathVariable("y") int y, @PathVariable("zoom") int zoom,
                                  @PathVariable("layer") String layer) {
        return mbTileService.getTile(x, y, zoom, layer);
    }
}
