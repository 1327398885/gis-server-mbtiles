# gis-server-mbtiles
一个基于Java实现的mbtiles数据发布管后台理服务

## 什么是MBTiles

MBTiles 是一种用于在 [SQLite (opens new window)](http://sqlite.org/)数据库中存储任意切片地图数据以供立即使用和高效传输的规范。

MBTiles 是一个紧凑的、限制性的规范。它仅支持分块数据，包括矢量或图像分块和交互网格分块。仅支持 Spherical Mercator 投影进行展示（平铺显示），并且仅支持经纬度坐标用于边界和中心等元数据。

这是最低规范，仅指定必须检索数据的方式。因此 MBTiles 文件可以在内部压缩和优化数据，并构建符合 MBTiles 规范的视图。

与[Spatialite (opens new window)](http://www.gaia-gis.it/spatialite/)、GeoJSON 和 Rasterlite 不同，MBTiles 不是原始数据存储。它是分块数据的存储，例如渲染的地图分块。

一个 MBTiles 文件代表一个单独的 tileset，可选地包括交互数据网格。多个 tilesets（图层，或其他术语中的地图）可以由多个 MBTiles 文件表示。

[MBTiles规范地址](https://github.com/mapbox/mbtiles-spec)

### 矢量瓦片

矢量切片可以以三种形式呈现：GeoJSON、TopoJSON 和 MapBox Vector Tile(.mvt)，矢量切片技术继承了矢量数据和切片地图的双重优势，有以下优点：

- 瓦片以mvt格式的存储，是以每一个瓦片号为基准进行存储的。大小都是256*256；粒度更小，信息接近无损；前端可根据数据定制渲染样式；数据更新快，更灵活；
- .pbf（mvt） 压缩率更高，体积更小；

如何制作矢量瓦片可以参考我的这篇文章：[Tippecanoe安装使用](https://blog.csdn.net/qq_36213352/article/details/128365505?spm=1001.2014.3001.5501)

### 栅格瓦片

每一块都是图片，可以是.png，也可以是.jpg。常见的大小有256 * 256，512 * 512。通过分层分块生成切片文件的思路进行缓存构建。

如何制作栅格瓦片可以参考我的这篇文章：[mbtiles-影像切片](https://blog.csdn.net/qq_36213352/article/details/128435797?spm=1001.2014.3001.5501)