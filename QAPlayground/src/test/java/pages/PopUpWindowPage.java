package pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PopUpWindowPage extends Page {

	
	public static final String URL = "/apps/popup/";
	private static final String URL_PopUp = "/apps/popup/popup";
	public static final String AssertMsg_DiscrepancyLabel = "Discrepancy in 'Button Clicked' label";

	@FindBy(id = "login")
	private WebElement OpenButton;
	
	@FindBy(xpath = "//*[@id='wrapper']/button")
	private WebElement SubmitButton;
	
	@FindBy(id = "info")
	private WebElement ButtonClickedLabel;
	
	private String originalTab;
	
	public PopUpWindowPage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	public PopUpWindowPage clickButtonOpen() {
		int numberOfWindowsBefore = driver.getWindowHandles().size();
		OpenButton.click();
		wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsBefore + 1));
		return this;
	}


	public PopUpWindowPage switchToPopup() {
		int numberOfTabs = driver.getWindowHandles().size();
		if(numberOfTabs != 2) {throw new IllegalArgumentException("There are expected 2 tabs, but there are " + numberOfTabs + ".");}
	
		originalTab = driver.getWindowHandle();
        Set<String> allTabs = driver.getWindowHandles();
        
        String secondTabHandler = allTabs.stream().filter(tab -> !tab.equals(originalTab)).findFirst().orElseThrow(() -> new NoSuchElementException("There is no other tab than the original one."));
        driver.switchTo().window(secondTabHandler);
        assertTrue(driver.getCurrentUrl().endsWith(URL_PopUp));
		return this;
	}

	public PopUpWindowPage switchToMainPage() {
		
		driver.switchTo().window(originalTab);
		assertEquals(originalTab, driver.getWindowHandle(), "Unexpected tab in use - discrepancy in windows handler");
		return this;
	}

//	public PopUpWindowPage closeSecondTab() {
//		int numberOfTabs = driver.getWindowHandles().size();
//		if(numberOfTabs != 2) {throw new IllegalArgumentException("There are expected 2 windows, but there are " + numberOfTabs + ".");}
//		
//		driver.close();
//		numberOfTabs = driver.getWindowHandles().size();
//		
//		assertEquals(1, numberOfTabs, "There are expected 1 tab, but there are " + numberOfTabs + ".");
//		return this;
//	}

	public PopUpWindowPage clickSubmitInPopupAndClosePopup() {
		SubmitButton.click();
		wait.until(ExpectedConditions.numberOfWindowsToBe(1));
		return this;
	}

	public String takeButtonClickedLabel() {
		return ButtonClickedLabel.getText();
	}

	
}
