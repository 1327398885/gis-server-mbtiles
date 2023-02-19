package com.sun.gis.server.mbtiles.mbTileHandle;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MBTiles本质就是SQLLite
 * 这里是读取操作SQLLite的方法
 */
public class MBTilesReader {

	private final File f;
	private final Connection connection;

	/**
	 * 构造函数进行初始化
	 * @param f 文件对象
	 * @throws Exception 异常
	 */
	public MBTilesReader(File f) throws Exception {
		try {
			connection = SQLiteHelper.establishConnection(f);
		} catch (Exception e) {
			throw new Exception("Establish Connection to " + f.getAbsolutePath() + " failed", e);
		}
		this.f = f;
	}

	/**
	 * 关闭SQLLite
	 * @return 文件对象
	 */
	public File close() {
		try {
			connection.close();
		} catch (SQLException ignored) {
		}
		return f;
	}

	/**
	 * 获取MBTiles元数据
	 * metadata表保存着MBTiles的图层元数据
	 * @return MetadataEntry
	 * @throws Exception 异常
	 */
	public MetadataEntry getMetadata() throws Exception {
		String sql = "SELECT * from metadata;";
		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			MetadataEntry ent = new MetadataEntry();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String value = resultSet.getString("value");
				ent.addKeyValue(name, value);
			}
			return ent;
		} catch (Exception e) {
			throw new Exception("Get Metadata failed", e);
		}
	}

	/**
	 * 获取所有的tile数据
	 * @return  TileIterator
	 * @throws Exception 异常
	 */
	public TileIterator getTiles() throws Exception {
		String sql = "SELECT * from tiles;";
		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			return new TileIterator(resultSet);
		} catch (Exception e) {
			throw new Exception("Access Tiles failed", e);
		}
	}

	/**
	 * 获取指定zxy的图片数据
	 * @param zoom 层级
	 * @param column y
	 * @param row x
	 * @return Tile
	 * @throws Exception 异常
	 */
	public Tile getTile(int zoom, int column, int row) throws Exception {
		String sql = String.format(
				"SELECT tile_data FROM tiles WHERE zoom_level = %d AND tile_column = %d AND tile_row = %d", zoom,
				column, row);

		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			// 如果存在数据
			if (resultSet.next()) {
				InputStream tileDataInputStream = null;
				tileDataInputStream = resultSet.getBinaryStream("tile_data");

				return new Tile(zoom, column, row, tileDataInputStream);
			} else {
				return new Tile(zoom, column, row, null);
			}
		} catch (Exception e) {
			throw new Exception(String.format("Could not get Tile for z:%d, column:%d, row:%d", zoom, column, row), e);
		}
	}

	/**
	 * 获取最大层级
	 * @return 最大层级
	 * @throws Exception 异常
	 */
	public int getMaxZoom() throws Exception {
		String sql = "SELECT MAX(zoom_level) FROM tiles";

		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			return resultSet.getInt(1);
		} catch (Exception e) {
			throw new Exception("Could not get max zoom", e);
		}
	}

	/**
	 * 获取最小层级
	 * @return 最小层级
	 * @throws Exception 异常
	 */
	public int getMinZoom() throws Exception {
		String sql = "SELECT MIN(zoom_level) FROM tiles";

		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			return resultSet.getInt(1);
		} catch (Exception e) {
			throw new Exception("Could not get min zoom", e);
		}
	}
}
