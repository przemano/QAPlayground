package pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.Config;

public class Page {

	
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected JavascriptExecutor js;
	
	protected String url;
	
	protected Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.Timeout));
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}
	

	
	
	
}
