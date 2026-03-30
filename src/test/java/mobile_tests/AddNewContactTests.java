package mobile_tests;

import data_providers.ContactDataProvider;
import dto.Contact;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.ErrorScreen;
import screens.LoginRegistrationScreen;

import java.lang.reflect.Method;

import static utils.ContactFactory.positiveContact;
import static utils.PropertiesReader.getProperty;

public class AddNewContactTests extends TestBase {
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    AddNewContactScreen addNewContactScreen;

    @BeforeMethod
    public void login() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
        contactListScreen.clickBtnPlus();
        addNewContactScreen = new AddNewContactScreen(driver);
    }

    @Test
    public void addNewContactPositiveTest() {
        Contact contact = positiveContact();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(contactListScreen
                .isTextInMessageContactWasAddedPresent("Contact was added!", 5));
    }

    @Test
    public void addNewContactNegative_WrongLengthPhone_Test() {
        Contact contact = positiveContact();
        contact.setPhone("098765443");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("min 10, max 15!", 5));
    }

    @Test
    public void addNewContactNegative_EmptyName_Test() {
        Contact contact = positiveContact();
        contact.setName("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("not be blank", 5));
    }

    @Test
    public void addNewContactNegative_EmptyLastName_Test() {
        Contact contact = positiveContact();
        contact.setLastName("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("not be blank", 5));
    }

    @Test
    public void addNewContactNegative_EmptyAddress_Test() {
        Contact contact = positiveContact();
        contact.setAddress("");
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("not be blank", 5));
    }

    @Test(dataProvider = "dataProviderFromFile_WrongEmail",
            dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegative_WrongEmail_Test(Method method, Contact contact) {
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("email=must be a well-formed email address", 5));
    }

    @Test(dataProvider = "dataProviderFromFile_WrongPhone",
            dataProviderClass = ContactDataProvider.class)
    public void addNewContactNegative_WrongPhone_Test(Method method, Contact contact) {
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("phone=Phone number must contain only digits!"
                        + " And length min 10, max 15!", 5));
    }
}
