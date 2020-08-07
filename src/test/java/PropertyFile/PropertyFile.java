package PropertyFile;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFile 
{
	public static Properties prop = new Properties();
    public static String filepath;
    static {
        try {
            System.out.println("FILE PATH "  + filepath);
            if (filepath == null) {
                filepath = System.getProperty("user.dir")+"//PropertyFiles//datafile.properties";                           
                System.out.println("FILE PATH 2"  + filepath);
                try{
                   // Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
                    prop.load(new FileInputStream(filepath));
                } catch (Exception E) {
                    System.out.println("Error occured "+E);
                }
            } else {
                System.out.println(filepath);
                prop.load(new FileInputStream(filepath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getValue(String key) 
    {
        return prop.getProperty(key);
    }
  

}
