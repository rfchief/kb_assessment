package com.kakaobank.profile.consumer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class ConsumerUtil {

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

    public static int getRandomNumber(int maxBound) {
        return ThreadLocalRandom.current().nextInt(maxBound);
    }
}
