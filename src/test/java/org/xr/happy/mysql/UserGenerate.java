package org.xr.happy.mysql;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.Scanner;

// 导入数据 -- sql语句很长，把mysql的参数调整一下，不然限制了数据传输的字节大小
//  show global variables like 'max_allowed_packet';
//  set global max_allowed_packet=1024*1024*128;
public class UserGenerate {
    public static void main(String[] args) throws Exception {
        System.out.println("输入你需要生成的数量：");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.close();

        File file = new File(System.getProperty("user.dir") + "/user_data_" + System.currentTimeMillis() + ".sql");
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        // 循环生成随机的N条信息
        for (int x = 0; x < num / 10_0000; x++) {
            String head = "INSERT INTO `ecs_users` \n" +
                    "(`user_id`, `email`, `user_name`, `password`, `question`, `answer`, `sex`, " +
                    "`user_money`, `frozen_money`, `pay_points`, `rank_points`,`reg_time`,`last_ip`,`alias`,`salt`) " +
                    "VALUES ";
            fileOutputStream.write(head.getBytes());
            for (int i = 0; i < 10_0000; i++) {
                String data = "";
                if (i != 0) {
                    data += ",\n";
                }
                data += "(NULL, ";
                data += "'nothing_" + i + "@vip.com' ,"; // email
                data += "'edu_" + System.currentTimeMillis() + i + x + "' ,"; // user_name
                data += " 'ilovetony',"; // password
                data += "'Whosyourdaddy？',"; // question
                data += "'33333' ,"; // answer
                data += new Random().nextInt(1) + " ,"; // sex 随机
                data += new Random().nextInt(10000) + " ,"; // user_money
                data += new Random().nextInt(10000) + " ,"; // frozen_money
                data += new Random().nextInt(10000) + " ,"; // pay_points
                data += new Random().nextInt(10000) + " ,";// rank_points
                // 生成一个随机时间 30天内的 -- 数据库用的是int
                //  Unix 时间戳从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数
                long t = 1000 * 60 * 60 * 24 * new Random().nextInt(30); // 分散到天即可
                long time = (System.currentTimeMillis() - t) / 1000;
                data += time + " ,"; // reg_time
                data += "'0.0.0.0' ,";// last_ip
                data += "'baby' ,";// alias
                data += new Random().nextInt(1024) + ")"; // salt分库分表标记ID
                fileOutputStream.write(data.getBytes());
            }
            fileOutputStream.write(";".getBytes());
            fileOutputStream.flush();
        }

        System.out.println("用户数据已生成：" + file.getPath());
    }
}
