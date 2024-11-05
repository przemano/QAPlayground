package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.HomePage;

public class BaseTest {

	
	public static WebDriver driver;
	public WebDriverWait wait;
	public String baseURL;
	
	@BeforeAll
	public static void Setup()
	{
		ChromeOptions options = new ChromeOptions();

		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--disable-search-engine-choice-screen");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-gpu");

		driver = new ChromeDriver(options);
	}
	
	@BeforeEach
	public void Open()
	{
		new HomePage(driver).open();
	}

	@AfterEach
	public void Close()
	{
		driver.quit();
	}
}
