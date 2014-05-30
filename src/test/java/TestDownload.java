import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestDownload extends TestCase {

    private static final String TEST_DOWNLOADS = "http://ostdlabs.com/#/about";
    private static final long TIMEOUT = 5000;

    @Test
    public void run() throws Exception {
        driver.get(TEST_DOWNLOADS);
        this.runTestCase();
    }

    protected void runTestCase() throws MalformedURLException {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        By button = By.cssSelector("a.case_button");
        wait.until(ExpectedConditions.elementToBeClickable(button));
        WebElement target = driver.findElement(button);
        target.click();
        String href = target.getAttribute("href");
        href = href.split("/")[href.split("/").length - 1];
        assertThat("downloaded file exists in desired destination",isDownloaded(href),is(true));
    }

}
