
public class Constants {

    public static final String DEFAULT_OUTPUT_DIR_PATH                    = "./_output";
    public static final String CONFIGURATION_FILE_PATH                    = "./configuration.properties";
    public static final String COOKIES_FILE_PATH                          = "./cookies.data";
    public static final String DRIVER_NAME                                = "webdriver.gecko.driver";
    public static final String DRIVER_PATH                                = "geckodriver.exe";
    public static final String DATE_FORMAT                                = "yyyy-MM-dd_HH-mm-ss";
    public static final String COOKIE_DATE_FORMAT                         = "EEE MMM d HH:mm:ss zzz yyyy";
    public static final String NUMBER_FORMAT                              = "%02d";
    public static final String LINE_SEPARATOR                             = "\n";
    public static final String LINE_ENDING                                = "\r\n";
    public static final String STRING_SEPARATOR                           = ",";
    public static final String COOKIE_SEPARATOR                           = ";";
    public static final String NULL_STRING                                = "null";

    public static final String OUTPUT_FILE_NAME_LOGIN_SCREEN              = "loginscreen.png";
    public static final String OUTPUT_FILE_NAME_DASHBOARD_SCREEN          = "dashboard.png";
    public static final String OUTPUT_FILE_NAME_COURSES_SCREEN            = "courses.png";
    public static final String OUTPUT_FILE_NAME_COURSE_SCREEN             = "00 - course.png";
    public static final String OUTPUT_FILE_EXTENSION_MP4                  = ".mp4";
    public static final String OUTPUT_FILE_EXTENSION_PNG                  = ".png";
    public static final String OUTPUT_FILE_EXTENSION_HTML                 = "_html";

    public static final String FILE_NAME_SEP                              = " - ";
    public static final String FILE_NAME_PART_SEP                         = "_";
    public static final String FILE_NAME_PART                             = ".timings";
    public static final String FILE_NAME_EXTENSION                        = ".txt";

    public static final String CONFIG_SCRAPER_DOWNLOAD_ATTACHED_CONTENTS  = "scraper.downloadAttachedContents";
    public static final String CONFIG_SCRAPER_DOWNLOAD_TRANSCRIPT         = "scraper.downloadTranscript";
    public static final String CONFIG_SCRAPER_DOWNLOAD_VIDEO              = "scraper.downloadVideos";
    public static final String CONFIG_SCRAPER_DASHBOARD_SCREENSHOT        = "scraper.makeDashboardScreenshots";
    public static final String CONFIG_SCRAPER_COURSES_SCREENSHOT          = "scraper.makeCoursesScreenshots";
    public static final String CONFIG_SCRAPER_COURSE_SCREENSHOT           = "scraper.makeCourseScreenshots";
    public static final String CONFIG_SCRAPER_TOPIC_SCREENSHOT            = "scraper.makeTopicScreenshots";
    public static final String CONFIG_SCRAPER_QUIZ_SCREENSHOT             = "scraper.makeQuizScreenshots";
    public static final String CONFIG_SCRAPER_DASHBOARD_HTML              = "scraper.saveDashboardHtml";
    public static final String CONFIG_SCRAPER_COURSES_HTML                = "scraper.saveCoursesHtml";
    public static final String CONFIG_SCRAPER_COURSE_HTML                 = "scraper.saveCourseHtml";
    public static final String CONFIG_SCRAPER_TOPIC_HTML                  = "scraper.saveTopicHtml";
    public static final String CONFIG_SCRAPER_QUIZ_HTML                   = "scraper.saveQuizHtml";

    public static final String CONFIGURATION_PROPERTY_USERNAME            = "username";
    public static final String CONFIGURATION_PROPERTY_PASSWORD            = "password";
    public static final String CONFIGURATION_PROPERTY_LOGIN_URL           = "login_url";
    public static final String CONFIGURATION_PROPERTY_COURSES_URL         = "courses_url";
    public static final String CONFIGURATION_PROPERTY_URLS                = "urls";

    public static final String ERROR_MSG_FILE_NOT_FOUND                   = "Input file '%s' not found.";
    public static final String ERROR_MSG_FILE_CANT_BE_READ                = "Input file '%s' can't be read.";
    public static final String ERROR_MSG_FILE_CANT_BE_WRITTEN             = "Output file '%s' can't be written.";
    public static final String ERROR_MSG_DIR_CANT_BE_CREATED              = "Couldn't create directory: '%s'";

    public static final String WEBSITE_ATTRIBUTE_INNER_HTML               = "data-metadata";
    public static final String WEBSITE_LOGIN_NAME_INPUT_FIELD_ID          = "login-email";
    public static final String WEBSITE_LOGIN_PASSWORD_INPUT_FIELD_ID      = "login-password";
    public static final String WEBSITE_LOGIN_REMEMBER_CHECKBOX_ID         = "login-remember";
    public static final String WEBSITE_LOGIN_SUBMIT_BUTTON_XPATH          = "/html/body/div[2]/div[2]/div/main/div/div/section[1]/div/form/button";
    public static final String WEBSITE_CONTENT_EXPAND_BUTTON_ID           = "expand-collapse-outline-all-button";
    public static final String WEBSITE_CONTENT_VIDEO_URL_CLASS            = "div[class='video is-captions-rendered is-initialized']";
    public static final String WEBSITE_CONTENT_TRANSCRIPT_CLASS           = "div[class='subtitles']";
    public static final String WEBSITE_CONTENT_TRANSCRIPT_1_XPATH         = ".//ol/li[span]";
    public static final String WEBSITE_CONTENT_TRANSCRIPT_2_XPATH         = ".//ol/li/span";
    public static final String WEBSITE_CONTENT_COURSE_TITLE_XPATH         = "/html/body/div[3]/div[2]/div/header/div[1]/nav/h2";
    public static final String WEBSITE_CONTENT_TOPIC_URLS_XPATH           = "/html/body/div[3]/div[2]/div/div/div/main/ol/li";
    public static final String WEBSITE_CONTENT_SIDE_CONTENT_XPATH         = "/html/body/div[3]/div[2]/div/div/aside/div[3]/p/a";
    public static final String WEBSITE_CONTENT_MAIN_LINK_XPATH            = "/html/body/div[3]/div[2]/div/div/div/main/ol/li/ol/li/ol/li/a";
    public static final String WEBSITE_CONTENT_MAIN_SUBCONTENT_XPATH      = ".//div/div";
    public static final String WEBSITE_CONTENT_MAIN_UPPER_TITLE_SUB_XPATH = ".//parent::*/parent::*/parent::*/button/h4";
    public static final String WEBSITE_CONTENT_MAIN_UPPER_TITLE_TOP_XPATH = ".//parent::*/parent::*/parent::*/parent::*/parent::*/button/h3";
    public static final String WEBSITE_SHORTEST_MATCHING_MP4_URL          = ".*(https.*\\.mp4\\?.*?)&";
    public static final String WEBSITE_INNER_HTML                         = "innerHTML";
    public static final String WEBSITE_HREF                               = "href";

    public static final int SECOND_IN_MILLISECONDS                        = 1000;
    public static final int BYTE_CONVERSION_RATE                          = 1024;
    public static final int MAX_PERCENT                                   = 100;
}
