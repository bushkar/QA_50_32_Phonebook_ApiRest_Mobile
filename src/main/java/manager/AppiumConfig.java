package manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import utils.PropertiesReader;

import java.net.MalformedURLException;
import java.net.URL;

import static utils.PropertiesReader.*;

public class AppiumConfig {
    public static AppiumDriver createAppiumDriver(String fileName) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(getProperty(fileName, "os"))
                .setAutomationName(getProperty(fileName, "automationName"))
                .setDeviceName(getProperty(fileName, "deviceName"))
                .setAppPackage(getProperty(fileName, "appPackage"))
                .setAppActivity(getProperty(fileName, "appActivity"));
        AppiumDriver appiumDriver = null;
        try {
            appiumDriver = new AppiumDriver(new URL(getProperty(fileName, "appiumUrl")), options);
            return appiumDriver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Bad Appium URL: "
                    + getProperty(fileName, "appiumUrl"), e);
        }
    }
}
