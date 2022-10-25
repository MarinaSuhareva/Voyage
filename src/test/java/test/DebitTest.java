package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.Notification;
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
import static data.Notification.*;
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
        debitPage.waitNotificationOk(cardNumberOkText);
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
        debitPage.waitNotificationError(cardNumberErrorText);
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
        debitPage.waitNotificationError(cardNumberErrorText);
        assertEquals("Ошибка! Банк отказал в проведении операции.", Notification.cardNumberErrorText);

    }


    @Test
    void invalidMonthInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidMonthApprovedCard());
        debitPage.fieldMonthError(invalidDateText);
        assertEquals("Неверно указан срок действия карты", Notification.invalidDateText);
    }


    @Test
    void bygoneMonthApprovedInCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getBygoneMonthApprovedCard());
        debitPage.fieldMonthError(invalidDateText);
        assertEquals("Неверно указан срок действия карты", Notification.invalidDateText);
    }


    @Test
    void incompleteFieldMonth() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getIncompleteField());
        debitPage.fieldMonthError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void symbolInNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getCharactersInFieldOwnerApprovedCard());
        debitPage.fieldNameError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void cyrillicInNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getCyrillicInApprovedCard());
        debitPage.fieldNameError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void bygoneYearInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getBygoneYearApprovedCard());
        debitPage.fieldYearError(expiryDateText);
        assertEquals("Истёк срок действия карты", Notification.expiryDateText);
    }


    @Test
    void invalidYearInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidYearApprovedCard());
        debitPage.fieldYearError(expiryDateText);
        assertEquals("Истёк срок действия карты", Notification.expiryDateText);

    }


    @Test
    void debitInvalidCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getNonExistentCard());
        debitPage.waitNotificationError(cardNumberErrorText);
        assertEquals("Ошибка! Банк отказал в проведении операции.", Notification.cardNumberErrorText);
    }


    @Test
    void invalidCVCInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidCvc());
        debitPage.fieldCVVError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldCVC() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidCvc2());
        debitPage.fieldCVVError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);

    }


    @Test
    void theNumberIsNotComplete() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getInvalidCard());
        debitPage.getCardIsIncorrect(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldMonth() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getEmptyFieldMonth());
        debitPage.fieldMonthError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldYear() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getEmptyFieldYear());
        debitPage.fieldYearError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldNumber() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getEmptyFieldNumber());
        debitPage.getCardIsIncorrect(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldName() {
        PaymentMethod payment = new PaymentMethod();
        payment.openDebitPage();
        val debitPage = new DebitPage();
        debitPage.fillData(DataHelper.getEmptyFieldName());
        debitPage.fieldNameError(filledText);
        assertEquals("Поле обязательно для заполнения", Notification.filledText);
    }

}



