package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.PaymentMethod;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreditTest {

    @BeforeEach
    public void setUpEach() {
        open("http://localhost:8080/");
        SqlHelper.clearData();
    }

    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }


    @Test
    void creditByCardWithStatusApproved() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getApprovedCard());
        creditPage.waitNotificationOk();
        val expected = DataHelper.getApprovedCardStatus();
        val actual = SqlHelper.getCreditStatus();
        assertEquals(expected, actual);

    }


    @Test
    void creditByCardWithStatusDeclined() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getDeclinedCard());
        creditPage.waitNotificationError();
        val expected = DataHelper.getDeclinedCardStatus();
        val actual = SqlHelper.getCreditStatus();
        assertEquals(expected, actual);

    }


    @Test
    void shortNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getShortNameInOwnerApprovedCard());
        creditPage.waitNotificationError();
    }


    @Test
    void invalidMonthInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }


    @Test
    void bygoneMonthInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getBygoneMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }


    @Test
    void incompleteFieldMonth() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getIncompleteField());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }


    @Test
    void symbolInNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getCharactersInFieldOwnerApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }


    @Test
    void cyrillicInNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getCyrillicInApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }


    @Test
    void bygoneYearInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getBygoneYearApprovedCard());
        assertEquals("Истёк срок действия карты", creditPage.getInputInvalid());
    }


    @Test
    void invalidYearInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidYearApprovedCard());
        creditPage.getInputInvalid();
    }


    @Test
    void creditInvalidCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getNonExistentCard());
        creditPage.getCardIsIncorrect();
    }


    @Test
    void invalidCVCInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidCvc());
        creditPage.getInputInvalid();
    }


    @Test
    void emptyFieldCVC() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidCvc2());
        creditPage.getInputInvalid();
    }

    @Test
    void theNumberIsNotComplete() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidCard());
        creditPage.getInputInvalid();
    }


    @Test
    void emptyFieldMonth() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getEmptyFieldMonth());
        creditPage.getInputInvalid();
    }


    @Test
    void emptyFieldYear() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getEmptyFieldYear());
        creditPage.getInputInvalid();
    }


    @Test
    void emptyFieldNumber() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getEmptyFieldNumber());
        creditPage.getInputInvalid();
    }


    @Test
    void emptyFieldName() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getEmptyFieldName());
        creditPage.getInputInvalid();
    }

}
