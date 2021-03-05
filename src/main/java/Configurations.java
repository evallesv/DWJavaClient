/**
 * 
 */


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author evall
 *
 */
public class Configurations  {
	private Properties prop = new Properties();
	public Configurations() throws IOException {
 		InputStream config = getClass().getClassLoader().getResourceAsStream("config.properties");
 		if(config != null) {
			prop.load(config);
 		}
	}
	
	public String Get(String key) {
 		return prop.getProperty(key);
	 }
}
