package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String number;
        private String month;
        private String year;
        private String owner;
        private String cvc;
    }


    private static String getSpecialSymbols() {
        Random random = new Random();
        List<String> specialSymbols = new ArrayList<>(Arrays.asList("~", "`", "!", "@", "\"", "#", "№", "$",
                ";", "%", ":", "^", "&", "?", "*", "(", ")", "_", "=", "+", "{", "}", "[", "]", "/", "|", "\\",
                "'", "<", ">", ",", "."));
        var fistSymbol = specialSymbols.get(random.nextInt(specialSymbols.size()));
        var secondSymbol = specialSymbols.get(random.nextInt(specialSymbols.size()));
        return fistSymbol + secondSymbol;
    }


    // Поле номер карты

    private static final String approvedCard = "1111 2222 3333 4444";
    private static final String declinedCard = "5555 6666 7777 8888";


    private static String getInvalidCardWithLetters() {
        Faker faker = new Faker();
        return faker.letterify("1111 2222 3333 ????");
    }

    private static String getInvalidCardWithSpecialSymbols() {
        return getCardWithFewDigits() + getSpecialSymbols();
    }

    private static String getCardWithFewDigits() {
        return approvedCard.substring(0, 17);
    }

    private static String getRandomCard() {
        Faker faker = new Faker();
        return faker.numerify("#### #### #### ####");
    }


    // Поле месяц

    private static String getValidMonth() {
        Faker faker = new Faker();
        DecimalFormat dF = new DecimalFormat("00");
        var month = faker.number().numberBetween(1, 12);
        return dF.format(month);
    }

    private static String getInvalidMonthWithLetters() {
        Faker faker = new Faker();
        return faker.letterify("1?");
    }

    private static String getInvalidMonthUpperBound() {
        return "14";
    }

    private static String getInvalidMonthLowerBound() {
        return "00";
    }

    private static String getInvalidMonthFewDigits() {
        Faker faker = new Faker();
        return getValidMonth().substring(0, 0);
    }


    // Поле год

    private static String getValidYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getInvalidYearWithLetters() {
        Faker faker = new Faker();
        return faker.letterify("2?");
    }

    private static String getInvalidYearLowerBound() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getInvalidYearFewDigits() {
        Faker faker = new Faker();
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy")).substring(0, 1);
    }


    // Поле владелец

    private static String getValidOwner() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    private static String getInvalidOwnerWithDigits() {
        Faker faker = new Faker();
        return faker.name().fullName() + faker.number().digits(2);
    }

    private static String getInvalidOwnerWithSpecialSymbols() {
        Faker faker = new Faker();
        return faker.name().fullName() + getSpecialSymbols();
    }

    private static String getInvalidOwnerRussianLetters() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }


    // Поле CVC/CVV

    private static String getValidCvc() {
        Faker faker = new Faker();
        return faker.number().digits(3);
    }

    private static String getInvalidCvcWithLetters() {
        Faker faker = new Faker();
        return faker.number().digits(2) + faker.letterify("?");
    }

    private static String getInvalidCvcFewDigits() {
        Faker faker = new Faker();
        return faker.number().digits(2);
    }


    public static CardInfo getApprovedCard() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(declinedCard, getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardNumberWithLetters() {
        return new CardInfo(getInvalidCardWithLetters(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardNumberWithSpecialSymbols() {
        return new CardInfo(getInvalidCardWithSpecialSymbols(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardNumberFewDigits() {
        return new CardInfo(getCardWithFewDigits(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardInvalidNumber() {
        return new CardInfo(getRandomCard(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardMonthWithLetters() {
        return new CardInfo(approvedCard, getInvalidMonthWithLetters(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardMonthWithSpecialSymbols() {
        return new CardInfo(approvedCard, getSpecialSymbols(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardMonthUpperBound() {
        return new CardInfo(approvedCard, getInvalidMonthUpperBound(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardMonthLowerBound() {
        return new CardInfo(approvedCard, getInvalidMonthLowerBound(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardMonthFewDigits() {
        return new CardInfo(approvedCard, getInvalidMonthFewDigits(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardYearWithLetters() {
        return new CardInfo(approvedCard, getValidMonth(), getInvalidYearWithLetters(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardYearWithSpecialSymbols() {
        return new CardInfo(approvedCard, getValidMonth(), getSpecialSymbols(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardYearLowerBound() {
        return new CardInfo(approvedCard, getValidMonth(), getInvalidYearLowerBound(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardYearFewDigits() {
        return new CardInfo(approvedCard, getValidMonth(), getInvalidYearFewDigits(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getCardOwnerWithDigits() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getInvalidOwnerWithDigits(), getValidCvc());
    }

    public static CardInfo getCardOwnerWithSpecialSymbols() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getInvalidOwnerWithSpecialSymbols(), getValidCvc());
    }

    public static CardInfo getCardOwnerWithRussianLetters() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getInvalidOwnerRussianLetters(), getValidCvc());
    }

    public static CardInfo getCardCvcWithLetters() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidOwner(), getInvalidCvcWithLetters());
    }

    public static CardInfo getCardCvcWithSpecialSymbols() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidOwner(), getSpecialSymbols());
    }

    public static CardInfo getCardCvcFewDigits() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidOwner(), getInvalidCvcFewDigits());
    }
}
