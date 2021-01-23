package org.xr.happy.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 删除当前文件夹，包括删除当前文件夹下的所有文件及及其文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        File[] files = file.listFiles();

        Arrays.stream(files).forEach(file1 -> {

            if (file1.isDirectory()) {
                deleteFile(file1);
            } else {
                file1.delete();
            }
        });

        file.delete();
    }


    /**
     * 过滤指定文件
     *
     * @param path   file path
     * @param prefix file prefix ,such as : exe、txt、pptx、doc ec;
     * @return
     */
    public static File[] fileFilter(String path, String prefix) {
        File file = new File(path);
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(prefix)) {
                    return true;
                }
                return false;
            }
        });

        logger.info("after filter file :{}" + Arrays.toString(files));

        return files;
    }

}
