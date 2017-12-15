package utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PropertyReader {
    private static String PROPERTIES_FILE = ".properties";
    private static java.util.Properties properties;

    public static java.util.Properties readProperties() {
        if (properties == null) {
            java.util.Properties prop = new java.util.Properties();
            InputStream input;
            try {
                input = new FileInputStream(PROPERTIES_FILE);
                prop.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            properties = prop;
        }
        return properties;
    }

}
