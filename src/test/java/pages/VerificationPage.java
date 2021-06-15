package pages;

import com.codeborne.selenide.SelenideElement;
import data.VerificationInfo;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement code = $("[data-test-id=code] input");
    private final SelenideElement button = $("[data-test-id='action-verify']");

    public DashboardPage validCode() {
        code.setValue(VerificationInfo.getVerificationCode().getCode());
        button.click();
        return new DashboardPage();
    }
}