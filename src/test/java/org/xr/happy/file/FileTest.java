package org.xr.happy.file;


import org.springframework.boot.test.context.SpringBootTest;
import org.xr.happy.Application;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

@SpringBootTest(classes = Application.class)
public class FileTest {

    public static void main(String[] args) throws Exception {

        File first = new File("E:\\my_demo_test");
        File file = new File("E:\\my_demo_test\\test");
        File file2 = new File("E:my_demo_test\\test");
        File file3 = new File(first, "test");
        File file4 = new File(file, "test.txt");

        File file5 = new File("test");

        if (!file5.exists()) {
            file5.mkdirs();
            System.out.println(file.getName() + "创建成功");
        }

        if (file3.isDirectory()) {

            if (!file4.exists()) {
                file4.createNewFile();
                System.out.println("文件创建成功");

            }
        }

        System.out.println("getAbsolutePath " + file5.getAbsolutePath());
        System.out.println("getCanonicalPath " + file5.getCanonicalPath());
        System.out.println("getAbsoluteFile " + file5.getAbsoluteFile());
        System.out.println("getCanonicalFile " + file5.getCanonicalFile());
        System.out.println("getParent " + file5.getParent());
        System.out.println("getParentFile " + file5.getParentFile());
        System.out.println("getName " + file5.getName());
        System.out.println("getPath " + file5.getPath());

  /*      File file = new File("E:\\vpn");
        deleteFile(file);*/

        /*testFileFilter();*/


    }

    private static void testFileFilter() {
        File file = new File("E:\\vpn\\letsvpn");

        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".exe")) {
                    return true;
                }
                return false;
            }
        });


        System.out.println(Arrays.toString(files));
    }

    /**
     * 删除当前文件夹，包括删除当前文件夹下的所有文件及及其文件夹
     *
     * @param file
     */
    private static void deleteFile(File file) {
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

}
