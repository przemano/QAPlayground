package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DynamicTablePage extends Page {

	
	public static final String URL = "/apps/dynamic-table/";
	
	private static final String XPATH_SUPERHERO = ".//*[contains(@class,'ml-4')]//*[1]"; //".//*[contains(@class,'ml-4')]//*[contains(@class,'text-white-900')]";
	private static final String XPATH_EMAIL = ".//*[contains(@class,'ml-4')]//*[2]"; //".//*[contains(@class,'ml-4')]//*[contains(@class,'text-gray-500')]";
	private static final String XPATH_ICON = ".//img";
	private static final String XPATH_STATUS = ".//td[2]/span";
	private static final String XPATH_REALNAME = ".//td[3]/span";
	
	
	@FindBy(xpath = "//*[@id='tbody']/tr")
	private List<WebElement> TableRowList;
	
	public DynamicTablePage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	public String takeRealNameOfUser(String usersuperhero) {
		waitForTableData();
		WebElement userRow = findUserRow(usersuperhero);
		return takeUserRealName(userRow);
		
	}

	private String takeUserRealName(WebElement userRow) {
		return userRow.findElement(By.xpath(XPATH_REALNAME)).getText();
		
	}

	private void waitForTableData()
	{
		wait.until(ExpectedConditions.visibilityOfAllElements(TableRowList));
	}

	private WebElement findUserRow(String usersuperhero)
	{
		return
			TableRowList.stream()
			.filter(row -> row.findElement(By.xpath(XPATH_SUPERHERO)).getText().equals(usersuperhero))
			.findFirst()
			.orElseThrow(() -> new NotFoundException("Table row for superhero user: '" + usersuperhero + "' not found on list."));
	}
	
}
