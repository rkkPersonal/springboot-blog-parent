package org.xr.happy.file;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

public class ZipFiles {

    private static void zipFile() throws ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile("filetest.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        // 要打包的文件夹
        File currentFile = new File("filetest\\test");
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

    public static void addFile() {
        try {
            ZipFile zipFile = new ZipFile("filetest.zip");
            ArrayList<File> addFiles = new ArrayList<>();

            File currentFile = new File("filetest\\test");
            File[] fs = currentFile.listFiles();
            // 遍历test文件夹下所有的文件、文件夹
            for (File f : fs) {
                if (f.isDirectory()) {
                } else {
                    addFiles.add(f);
                }
            }

            String password = "123456";
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(password.toCharArray());
            // 目标路径
            parameters.setRootFolderInZip("filetest\\test1");
            zipFile.addFiles(addFiles, parameters);
            // 可以添加单个文件
//            zipFile.addFile(new File("D:\\addFile2.txt"),parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    private static void zipFile1() throws ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile("filetest1.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles( true );
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword("111");
        // 要打包的文件夹
        File currentFile = new File("tt");
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


    public static void main(String[] args) throws ZipException {
//        zipFile();
        addFile();

//        zipFile1();
    }

}