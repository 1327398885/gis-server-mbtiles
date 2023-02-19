# gis-server-mbtiles
一个基于Java实现的mbtiles数据发布管后台理服务

## 什么是MBTiles

MBTiles 是一种用于在 [SQLite (opens new window)](http://sqlite.org/)数据库中存储任意切片地图数据以供立即使用和高效传输的规范。

MBTiles 是一个紧凑的、限制性的规范。它仅支持分块数据，包括矢量或图像分块和交互网格分块。仅支持 Spherical Mercator 投影进行展示（平铺显示），并且仅支持经纬度坐标用于边界和中心等元数据。

这是最低规范，仅指定必须检索数据的方式。因此 MBTiles 文件可以在内部压缩和优化数据，并构建符合 MBTiles 规范的视图。

与[Spatialite (opens new window)](http://www.gaia-gis.it/spatialite/)、GeoJSON 和 Rasterlite 不同，MBTiles 不是原始数据存储。它是分块数据的存储，例如渲染的地图分块。

一个 MBTiles 文件代表一个单独的 tileset，可选地包括交互数据网格。多个 tilesets（图层，或其他术语中的地图）可以由多个 MBTiles 文件表示。

[MBTiles规范地址](https://github.com/mapbox/mbtiles-spec)