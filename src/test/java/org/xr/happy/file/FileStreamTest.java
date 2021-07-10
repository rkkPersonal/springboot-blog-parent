package org.xr.happy.file;


import java.io.*;

public class FileStreamTest {

    public static void main(String[] args) {


        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {


            bufferedInputStream = new BufferedInputStream(new FileInputStream("study.txt"));
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("study1.txt"));

            int data;
            byte[] buf = new byte[bufferedInputStream.available()];
            while ((data = bufferedInputStream.read(buf)) > -1) {
                bufferedOutputStream.write(buf);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
