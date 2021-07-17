package org.xr.happy.common.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {


    public static void main(String[] args) {

        ServiceLoader<Serialization> load = ServiceLoader.load(Serialization.class);
        Iterator<Serialization> iterator = load.iterator();

        while (iterator.hasNext()) {

            Serialization next = iterator.next();
            next.deSerial();
        }
    }
}
