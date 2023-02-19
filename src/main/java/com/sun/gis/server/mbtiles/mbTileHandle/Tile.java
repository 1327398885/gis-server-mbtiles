package com.sun.gis.server.mbtiles.mbTileHandle;

import java.io.InputStream;

public class Tile {
    private final int zoom;
    private final int column;
    private final int row;
    private final InputStream data;

    public Tile(int zoom, int column, int row, InputStream tile_data) {
        this.zoom = zoom;
        this.column = column;
        this.row = row;
        this.data = tile_data;
    }

    public InputStream getData() {
        return data;
    }

    public int getZoom() {
        return zoom;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}