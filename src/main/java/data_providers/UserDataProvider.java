package data_providers;

import dto.User;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.UserFactory.positiveUser;

public class UserDataProvider {
    @DataProvider
    public Iterator<User> dataProviderFromFile_WrongUsername() {
        User user;
        List<User> list = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data_csv/data_wrong_emails.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                user = positiveUser();
                user.setUsername(line);
                list.add(user);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }

    @DataProvider
    public Iterator<User> dataProviderFromFile_WrongPassword() {
        User user;
        List<User> list = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader
                             ("src/test/resources/data_csv/data_wrong_passwords.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                user = positiveUser();
                user.setPassword(line);
                list.add(user);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO exception");
        }
        return list.listIterator();
    }
}
