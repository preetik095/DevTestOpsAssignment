package utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConfigManager {
		
	private static ConfigManager manager;
	private static final Properties prop = new Properties();
	private static final Logger log = LogManager.getLogger(ConfigManager.class);
	
	private ConfigManager() throws IOException {
		InputStream inputStream = ConfigManager.class.getResourceAsStream("/config/config.properties");
		try {
			
			prop.load(inputStream);
			log.debug("Proerties file found");
			
		} catch (FileNotFoundException e) {
			log.error("Unable to find config.properties on classpath");
			e.printStackTrace();
		}
		log.debug("ConfigManager exit");
	}
	
	
	
	public static ConfigManager getInstance() {
		//only one instance of the class is created
		if(manager == null) {
			
			//no other thread will not able to access this class
			synchronized (ConfigManager.class) {
				try {
					manager = new ConfigManager();
				} catch (IOException e) {
					log.error("IOException occured");
					e.printStackTrace();
				}
			}
		}
		return manager;
	}
	
	
	//function to get property value
	public String getString(String key) {
		return System.getProperty(key, prop.getProperty(key));
	}

}
