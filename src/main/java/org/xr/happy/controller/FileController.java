package org.xr.happy.controller;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xr.happy.common.utils.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/zip_file")
    public void zipFile(HttpServletRequest request) {

        String realPath = request.getSession().getServletContext().getRealPath("/");

         File file = new File("test_file_01");
         if (!file.exists()){
             file.mkdirs();
         }


        FileUtils.deleteFile(file);
        logger.info("file-path :{}",file.getAbsolutePath());
        logger.info("realPath:{}", realPath);

     /*   try {
            zipFile1();
        } catch (ZipException e) {
            e.printStackTrace();
        }*/

    }

    private static void zipFile1() throws ZipException {
        logger.info("开始压缩文件");

        // 生成的压缩文件
        ZipFile zipFile = new ZipFile("filetest\\filetest.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword("111");
        // 要打包的文件夹
        File currentFile = new File("filetest");

        File[] fs = currentFile.listFiles();
        // 遍历test文件夹下所有的文件、文件夹
        for (File f : fs) {
            if (f.isDirectory()) {
                zipFile.addFolder(f.getPath(), parameters);
            } else {
                zipFile.addFile(f, parameters);
            }
        }
    }
}
