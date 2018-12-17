import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class CookieLoader {
	
	private final String cookieFilePath;
	private File cookiesFile;
	private SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

	public CookieLoader() {
		this(Constants.COOKIES_FILE_PATH);
	}
	
	public CookieLoader(String cookieFilePath) {
		this.cookieFilePath = cookieFilePath;
		cookiesFile = new File(this.cookieFilePath);
	}
	
	public boolean cookiesAreSavedInFile() {
		
		if (cookiesFile.exists() == false)
			return false;
		// TODO: check if file contains any entries
		return true;
	}
	
	public Set<Cookie> getCookiesFromDriver(WebDriver driver) {
		
		return driver.manage().getCookies();
	}
	
	public Set<Cookie> getCookiesFromFile()
			throws IOException {

		Set<Cookie> setofCookiesFromFile = new HashSet<>();
		try ( FileReader fileReader = new FileReader(cookiesFile);
			  BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			
			String strline;
			while( (strline = bufferedReader.readLine()) != null ) {
				
		        StringTokenizer token = new StringTokenizer(strline,";");
		        
		        while(token.hasMoreTokens()) {
		        	
			        String name = token.nextToken();
			        String value = token.nextToken();
			        String domain = token.nextToken();
			        String path = token.nextToken();
			        Date expiry = null;
			        String val;
			        
			        if(!(val=token.nextToken()).equals("null")) {
			        	try {
							expiry = dateParser.parse(val);
						} catch (ParseException e) { }
			        }

			        Boolean isSecure = new Boolean(token.nextToken()).booleanValue();
			        
			        Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);
			        setofCookiesFromFile.add(ck);			        
		        }
	        }			
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(String.format(Constants.ERROR_MSG_FILE_NOT_FOUND, cookieFilePath));
		} catch (IOException e) {
			throw new IOException(String.format(Constants.ERROR_MSG_FILE_CANT_BE_READ, cookieFilePath));
		}
		return setofCookiesFromFile;
	}
	
	public String getCookiesString(Set<Cookie> cookies) {
		
		StringBuffer stringToReturn = new StringBuffer();
		for(Cookie ck : cookies) {
			
			String entry = ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()+"\n";
			stringToReturn.append(entry);
		}
		
		return stringToReturn.toString();
	}
	
	public void writeCookiesIntoFile(WebDriver driver) 
			throws IOException {
		
		cookiesFile.delete();
		cookiesFile.createNewFile();
		
		Set<Cookie> cookies = getCookiesFromDriver(driver);

    	try ( FileWriter fileWriter = new FileWriter(cookiesFile);
    		  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
    		
    		for(Cookie ck : cookies) {

    			StringBuffer entry = new StringBuffer()
    				.append(ck.getName())
    				.append(";")
    				.append(ck.getValue())
    				.append(";")
    				.append(ck.getDomain())
    				.append(";")
    				.append(ck.getPath())
    				.append(";")
    				.append(ck.getExpiry())
    				.append(";")
    				.append(ck.isSecure());
    			
				bufferedWriter.write(entry.toString());
	        	bufferedWriter.newLine();
	        }
	        bufferedWriter.close();
	        fileWriter.close();
	        
		} catch (IOException e) {
			
			throw new IOException(String.format(Constants.ERROR_MSG_FILE_CANT_BE_WRITTEN, cookieFilePath));
		}
	}

	public WebDriver addCookieToWebDriver(WebDriver driver) 
			throws IOException {

		for (Cookie ck : getCookiesFromFile()) {

	        //System.out.println(ck);
			driver.manage().addCookie(ck);
		}        
		return driver;
	}

}
