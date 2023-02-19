package com.sun.gis.server.mbtiles.mbTileHandle;

import com.sun.gis.server.mbtiles.entity.model.MBTileInfo;
import com.sun.gis.server.mbtiles.entity.model.MbtilesObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 初始化读取指定目录文件
 */
@Component
public class MbListBean implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MbListBean.class);

    @Value("${MBData.path}")
    private String mbPath;

    private final List<MBTileInfo> allMBFiles = new ArrayList<MBTileInfo>();

    private final HashMap<String, MbtilesObject> _mbReaderHM = new HashMap<String, MbtilesObject>();

    public List<MBTileInfo> getAllMBFiles() {
        return allMBFiles;
    }

    public HashMap<String, MbtilesObject> getMBReaderHM() {
        return _mbReaderHM;
    }

    /**
     * 根据keyValue获取图层属性
     * @param keyValue 键值
     * @return MbtilesObject
     */
    public MbtilesObject getMBReader(String keyValue) {
        if (_mbReaderHM.containsKey(keyValue)) {
            return _mbReaderHM.get(keyValue);
        }
        return null;
    }

    public MetadataEntry getMetadata(MBTilesReader _mbReader) throws Exception {
        return _mbReader.getMetadata();
    }

    /**
     * 获取最新文件夹下图层数据
     * @throws Exception 异常
     */
    public void setMBTilesValue() throws Exception {
        _mbReaderHM.clear();
        this.allMBFiles.clear();
        this.readAllFile(mbPath);
        log.info("readFinish!");
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        this.readAllFile(mbPath);
    }

    /**
     * 读取目录下所有文件
     * @param filePath 文件目录
     * @throws Exception 异常
     */
    private void readAllFile(String filePath) throws Exception {
        File f = new File(filePath);
        File[] files = f.listFiles();
        assert files != null;
        // 遍历获取文件夹下所有文件，并写入信息
        for (File file : files) {
            if (file.isDirectory()) {
                readAllFile(file.getAbsolutePath());
            } else {
                String _fileName = file.getName();
                String suffix = _fileName.substring(_fileName.lastIndexOf("."));
                if (suffix.equals(".mbtiles")) {
                    MBTileInfo _mbInfo = new MBTileInfo();
                    _mbInfo.mbName = _fileName;
                    _mbInfo.fullPath = file.getAbsolutePath();
                    _mbInfo.mbFile = file;
                    // 如果map中不包含该文件信息，则写map中
                    if (!_mbReaderHM.containsKey(_fileName)) {
                        MBTilesReader _mbReader = new MBTilesReader(file);
                        String _imageFormat = _mbReader.getMetadata().getTileMimeType().toString();
                        if (_imageFormat.equals("")) {
                            _imageFormat = "png";
                        }
                        _mbReaderHM.put(_fileName, new MbtilesObject(_mbReader, _imageFormat));
                    }
                    allMBFiles.add(_mbInfo);
                }
            }
        }
    }

}
