package ru.netology.page;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    public PurchasePage payBuyCard() {
        $(byText("Купить")).click();;
        return new PurchasePage();
    }

    public PurchasePage payCreditCard() {
        $(byText("Купить в кредит")).click();
        return new PurchasePage();
    }
}
