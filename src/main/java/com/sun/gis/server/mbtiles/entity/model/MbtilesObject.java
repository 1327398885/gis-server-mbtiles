package com.sun.gis.server.mbtiles.entity.model;


import com.sun.gis.server.mbtiles.mbTileHandle.MBTilesReader;

public class MbtilesObject {

	private MBTilesReader _mBTileReader;

	private String _imageFormat;


	public MbtilesObject(MBTilesReader reader, String format) {
		this._mBTileReader = reader;
		this._imageFormat = format;
	}

	public MBTilesReader get_mBTileReader() {
		return _mBTileReader;
	}

	public void set_mBTileReader(MBTilesReader _mBTileReader) {
		this._mBTileReader = _mBTileReader;
	}

	public String get_imageFormat() {
		return _imageFormat;
	}

	public void set_imageFormat(String _imageFormat) {
		this._imageFormat = _imageFormat;
	}

}
