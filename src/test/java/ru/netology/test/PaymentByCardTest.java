package ru.netology.test;

import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.page.PurchasePage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.database.DbHelper.*;


public class PaymentByCardTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @BeforeEach
    void openPage() {
        open("http://localhost:8080/");
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        cleanAllTables();
    }


    // Позитивные сценарии
    @Nested
    public class PositiveScenarios {

        @Story("Позитивные сценарии при оплате картой")
        @Test
        @DisplayName("Покупка картой Approved")
        public void shouldPayWithApprovedCard() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getApprovedCard();
            purchasePage.sendData(info);
            purchasePage.successNotificationVisible();

            var paymentInfo = getInfoFromPaymentTable();
            var orderInfo = getInfoFromOrderTable();

            var expected = "APPROVED";
            var actual = paymentInfo.getStatus();

            assertEquals(expected, actual);

            assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
        }
    }

    // Негативные сценарии
    @Nested
    public class NegativeScenarios {

        @Story("Пустая форма при оплате картой")
        @Test
        @DisplayName("Пустая форма")
        public void shouldNotSendEmptyForm() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            purchasePage.emptyFields();
        }


        @Story("Валидация поля Номер карты при оплате картой")
        @Test
        @DisplayName("Номер, содержащий буквы")
        public void shouldWarnCardNumberWithLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardNumberWithLetters();
            purchasePage.cardNumberFieldInvalidFormat(info);
        }


        @Story("Валидация поля Номер карты при оплате картой")
        @Test
        @DisplayName("Номер, содержащий иные спецсимволы и знаки препинания (кроме цифр)")
        public void shouldWarnCardNumberWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardNumberWithSpecialSymbols();
            purchasePage.cardNumberFieldInvalidFormat(info);
        }


        @Story("Валидация поля Номер карты при оплате картой")
        @Test
        @DisplayName("Неполный номер")
        public void shouldWarnCardNumberFewDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardNumberFewDigits();
            purchasePage.cardNumberFieldInvalidFormat(info);
        }


        @Story("Валидация поля Номер карты при оплате картой")
        @Test
        @DisplayName("Покупка картой Declined")
        public void shouldNotPurchaseWithDeclinedCard() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getDeclinedCard();
            purchasePage.sendData(info);
            purchasePage.errorNotificationVisible();

            var paymentInfo = getInfoFromPaymentTable();
            var orderInfo = getInfoFromOrderTable();

            var expected = "DECLINED";
            var actual = paymentInfo.getStatus();

            assertEquals(expected, actual);

            assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
        }


        @Story("Валидация поля Номер карты при оплате картой")
        @Test
        @DisplayName("Невалидный номер")
        public void shouldWarnCardNumberInvalid() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardInvalidNumber();
            purchasePage.sendData(info);

            purchasePage.errorNotificationVisible();
        }


        @Story("Валидация поля Месяц при оплате картой")
        @Test
        @DisplayName("Месяц, содержащий буквы")
        public void shouldWarnMonthWithLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardMonthWithLetters();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Месяц при оплате картой")
        @Test
        @DisplayName("Месяц, содержащий иные спецсимволы и знаки препинания (кроме цифр)")
        public void shouldWarnMonthWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardMonthWithSpecialSymbols();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Месяц при оплате картой")
        @Test
        @DisplayName("Невалидный месяц, верхнее граничное значение")
        public void shouldWarnMonthUpperBound() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardMonthUpperBound();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Месяц при оплате картой")
        @Test
        @DisplayName("Невалидный месяц, нижнее граничное значение")
        public void shouldWarnMonthLowerBound() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardMonthLowerBound();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Месяц при оплате картой")
        @Test
        @DisplayName("Неполный месяц")
        public void shouldWarnMonthFewDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardMonthFewDigits();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Год при оплате картой")
        @Test
        @DisplayName("Год, содержащий буквы")
        public void shouldWarnYearWithLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardYearWithLetters();
            purchasePage.yearFieldInvalidFormat(info);
        }


        @Story("Валидация поля Год при оплате картой")
        @Test
        @DisplayName("Год, содержащий иные спецсимволы и знаки препинания (кроме цифр)")
        public void shouldWarnYearWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardYearWithSpecialSymbols();
            purchasePage.yearFieldInvalidFormat(info);
        }


        @Story("Валидация поля Год при оплате картой")
        @Test
        @DisplayName("Невалидный год, нижнее граничное значение")
        public void shouldWarnYearLowerBound() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardYearLowerBound();
            purchasePage.yearFieldInvalidFormat(info);
        }


        @Story("Валидация поля Год при оплате картой")
        @Test
        @DisplayName("Неполный год")
        public void shouldWarnYearFewDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardYearFewDigits();
            purchasePage.yearFieldInvalidFormat(info);
        }


        @Story("Валидация поля Владелец при оплате картой")
        @Test
        @DisplayName("Имя, содержащее цифры")
        public void shouldWarnOwnerWithDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardOwnerWithDigits();
            purchasePage.ownerFieldInvalidFormat(info);
        }


        @Story("Валидация поля Владелец при оплате картой")
        @Test
        @DisplayName("Имя, содержащее иные спецсимволы и знаки препинания")
        public void shouldWarnOwnerWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardOwnerWithSpecialSymbols();
            purchasePage.ownerFieldInvalidFormat(info);
        }


        @Story("Валидация поля Владелец при оплате картой")
        @Test
        @DisplayName("Имя на русском языке")
        public void shouldWarnOwnerWithRussianLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardOwnerWithRussianLetters();
            purchasePage.ownerFieldInvalidFormat(info);
        }


        @Story("Валидация поля CVC/CVV при оплате картой")
        @Test
        @DisplayName("CVC/CVV, содержащее буквы")
        public void shouldWarnCvcWithLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardCvcWithLetters();
            purchasePage.cvcFieldInvalidFormat(info);
        }


        @Story("Валидация поля CVC/CVV при оплате картой")
        @Test
        @DisplayName("CVC/CVV, содержащее иные спецсимволы и знаки препинания (кроме цифр)")
        public void shouldWarnCvcWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardCvcWithSpecialSymbols();
            purchasePage.cvcFieldInvalidFormat(info);
        }


        @Story("Валидация поля CVC/CVV при оплате картой")
        @Test
        @DisplayName("Неполный номер CVC/CVV")
        public void shouldWarnCvcWithFewDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.cardPayment();

            var info = DataHelper.getCardCvcFewDigits();
            purchasePage.cvcFieldInvalidFormat(info);
        }
    }
}
