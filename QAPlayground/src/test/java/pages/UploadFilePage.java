package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.net.URL;

public class UploadFilePage extends Page{

    public static final String URL = "/apps/upload/";

    @FindBy(id = "file-input")
    private WebElement FileInputField;

    @FindBy(xpath = "//figcaption")
    private WebElement FileNameLabel;

    public UploadFilePage(WebDriver driver) {
        super(driver);
    }

    public UploadFilePage selectAndUploadImage(String imageName) {
        URL resource = getClass().getResource("/" + imageName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found in resources: " + imageName);
        }

        File file = new File(resource.getFile());
        FileInputField.sendKeys(file.getAbsolutePath());

        return this;
    }

    public String takeFileName() {
        return wait.until(ExpectedConditions.visibilityOf(FileNameLabel)).getText();
    }
}
