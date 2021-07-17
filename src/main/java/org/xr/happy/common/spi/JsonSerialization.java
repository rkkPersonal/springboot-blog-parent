package org.xr.happy.common.spi;

public class JsonSerialization implements Serialization{
    @Override
    public void deSerial() {
        System.out.println("json serialization");
    }
}
