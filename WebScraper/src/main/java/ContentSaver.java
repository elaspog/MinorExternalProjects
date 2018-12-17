import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ContentSaver {
	
	
	public static File createDirectoryStructureFromBaseDir (String ... subDirPath) {
		
		File directoryStructureToCreate = new File(Utils.cleanString(Constants.DEFAULT_OUTPUT_DIR_PATH));
		for (final String nextSubdir : subDirPath) {

			directoryStructureToCreate = new File(directoryStructureToCreate.getPath(), nextSubdir);
		}
		return createDirectoryStructure(directoryStructureToCreate);
	}
	
	private static File createDirectoryStructure (String directoryPath) {
		
		return createDirectoryStructure(new File(directoryPath));
	}	
	
	private static File createDirectoryStructure (File targetDir) {
		
		if (!targetDir.exists() && !targetDir.mkdirs()) {
		    throw new IllegalStateException(String.format(Constants.ERROR_MSG_DIR_CANT_BE_CREATED, targetDir));
		}
		return targetDir;
	}

    public static void createSymbolicLink(Path linkPath, Path targetPath) 
    		throws IOException {
    	
        if (Files.exists(linkPath)) {
            Files.delete(linkPath);
        }
        Files.createSymbolicLink(linkPath, targetPath);
    }

    public static void createHardLink(Path linkPath, Path targetPath) 
    		throws IOException {
    	
        if (Files.exists(linkPath)) {
            Files.delete(linkPath);
        }
        Files.createLink(linkPath, targetPath);
    }
    
    public static String getTimestampString() {
    	
        return new SimpleDateFormat(Constants.DATE_FORMAT).format(Calendar.getInstance().getTime());
    }
    
    public static void writeTextFile(String textContent, String filename, File dirPath) 
    		throws IOException {
    	
        File logFile = new File(dirPath, filename);
        System.out.println(logFile.getCanonicalPath());
        try (	FileWriter fileWriter = new FileWriter(logFile);
        		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
        	
        	bufferedWriter.write(textContent);
        } catch (IOException e) {
            throw new IOException("Error while writing file: " + filename);
        }
    }
    
    public static String getFileNameFromUrl(String url) 
    		throws MalformedURLException {
    	
		return getFileNameFromUrl(new URL(url));
    }
    
    public static String getFileNameFromUrl(URL url) 
    		throws MalformedURLException {
		
		String fileName = null;
		String[] pathContents = url.getPath().split("[\\\\/]");
		if(pathContents != null){
			fileName = pathContents[pathContents.length-1];
		}
		return fileName;
    }
	
	public static File downloadFileWithWaiting(String urlOfFileToDownload, String fileLocationToSave) 
			throws IOException {
		
		String fileToDownload = getFileNameFromUrl(urlOfFileToDownload);
		System.out.println("File name: " + fileToDownload );

		URL url = new URL(urlOfFileToDownload);
		double totalFileSize = tryGetFileSizeInKB(url) * Constants.BYTE_CONVERSION_RATE;
		System.out.println("File size: " + totalFileSize / Constants.BYTE_CONVERSION_RATE + " KB");

		File targetFile = new File(fileLocationToSave);
		try (	InputStream inputStream = new BufferedInputStream(url.openStream());
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				FileOutputStream fos = new FileOutputStream(targetFile)){

			byte[] buf = new byte[1024];
			int percent;
			int n = 0;
			double updatedFileSize = 0;
			long lastCurrentTimeMillis = System.currentTimeMillis();
			boolean finishedStatusWasPrinted = false;
			while (-1 != (n = inputStream.read(buf))) {
				updatedFileSize = updatedFileSize + n;
				byteArrayOutputStream.write(buf, 0, n);
				percent = (int) Math.round((updatedFileSize/totalFileSize) * Constants.MAX_PERCENT);
				if(percent != 100) {
					if (System.currentTimeMillis() - lastCurrentTimeMillis >= Constants.SECOND_IN_MILLISECONDS ) {
						printStatus(percent, updatedFileSize, totalFileSize);
						lastCurrentTimeMillis = System.currentTimeMillis();
					}
				} else {
					if (! finishedStatusWasPrinted) {
						printStatus(percent, updatedFileSize, totalFileSize);
						finishedStatusWasPrinted = true;
					}
				}
		    }
			System.out.println("Downloaded: " + fileLocationToSave);
			byte[] response = byteArrayOutputStream.toByteArray();
			fos.write(response);
		}		
		return targetFile;
	}
	
	private static void printStatus(int percent, double updatedFileSize, double totalFileSize) {
		
		System.out.println("Status: " + percent + "%\tSize: " + (updatedFileSize / Constants.BYTE_CONVERSION_RATE) + " kbyte / " + ( totalFileSize / Constants.BYTE_CONVERSION_RATE) + " kbyte");
	}
	
	private static int tryGetFileSizeInKB(URL url) {
		
		HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength() / Constants.BYTE_CONVERSION_RATE;
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }
	
	public static void downloadFileWithoutWaiting(String url, File outputFile) 
			throws IOException {
		
		System.out.println("filePath: " + outputFile.getPath());
		
		URL website = new URL(url);;
		ReadableByteChannel rbc;
		
		try (FileOutputStream fos = new FileOutputStream(outputFile.getPath())){
			rbc = Channels.newChannel(website.openStream());
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}	
	}
	
	public static void saveScreenshot(WebDriver driver, String destinationFolder, String screenshotFileName) 
			throws IOException {
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(destinationFolder, screenshotFileName));
	}	
	
	public static File saveFullScreenScreenshot(WebDriver driver, String destinationFolder, String screenshotFileName) 
			throws IOException {
		
		// The external lib has bugs
		// That's the reason why the saveScreenshot() needs to be called before this method
		
		File screenshot = new File(destinationFolder, screenshotFileName);
		Screenshot fpScreenshot = new AShot()
				.shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);
		ImageIO.write(fpScreenshot.getImage(), "PNG", screenshot);
		
		return screenshot;
	}
	
}
