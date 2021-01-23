            POI-HSLF和POI-XLSF-用于访问Microsoft Powerpoint格式文件的Java API
总览

一、POI-HSLF
HSLF是Powerpoint '97（-2007）文件格式的POI项目的纯Java实现。

HSLF提供了一种阅读，创建或修改PowerPoint演示文稿的方法。特别是，它提供：

用于数据提取的API（文本，图片，嵌入的对象，声音）
用于创建，读取和修改ppt文件的usermodel api


二、POI-XSLF
XSLF是PowerPoint 2007 OOXML（.xlsx）文件格式的POI项目的纯Java实现。虽然HSLF和XSLF提供类似的功能，但目前两者之间没有公共接口。

请注意，XSLF仍在早期开发中，将来可能会发生不兼容的更改


快速入门指南

基于文本提取

对于基本文本提取，请使用 org.apache.poi.sl.extractor.SlideShowExtractor。它接受可以通过org.apache.poi.sl.usermodel.SlideShowFactory从文件或流创建的幻灯片。该的getText（）方法可以用来从幻灯片的文本