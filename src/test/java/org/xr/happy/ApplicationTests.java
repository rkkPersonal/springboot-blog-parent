package org.xr.happy;


import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xslf.usermodel.XSLFSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.xr.happy.common.bean.CsvUser;
import org.xr.happy.config.RedisLock;
import org.xr.happy.service.UserService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(classes = Application.class)
@RunWith(value = SpringRunner.class)
class ApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;


    private static int CURRENT_COUNTS = 200;
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static ExecutorService executorService = Executors.newFixedThreadPool(CURRENT_COUNTS);

    @Test
    public void testTransactionMq() {
        rabbitTemplate.convertAndSend("xr-blog-love", "1");
    }

    @Test
    @Ignore
    public void testRestTemplate() {

   /*     String url = "http://localhost:8080/sender";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

        System.out.println(forEntity.getStatusCodeValue());*/


        /*ResponseEntity<String> url1 = restTemplate.exchange("url", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class, new HashMap<>());*/

        // 用来处理返回的流文件。
        List<CsvUser> execute = restTemplate.execute("http://localhost:8080/file/download", HttpMethod.GET, request -> {

            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", "Basic 271857fe-1acb-4b87-aacb-3056e088b4f3");

        }, response -> {
            int value = response.getStatusCode().value();
            System.out.println("value = " + value);
            InputStream responseBodyStream = response.getBody();
            List<CsvUser> csvUserList = new CsvToBeanBuilder<CsvUser>(new InputStreamReader(responseBodyStream, "UTF-8"))
                    .withType(CsvUser.class)
                    .build().parse();

            // 是否写入磁盘,只是为了演示
            boolean writeFlag = false;
            if (writeFlag) {
                writeToLocalDisk(responseBodyStream);
            }
            return csvUserList;
        });

        System.out.println(execute);
    }

    private void writeToLocalDisk(InputStream responseBodyStream) throws IOException {
        byte[] buffer = new byte[2048];
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("test.csv")));
        while (responseBodyStream.read(buffer) != -1) {
            bufferedOutputStream.write(buffer);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }


    @Test
    public void test2() {

        userService.createUser(206);
    }

    @Test
    public void testTransaction() {

        ExecutorService submit = Executors.newFixedThreadPool(20);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(20);
        for (int i = 0; i < 20; i++) {

            int c = i;
            submit.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        cyclicBarrier.await();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    userService.createUser(c);
                }
            });

        }


        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void bachSendMq() {

        String key = "ORDER_KEY_12345678";
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>());

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                RedisLock redisLock = new RedisLock(redisTemplate);
                boolean lock = redisLock.lock(key);

                try {

                    if (lock) {
                        try {
                            Thread.sleep(20000);
                            System.out.println(Thread.currentThread().getName() + "-加锁成功");
                            countDownLatch.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // todo

                    }
                } finally {
                    redisLock.unLock(key);
                    System.out.println(Thread.currentThread().getName() + "-解锁");
                }


            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoads() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }

        List<List<String>> lists = splitList(list, 3);

        System.out.println(lists.size());

    }

    private static List<List<String>> splitList(List<String> list, int length) {


        int listSize = list.size() / length;

        int fromIndex = 0;
        int toIndex = length;
        List<List<String>> finalList = new ArrayList<>();
        for (int i = 0; i <= listSize; i++) {
            if (toIndex > list.size())
                toIndex = list.size();
            List<String> subList = list.subList(fromIndex, toIndex);
            finalList.add(subList);
            fromIndex = toIndex;

            toIndex = toIndex + length;
        }

        return finalList;


    }

    public static void main(String[] args) throws Exception {

/*
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("");

         User bean = context.getBean("com.pack", User.class);*/

    }


    private static void testEmbandedFile() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\Steven\\Desktop\\My.xlsx");
        for (PackagePart pPart : workbook.getAllEmbeddedParts()) {
            String contentType = pPart.getContentType();
            // Excel Workbook - either binary or OpenXML
            if (contentType.equals("application/vnd.ms-excel")) {
                HSSFWorkbook embeddedWorkbook = new HSSFWorkbook(pPart.getInputStream());
            }
            // Excel Workbook - OpenXML file format
            else if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                OPCPackage docPackage = OPCPackage.open(pPart.getInputStream());
                XSSFWorkbook embeddedWorkbook = new XSSFWorkbook(docPackage);
            }
            // Word Document - binary (OLE2CDF) file format
            else if (contentType.equals("application/msword")) {
                HWPFDocument document = new HWPFDocument(pPart.getInputStream());
            }
            // Word Document - OpenXML file format
            else if (contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                OPCPackage docPackage = OPCPackage.open(pPart.getInputStream());
                XWPFDocument document = new XWPFDocument(docPackage);
            }
            // PowerPoint Document - binary file format
            else if (contentType.equals("application/vnd.ms-powerpoint")) {
                HSLFSlideShow slideShow = new HSLFSlideShow(pPart.getInputStream());
            }
            // PowerPoint Document - OpenXML file format
            else if (contentType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
                OPCPackage docPackage = OPCPackage.open(pPart.getInputStream());
                XSLFSlideShow slideShow = new XSLFSlideShow(docPackage);
            }
            // Any other type of embedded object.
            else {
                System.out.println("Unknown Embedded Document: " + contentType);
                InputStream inputStream = pPart.getInputStream();
            }
        }
    }
}
