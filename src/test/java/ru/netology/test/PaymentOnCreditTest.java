package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.database.DbHelper.*;

public class PaymentOnCreditTest {

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

        @Story("Позитивные сценарии при оплате в кредит")
        @Test
        @DisplayName("Покупка картой Approved")
        public void shouldPayWithApprovedCard() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getApprovedCard();
            purchasePage.sendData(info);
            purchasePage.successNotificationVisible();

            var creditInfo = getInfoFromCreditRequestTable();
            var orderInfo = getInfoFromOrderTable();

            var expected = "APPROVED";
            var actual = creditInfo.getStatus();

            assertEquals(expected, actual);

            assertEquals(creditInfo.getBank_id(), orderInfo.getPayment_id());
        }
    }

    // Негативные сценарии
    @Nested
    public class NegativeScenarios {

        @Story("Пустая форма при оплате в кредит")
        @Test
        @DisplayName("Пустая форма")
        public void shouldNotSendEmptyForm() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            purchasePage.emptyFields();
        }

        @Story("Валидация поля Номер карты при оплате в кредит")
        @Test
        @DisplayName("Номер, содержащий буквы")
        public void shouldWarnCardNumberWithLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardNumberWithLetters();
            purchasePage.cardNumberFieldInvalidFormat(info);
        }


        @Story("Валидация поля Номер карты при оплате в кредит")
        @Test
        @DisplayName("Номер, содержащий иные спецсимволы и знаки препинания (кроме цифр)")
        public void shouldWarnCardNumberWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardNumberWithSpecialSymbols();
            purchasePage.cardNumberFieldInvalidFormat(info);
        }


        @Story("Валидация поля Номер карты при оплате в кредит")
        @Test
        @DisplayName("Неполный номер")
        public void shouldWarnCardNumberFewDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardNumberFewDigits();
            purchasePage.cardNumberFieldInvalidFormat(info);
        }


        @Story("Валидация поля Номер карты при оплате в кредит")
        @Test
        @DisplayName("Покупка картой Declined")
        public void shouldNotPurchaseWithDeclinedCard() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getDeclinedCard();
            purchasePage.sendData(info);
            purchasePage.errorNotificationVisible();

            var creditInfo = getInfoFromCreditRequestTable();
            var orderInfo = getInfoFromOrderTable();

            var expected = "DECLINED";
            var actual = creditInfo.getStatus();

            assertEquals(expected, actual);

            assertEquals(creditInfo.getBank_id(), orderInfo.getPayment_id());
        }


        @Story("Валидация поля Номер карты при оплате в кредит")
        @Test
        @DisplayName("Невалидный номер")
        public void shouldWarnCardNumberInvalid() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardInvalidNumber();
            purchasePage.sendData(info);

            purchasePage.errorNotificationVisible();
        }


        @Story("Валидация поля Месяц при оплате в кредит")
        @Test
        @DisplayName("Месяц, содержащий буквы")
        public void shouldWarnMonthWithLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardMonthWithLetters();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Месяц при оплате в кредит")
        @Test
        @DisplayName("Месяц, содержащий иные спецсимволы и знаки препинания (кроме цифр)")
        public void shouldWarnMonthWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardMonthWithSpecialSymbols();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Месяц при оплате в кредит")
        @Test
        @DisplayName("Невалидный месяц, верхнее граничное значение")
        public void shouldWarnMonthUpperBound() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardMonthUpperBound();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Месяц при оплате в кредит")
        @Test
        @DisplayName("Невалидный месяц, нижнее граничное значение")
        public void shouldWarnMonthLowerBound() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardMonthLowerBound();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Месяц при оплате в кредит")
        @Test
        @DisplayName("Неполный месяц")
        public void shouldWarnMonthFewDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardMonthFewDigits();
            purchasePage.monthFieldInvalidFormat(info);
        }


        @Story("Валидация поля Год при оплате в кредит")
        @Test
        @DisplayName("Год, содержащий буквы")
        public void shouldWarnYearWithLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardYearWithLetters();
            purchasePage.yearFieldInvalidFormat(info);
        }


        @Story("Валидация поля Год при оплате в кредит")
        @Test
        @DisplayName("Год, содержащий иные спецсимволы и знаки препинания (кроме цифр)")
        public void shouldWarnYearWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardYearWithSpecialSymbols();
            purchasePage.yearFieldInvalidFormat(info);
        }


        @Story("Валидация поля Год при оплате в кредит")
        @Test
        @DisplayName("Невалидный год, нижнее граничное значение")
        public void shouldWarnYearLowerBound() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardYearLowerBound();
            purchasePage.yearFieldInvalidFormat(info);
        }


        @Story("Валидация поля Год при оплате в кредит")
        @Test
        @DisplayName("Неполный год")
        public void shouldWarnYearFewDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardYearFewDigits();
            purchasePage.yearFieldInvalidFormat(info);
        }


        @Story("Валидация поля Владелец при оплате в кредит")
        @Test
        @DisplayName("Имя, содержащее цифры")
        public void shouldWarnOwnerWithDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardOwnerWithDigits();
            purchasePage.ownerFieldInvalidFormat(info);
        }


        @Story("Валидация поля Владелец при оплате в кредит")
        @Test
        @DisplayName("Имя, содержащее иные спецсимволы и знаки препинания")
        public void shouldWarnOwnerWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardOwnerWithSpecialSymbols();
            purchasePage.ownerFieldInvalidFormat(info);
        }


        @Story("Валидация поля Владелец при оплате в кредит")
        @Test
        @DisplayName("Имя на русском языке")
        public void shouldWarnOwnerWithRussianLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardOwnerWithRussianLetters();
            purchasePage.ownerFieldInvalidFormat(info);
        }


        @Story("Валидация поля CVC/CVV при оплате в кредит")
        @Test
        @DisplayName("CVC/CVV, содержащее буквы")
        public void shouldWarnCvcWithLetters() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardCvcWithLetters();
            purchasePage.cvcFieldInvalidFormat(info);
        }


        @Story("Валидация поля CVC/CVV при оплате в кредит")
        @Test
        @DisplayName("CVC/CVV, содержащее иные спецсимволы и знаки препинания (кроме цифр)")
        public void shouldWarnCvcWithSpecialSymbols() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardCvcWithSpecialSymbols();
            purchasePage.cvcFieldInvalidFormat(info);
        }


        @Story("Валидация поля CVC/CVV при оплате в кредит")
        @Test
        @DisplayName("Неполный номер CVC/CVV")
        public void shouldWarnCvcWithFewDigits() {
            var purchasePage = new PurchasePage();
            purchasePage.creditPayment();

            var info = DataHelper.getCardCvcFewDigits();
            purchasePage.cvcFieldInvalidFormat(info);
        }
    }
}
