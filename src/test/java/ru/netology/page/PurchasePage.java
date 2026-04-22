package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PurchasePage {

    // Поля ввода
    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement continueButton = $(byText("Продолжить"));

    // Уведомления
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

    // Элементы ошибок
    private final SelenideElement cardNumberError = $(byText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private final SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private final SelenideElement ownerError = $(byText("Владелец")).parent().$(".input__sub");
    private final SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");

    // Заполнение формы
    public void fillingOutTheForm(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getHolder());
        cvcField.setValue(cardInfo.getCodCvcCvv());
        continueButton.click();
    }

    // Оплата успешна
    public void paymentSuccessfull(String expectedText) {
        successNotification.shouldBe(visible, Duration.ofSeconds(30))
                .shouldHave(exactText(expectedText));
    }

    // Отказ банка
    public void declinedPayment(String expectedText) {
        errorNotification.shouldBe(visible, Duration.ofSeconds(20))
                .shouldHave(exactText(expectedText));
    }

    // Валидация формата (под полем)
    public void invalidCardFormat(String expectedText) {

        cardNumberError.shouldBe(visible).shouldHave(exactText(expectedText));
    }

    // Срок действия
    public void invalidCardExpirationDate(String expectedText) {
        $(byText(expectedText)).shouldBe(visible);
    }

    public void theCardExpired(String expectedText) {
        $(byText(expectedText)).shouldBe(visible);
    }

    // Ошибки конкретных полей
    public void monthNotValid(String expectedText) {
        monthError.shouldBe(visible).shouldHave(exactText(expectedText));
    }

    public void yearNotValid(String expectedText) {
        yearError.shouldBe(visible).shouldHave(exactText(expectedText));
    }

    public void ownerNotValid(String expectedText) {
        ownerError.shouldBe(visible).shouldHave(exactText(expectedText));
    }

    public void cvcNotValid(String expectedText) {
        cvcError.shouldBe(visible).shouldHave(exactText(expectedText));
    }
}
