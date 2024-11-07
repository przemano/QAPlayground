package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TagsInputBox extends Page {

	public static final String URL = "/apps/tags-input-box/";
	public static final String AssertMsg_DiscrepancyTagsNumber = "Discrepancy in the number of tags";
	
	private static final String XPATH_REMOVEICON = "./i[contains(@onclick,'remove')]";
	
	
	@FindBy(xpath = "//*[@role='main']//ul/input")
	private WebElement TagsInputField;
	
	@FindBy(xpath = "//*[@role='main']//ul/li")
	private List<WebElement> TagList;

	@FindBy(xpath = "//*[@role='main']//*[contains(@class,'details')]//span")
	private WebElement RemainingTagsNo;
	
	@FindBy(xpath = "//*[@role='main']//button[.='Remove All']")
	private WebElement RemoveAllButton;
			
	
	public TagsInputBox(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	public TagsInputBox addTag(String tag) {
		TagsInputField.sendKeys(tag);
		TagsInputField.sendKeys(Keys.ENTER);
		return this;
	}

	public String takeLastTag() {
		return TagList.get(TagList.size()-1).getText();
	}

	public TagsInputBox removeLastTag() {
		TagList.get(TagList.size()-1).findElement(By.xpath(XPATH_REMOVEICON)).click();
		return this;
	}

	public int takeReainingTagsNo() {
		return Integer.valueOf(RemainingTagsNo.getText());
	}

	public TagsInputBox removeAll() {
		RemoveAllButton.click();
		return this;
		
	}
	
}
