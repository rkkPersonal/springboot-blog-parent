package org.xr.happy.file;

import java.io.*;

public class FileCopyTest {

    public static void main(String[] args) {

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader("study.txt"));
            writer = new BufferedWriter(new FileWriter("scu.txt"));

            String msg = null;
            while ((msg = reader.readLine()) != null) {
                writer.write(msg);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
