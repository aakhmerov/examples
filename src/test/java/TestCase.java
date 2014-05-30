public abstract class TestCase {

    public static final java.lang.String DEFAULT_DIRECTORY = "profile.default_directory";
    public static final java.lang.String DOWNLOADS_FOLDER = "download.default_directory";
    protected WebDriver driver;
    protected Host host;
    private Properties p;

    @Before
    public void setUp() {
        try {
            this.setupDriver();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set up driver based on properties values
     *
     * @throws IOException
     */
    protected void setupDriver() throws IOException {
        p = new Properties();
        File props = new File(this.getClass().getClassLoader().getResource("testing.properties").getFile());
        p.load(new FileInputStream(props));

        Map<String, Object> prefs = new Hashtable<String, Object>();
        File destDir = new File(p.getProperty(DOWNLOADS_FOLDER));
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + p.getProperty(DEFAULT_DIRECTORY));

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        if (Boolean.valueOf(p.getProperty("capabilities.enabled"))) {
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }

        String val = p.getProperty("useLocalDriver");
        if (Boolean.parseBoolean(val)) {
            System.setProperty("webdriver.chrome.driver", p.getProperty("chromdiver.path"));
            driver = new ChromeDriver(capabilities);
        } else {
            System.setProperty("webdriver.chrome.driver", p.getProperty("chromdiver.path"));
            driver = new RemoteWebDriver(new URL(REMOTE_URL), capabilities);

        }
        driver.manage().deleteAllCookies();
    }


    @After
    public void tearDownDriver() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.close();
            driver.quit();
        }
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Host getHost() {
        return this.host;
    }

    public Properties getP() {
        return p;
    }

    public void setP(Properties p) {
        this.p = p;
    }

    /**
     * Method will check downloads folder if specified file exists.
     * Method will wait 1 second before checking.
     *
     * @param href - file name without any extra characters - NOT null
     * @return
     */
    protected boolean isDownloaded(String href) {
        boolean result = false;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String searchName = href;
        String dir = this.getP().getProperty(TestCase.DOWNLOADS_FOLDER);
        File dirWrapper = new File(dir);
        assertThat("destination folder exists", dirWrapper.exists(), is(true));
        assertThat("destination is folder", dirWrapper.isDirectory(), is(true));
        for (File f : dirWrapper.listFiles()) {
            if (f.getName().equals(searchName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * return file wrapper around current downloads folder that is configured for
     * tests in properties file.
     *
     * @return
     * @see {testing.properties}
     */
    protected File getDownloadsFolder() {
        String dir = this.getP().getProperty(TestCase.DOWNLOADS_FOLDER);
        File dirWrapper = new File(dir);
        assertThat("destination folder exists", dirWrapper.exists(),
is(true));
        assertThat("destination is folder", dirWrapper.isDirectory(),
is(true));
        return dirWrapper;
    }
}

