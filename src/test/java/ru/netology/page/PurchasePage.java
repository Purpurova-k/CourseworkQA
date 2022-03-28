package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PurchasePage {

    // Кнопки "купить" и "купить в кредит"
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    // Заголовки после нажатия кнопок
    private SelenideElement buyHeading = $(byText("Оплата по карте"));
    private SelenideElement creditHeading = $(byText("Кредит по данным карты"));

    // Поля карты и ошибки рядом с полями
    private SelenideElement cardNumberField = $x("//*[text()='Номер карты']/..//input");
    private SelenideElement cardNumberFieldError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");

    private SelenideElement monthField = $x("//*[text()='Месяц']/..//input");
    private SelenideElement monthFieldError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");

    private SelenideElement yearField = $x("//*[text()='Год']/..//input");
    private SelenideElement yearFieldError = $x("//*[text()='Год']/..//*[@class='input__sub']");

    private SelenideElement ownerField = $x("//*[text()='Владелец']/..//input");
    private SelenideElement ownerFieldError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");

    private SelenideElement cvcField = $x("//*[text()='CVC/CVV']/..//input");
    private SelenideElement cvcFieldError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");

    // Кнопка продолжить
    private SelenideElement continueButton = $("form button");

    // Уведомления
    private SelenideElement notificationSuccess = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");


    public void cardPayment() {
        buyButton.click();
        buyHeading.shouldBe(visible);
    }

    public void creditPayment() {
        creditButton.click();
        creditHeading.shouldBe(visible);
    }


    public void sendData(DataHelper.CardInfo info) {
        cardNumberField.val(info.getNumber());
        monthField.val(info.getMonth());
        yearField.val(info.getYear());
        ownerField.val(info.getOwner());
        cvcField.val(info.getCvc());
        continueButton.click();
    }

    public void emptyFields() {
        continueButton.click();
        cardNumberFieldError.shouldBe(visible);
        monthFieldError.shouldBe(visible);
        yearFieldError.shouldBe(visible);
        ownerFieldError.shouldBe(visible);
        cvcFieldError.shouldBe(visible);
    }

    public void cardNumberFieldInvalidFormat(DataHelper.CardInfo info) {
        sendData(info);
        cardNumberFieldError.shouldBe(visible);
        monthFieldError.shouldBe(hidden);
        yearFieldError.shouldBe(hidden);
        ownerFieldError.shouldBe(hidden);
        cvcFieldError.shouldBe(hidden);
    }


    public void monthFieldInvalidFormat(DataHelper.CardInfo info) {
        sendData(info);
        monthFieldError.shouldBe(visible);
        cardNumberFieldError.shouldBe(hidden);
        yearFieldError.shouldBe(hidden);
        ownerFieldError.shouldBe(hidden);
        cvcFieldError.shouldBe(hidden);
    }


    public void yearFieldInvalidFormat(DataHelper.CardInfo info) {
        sendData(info);
        yearFieldError.shouldBe(visible);
        cardNumberFieldError.shouldBe(hidden);
        monthFieldError.shouldBe(hidden);
        ownerFieldError.shouldBe(hidden);
        cvcFieldError.shouldBe(hidden);
    }


    public void ownerFieldInvalidFormat(DataHelper.CardInfo info) {
        sendData(info);
        ownerFieldError.shouldBe(visible);
        cardNumberFieldError.shouldBe(hidden);
        monthFieldError.shouldBe(hidden);
        yearFieldError.shouldBe(hidden);
        cvcFieldError.shouldBe(hidden);
    }


    public void cvcFieldInvalidFormat(DataHelper.CardInfo info) {
        sendData(info);
        cvcFieldError.shouldBe(visible);
        cardNumberFieldError.shouldBe(hidden);
        monthFieldError.shouldBe(hidden);
        yearFieldError.shouldBe(hidden);
        ownerFieldError.shouldBe(hidden);
    }


    public void successNotificationVisible() {
        notificationSuccess.shouldBe(visible, Duration.ofSeconds(10));
    }


    public void errorNotificationVisible() {
        notificationError.shouldBe(visible, Duration.ofSeconds(10));
    }
}
