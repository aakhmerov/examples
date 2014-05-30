public class TestDownload extends TestCase {

    private static final String TEST_DOWNLOADS = "test.downloads.askar.testing";

    @Test
    public void run() throws Exception {
        _host = new Host(TEST_DOWNLOADS);
        _driver.get("http://" +TEST_DOWNLOADS);
        this.runTestCase(null);
    }

    @Override
    protected void runTestCase(Registration registration) throws MalformedURLException {
        WebElement target = driver.findElement(By.cssSelector("#test_anchor"));
        target.click();
        String href = target.getAttribute("href");
        href = href.split("/")[href.split("/").length - 1];
        assertThat("downloaded file exists in desired destination",isDownloaded(href),is(true));
    }

}
