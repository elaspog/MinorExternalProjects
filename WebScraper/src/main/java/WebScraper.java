
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


public class WebScraper {

	private WebDriver driver;
	private CookieLoader cookieLoader;
	private PropertyFileConfiguration defaultConf;
	private TimeRandomizer timeRandomizer = new TimeRandomizer(3000, 6000);

	private boolean downloadAttachedContents;
	private boolean downloadTranscript;
	private boolean downloadVideos;
	private boolean makeDashboardScreenshots;
	private boolean makeCoursesScreenshot;
	private boolean makeCourseScreenshots;
	private boolean makeTopicScreenshots;
	private boolean makeQuizScreenshots;
	private boolean saveDashboardHtml;
	private boolean saveCoursesHtml;
	private boolean saveCourseHtml;
	private boolean saveTopicHtml;
	private boolean saveQuizHtml;

	private final String MSG_STATUS_NEW_DIRECTORY      = "Directory created: '%s'";
	private final String MSG_STATUS_HTML_CREATED       = "HTML file created: '%s'";
	private final String MSG_STATUS_LOGIN_COOKIE_BEGIN = "Logging in and generating new cookies for webdriver.";
	private final String MSG_STATUS_LOGIN_COOKIE_END   = "New cookies for webdriver were saved.";
	private final String MSG_STATUS_PROCESSING         = "\nProcessing...";
	private final String MSG_STATUS_DOWNLOADING        = "Downloading: '%s' into '%s'";
	private final String MSG_STATUS_ATTACHED_CNT       = "Attached content count: %s";
	private final String MSG_STATUS_ANCHOR_CNT         = "Anchor count: '%s'";
	private final String MSG_STATUS_GET_ATTACHED       = "Getting attached contents";
	private final String MSG_STATUS_PROCESS_ANCHOR     = "Getting contents of main anchors";
	private final String MSG_STATUS_NAVIGATE           = "Navigate back to: '%s'";
	private final String MSG_STATUS_LOGIN_END          = "Login is done.";
	private final String MSG_STATUS_LOGIN_START        = "Doing login: '%s'";
	private final String MSG_STATUS_WAITING            = "Waiting: %s msec";
	private final String MSG_STATUS_EXITED             = "Exited.";
	private final String MSG_STATUS_CURRENT_URL        = "[ %s / %s ] Current url: '%s'";
	private final String MSG_ERROR_NO_MP4_A            = "No MP4 URL found.";
	private final String MSG_ERROR_NO_MP4_B            = "Not an MP4 page.";
	private final String MSG_ERROR_NO_SUB              = "No Transcript URL.";
	private final String MSG_ERROR_NO_COOKIE           = "There is no cookie in the file:\n'%s'";
	private final String MSG_ERROR_COOKIE_LOGIN        = "Cookies were not present in files.";
	private final String MSG_ERROR_UNKNOWN_PAGE_TYPE   = "Unknown type of page: '%s'";
	private final String MSG_ERROR_WRONG_VIDEO_PAGE    = "Wrong video type page: '%s'";
	private final String OUTPUT_SEP_1                  = " /// ";
	private final String OUTPUT_SEP_2                  = " ::: ";
	
	
	public static void main(String[] args) {
		new WebScraper();
	}

