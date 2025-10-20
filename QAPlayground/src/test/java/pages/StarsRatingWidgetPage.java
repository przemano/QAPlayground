package pages;

import config.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class StarsRatingWidgetPage extends Page {


	public static final String URL = "/apps/rating/";

	@FindBy(xpath = "//*[contains(@class, 'stars')]//label")
	private List<WebElement> StarsButtonList;
	@FindBy(xpath = "//*[contains(@class, 'footer')]//*[contains(@class, 'text')]")
	private WebElement DescriptionLabel;
	@FindBy(xpath = "//*[contains(@class, 'footer')]//*[contains(@class, 'numb')]")
	private WebElement NumberLabel;
	@FindBy(xpath = "//*[contains(@class, 'emojis')]//img")
	private List<WebElement> ImageList;

	public StarsRatingWidgetPage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}


	public StarsRatingWidgetPage clickStars(int stars) {
		StarsButtonList.stream()
				.filter(label -> Integer.valueOf(label.getAttribute("for").replace("star-", "")) == stars)
				.findFirst()
				.orElseThrow(() ->  new NoSuchElementException("No element with value :" + stars))
				.click();

	return this;
	}

	public String takeDescription() {
		return getBeforeContent(DescriptionLabel);
	}

	private String getBeforeContent(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content')";
		return ((String) Objects.requireNonNull(js.executeScript(script, element))).replace("\"", "");
	}

	public int takeNumber() {
		return Integer.parseInt(getBeforeContent(NumberLabel).replace(Config.StarsRatingWidget.NumberLabelSuffix, ""));
	}

	public String takeImage() {
		return ImageList
			.stream()
			.filter(WebElement::isDisplayed)
			.map(img ->  Optional.ofNullable(img.getAttribute("src")).orElse(""))
			.findFirst()
			.orElseThrow(() -> new NoSuchElementException("There is no image displayed."));
	}
}
