package org.xr.happy.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class PropertiesParser {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesParser.class);

    static Properties properties = new Properties();
    static List<String> queueList = new LinkedList<>();

    public static void main(String[] args) {

        String property = System.getProperty("user.dir");
        System.out.println(property);
    }

    static {

        InputStream resourceAsStream = PropertiesParser.class.getClassLoader().getResourceAsStream("rabbitmq.yml");


        try {
            properties.load(resourceAsStream);
            registerMqQueue();
        } catch (IOException e) {

        }
    }

    public static void registerMqQueue() {

        Enumeration<?> enumeration = properties.propertyNames();

        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement().toString();
            if (StringUtils.isNotBlank(string)){

                if (string.contains("-")){
                    queueList.add(string.substring(string.indexOf("-")+1, string.length()));

                }
            }
        }
        logger.info("queueList :{}", queueList);
    }


}
