package com.sun.gis.server.mbtiles.mbTileHandle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用于提取图块的流式迭代器
 * A streaming iterator to extract tiles
 */
public class TileIterator {

    //定义记录集
    private final ResultSet rs;

    //构造函数初始化赋值
    public TileIterator(ResultSet s) {
        rs = s;
    }

    /**
     * 是否有下一条记录
     * @return boolean
     */
    public boolean hasNext() {
        try {
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * 获取下一条记录
     * @return Tile
     * @throws Exception 异常
     */
    public Tile next() throws Exception {
        try {
            int zoom = rs.getInt("zoom_level");
            int column = rs.getInt("tile_column");
            int row = rs.getInt("tile_row");
            InputStream tile_data;
            if (rs.getBytes(4) != null) {
                tile_data = new ByteArrayInputStream(rs.getBytes(4));
            } else {
                tile_data = new ByteArrayInputStream(new byte[]{});
            }
            return new Tile(zoom, column, row, tile_data);
        } catch (SQLException e) {
            throw new Exception("Read next tile", e);
        }
    }

    public void close() {
        try {
            rs.close();
        } catch (SQLException ignored) {
        }
    }
}

