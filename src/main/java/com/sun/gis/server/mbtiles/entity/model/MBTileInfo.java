package com.sun.gis.server.mbtiles.entity.model;

import lombok.Data;

import java.io.File;

@Data
public class MBTileInfo {
	public String mbName;
	public String fullPath;
	public File mbFile;


}
