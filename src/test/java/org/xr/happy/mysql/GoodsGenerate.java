package org.xr.happy.mysql;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.Scanner;

// 导入数据 -- sql语句很长，把mysql的参数调整一下，不然限制了数据传输的字节大小
//  show global variables like 'max_allowed_packet';
//  set global max_allowed_packet=1024*1024*128;
public class GoodsGenerate {
    public static void main(String[] args) throws Exception {
        System.out.println("输入你需要生成的数量：");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.close();

        File file = new File(System.getProperty("user.dir") + "/goods_data_" + System.currentTimeMillis() + ".sql");
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        // 循环生成随机的N用户信息
        for (int x = 0; x < num / 10_0000; x++) {
            String head = "INSERT INTO `ecs_goods` \n" +
                    "(`goods_id`, `cat_id`, `goods_sn`, `goods_name`, `goods_name_style`, \n" +
                    "`brand_id`, `provider_name`, `goods_number`, `goods_weight`, `market_price`, \n" +
                    "`shop_price`, `promote_price`, `warn_number`, \n" +
                    "`keywords`, `goods_brief`, `goods_desc`, `goods_thumb`, `goods_img`, `original_img`,`seller_note`) \n" +
                    "VALUES ";
            fileOutputStream.write(head.getBytes());
            for (int i = 0; i < 10_0000; i++) {
                String data = "";
                if (i != 0) {
                    data += ",\n";
                }
                data += "(NULL, ";
                data += new Random().nextInt(999) + " ,"; // cat_id
                data += "'" + System.currentTimeMillis() + i + x + "' ,"; // goods_sn 时间戳加序号
                data += "'" + new Random().nextInt(999999) + "这是一个随便的名称' ,"; // goods_name
                data += "'#ff00ff+strong' ,"; // goods_name_style
                data += new Random().nextInt(99) + " ,"; // brand_id
                data += "'供货商' ,"; // provider_name
                data += new Random().nextInt(999) + " ,"; // goods_number
                data += new Random().nextInt(59) + " ,"; // goods_weight
                data += new Random().nextInt(9999) + " ,"; // market_price
                data += new Random().nextInt(8888) + " ,"; // shop_price
                data += new Random().nextInt(7777) + " ,"; // promote_price
                data += new Random().nextInt(55) + " ,"; // warn_number
                data += "'商品搜索关键字' ,"; // keywords
                data += "'这件商品你必须要买' ,"; // goods_brief
                data += "'这里好那里好你必须买' ,"; // goods_desc
                data += "'xx9999.png' ,"; // goods_thumb
                data += "'xxl9999.png' ,"; // goods_img
                data += "'yyyy99999.png' ,"; // original_img
                data += "'后台备注仅商家可见'  )"; // seller_note
                fileOutputStream.write(data.getBytes());
            }
            fileOutputStream.write(";".getBytes());
            fileOutputStream.flush();
        }

        System.out.println("商品数据已生成：" + file.getPath());
    }
}
