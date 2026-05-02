package org.RobustFramework.Utilities;

import java.io.*;
import java.util.*;

public class ConfigReader {

    //Cannot be accessed directly outside class
    private static Properties properties;

    //Loads static only once
    static {
        try {
            //InputStream used instead of FileInputStream
            //Works in local but fails in Maven / CI / JAR
            //ClassLoader is environment independent
            InputStream is = ConfigReader.class
                    .getClassLoader() //finds files from resources folder
                    .getResourceAsStream("config.properties"); //loads file as stream

            if (is == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties = new Properties();
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public static String getBrowser(){
            return properties.getProperty("browser");
        }

    public static boolean isHeadless() {
        String value = properties.getProperty("headless");
        return value != null && value.equalsIgnoreCase("true");
    }
    }


