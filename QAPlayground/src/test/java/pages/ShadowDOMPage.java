package pages;

import config.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class ShadowDOMPage extends Page {


	public static final String URL = "/apps/shadow-dom/";
	public static final int TimeoutPoolingProgressbar =  250;
	public static final int TimeoutProgressbar = 7;

	//private static final String XPATH_BoostButton =  "//*[contains(@class,'btn-green-outline')]";
	private static final String CSS_BoostButton = "button.btn-green-outline";

	private static final String XPATH_ShadowRootProgressBar = "//progress-bar";
	@FindBy(xpath = XPATH_ShadowRootProgressBar)
	private WebElement ShadowRootProgressBar;


	public ShadowDOMPage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	public ShadowDOMPage clickBoost() {

		SearchContext shadowRoot = takeShadowRoot();
		shadowRoot.findElement(By.cssSelector(CSS_BoostButton)).click();
		//shadowRoot.findElement(By.xpath(XPATH_BoostButton)).click();

		return this;
	}

	private SearchContext takeShadowRoot()
	{
		return ShadowRootProgressBar.getShadowRoot();
	}

	public ShadowDOMPage waitForProgressbarValue(String progressbarValue) {

		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(TimeoutProgressbar))
				.pollingEvery(Duration.ofMillis(TimeoutPoolingProgressbar))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		wait.until(driver -> {
			String percentage = takeProgressbarValue();

			if (percentage == null || percentage.isEmpty()) {
				return false;
			}

			return percentage.equals(Config.ShadowDOM.progressbarValue);
		});
		return this;
	}

	public String takeProgressbarValue() {
		return ShadowRootProgressBar.getAttribute("percent");
	}
}
