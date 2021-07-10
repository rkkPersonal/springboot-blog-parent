package org.xr.happy.poi;

import org.apache.poi.xslf.usermodel.*;

import java.io.FileOutputStream;

public class MyPPTTest {

    public static void main(String[] args) throws Exception {

        XMLSlideShow ppt = new XMLSlideShow();

        // 创建一个空的幻灯片
        XSLFSlide slide = ppt.createSlide();

        // 设置幻灯片的格式布局
        XSLFSlideLayout slideLayout = slide.getSlideLayout();
        XSLFSlideMaster slideMaster = slideLayout.getSlideMaster();
        slideMaster.getLayout(SlideLayout.TITLE);
        ppt.createSlide(slideLayout);


        XSLFTextBox textBox = slide.createTextBox();

        textBox.clearText();

        XSLFTextRun xslfTextRun = textBox.addNewTextParagraph().addNewTextRun();

        xslfTextRun.setText("hello ,this is a demo !!!!!");


        ppt.write(new FileOutputStream("mytest.pptx"));

        ppt.close();


    }
}
