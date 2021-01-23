package org.xr.happy;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.xr.happy.atomic.StevenUnsafe;
import org.xr.happy.model.User;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@SpringBootTest(classes = Application.class)
public class ThreadTest {

    private static final Logger logger = LoggerFactory.getLogger(ThreadTest.class);

    @Test
    public void testTheadReadWrite() throws Exception {


    }

    private static Map<String, String> put(String key, String value, int time) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.schedule(() -> {
            map.remove(key);
            logger.info(key+" "+"is"+ " "+"expired");
        }, time, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.shutdown();
        return map;
    }

    public static void main(String[] args) throws Exception {
/*
        testClassLoader();
        testReadAndWriteSync();*/

      /*  List<User> users  =new ArrayList<>();
        while (true){
            users.add(new User());
        }*/

         StevenUnsafe stevenUnsafe = new StevenUnsafe();


        for (int i = 0; i < 6; i++) {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        stevenUnsafe.add();
                    }

                }
            }).start();
            System.out.println("done");
        }


        Thread.sleep(5000L);

//        System.out.println(stevenUnsafe.i);

    }

    private static void testReadAndWriteSync() throws IOException {
        LinkedList<String> list = new LinkedList<>();


        Scanner scanner = new Scanner(System.in);
        logger.info("请输入你想说的话");
        PrintWriter writer = new PrintWriter(new FileWriter("work.txt"));

        Thread writeThread = new Thread(() -> {

            while (true) {

                try {

                    if (list.size() <= 0) {
                        LockSupport.park();
                    }
                    logger.info("休息一会儿");

                    if (list.size() > 0) {

                        String s = list.removeFirst();
                        writer.println(s);
                        writer.flush();
                    }
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread readThread = new Thread(() -> {
            while (true) {
                String s = scanner.nextLine();

                if ("quit".equals(s)) {
                    break;
                }
                list.add(s);
                LockSupport.unpark(writeThread);
            }
        });

        writeThread.setDaemon(true);
        writeThread.start();
        readThread.start();
    }

    private static void testClassLoader() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InterruptedException {
        URL url = new URL("file:E:\\");

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        while(true){

            if (urlClassLoader==null)break;
            Class<?> helloWord = urlClassLoader.loadClass("HelloWord");


            System.out.println("HelloWord所使用的类加载器是："+helloWord.getClassLoader());

            Object o = helloWord.newInstance();

            Object test = helloWord.getMethod("test").invoke(o);
            System.out.println("调用getValue()获得的返回值："+test);
            Thread.sleep(2000L);

            o=null;

            urlClassLoader=null;
        }

        System.gc();

        Thread.sleep(10000);
    }

}
