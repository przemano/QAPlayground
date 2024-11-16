package pages;

import java.util.List;
import java.util.stream.IntStream;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VerifyAccountPage extends Page {

	
	public static final String URL = "/apps/verify-account/";
	
	@FindBy(xpath = "//*[contains(@class,'code-container')]//input")
	private List<WebElement> CodeFieldList;
	
	@FindBy(className = "success")
	private WebElement SuccessLabel;
	
	public VerifyAccountPage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	public VerifyAccountPage enterCodeTyping(String validcode) {
		
		IntStream.range(0, CodeFieldList.size())
		.forEach(i -> {
			CodeFieldList.get(i).clear();
			CodeFieldList.get(i).sendKeys(String.valueOf(validcode.charAt(i)));
		});
		return this;
	}

	public String takeSuccessMsg() {
		wait.until(ExpectedConditions.visibilityOf(SuccessLabel));
		return SuccessLabel.getText(); 
		
	}

	public VerifyAccountPage enterCodePressingKeyUp(String validcode) {
		IntStream.range(0, CodeFieldList.size())
		.forEach(iField -> {
			CodeFieldList.get(iField).clear();
			
			IntStream.range(0, Integer.valueOf(String.valueOf(validcode.charAt(iField))))
			.forEach(iKeyUp -> {
				CodeFieldList.get(iField).sendKeys(Keys.UP);	
			});
		});
		return this;
	}

	
}
