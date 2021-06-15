package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.*;

public class TransferPage {
    private final SelenideElement amount = $("[data-test-id=amount] input");
    private final SelenideElement fromCard = $("[data-test-id=from] input");
    private final SelenideElement button = $("[data-test-id='action-transfer']");
    private final SelenideElement error = $(".notification__content");

    public void clickButtonAdd() {
        button.click();
    }

    public void transaction(String value, String source) {
        amount.sendKeys(chord(CONTROL, "a"),
                BACK_SPACE,
                value.replace(" ", ""));
        fromCard.sendKeys(chord(CONTROL, "a"),
                BACK_SPACE,
                source.replace(" ", ""));
        clickButtonAdd();
    }

    public void errorMessage() {
        error.shouldHave(Condition.exactText("Ошибка! Произошла ошибка"));
    }

    public void wrongAmount() {
        error.shouldHave(Condition.exactText("Вы не можете отправить данное количество,введите пожалуйста положительную сумму"));
    }
}