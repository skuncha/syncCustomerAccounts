package customerAccountsSync.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author srinivasa.kuncha
 *
 */

public class WebDriverConfigurer {

    public static final int DEFAULT_TIMEOUT = 20000;

    private static final Logger log = LoggerFactory.getLogger(WebDriverConfigurer.class);

    public static String configureDrivers() {
        OSName os = OSName.from(System.getProperty("os.name"));
        try {
            log.info("OS: " + os.name() + ". Current dir: " + new File(".").getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         System.setProperty("webdriver.chrome.driver", "drivers" + os.chromeDriverPath());
         System.setProperty("webdriver.ie.driver", "drivers\\windows\\IEDriverServer.exe");
         return os.name();
         
    }

    private static enum OSName {

        linux() {
            @Override
            public boolean matches(String fullOSName) {
                return fullOSName.toLowerCase().contains("linux");
            }

            @Override
            public String chromeDriverPath() {
                return "/linux/chromedriver";
            }
        },

        mac() {
            @Override
            public boolean matches(String fullOSName) {
                return fullOSName.toLowerCase().contains("mac");
            }

            @Override
            public String chromeDriverPath() {
                return "/mac/chromedriver";
            }
        },

        windows() {
            @Override
            public boolean matches(String fullOSName) {
                return fullOSName.toLowerCase().contains("win");
            }

            @Override
            public String chromeDriverPath() {
                return "\\windows\\chromedriver.exe";
            }
           
        };

        public abstract boolean matches(String fullOSName);
        public abstract String chromeDriverPath();

        public static OSName from(String fullOSName) {
            for (OSName name: values()) {
                if (name.matches(fullOSName)) {
                    return name;
                }
            }

            throw new IllegalArgumentException("Not a recognised OS: " + fullOSName);
        }
    }
    
}
