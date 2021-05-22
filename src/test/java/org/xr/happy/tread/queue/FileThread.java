package org.xr.happy.tread.queue;


import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileThread {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {


        File file = new File("E:\\my_demo_test\\picture");
        File[] files = file.listFiles();
        CountDownLatch countDownLatch = new CountDownLatch(files.length);

        for (File file1 : files) {

            FileInputStream fileInputStream = new FileInputStream(file1);

            String path = "E:\\my_demo_test\\filetest";
            FileOperatorThread fileOperatorThread = new FileOperatorThread(countDownLatch, path, fileInputStream, file1.getName());

            new Thread(fileOperatorThread).start();

            System.out.println("---");
        }

        countDownLatch.await();
    }


}

class FileOperatorThread implements Runnable {


    private CountDownLatch count;

    private FileInputStream inputStream;

    private String path;

    private String fileName = generatorFileName();

    private String generatorFileName() {
        String s = RandomStringUtils.randomNumeric(5);
        return s;
    }

    public FileOperatorThread(CountDownLatch count, String path, FileInputStream inputStream, String fileName) {
        this.count = count;
        this.path = path;
        this.inputStream = inputStream;
        if (StringUtils.isNotBlank(fileName)) {
            this.fileName = fileName;
        }
    }

    public FileOperatorThread(CountDownLatch count, String path, FileInputStream inputStream) {
        this(count, path, inputStream, null);
    }

    @SneakyThrows
    @Override
    public void run() {
        count.countDown();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        byte[] bytes = new byte[1024];
        while (bufferedInputStream.read(bytes) > -1) {
            File file = new File(path + "\\" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        }
        System.out.println("开始写入文件");
    }
}

class FastTest {
    public static void main(String[] args) throws Exception {

        ExecutorService readPool = Executors.newFixedThreadPool(10);
        ExecutorService writePool = Executors.newFixedThreadPool(10);

        File file = new File("E:\\my_demo_test\\picture");


        File[] files = file.listFiles();
        String path = "E:\\my_demo_test\\filetest";
        for (File file1 : files) {
            new FastCopyFile(readPool, writePool, new FileInputStream(file1), path, file1.getName()).executor();
        }

        System.out.println("完成");
    }
}


class FastCopyFile {

    private ExecutorService readPool;

    private ExecutorService writePool;
    private FileInputStream inputStream;

    private String path;

    private String fileName = generatorFileName();

    FastCopyFile() {

    }

    private String generatorFileName() {
        String s = RandomStringUtils.randomNumeric(5);
        return s;
    }

    public FastCopyFile(ExecutorService readPool, ExecutorService writePool, FileInputStream fileInputStream, String path, String fileName) {
        this.readPool = readPool;
        this.writePool = writePool;
        this.path = path;
        inputStream = fileInputStream;
        if (StringUtils.isNotBlank(fileName)) {
            this.fileName = fileName;
        }
    }


    public void executor() throws Exception {

        readPool.submit(() -> {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);


                byte[] bytes = new byte[bufferedInputStream.available()];
                while (true) {

                    if (!(bufferedInputStream.read(bytes) > -1)) {
                        System.out.println(Thread.currentThread().getName() + "读取完成");
                        break;
                    }


                    writePool.submit(() -> {
                        try {

                            File file = new File(path + "\\" + fileName);
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                            bufferedOutputStream.write(bytes);
                            bufferedOutputStream.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });

                }
            } catch (Exception e) {
                System.err.println("error :" + e);
            }

        });


    }


}