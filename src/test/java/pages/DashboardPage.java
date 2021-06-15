package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import lombok.val;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static data.CardsInfo.cardNumber;

public class DashboardPage {
    private final ElementsCollection cards = $$(".list__item");
    private final ElementsCollection buttons = $$("button[data-test-id='action-deposit']");

    public DashboardPage() {
    }

    public void theEnd() {
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    private int extractBalance(String text) {
        String balanceStart = "баланс: ";
        String balanceFinish = " р.";
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish).trim();
        return Integer.parseInt(value);
    }

    public int getCardBalance(int number) {
        String text = cards.get(number).text();
        return extractBalance(text);
    }


    public TransferPage transferTo(int number) {
        buttons.get(number).click();
        return new TransferPage();
    }

    public void equalizeBalance() {
        int firstCardInitialBalance = getCardBalance(0);
        int secondCardInitialBalance = getCardBalance(1);

        if (firstCardInitialBalance > secondCardInitialBalance) {
            int amount = firstCardInitialBalance - 10_000;
            transferTo(1).transaction(Integer.toString(amount), cardNumber(0));
        } else if (secondCardInitialBalance > firstCardInitialBalance) {
            int amount = secondCardInitialBalance - 10_000;
            transferTo(0).transaction(Integer.toString(amount), cardNumber(1));
        }
    }
}