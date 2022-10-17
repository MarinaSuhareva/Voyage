package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.DataHelper;
import data.SqlHelper;
import page.PaymentMethod;
import page.DebitPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitTest {
    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUpEach() {
        open("http://localhost:8080/");
        SqlHelper.clearData();
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldDebitByCardWithStatusApproved() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getApprovedCard());
        debitPage.waitNotificationOk();
        val expected = DataHelper.getApprovedCardStatus();
        val actual = SqlHelper.getDebitStatus();
         assertEquals(expected, actual);

    }


    @Test
    void shouldDebitByCardWithStatusDecline() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getDeclinedCard());
        debitPage.waitNotificationError();
        val expected = DataHelper.getDeclinedCardStatus();
        val actual = SqlHelper.getDebitStatus();
        assertEquals(expected, actual);

    }


    @Test
    void shortNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getShortNameInOwnerApprovedCard());
        debitPage.waitNotificationError();
    }


    @Test
    void invalidMonthInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }


    @Test
    void bygoneMonthApprovedInCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getBygoneMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }


    @Test
    void incompleteFieldMonth() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getIncompleteField());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }


    @Test
    void symbolInNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getCharactersInFieldOwnerApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }


    @Test
    void cyrillicInNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getCyrillicInApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }


    @Test
    void bygoneYearInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getBygoneYearApprovedCard());
        assertEquals("Истёк срок действия карты", debitPage.getInputInvalid());
    }


    @Test
    void invalidYearInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidYearApprovedCard());
        debitPage.getInputInvalid();
    }


    @Test
    void debitInvalidCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getNonExistentCard());
        debitPage.getCardIsIncorrect();
    }


    @Test
    void invalidCVCInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidCvc());
        debitPage.getInputInvalid();
    }


    @Test
    void emptyFieldCVC() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidCvc2());
        debitPage.getInputInvalid();
    }


    @Test
    void theNumberIsNotComplete() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidCard());
        debitPage.getInputInvalid();
    }


    @Test
    void emptyFieldMonth() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getEmptyFieldMonth());
        debitPage.getInputInvalid();
    }


    @Test
    void emptyFieldYear() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getEmptyFieldYear());
        debitPage.getInputInvalid();
    }


    @Test
    void emptyFieldNumber() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getEmptyFieldNumber());
        debitPage.getInputInvalid();
    }


    @Test
    void emptyFieldName() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getEmptyFieldName());
        debitPage.getInputInvalid();
    }

}



