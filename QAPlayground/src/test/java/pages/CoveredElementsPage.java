package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CoveredElementsPage extends Page {

    public static final String URL = "/apps/covered/";
    public static final String EXPECTED_LABEL = "Mission accomplished";

    public CoveredElementsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id ="info")
    WebElement InfoLabel;

    @FindBy(id = "fugitive")
    WebElement YoufoundmeButton;


    public CoveredElementsPage clickButton() {

        YoufoundmeButton.click();
        return this;
    }

    public String readLabel() {
        return InfoLabel.getText();
    }
}
