package org.xr.happy.mysql;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.Scanner;

// 导入数据 -- sql语句很长，把mysql的参数调整一下，不然限制了数据传输的字节大小
//  show global variables like 'max_allowed_packet';
//  set global max_allowed_packet=1024*1024*128;
public class OrderGenerate {
    public static void main(String[] args) throws Exception {
        System.out.println("输入你需要生成的数量：");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.close();

        File file = new File(System.getProperty("user.dir") + "/order_data_" + System.currentTimeMillis() + ".sql");
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        // 循环生成随机的N条信息 每天十万单左右
        //  Unix 时间戳从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数
        int days = 5000000 / 86400 + 1;
        long t = 86400 * (days); // 分散到天即可
        long time = System.currentTimeMillis() / 1000 - t;

        for (int x = 0; x < num / 86400; x++) {
            String head = "INSERT INTO `ecs_order_info` \n" +
                    "(`order_id`, `order_sn`, `user_id`, `order_status`, `shipping_status`, `pay_status`, \n" +
                    "`consignee`, `country`, `province`, `city`, `district`, `address`, `zipcode`, `tel`, \n" +
                    "`mobile`, `email`, `best_time`, `sign_building`, `postscript`, `shipping_id`, \n" +
                    "`shipping_name`, `pay_id`, `pay_name`,  `inv_payee`, `inv_content`, `goods_amount`, `shipping_fee`, \n" +
                    "`insure_fee`, `pay_fee`, `money_paid`, `surplus`, `integral`, \n" +
                    "`integral_money`, `bonus`, `order_amount`, `add_time`, `confirm_time`, \n" +
                    "`pay_time`, `shipping_time`, `bonus_id`, `invoice_no`, `discount`) \n" +
                    "VALUES ";
            fileOutputStream.write(head.getBytes());


            for (long i = 0; i < 86400; i++) {
                time = time + 1;
                String data = "";
                if (i != 0) {
                    data += ",\n";
                }
                data += "(NULL, ";
                data += "'" + System.currentTimeMillis() + i + x + "' ,"; // order_sn 时间戳加序号
                data += new Random().nextInt(2000000) + " ,"; // user_id
                data += new Random().nextInt(4) + " ,"; // order_status
                data += new Random().nextInt(3) + " ,"; // shipping_status
                data += new Random().nextInt(2) + " ,"; // pay_status
                data += "'edu_" + System.currentTimeMillis() + i + "' ,"; // consignee
                data += new Random().nextInt(4043) + " ,"; // country
                data += new Random().nextInt(4043) + " ,"; // province
                data += new Random().nextInt(4043) + " ,"; // city
                data += new Random().nextInt(4043) + " ,"; // district
                data += "'东方红太阳升我在北京天安门' ,"; // address
                data += new Random().nextInt(999999) + " ,"; // zipcode
                data += "'13800138000' ,"; // tel
                data += "'13800138000' ,"; // mobile
                data += "'nothing_" + i + "@vip.com' ,"; // email
                data += "'上午配送' ,"; // best_time
                data += "'一号大厦' ,"; // sign_building
                data += "'给我送的东西要私密包装' ,"; // postscript
                data += new Random().nextInt(9) + " ,"; // shipping_id
                data += "'韵达快递' ,"; // shipping_name
                data += new Random().nextInt(9) + " ,"; // pay_id
                data += "'货到付款' ,"; // pay_name
                data += " '天下第一公司' ,"; // inv_payee
                data += "'个人护理用品' ,"; // inv_content
                data += new Random().nextInt(9999) + " ,"; // goods_amount
                data += new Random().nextInt(99) + " ,"; // shipping_fee
                data += new Random().nextInt(99) + " ,"; // insure_fee
                data += new Random().nextInt(99) + " ,"; // pay_fee
                data += new Random().nextInt(9999) + " ,"; // money_paid
                data += new Random().nextInt(9) + " ,"; // surplus
                data += new Random().nextInt(9999) + " ,"; // integral
                data += new Random().nextInt(99) + " ,"; // integral_money
                data += new Random().nextInt(999) + " ,"; // bonus
                data += new Random().nextInt(9999) + " ,"; // order_amount
                data += time + " ,"; // add_time
                data += time + " ,"; // confirm_time
                data += time + " ,"; // pay_time
                data += time + " ,"; // shipping_time
                data += new Random().nextInt(9999) + " ,"; // bonus_id
                data += "'m" + new Random().nextInt(999999999) + "' ,"; // invoice_no
                data += new Random().nextInt(9999) + " )"; // discount
                fileOutputStream.write(data.getBytes());
            }
            fileOutputStream.write(";".getBytes());
            fileOutputStream.flush();
        }

        System.out.println("订单数据已生成：" + file.getPath());
    }
}
