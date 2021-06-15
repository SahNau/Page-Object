package tests;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import data.CardsInfo;
import data.Login;
import pages.DashboardPage;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    private DashboardPage dashboardPage;
    private final int amount = 500;

    @BeforeEach
    void setUp() {
        open("http://localhost:7777");
        val loginPage = new LoginPage();
        val authInfo = Login.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        dashboardPage = verificationPage.validCode();
        dashboardPage.equalizeBalance();
    }

    @AfterEach
    void theEnd() {
        dashboardPage.equalizeBalance();
        dashboardPage.theEnd();
    }

    @Test
    @DisplayName("Трансфер с первой на вторую карту = тест проходит")
    void shouldTransferFrom1to2() {
        int expected1 = dashboardPage.getCardBalance(0) - amount;
        int expected2 = dashboardPage.getCardBalance(1) + amount;
        val transferPage = dashboardPage.transferTo(1);
        transferPage.transaction(Integer.toString(amount), CardsInfo.cardNumber(0));
        int actual1 = dashboardPage.getCardBalance(0);
        int actual2 = dashboardPage.getCardBalance(1);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Трансфер со второй на первую карту = тест проходит")
    void shouldTransferFrom2to1() {
        int expected1 = dashboardPage.getCardBalance(1) - amount;
        int expected2 = dashboardPage.getCardBalance(0) + amount;
        val transferPage = dashboardPage.transferTo(0);
        transferPage.transaction(Integer.toString(amount), CardsInfo.cardNumber(1));
        int actual1 = dashboardPage.getCardBalance(1);
        int actual2 = dashboardPage.getCardBalance(0);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Минус нельзя отправить = тест проходит")
    void shouldNotTransferMinus(){
        int minus = -1000;
        //минус на минус дает плюс,поэтому нужно заставить действительно отправлять нужную сумму
        int expected1 = dashboardPage.getCardBalance(0) - -minus; // 10000 - (-(-10000)=0
        int expected2 = dashboardPage.getCardBalance(1) + -minus; // 10000 + (-(-10000)=20000
        val transferPage = dashboardPage.transferTo(1);
        transferPage.transaction(Integer.toString(minus), CardsInfo.cardNumber(0));
        int actual1 = dashboardPage.getCardBalance(0);
        int actual2 = dashboardPage.getCardBalance(1);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("С несуществующих карт нельзя пополнять = тест проходит")
    void shouldShowErrorMessage() {
        int error = 100;
        val transferPage = dashboardPage.transferTo(0);
        transferPage.transaction(Integer.toString(error), CardsInfo.unavailableCardNumber(1));
        transferPage.errorMessage();
        transferPage.transaction(Integer.toString(error), CardsInfo.unavailableCardNumber(0));
        transferPage.errorMessage();
        transferPage.transaction(Integer.toString(error), CardsInfo.cardNumber(1));
    }

    @Test
    @DisplayName("Нулевая сумма проходит = тест падает")
    void shouldNotTransferZero() {
        int zero = 0;
        val transferPage = dashboardPage.transferTo(0);
        transferPage.transaction(Integer.toString(zero), CardsInfo.cardNumber(1));
        transferPage.wrongAmount();
    }

    @Test
    @DisplayName("Овердрафт проходит = тест падает")
    void shouldNotTransferOverdraft() {
        int overdraft = 15000;
        val transferPage = dashboardPage.transferTo(0);
        transferPage.transaction(Integer.toString(overdraft), CardsInfo.cardNumber(1));
        transferPage.wrongAmount();
    }
}