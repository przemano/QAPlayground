package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NestedIframePage extends Page{

	public static final String URL ="/apps/iframe/";
	
	
	@FindBy(xpath = "//*[contains(@class,'btn-green-outline')]")
	WebElement ClickMeButton;
	
	@FindBy(id = "msg")
	WebElement ButtonClickedLabel;
	
	@FindBy(id = "frame1")
	WebElement Iframe1;
	
	@FindBy(id = "frame2")
	WebElement Iframe2;
	
	
	public NestedIframePage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	public NestedIframePage clickClickMeButton() {
		goToIframe2();
		ClickMeButton.click();
		driver.switchTo().defaultContent();
		return this;
	}

	public String takeButtonClickedLabel()
	{
		goToIframe2();
		String label = ButtonClickedLabel.getText();
		driver.switchTo().defaultContent();
		return label;
	}
	
	private void goToIframe2()
	{
		driver.switchTo().frame(Iframe1);
		driver.switchTo().frame(Iframe2);
	}
	
}
