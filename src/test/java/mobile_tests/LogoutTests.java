package mobile_tests;

import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.LoginRegistrationScreen;

import static utils.PropertiesReader.getProperty;

public class LogoutTests extends TestBase {
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;

    @BeforeMethod
    public void login() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
    }

    @Test
    public void logoutPositiveTest() {
        contactListScreen.logout();
        Assert.assertTrue(loginRegistrationScreen.isBtnRegistrationPresent());
    }
}