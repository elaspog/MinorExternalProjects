
public class Constants {

    public static final int SECOND_IN_MILLISECONDS                        = 1000;
    public static final int BYTE_CONVERSION_RATE                          = 1024;
    public static final int MAX_PERCENT                                   = 100;

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
    public static final String URL_SPLITTER                               = "[\\\\/]";
    public static final String HTTP_HEAD                                  = "HEAD";

    public static final String OUTPUT_FILE_NAME_ZERO_PREFIX               = "00 - ";
    public static final String OUTPUT_FILE_NAME_LOGIN_SCREEN              = "loginscreen.png";
    public static final String OUTPUT_FILE_NAME_DASHBOARD_SCREEN          = "dashboard.png";
    public static final String OUTPUT_FILE_NAME_COURSES_SCREEN            = "courses.png";
    public static final String OUTPUT_FILE_NAME_COURSE_SCREEN             = "00 - course.png";
    public static final String OUTPUT_FILE_EXTENSION_MP4                  = ".mp4";
    public static final String OUTPUT_FILE_EXTENSION_PNG                  = ".png";
    public static final String OUTPUT_FILE_EXTENSION_HTML                 = ".html";
    public static final String OUTPUT_FILE_EXTENSION_HTML_PSEUDO          = "_html";
    public static final String OUTPUT_FILE_EXTENSION_PNG_TYPE             = "PNG";

    public static final String FILE_NAME_SEP                              = " - ";
    public static final String FILE_NAME_PART_SEP                         = "_";
    public static final String FILE_NAME_PART                             = ".timings";
    public static final String FILE_NAME_EXTENSION                        = ".txt";


    public static final String CONFIG_SCRAPER_DASHBOARD_HTML              = "scraper.saveDashboardHtml";
    public static final String CONFIG_SCRAPER_COURSES_HTML                = "scraper.saveCoursesHtml";

    public static final String CONFIG_SCRAPER_DASHBOARD_SCREENSHOT        = "scraper.saveDashboardScreenshot";
    public static final String CONFIG_SCRAPER_COURSES_SCREENSHOT          = "scraper.saveCoursesScreenshot";

    public static final String CONFIG_SCRAPER_COURSE_HTML                 = "scraper.saveCourseHtml";
    public static final String CONFIG_SCRAPER_COURSE_SCREENSHOT           = "scraper.saveCourseScreenshots";
    public static final String CONFIG_SCRAPER_DOWNLOAD_ATTACHED_CONTENTS  = "scraper.saveAttachedContents";

    public static final String CONFIG_SCRAPER_TOPIC_HTML                  = "scraper.saveTopicHtml";
    public static final String CONFIG_SCRAPER_TOPIC_SCREENSHOT            = "scraper.saveTopicScreenshots";
    public static final String CONFIG_SCRAPER_DOWNLOAD_VIDEO              = "scraper.saveVideos";
    public static final String CONFIG_SCRAPER_DOWNLOAD_TRANSCRIPT         = "scraper.saveTranscript";
    public static final String CONFIG_SCRAPER_QUIZ_OR_ARTICLE_HTML        = "scraper.saveQuizOrArticleHtml";


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
    public static final String WEBSITE_CONTENT_SEQ_ID                     = "seq_content";
    public static final String WEBSITE_SHORTEST_MATCHING_MP4_URL          = ".*(https.*\\.mp4\\?.*?)&";
    public static final String WEBSITE_INNER_HTML                         = "innerHTML";
    public static final String WEBSITE_OUTER_HTML                         = "outerHTML";
    public static final String WEBSITE_HREF                               = "href";

    public static final String MSG_STATUS_NEW_DIRECTORY                   = "Directory created: '%s'";
    public static final String MSG_STATUS_HTML_CREATED                    = "HTML file created: '%s'";
    public static final String MSG_STATUS_LOGIN_COOKIE_BEGIN              = "Logging in and generating new cookies for webdriver.";
    public static final String MSG_STATUS_LOGIN_COOKIE_END                = "New cookies for webdriver were saved.";
    public static final String MSG_STATUS_PROCESSING                      = "\nProcessing...";
    public static final String MSG_STATUS_DOWNLOADING                     = "Downloading: '%s' into '%s'";
    public static final String MSG_STATUS_ATTACHED_CNT                    = "Attached content count: %s";
    public static final String MSG_STATUS_ANCHOR_CNT                      = "Anchor count: '%s'";
    public static final String MSG_STATUS_GET_ATTACHED                    = "Getting attached contents";
    public static final String MSG_STATUS_PROCESS_ANCHOR                  = "Getting contents of main anchors";
    public static final String MSG_STATUS_NAVIGATE                        = "Navigate back to: '%s'";
    public static final String MSG_STATUS_LOGIN_END                       = "Login is done.";
    public static final String MSG_STATUS_LOGIN_START                     = "Doing login: '%s'";
    public static final String MSG_STATUS_WAITING                         = "Waiting: %s msec";
    public static final String MSG_STATUS_EXITED                          = "Exited.";
    public static final String MSG_STATUS_CURRENT_URL                     = "[ %s / %s ] Current url: '%s'";
    public static final String MSG_ERROR_NO_MP4_A                         = "No MP4 URL found.";
    public static final String MSG_ERROR_NO_MP4_B                         = "Not an MP4 page.";
    public static final String MSG_ERROR_NO_SUB                           = "No Transcript URL.";
    public static final String MSG_ERROR_NO_COOKIE                        = "There is no cookie in the file:\n'%s'";
    public static final String MSG_ERROR_COOKIE_LOGIN                     = "Cookies were not present in files.";
    public static final String MSG_ERROR_UNKNOWN_PAGE_TYPE                = "Unknown type of page: '%s'";
    public static final String MSG_ERROR_WRONG_VIDEO_PAGE                 = "Wrong video type page: '%s'";
    public static final String OUTPUT_SEP_1                               = " /// ";
    public static final String OUTPUT_SEP_2                               = " ::: ";

}