	private WebScraper() {
		
		try {
			
			defaultConf 				= new PropertyFileConfiguration(Constants.CONFIGURATION_FILE_PATH);
			cookieLoader 				= new CookieLoader();	

			downloadAttachedContents	= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_DOWNLOAD_ATTACHED_CONTENTS);
			downloadTranscript 			= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_DOWNLOAD_TRANSCRIPT);
			downloadVideos 				= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_DOWNLOAD_VIDEO);
			makeDashboardScreenshots 	= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_DASHBOARD_SCREENSHOT);
			makeCoursesScreenshot		= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_COURSES_SCREENSHOT);
			makeCourseScreenshots 		= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_COURSE_SCREENSHOT);
			makeTopicScreenshots 		= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_TOPIC_SCREENSHOT);
			makeQuizScreenshots 		= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_QUIZ_SCREENSHOT);
			saveDashboardHtml			= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_DASHBOARD_HTML);
			saveCoursesHtml				= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_COURSES_HTML);
			saveCourseHtml				= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_COURSE_HTML);
			saveTopicHtml				= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_TOPIC_HTML);
			saveQuizHtml				= defaultConf.getFlagValueByKey(Constants.CONFIG_SCRAPER_QUIZ_HTML);
			
			String username 			= defaultConf.getValueByKey(Constants.CONFIGURATION_PROPERTY_USERNAME);
			String password 			= defaultConf.getValueByKey(Constants.CONFIGURATION_PROPERTY_PASSWORD);
			String urls 				= defaultConf.getValueByKey(Constants.CONFIGURATION_PROPERTY_URLS);
			
			System.out.println("username : " + username);
			System.out.println("password : " + password);
			System.out.println("urls     : " + urls);
	
			ContentSaver.createDirectoryStructureFromBaseDir();
			
			/// Configure driver and window
			
			System.setProperty(Constants.DRIVER_NAME, Constants.DRIVER_PATH);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			
			/// Logging into the site
			
			boolean doLogin = false;
			if (cookieLoader.cookiesAreSavedInFile()) {
				
				Set<Cookie> cookies = cookieLoader.getCookiesFromFile();
				if (cookies == null || cookies.size() == 0) {
					
					System.out.println(String.format(MSG_ERROR_NO_COOKIE, cookieLoader.getCookiesString(cookies)));
					doLogin = true;
					
				} else {
					
					/// Autologin by using cookies
					
					//System.out.println("Adding cookies to webdriver:\n" + cookieLoader.getCookiesString(cookies));
					//String loginUrl = defaultConf.getValueByKey(StringConstants.CONFIGURATION_PROPERTY_LOGIN_URL);
					//String coursesUrl = defaultConf.getValueByKey(StringConstants.CONFIGURATION_PROPERTY_COURSES_URL);
					//driver.navigate().to(loginUrl);
					//driver.navigate().to(coursesUrl);
					//driver.get(loginUrl);
					//cookieLoader.addCookieToWebDriver(driver);
					//driver.navigate().to(coursesUrl);
					//System.out.println("Cookies were added to webdriver.");
					
					doLogin = true; 	// TMP
				}
			} else {
				
				doLogin = true;
				System.out.println(MSG_ERROR_COOKIE_LOGIN);
			}

			if (doLogin) {
				
				System.out.println(MSG_STATUS_LOGIN_COOKIE_BEGIN);
				doLogin();
				cookieLoader.writeCookiesIntoFile(driver);
				System.out.println(MSG_STATUS_LOGIN_COOKIE_END);
			}

			// Note: this method here is called due to a bug in external lib: AShot
			ContentSaver.saveScreenshot(driver, Constants.DEFAULT_OUTPUT_DIR_PATH, Constants.OUTPUT_FILE_NAME_LOGIN_SCREEN);

			/// Processing
			ContentSaver.createDirectoryStructureFromBaseDir();
						
			if (makeDashboardScreenshots) {
				simpleWait(3000);
				ContentSaver.saveFullScreenScreenshot(driver, Constants.DEFAULT_OUTPUT_DIR_PATH, Constants.OUTPUT_FILE_NAME_DASHBOARD_SCREEN);
			}
			
			if (saveDashboardHtml) {

			}
			
			if (makeCoursesScreenshot) {
				driver.navigate().to(defaultConf.getValueByKey(Constants.CONFIGURATION_PROPERTY_COURSES_URL));
				simpleWait(3000);
				ContentSaver.saveFullScreenScreenshot(driver, Constants.DEFAULT_OUTPUT_DIR_PATH, Constants.OUTPUT_FILE_NAME_COURSES_SCREEN);
			}
			
			if (saveCoursesHtml) {

			}

			simpleWait(5500);
			System.out.println(MSG_STATUS_PROCESSING);
			for (String url : urls.split(Constants.STRING_SEPARATOR)) {
				// iterating over input URLs
				
				simpleWait();
				
				System.out.println(Constants.LINE_SEPARATOR + url);
				
				driver.navigate().to(url);
				
				WebElement courseTitleElement = driver.findElement(By.xpath(Constants.WEBSITE_CONTENT_COURSE_TITLE_XPATH));
				String cleanedCourseTitleText = Utils.cleanString(courseTitleElement.getText());
				File courseDirectory = ContentSaver.createDirectoryStructureFromBaseDir(cleanedCourseTitleText);
				System.out.println(String.format(MSG_STATUS_NEW_DIRECTORY, courseDirectory.getAbsolutePath()));
				
				if (saveCourseHtml) {
					simpleWait(3000);
					String htmlFileName = ContentSaver.getTimestampString() + Constants.FILE_NAME_PART_SEP + Utils.cleanString(url) + Constants.OUTPUT_FILE_EXTENSION_HTML;
					ContentSaver.writeTextFile(driver.getPageSource(), htmlFileName, courseDirectory);
					System.out.println(String.format(MSG_STATUS_HTML_CREATED, htmlFileName));
				}

				simpleWait();
				WebElement expandButton = driver.findElement(By.id(Constants.WEBSITE_CONTENT_EXPAND_BUTTON_ID));
				expandButton.click();

				if (makeCourseScreenshots) {
					simpleWait(3000);
					ContentSaver.saveFullScreenScreenshot(driver, courseDirectory.getPath(), Constants.OUTPUT_FILE_NAME_COURSE_SCREEN);
				}
				
				simpleWait();
				expandButton.click();

				if (downloadAttachedContents) {
					
					System.out.println(MSG_STATUS_GET_ATTACHED);
					simpleWait();
					List<WebElement> sideContents = driver.findElements(By.xpath(Constants.WEBSITE_CONTENT_SIDE_CONTENT_XPATH));
					
					System.out.println(String.format(MSG_STATUS_ATTACHED_CNT, sideContents.size()));
					for (WebElement we : sideContents) {
						
						String sideElementUrl = we.getAttribute(Constants.WEBSITE_HREF);
						String fileName = FilenameUtils.getName(new URL(sideElementUrl).getPath());
						File newFile = new File(courseDirectory, fileName);
						String filePath = newFile.getPath();
						System.out.println(String.format(MSG_STATUS_DOWNLOADING, sideElementUrl, filePath));
						ContentSaver.downloadFileWithoutWaiting(sideElementUrl, newFile);
						
						simpleWait(1000, 3000);
					}
				}
				
				if ( makeTopicScreenshots || saveTopicHtml 
					//  || makeArticleScreenshots || saveArticleHtml 
						|| makeQuizScreenshots || saveQuizHtml 
					//	|| makeVideoScreenshots || saveVideoHtml
						|| downloadVideos || downloadTranscript) {

					System.out.println(MSG_STATUS_PROCESS_ANCHOR);
					
					simpleWait();
					List<WebElement> anchors = driver.findElements(By.xpath(Constants.WEBSITE_CONTENT_MAIN_LINK_XPATH));
					int anchorsCount = anchors.size();
					System.out.println(String.format(MSG_STATUS_ANCHOR_CNT, anchorsCount));

					for (int navigationCounter = 0; navigationCounter < anchorsCount; navigationCounter ++) {
						// iterating over all anchors found on course page

						String prefix = String.format(Constants.NUMBER_FORMAT, (navigationCounter + 1));
						
						expandButton = driver.findElement(By.id(Constants.WEBSITE_CONTENT_EXPAND_BUTTON_ID));
						expandButton.click();
						
						simpleWait();
						
						// getting the elements of the page again after possible navigation
						anchors = driver.findElements(By.xpath(Constants.WEBSITE_CONTENT_MAIN_LINK_XPATH));
						
						WebElement we = anchors.get(navigationCounter);
						
						WebElement titleLevel1 = we.findElement(By.xpath(Constants.WEBSITE_CONTENT_MAIN_UPPER_TITLE_TOP_XPATH));
						WebElement titleLevel2 = we.findElement(By.xpath(Constants.WEBSITE_CONTENT_MAIN_UPPER_TITLE_SUB_XPATH));
						WebElement titleLevel3 = we.findElement(By.xpath(Constants.WEBSITE_CONTENT_MAIN_SUBCONTENT_XPATH));

						String link = we.getAttribute(Constants.WEBSITE_HREF);
						String nameLevel1 = Utils.cleanString(titleLevel1.getText());
						String nameLevel2 = Utils.cleanString(titleLevel2.getText());
						String nameLevel3 = Utils.cleanString(titleLevel3.getText());				

						File targetDir = ContentSaver.createDirectoryStructureFromBaseDir(cleanedCourseTitleText, nameLevel1, nameLevel2);
						System.out.println(titleLevel1.getText() + OUTPUT_SEP_1 + titleLevel2.getText() + OUTPUT_SEP_1 + we.getText() + OUTPUT_SEP_2 + link);

						simpleWait();
						we.click();

						simpleWait(3000, 5000);
						String pageSource = driver.getPageSource();
						
						if (saveTopicHtml) {
							ContentSaver.writeTextFile(pageSource, prefix + Constants.FILE_NAME_SEP + nameLevel3 + Constants.OUTPUT_FILE_EXTENSION_HTML, courseDirectory);
						}

						if (makeTopicScreenshots) {

							simpleWait(3000);
							String pngFileName = nameLevel3 + Constants.OUTPUT_FILE_EXTENSION_PNG;
							File pngFile = ContentSaver.saveFullScreenScreenshot(driver, targetDir.getPath(), pngFileName);
							Path linkPath = new File(courseDirectory, prefix + Constants.FILE_NAME_SEP + pngFileName).toPath();
							ContentSaver.createSymbolicLink(linkPath, pngFile.toPath());
						}
						
						boolean wasVideo = false;
						if (downloadVideos) {							

							try {
								System.out.println(String.format(MSG_STATUS_CURRENT_URL, prefix, String.format(Constants.NUMBER_FORMAT, anchorsCount), driver.getCurrentUrl()));
								WebElement videoField = driver.findElement(By.cssSelector(Constants.WEBSITE_CONTENT_VIDEO_URL_CLASS));
								wasVideo = true;	// or NoSuchElementException happens
								
								String videoDiv = videoField.getAttribute(Constants.WEBSITE_ATTRIBUTE_INNER_HTML);
								//ContentSaver.writeTextFile(videoDiv, prefix + NAME_SEP + nameLevel3 + ".txt", courseDirectory);
								String mp4Url = getMp4UrlFromPageSource(videoDiv);
								
								if (mp4Url != null) {
									
									simpleWait(1000, 3000);
									String mp4FileName = nameLevel3 + Constants.OUTPUT_FILE_EXTENSION_MP4;								
									File mp4File = ContentSaver.downloadFileWithWaiting(mp4Url, new File(targetDir, mp4FileName).getPath());
									Path linkPath = new File(courseDirectory, prefix + Constants.FILE_NAME_SEP + mp4FileName).toPath();
									ContentSaver.createSymbolicLink(linkPath, mp4File.toPath());
									
								} else {
									System.out.println(MSG_ERROR_NO_MP4_A);
								}
							} catch (NoSuchElementException e) {
								System.out.println(MSG_ERROR_NO_MP4_B);
							}
						}

						boolean wasTranscript = false;
						if (downloadTranscript) {

							try {
								
								WebElement transcriptDiv = driver.findElement(By.cssSelector(Constants.WEBSITE_CONTENT_TRANSCRIPT_CLASS));
								wasTranscript = true;	// or NoSuchElementException happens

								List<WebElement> listOfSpanElements1 = transcriptDiv.findElements(By.xpath(Constants.WEBSITE_CONTENT_TRANSCRIPT_1_XPATH));
								List<WebElement> listOfSpanElements2 = transcriptDiv.findElements(By.xpath(Constants.WEBSITE_CONTENT_TRANSCRIPT_2_XPATH));
								
								String transcriptString1 = listOfSpanElements1.stream().map(s -> s.getAttribute(Constants.WEBSITE_INNER_HTML)).collect(Collectors.joining(Constants.LINE_ENDING)) + Constants.LINE_ENDING;
								String transcriptString2 = listOfSpanElements2.stream().map(s -> s.getText()).collect(Collectors.joining(Constants.LINE_ENDING)) + Constants.LINE_ENDING;
								
								ContentSaver.writeTextFile(transcriptString1, prefix + Constants.FILE_NAME_SEP + nameLevel3 + Constants.FILE_NAME_PART + Constants.FILE_NAME_EXTENSION, courseDirectory);
								ContentSaver.writeTextFile(transcriptString2, prefix + Constants.FILE_NAME_SEP + nameLevel3 + Constants.FILE_NAME_EXTENSION, courseDirectory);
								
							} catch (NoSuchElementException e) {
								System.out.println(MSG_ERROR_NO_SUB);
							}
						}

						boolean wasArticle = false;
						boolean wasQuiz = false;

						if ( (wasVideo && wasTranscript) || wasArticle || wasQuiz) {
							// Already known page types
						} else if ( ((! wasVideo) && wasTranscript) || (wasVideo && (! wasTranscript)) ) {
							// Error - Wrong video page
							throw new RuntimeException(String.format(MSG_ERROR_WRONG_VIDEO_PAGE, url));
						} else {
							// Error - Other unknown type of page
							throw new RuntimeException(String.format(MSG_ERROR_UNKNOWN_PAGE_TYPE, url));
						}

						simpleWait();
						driver.navigate().back();
					}
				}
				
				simpleWait();
				
				String navigateBackUrl = defaultConf.getValueByKey(Constants.CONFIGURATION_PROPERTY_COURSES_URL);
				System.out.print(String.format(MSG_STATUS_NAVIGATE, navigateBackUrl));
				driver.navigate().to(navigateBackUrl);
			}
			
			//driver.close();
			
			System.out.println(MSG_STATUS_EXITED);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	private void doLogin() {
		
		/// Manual login by using the structure of the webpage
		
		String loginUrl = defaultConf.getValueByKey(Constants.CONFIGURATION_PROPERTY_LOGIN_URL);		
		
		System.out.println(String.format(MSG_STATUS_LOGIN_START, loginUrl));

		driver.get(loginUrl);
		
		WebElement loginInputField 			= driver.findElement(By.id(Constants.WEBSITE_LOGIN_NAME_INPUT_FIELD_ID));
		WebElement loginPasswordField		= driver.findElement(By.id(Constants.WEBSITE_LOGIN_PASSWORD_INPUT_FIELD_ID));
		WebElement loginRememberCheckbox 	= driver.findElement(By.id(Constants.WEBSITE_LOGIN_REMEMBER_CHECKBOX_ID));
		WebElement submitButton				= driver.findElement(By.xpath(Constants.WEBSITE_LOGIN_SUBMIT_BUTTON_XPATH));
		
		loginInputField.clear();
		loginPasswordField.clear();
		loginInputField.sendKeys(defaultConf.getValueByKey(Constants.CONFIGURATION_PROPERTY_USERNAME));
		loginPasswordField.sendKeys(defaultConf.getValueByKey(Constants.CONFIGURATION_PROPERTY_PASSWORD));
		loginRememberCheckbox.click();
		
		simpleWait();
		
		submitButton.click();
		System.out.println(MSG_STATUS_LOGIN_END);
	}
	
	private void simpleWait(int min, int max) {
		
		simpleWait(TimeRandomizer.RandomTimeBetweenRange(min, max));
	}

	private void simpleWait() {

		simpleWait(timeRandomizer.RandomTime());
	}
	
	private void simpleWait(int msec) {

		try {
			System.out.println(String.format(MSG_STATUS_WAITING, Integer.toString(msec)));
			Thread.sleep(msec);
			// TODO
			//driver.manage().timeouts().implicitlyWait(msec, TimeUnit.MICROSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private String getMp4UrlFromPageSource(String pageSource) {
		
		String retVal = null;
		
		Pattern pattern = Pattern.compile(Constants.WEBSITE_SHORTEST_MATCHING_MP4_URL);
		Matcher matcher = pattern.matcher(pageSource);
		if (matcher.find()) {
		    System.out.println(matcher.group(1));
		    retVal = matcher.group(1);
		}
		
		return retVal;
	}
	
}
