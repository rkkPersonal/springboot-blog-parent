package org.xr.happy.file;/*
package org.xr.org.xr.happy.file;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.exception.NullArgumentException;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipTest {

    public static void main(String[] args) throws Exception {

*/
/*        File zipFile = new File("testfile.zip"); //压缩的文件名和地址

        ZipOutputStream out2 = new ZipOutputStream(new FileOutputStream(zipFile));//传入压缩文件路径，得到压缩流

        byte[] buffer = new byte[1024];


        File file = new File("filetest");

        File[] file1 = file.listFiles();

        for (int x = 0; x < file1.length; x++) {
            FileInputStream fis = new FileInputStream(file1[x]);
            out2.putNextEntry(new ZipEntry(file1[x].getName()));//将要压缩的文件放入压缩流
            int len;
            //读入需要下载的文件的内容，打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out2.write(buffer, 0, len);
            }
            out2.closeEntry();
            fis.close();
        }
        out2.close();*//*


//        zipFilesAndEncrypt("filetest","testfile.zip","123456");
        String sss = "五千年的风和雨啊创了多少梦\r\nahhwdpjfwjfpwjpf\r\n19eu923ur932ur03";
        String passwd = "111#";
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);           // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别
        parameters.setSourceExternalStream(true);
        parameters.setFileNameInZip("aaaa.txt");
        if (!"".equals(passwd)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipFile = new ZipFile("D:\\java压缩文件.zip");
            zipFile.addStream(new ByteArrayInputStream(sss.getBytes()), parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    */
/**
 * @param srcFileName 待压缩文件路径
 * @param zipFileName zip文件名
 * @param password    加密密码
 * @return
 * @throws Exception
 * @Title: zipFilesAndEncrypt
 * @Description: 将指定路径下的文件压缩至指定zip文件，并以指定密码加密 若密码为空，则不进行加密保护
 *//*

    public static void zipFilesAndEncrypt(String srcFileName, String zipFileName, String password) throws Exception {
        ZipOutputStream outputStream = null;
        InputStream inputStream = null;
        if (StringUtils.isEmpty(srcFileName) || StringUtils.isEmpty(zipFileName)) {
            throw new NullArgumentException("待压缩文件或者压缩文件名");
        }
        try {
            File srcFile = new File(srcFileName);
            File[] files = new File[0];
            if (srcFile.isDirectory()) {
                files = srcFile.listFiles();
            } else {
                files = new File[1];
                files[0] = srcFile;
            }
            outputStream = new ZipOutputStream(new FileOutputStream(new File(zipFileName)));
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            if (!StringUtils.isEmpty(password)) {
                parameters.setEncryptFiles(true);
//Zip4j supports AES or Standard Zip Encryption (also called ZipCrypto)
//If you choose to use Standard Zip Encryption, please have a look at example
//as some additional steps need to be done while using ZipOutputStreams with
//Standard Zip Encryption
                parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                parameters.setPassword(password);
            }

//Now we loop through each file and read this file with an inputstream
//and write it to the ZipOutputStream.
            int fileNums = files.length;
            for (int i = 0; i < fileNums; i++) {
                File file = (File) files[i];

//This will initiate ZipOutputStream to include the file
//with the input parameters
                outputStream.putNextEntry(file, parameters);

//If this file is a directory, then no further processing is required
//and we close the entry (Please note that we do not close the outputstream yet)
                if (file.isDirectory()) {
                    outputStream.closeEntry();
                    continue;
                }

                inputStream = new FileInputStream(file);
                byte[] readBuff = new byte[4096];
                int readLen = -1;
//Read the file content and write it to the OutputStream
                while ((readLen = inputStream.read(readBuff)) != -1) {
                    outputStream.write(readBuff, 0, readLen);
                }
//Once the content of the file is copied, this entry to the zip file
//needs to be closed. ZipOutputStream updates necessary header information
//for this file in this step
                outputStream.closeEntry();
                inputStream.close();
            }
//ZipOutputStream now writes zip header information to the zip file
            outputStream.finish();
        } catch (Exception e) {
            log.error("文件压缩出错", e);
            throw e;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("压缩文件输出错误", e);
                    throw e;
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("压缩文件错误", e);
                    throw e;
                }
            }
        }
    }

}
*/
