package ru.netology.testes;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.GenerateData;
import ru.netology.data.User;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class EnterTest {
    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void RegisteredUserLogin() {
        User userInfo = GenerateData.Registration.generateUser("active");
        GenerateData.Registration.sendRequest(userInfo);
        $("[data-test-id='login'] input").setValue(userInfo.getLogin());
        $("[data-test-id='password'] input").setValue(userInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldBe(Condition.exactText("Личный кабинет"));


    }

    @Test
    void BlockedUserLogin() {
        User userInfo = GenerateData.Registration.generateUser("blocked");
        GenerateData.Registration.sendRequest(userInfo);
        $("[data-test-id='login'] input").setValue(userInfo.getLogin());
        $("[data-test-id='password'] input").setValue(userInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Пользователь заблокирован"));


    }

    @Test
    void InvalidPassword() {
        User userInfo = GenerateData.Registration.generateUser("active");
        GenerateData.Registration.sendRequest(userInfo);
        var anotherPassword = GenerateData.generatePassword();
        $("[data-test-id='login'] input").setValue(userInfo.getLogin());
        $("[data-test-id='password'] input").setValue(anotherPassword);
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void InvalidLogin() {
        User userInfo = GenerateData.Registration.generateUser("active");
        GenerateData.Registration.sendRequest(userInfo);
        var anotherLogin = GenerateData.generateLogin();
        $("[data-test-id='login'] input").setValue(anotherLogin);
        $("[data-test-id='password'] input").setValue(userInfo.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }


}