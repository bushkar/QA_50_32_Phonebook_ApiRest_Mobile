package mobile_tests;

import data_providers.ContactDataProvider;
import dto.Contact;
import dto.TokenDto;
import dto.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.*;

import java.lang.reflect.Method;

import static utils.ContactFactory.positiveContact;
import static utils.PropertiesReader.getProperty;

public class EditContactTests extends TestBase {
    LoginRegistrationScreen loginRegistrationScreen;
    ContactListScreen contactListScreen;
    TokenDto tokenDto;
    EditContactScreen editContactScreen;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void login() {
        loginRegistrationScreen = new LoginRegistrationScreen(driver);
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginRegistrationScreen.typeLoginRegistrationForm(user);
        loginRegistrationScreen.clickBtnLogin();
        contactListScreen = new ContactListScreen(driver);
        try {
            if (contactListScreen.validateTextContactListScreenAfterRegistration("No Contacts. Add One more!", 5)) {
                Contact contact = positiveContact();
                contactListScreen.clickBtnPlus();
                AddNewContactScreen addNewContactScreen = new AddNewContactScreen(driver);
                addNewContactScreen.typeContactForm(contact);
                addNewContactScreen.clickBtnCreate();
            }
        } catch (Exception e) {
        }
    }

    @Test
    public void editFirstContactPositiveTest() {
        Contact contact = positiveContact();
        contactListScreen.editFirstContact();
        editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditContactForm(contact);
        editContactScreen.clickBtnUpdate();
        Assert.assertTrue(contactListScreen
                .isTextInMessageContactWasUpdatedPresent("Contact was updated!", 5));

    }

    @Test
    public void editFirstContactPositive_WithValidateFields_Test() {
        Contact contact = positiveContact();
        contactListScreen.editFirstContact();
        editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditContactForm(contact);
        editContactScreen.clickBtnUpdate();
        contactListScreen.clickFirstContact();
        ContactScreen contactScreen = new ContactScreen(driver);
        softAssert.assertTrue(contactScreen.validateTextInNameTxt(contact.getName(), 5)
                , "validate Name in DetailCard");
        softAssert.assertTrue(contactScreen.validateTextInLastNameTxt(contact.getLastName(), 5)
                , "validate LastName in DetailCard");
        softAssert.assertTrue(contactScreen.validateTextInEmailTxt(contact.getEmail(), 5)
                , "validate Email in DetailCard");
        softAssert.assertTrue(contactScreen.validateTextInPhoneTxt(contact.getPhone(), 5)
                , "validate Phone in DetailCard");
        softAssert.assertTrue(contactScreen.validateTextInAddressTxt(contact.getAddress(), 5)
                , "validate Address in DetailCard");
//        softAssert.assertTrue(contactScreen.validateTextInDescTxt(contact.getDescription(), 5)
//                , "validate Description in DetailCard");
        softAssert.assertAll();
    }

    @Test(dataProvider = "dataProviderFromFile_WrongEmail",
            dataProviderClass = ContactDataProvider.class)
    public void editFirstContactNegative_WrongEmail_Test(Method method, Contact contact) {
        contactListScreen.editFirstContact();
        editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditContactForm(contact);
        editContactScreen.clickBtnUpdate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("email=must be a well-formed email address", 5));
    }

    @Test(dataProvider = "dataProviderFromFile_WrongPhone",
            dataProviderClass = ContactDataProvider.class)
    public void editFirstContactNegative_WrongPhone_Test(Method method, Contact contact) {
        contactListScreen.editFirstContact();
        editContactScreen = new EditContactScreen(driver);
        editContactScreen.typeEditContactForm(contact);
        editContactScreen.clickBtnUpdate();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateTextInError("phone=Phone number must contain only digits!"
                        + " And length min 10, max 15!", 5));
    }
}
