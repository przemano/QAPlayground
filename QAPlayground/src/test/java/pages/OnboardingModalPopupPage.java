package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OnboardingModalPopupPage extends Page{
    public static final String URL = "/apps/onboarding-modal/";
    public static String MessageWnenModalDisplayed = "Welcome Peter Parker! \uD83D\uDD77\uD83C\uDF89";
    public static String MessageWnenNoDisplayed = "Application successfully launched! \uD83D\uDE80";

    @FindBy(className = "wrapper")
    WebElement Wrapper;

    @FindBy(className = "menu-btn")
    WebElement ModalCloseButtton;

    @FindBy(className = "title")
    WebElement MessageLabel;

    private static final String CSS_STYLE_MENU_DISPLAYED_ATTR = "clip-path";
    private static final String CSS_STYLE_MENU_DISPLAYED_VALUE = "circle(75%);";

    public OnboardingModalPopupPage(WebDriver driver) {
        super(driver);
    }

    public boolean isModalOpen() {
        return Wrapper.getCssValue(CSS_STYLE_MENU_DISPLAYED_ATTR).equalsIgnoreCase(CSS_STYLE_MENU_DISPLAYED_VALUE);
    }


    public void closeModal() {
        ModalCloseButtton.click();
    }

    public String takeMessage() {
        return MessageLabel.getText();
    }
}
