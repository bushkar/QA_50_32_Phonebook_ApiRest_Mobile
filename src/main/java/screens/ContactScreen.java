package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ContactScreen extends BaseScreen {
    public ContactScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.sheygam.contactapp:id/nameTxt")
    WebElement nameTxt;
    @AndroidFindBy(id = "com.sheygam.contactapp:id/lastNameTxt")
    WebElement lastNameTxt;
    @AndroidFindBy(id = "com.sheygam.contactapp:id/emailTxt")
    WebElement emailTxt;
    @AndroidFindBy(id = "com.sheygam.contactapp:id/phoneTxt")
    WebElement phoneTxt;
    @AndroidFindBy(id = "com.sheygam.contactapp:id/addressTxt")
    WebElement addressTxt;
    @AndroidFindBy(id = "com.sheygam.contactapp:id/descTxt")
    WebElement descTxt;

    public boolean validateTextInNameTxt(String text, int time) {
        return isTextInElementPresent(nameTxt, text, time);
    }

    public boolean validateTextInLastNameTxt(String text, int time) {
        return isTextInElementPresent(lastNameTxt, text, time);
    }

    public boolean validateTextInEmailTxt(String text, int time) {
        return isTextInElementPresent(emailTxt, text, time);
    }

    public boolean validateTextInPhoneTxt(String text, int time) {
        return isTextInElementPresent(phoneTxt, text, time);
    }

    public boolean validateTextInAddressTxt(String text, int time) {
        return isTextInElementPresent(addressTxt, text, time);
    }

    public boolean validateTextInDescTxt(String text, int time) {
        return isTextInElementPresent(descTxt, text, time);
    }
}