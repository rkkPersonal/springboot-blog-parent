package org.xr.happy.common.spi;

public class XmlSerialization implements Serialization{
    @Override
    public void deSerial() {
        System.out.println("xml serialization");
    }
}
