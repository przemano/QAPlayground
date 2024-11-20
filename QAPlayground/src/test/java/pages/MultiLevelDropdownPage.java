package pages;

import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultiLevelDropdownPage extends Page {

	public static final String URL = "/apps/multi-level-dropdown/";
	public static final String AssertMsg_DiscrepancyLinkURL = "Discrepancy in link URL";
	
	private static final String XPATH_LINK = "./a"; //".//a[@class='menu-item' and span[@class='icon-button']]";//"./a";
	private static final String URL_SEPARATOR = "#";

		
	@FindBy(xpath = "//*[@id='root']//li[contains(@class,'nav-item')][4]/a")
	private WebElement MultiLevelMenuLink;
			
	@FindBy(xpath = "//*[contains(@class,'dropdown')]/*[contains(@class,'menu')]")
	private WebElement MultiLevelMenu1stLevelWindow;
	
	@FindBy(xpath = "//*[contains(@class,'dropdown')]/*[contains(@class,'menu-secondary-enter-done')]")
	private WebElement MultiLevelMenu2ndLevelWindow;

	@FindBy(xpath = "//footer")
	private WebElement Footer;
	
	public MultiLevelDropdownPage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	public MultiLevelDropdownPage openDropDownMenu() {
		MultiLevelMenuLink.click();
		return this;
	}
	
	private MultiLevelDropdownPage clickMenu(String menuLabel, WebElement menuWindow)
	{
		wait.until(ExpectedConditions.visibilityOf(menuWindow));
		
		//System.out.println("... menu links: " + menuWindow.findElements(By.xpath(XPATH_LINK)).stream().map(a -> a.getText()).collect(Collectors.joining(",")));

		String prevURL = driver.getCurrentUrl();
		
		WebElement menuLink = menuWindow.findElements(By.xpath(XPATH_LINK))
			.stream()
			.filter(a -> a.getText().endsWith(menuLabel))
			.findFirst()
			.orElseThrow(()-> new NoSuchElementException("There is no menu position: " + menuLabel+"."));
		
		try {
			menuLink.click();
		} catch (ElementClickInterceptedException e) {
			//App bug fixing - footer is over menu but it should not
			moveFooterUnderMenu();
			menuLink.click();
		}
		
		wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(prevURL)));
		return this;
	}
	
	public MultiLevelDropdownPage clickMenu1stLevel(String menuLabel) {
		return clickMenu(menuLabel, MultiLevelMenu1stLevelWindow);
	}


	public MultiLevelDropdownPage clickMenu2ndLevel(String menuLabel) {
		if(menuLabel == null || menuLabel.isBlank()) return this;
		return clickMenu(menuLabel, MultiLevelMenu2ndLevelWindow);
	}

	/** Take last part of of URL started with # eg. '#!CSS' from URL 'https://qaplayground.dev/apps/multi-level-dropdown/#!CSS'
	 * 
	 * @return last part of URL started with # eg. '#!CSS'
	 */
	public String takeLastPartURL() {
		return URL_SEPARATOR +  driver.getCurrentUrl().split(URL_SEPARATOR)[1];
	}
	
	private void moveFooterUnderMenu()
	{
		js.executeScript("arguments[0].style.zIndex = '-1';", Footer);
		
	}
	
	
}
