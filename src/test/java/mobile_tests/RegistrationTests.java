package mobile_tests;

import data_providers.UserDataProvider;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.LoginRegistrationScreen;
import screens.SplashScreen;

import java.lang.reflect.Method;

import static utils.UserFactory.positiveUser;

public class RegistrationTests extends TestBase {
    LoginRegistrationScreen loginRegistrationScreen;

    @BeforeMethod
    public void openAuthScreen() {
        new SplashScreen(driver);
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
    }

    @Test
    public void registrationPositiveTest() {
        User user = positiveUser();
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ContactListScreen(driver)
                .validateTextContactListScreenAfterRegistration("No Contacts. Add One more!", 5));
    }

    @Test
    public void registrationNegative_EmptyEmail_Test() {
        User user = positiveUser();
        user.setUsername("");
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("username=must not be blank", 5));
    }

    @Test(dataProvider = "dataProviderFromFile_WrongUsername",
            dataProviderClass = UserDataProvider.class)
    public void registrationNegative_WrongUsername_Test(Method method, User user) {
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("username=must be a well-formed email address", 5));
    }

    @Test(dataProvider = "dataProviderFromFile_WrongPassword",
            dataProviderClass = UserDataProvider.class)
    public void registrationNegative_WrongPassword_Test(Method method, User user) {
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnRegistration();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("{password= At least 8 characters;"
                        + " Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number;"
                        + " Can contain special characters [@$#^&*!]}", 5));
    }
}
