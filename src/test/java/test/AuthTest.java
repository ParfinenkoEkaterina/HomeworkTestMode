package test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataGenerator.Registration.getRegisteredUser;
import static data.DataGenerator.Registration.getUser;
import static data.DataGenerator.getRandomLogin;
import static data.DataGenerator.getRandomPassword;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin()); // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword()); //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        $("[data-test-id='action-login']").click(); //  пользователя registeredUser
        $("h2.heading").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] .input__control").setValue(notRegisteredUser.getLogin());// TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        $("[data-test-id='password'] .input__control").setValue(notRegisteredUser.getPassword());//  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());// TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword()); //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] .input__control").setValue(wrongLogin);// TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword()); //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        $("[data-test-id='action-login']").click(); //  "Пароль" - пользователя registeredUser
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());// TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        $("[data-test-id='password'] .input__control").setValue(wrongPassword);//  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        $("[data-test-id='action-login']").click();//  "Пароль" - переменную wrongPassword
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}
