import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileConfiguration {
	
	protected String configurationFilePath;
	protected ProgramTime readTime;
	protected Properties properties = new Properties();

	public PropertyFileConfiguration(String configurationFilePath) 
			throws IOException {
		
		 this(configurationFilePath, ProgramTime.START_TIME);
	}
	
	public PropertyFileConfiguration(String configurationFilePath, ProgramTime readTime) 
			throws IOException {
		
		this.configurationFilePath = configurationFilePath;
		this.readTime = readTime;
		
		if (this.readTime.equals(ProgramTime.START_TIME)) {
			loadFileConfiguration();
		}
	}
	
	protected void loadFileConfiguration() 
			throws IOException {
	
		File configurationFile = new File(configurationFilePath);
		try (InputStream inputStream = new FileInputStream(configurationFile)) {
			if (inputStream != null) {
				properties.load(inputStream);
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(String.format(Constants.ERROR_MSG_FILE_NOT_FOUND, configurationFilePath));
		} catch (IOException e) {
			throw new IOException(String.format(Constants.ERROR_MSG_FILE_CANT_BE_READ, configurationFilePath));
		}
	}
	
	protected Properties getProperties() {
		
		return properties;
	}

	protected String getValueByKey(String key) {
		
		return properties.getProperty(key);
	}

	protected String getValueByKey(String key, String defaultValue) {
		
		try {
			return properties.getProperty(key);
		} catch (Exception e) {
			return defaultValue;
		}		
	}

	protected boolean getFlagValueByKey(String key) {
		
		return Boolean.parseBoolean(properties.getProperty(key));
	}

	protected boolean getFlagValueByKey(String key, boolean defaultValue) {
		
		try {
			return Boolean.parseBoolean(properties.getProperty(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
}
