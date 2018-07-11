package com.kakaobank.profile.producer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProducerUtil {

    public static Properties getProperties(String propertyFilePath) {
        InputStream input = null;
        try {
            input = new FileInputStream(propertyFilePath);
            Properties properties = new Properties();
            properties.load(input);

            return properties;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
