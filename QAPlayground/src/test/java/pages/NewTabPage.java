package pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewTabPage extends Page {

	
	public static final String URL = "/apps/new-tab/";
	private static final String URL_NewTab = "/apps/new-tab/new-page";
	public static final String AssertMsg_DiscrepancyLabel = "Discrepancy in new tab label";

	@FindBy(id = "open")
	private WebElement OpenButton;
	
	@FindBy(xpath = "//*[@id='wrapper']/h1")
	private WebElement NewTabLabel;
	private String originalTab;
	
	public NewTabPage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	public NewTabPage clickButtonOpenNewTab() {
		int numberOfWindowsBefore = driver.getWindowHandles().size();
		OpenButton.click();
		wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsBefore + 1));
		return this;
	}

	public String takeLabelInNewTab() {

		return NewTabLabel.getText();
	}

	public NewTabPage switchToSecondTab() {
		int numberOfTabs = driver.getWindowHandles().size();
		if(numberOfTabs != 2) {throw new IllegalArgumentException("There are expected 2 tabs, but there are " + numberOfTabs + ".");}
	
		originalTab = driver.getWindowHandle();
        Set<String> allTabs = driver.getWindowHandles();
        
        String secondTabHandler = allTabs.stream().filter(tab -> !tab.equals(originalTab)).findFirst().orElseThrow(() -> new NoSuchElementException("There is no other tab than the original one."));
        driver.switchTo().window(secondTabHandler);
        assertTrue(driver.getCurrentUrl().endsWith(URL_NewTab));
		return this;
	}

	public NewTabPage switchToFirstTab() {
		
		driver.switchTo().window(originalTab);
		assertEquals(originalTab, driver.getWindowHandle(), "Unexpected tab in use - discrepancy in windows handler");
		return this;
	}

	public NewTabPage closeSecondTab() {
		int numberOfTabs = driver.getWindowHandles().size();
		if(numberOfTabs != 2) {throw new IllegalArgumentException("There are expected 2 tabs, but there are " + numberOfTabs + ".");}
		
		driver.close();
		numberOfTabs = driver.getWindowHandles().size();
		
		assertEquals(1, numberOfTabs, "There are expected 1 tab, but there are " + numberOfTabs + ".");
		return this;
	}

	
}
