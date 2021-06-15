package pages;

import com.codeborne.selenide.SelenideElement;
import data.Login;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement login = $("[data-test-id=login] input");
    private final SelenideElement password = $("[data-test-id=password] input");
    private final SelenideElement button = $("[data-test-id='action-login']");

    public VerificationPage validLogin(Login.AuthInfo authInfo) {
        login.setValue(authInfo.getLogin());
        password.setValue(authInfo.getPassword());
        button.click();
        return new VerificationPage();
    }
}