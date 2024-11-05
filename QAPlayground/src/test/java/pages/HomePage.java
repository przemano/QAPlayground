package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import config.Config;

public class HomePage extends Page {

	@FindBy(xpath = "//a[contains(@class,'card-course')]")
	private List<WebElement> AppList;
	
	public HomePage(WebDriver driver) {
		super(driver);
		this.url = "";
	}

	public HomePage open() {
		
        driver.get(Config.BaseURL);
        wait.until(ExpectedConditions.urlToBe(Config.BaseURL));
        return this;
	}
	
	public HomePage goTo(String url)
	{
		
		//System.out.println(AppList.stream().map(app -> app.getAttribute("href")).collect(Collectors.joining(",")));
		
		AppList.stream()
			.filter(app -> app.getAttribute("href").contains(url))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No link found with href: '"+ url + "'."))
			.click();

		wait.until(ExpectedConditions.urlContains(url));
		return this;
	}

	public HomePage goTo(Page page)
	{
		return goTo(page.getURL());
	}

}
