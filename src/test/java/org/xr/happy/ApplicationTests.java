package org.xr.happy;


import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xslf.usermodel.XSLFSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.elasticsearch.monitor.jvm.JvmStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
     private RestTemplate restTemplate;

    private static final int CURRENT_COUNTS=300;
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
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            list.add(i + "");
        }

        List<List<String>> lists = splitList(list, 3);

        System.out.println(lists);
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
