package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker fakerEn = new Faker(new Locale("en"));
    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String holder;
        String codCvcCvv;
    }

    // --- БАЗОВЫЕ ВАЛИДНЫЕ ДАННЫЕ ---
    public static String getApprovedCardNumber() { return approvedCard; }
    public static String getDeclinedCardNumber() { return declinedCard; }
    public static String getValidMonth() { return LocalDate.now().format(DateTimeFormatter.ofPattern("MM")); }
    public static String getValidYear() { return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")); }
    public static String getValidHolder() { return fakerEn.name().firstName().toUpperCase() + " " + fakerEn.name().lastName().toUpperCase(); }
    public static String getValidCVC() { return "123"; }

    // --- ПОЗИТИВНЫЕ СЦЕНАРИИ ---
    public static CardInfo getApprovedCard() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(declinedCard, getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getUnknownCard() {
        return new CardInfo("4444 4444 4444 4443", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    // --- ТЕСТЫ НОМЕРА КАРТЫ ---
    public static CardInfo getCardNumberZero() {
        return new CardInfo("0000 0000 0000 0000", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getCardNumberLess() {
        return new CardInfo("4444 4444 4444 444", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getCardNumberSymbol() {
        return new CardInfo("4444 4444 4444 444@", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getCardNumberCyrillic() {
        return new CardInfo("4444 4444 4444 444Я", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getCardNumberLatin() {
        return new CardInfo("4444 4444 4444 444Q", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getCardNumberEmpty() {
        return new CardInfo("", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    // --- ТЕСТЫ МЕСЯЦА ---
    public static CardInfo getMonth13() {
        return new CardInfo(approvedCard, "13", getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getMonthZero() {
        return new CardInfo(approvedCard, "0", getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getMonthTwoZeros() {
        return new CardInfo(approvedCard, "00", getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getMonthSymbol() {
        return new CardInfo(approvedCard, "@#", getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getMonthLatin() {
        return new CardInfo(approvedCard, "MM", getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getMonthOneDigit() {
        return new CardInfo(approvedCard, "5", getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getMonthEmpty() {
        return new CardInfo(approvedCard, "", getValidYear(), getValidHolder(), getValidCVC());
    }
    public static CardInfo getExpiredMonth() {
        var date = LocalDate.now().minusMonths(1);
        return new CardInfo(approvedCard, date.format(DateTimeFormatter.ofPattern("MM")),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yy")), getValidHolder(), getValidCVC());
    }

    // --- ТЕСТЫ ГОДА ---
    public static CardInfo getYearLessThanCurrent() {
        return new CardInfo(approvedCard, getValidMonth(), LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy")), getValidHolder(), getValidCVC());
    }
    public static CardInfo getYearMoreThanFiveYears() {
        return new CardInfo(approvedCard, getValidMonth(), LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy")), getValidHolder(), getValidCVC());
    }
    public static CardInfo getYearSymbol() {
        return new CardInfo(approvedCard, getValidMonth(), "!@", getValidHolder(), getValidCVC());
    }
    public static CardInfo getYearLatin() {
        return new CardInfo(approvedCard, getValidMonth(), "YY", getValidHolder(), getValidCVC());
    }
    public static CardInfo getYearOneDigit() {
        return new CardInfo(approvedCard, getValidMonth(), "5", getValidHolder(), getValidCVC());
    }
    public static CardInfo getYearEmpty() {
        return new CardInfo(approvedCard, getValidMonth(), "", getValidHolder(), getValidCVC());
    }

    // --- ТЕСТЫ ВЛАДЕЛЬЦА ---
    public static CardInfo getHolderCyrillic() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), "ИВАН ИВАНОВ", getValidCVC());
    }
    public static CardInfo getHolderSymbol() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), "IVAN@!", getValidCVC());
    }
    public static CardInfo getHolderDigit() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), "IVAN123", getValidCVC());
    }
    public static CardInfo getHolderOneLetter() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), "I", getValidCVC());
    }
    public static CardInfo getHolderMoreThan100() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), "I".repeat(101), getValidCVC());
    }
    public static CardInfo getHolderEmpty() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), "", getValidCVC());
    }

    // --- ТЕСТЫ CVC ---
    public static CardInfo getCVCSymbol() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidHolder(), "!@");
    }
    public static CardInfo getCVCLetter() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidHolder(), "ABC");
    }
    public static CardInfo getCVConeDigit() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidHolder(), "1");
    }
    public static CardInfo getCVCtwoDigit() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidHolder(), "12");
    }
    public static CardInfo getCVCempty() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getValidHolder(), "");
    }
}
