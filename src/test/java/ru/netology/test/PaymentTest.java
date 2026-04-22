package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    private PurchasePage view;

    private final String approvedMessage = "Операция одобрена Банком.";
    private final String declinedMessage = "Ошибка! Банк отказал в проведении операции.";
    private final String formatError = "Неверный формат";
    private final String invalidDateError = "Неверно указан срок действия карты";
    private final String expiredError = "Истёк срок действия карты";
    private final String requiredError = "Поле обязательно для заполнения";

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
        var dashboardPage = new DashboardPage();
        view = dashboardPage.payBuyCard();
        SQLHelper.cleanDatabase();
    }

    // --- НОМЕР КАРТЫ ---

    @Test
    @DisplayName("Номер карты не из набора")
    void shouldDeclineWithUnknownCard() {
        view.fillingOutTheForm(DataHelper.getUnknownCard());
        view.declinedPayment(declinedMessage);
    }

    @Test
    @DisplayName("Номер карты менее 16 цифр")
    void shouldErrorShortCardNumber() {
        view.fillingOutTheForm(DataHelper.getCardNumberLess());
        view.invalidCardFormat(formatError);
    }

    @Test
    @DisplayName("Спецсимволы в поле номера карты")
    void shouldErrorSymbolsInCardNumber() {
        view.fillingOutTheForm(DataHelper.getCardNumberSymbol());
        view.invalidCardFormat(formatError);
    }

    @Test
    @DisplayName("Буквы на кириллице в поле номера карты")
    void shouldErrorCyrillicInCardNumber() {
        view.fillingOutTheForm(DataHelper.getCardNumberCyrillic());
        view.invalidCardFormat(formatError);
    }

    @Test
    @DisplayName("Буквы на латинице в поле номера карты")
    void shouldErrorLatinInCardNumber() {
        view.fillingOutTheForm(DataHelper.getCardNumberLatin());
        view.invalidCardFormat(formatError);
    }

    @Test
    @DisplayName("Пустое значение в поле номера карты")
    void shouldErrorEmptyCardNumber() {
        view.fillingOutTheForm(DataHelper.getCardNumberEmpty());
        view.invalidCardFormat(formatError);
    }

    // --- МЕСЯЦ ---

    @Test
    @DisplayName("Валидное значение в поле месяц")
    void shouldErrorMonth13() {
        view.fillingOutTheForm(DataHelper.getMonth13());
        view.invalidCardExpirationDate(invalidDateError);
    }

    @Test
    @DisplayName("Ввод 0 в поле месяц")
    void shouldErrorMonthZero() {
        view.fillingOutTheForm(DataHelper.getMonthZero());
        view.monthNotValid(formatError);
    }

    @Test
    @DisplayName("Спецсимволы в поле месяц")
    void shouldErrorSymbolsInMonth() {
        view.fillingOutTheForm(DataHelper.getMonthSymbol());
        view.monthNotValid(formatError);
    }

    @Test
    @DisplayName("Буквы в поле месяц")
    void shouldErrorLettersInMonth() {
        view.fillingOutTheForm(DataHelper.getMonthLatin());
        view.monthNotValid(formatError);
    }

    @Test
    @DisplayName("Одна цифра в поле месяц")
    void shouldErrorOneDigitInMonth() {
        view.fillingOutTheForm(DataHelper.getMonthOneDigit());
        view.monthNotValid(formatError);
    }

    @Test
    @DisplayName("Пустое поле месяц")
    void shouldErrorEmptyMonth() {
        view.fillingOutTheForm(DataHelper.getMonthEmpty());
        view.monthNotValid(formatError);
    }

    // --- ГОД ---

    @Test
    @DisplayName("Значение до текущего года")
    void shouldErrorPastYear() {
        view.fillingOutTheForm(DataHelper.getYearLessThanCurrent());
        view.theCardExpired(expiredError);
    }

    @Test
    @DisplayName("Превышение текущего года более чем на 5 лет")
    void shouldErrorFutureYear() {
        view.fillingOutTheForm(DataHelper.getYearMoreThanFiveYears()); // 6+ лет уже ошибка
        view.invalidCardExpirationDate(invalidDateError);
    }

    @Test
    @DisplayName("Спецсимволы в поле год")
    void shouldErrorSymbolsInYear() {
        view.fillingOutTheForm(DataHelper.getYearSymbol());
        view.yearNotValid(formatError);
    }

    @Test
    @DisplayName("Буквы в поле год")
    void shouldErrorLettersInYear() {
        view.fillingOutTheForm(DataHelper.getYearLatin());
        view.yearNotValid(formatError);
    }

    @Test
    @DisplayName("Одна цифра в поле год")
    void shouldErrorOneDigitInYear() {
        view.fillingOutTheForm(DataHelper.getYearOneDigit());
        view.yearNotValid(formatError);
    }

    @Test
    @DisplayName("Пустое поле год")
    void shouldErrorEmptyYear() {
        view.fillingOutTheForm(DataHelper.getYearEmpty());
        view.yearNotValid(formatError);
    }

    // --- ВЛАДЕЛЕЦ ---

    @Test
    @DisplayName("Ввод на кириллице в поле владелец")
    void shouldErrorCyrillicOwner() {
        view.fillingOutTheForm(DataHelper.getHolderCyrillic());
        view.ownerNotValid(formatError);
    }

    @Test
    @DisplayName("Спецсимволы в поле владелец")
    void shouldErrorSymbolsInOwner() {
        view.fillingOutTheForm(DataHelper.getHolderSymbol());
        view.ownerNotValid(formatError);
    }

    @Test
    @DisplayName("Цифры в поле владелец")
    void shouldErrorDigitsInOwner() {
        view.fillingOutTheForm(DataHelper.getHolderDigit());
        view.ownerNotValid(formatError);
    }

    @Test
    @DisplayName("Одна буква в поле владелец")
    void shouldErrorShortOwner() {
        view.fillingOutTheForm(DataHelper.getHolderOneLetter());
        view.ownerNotValid(formatError);
    }

    @Test
    @DisplayName("Более 100 букв в поле владелец")
    void shouldErrorLongOwner() {
        view.fillingOutTheForm(DataHelper.getHolderMoreThan100());
        view.ownerNotValid(formatError);
    }

    @Test
    @DisplayName("Пустое поле владелец")
    void shouldErrorEmptyOwner() {
        view.fillingOutTheForm(DataHelper.getHolderEmpty());
        view.ownerNotValid(requiredError);
    }

    // --- CVC/CVV ---

    @Test
    @DisplayName("Спецсимволы в поле CVC/CVV")
    void shouldErrorSymbolsInCvc() {
        view.fillingOutTheForm(DataHelper.getCVCSymbol());
        view.cvcNotValid(formatError);
    }

    @Test
    @DisplayName("Буквы в поле CVC/CVV")
    void shouldErrorLettersInCvc() {
        view.fillingOutTheForm(DataHelper.getCVCLetter());
        view.cvcNotValid(formatError);
    }

    @Test
    @DisplayName("Одна цифра в поле CVC/CVV")
    void shouldErrorShortCvcOneDigit() {
        view.fillingOutTheForm(DataHelper.getCVConeDigit());
        view.cvcNotValid(formatError);
    }

    @Test
    @DisplayName("Две цифры в поле CVC/CVV")
    void shouldErrorShortCvcTwoDigits() {
        view.fillingOutTheForm(DataHelper.getCVCtwoDigit());
        view.cvcNotValid(formatError);
    }

    @Test
    @DisplayName("Пустое поле CVC/CVV")
    void shouldErrorEmptyCvc() {
        view.fillingOutTheForm(DataHelper.getCVCempty());
        view.cvcNotValid(formatError);
    }

    // --- ПУСТАЯ ФОРМА ---

    @Test
    @DisplayName("Отправка полностью незаполненной формы")
    void shouldErrorAllFieldsEmpty() {
        view.fillingOutTheForm(DataHelper.getCardNumberEmpty()); // Или просто клик по кнопке через метод в Page
    }
}