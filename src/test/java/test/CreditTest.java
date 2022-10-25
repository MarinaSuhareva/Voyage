package test;


import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.Notification;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.PaymentMethod;


import static com.codeborne.selenide.Selenide.open;
import static data.Notification.*;
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
        creditPage.waitNotificationOk(cardNumberOkText);
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
        creditPage.waitNotificationError(cardNumberErrorText);
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
        creditPage.waitNotificationError(cardNumberErrorText);
        assertEquals("Ошибка! Банк отказал в проведении операции.", Notification.cardNumberErrorText);

    }


    @Test
    void invalidMonthInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidMonthApprovedCard());
        creditPage.fieldMonthError(invalidDateText);
        assertEquals("Неверно указан срок действия карты", Notification.invalidDateText);
    }


    @Test
    void bygoneMonthInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getBygoneMonthApprovedCard());
        creditPage.fieldMonthError(invalidDateText);
        assertEquals("Неверно указан срок действия карты", Notification.invalidDateText);
    }


    @Test
    void incompleteFieldMonth() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getIncompleteField());
        creditPage.fieldMonthError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void symbolInNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getCharactersInFieldOwnerApprovedCard());
        creditPage.fieldNameError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void cyrillicInNameInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getCyrillicInApprovedCard());
        creditPage.fieldNameError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void bygoneYearInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getBygoneYearApprovedCard());
        creditPage.fieldYearError(expiryDateText);
        assertEquals("Истёк срок действия карты", Notification.expiryDateText);
    }


    @Test
    void invalidYearInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidYearApprovedCard());
        creditPage.fieldYearError(expiryDateText);
        assertEquals("Истёк срок действия карты", Notification.expiryDateText);
    }


    @Test
    void creditInvalidCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getNonExistentCard());
        creditPage.waitNotificationError(cardNumberErrorText);
        assertEquals("Ошибка! Банк отказал в проведении операции.", Notification.cardNumberErrorText);
    }


    @Test
    void invalidCVCInApprovedCard() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidCvc());
        creditPage.fieldCVVError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldCVC() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidCvc2());
        creditPage.fieldCVVError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }

    @Test
    void theNumberIsNotComplete() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getInvalidCard());
        creditPage.getCardIsIncorrect(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldMonth() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getEmptyFieldMonth());
        creditPage.fieldMonthError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldYear() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getEmptyFieldYear());
        creditPage.fieldYearError(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldNumber() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getEmptyFieldNumber());
        creditPage.getCardIsIncorrect(wrongFormatText);
        assertEquals("Неверный формат", Notification.wrongFormatText);
    }


    @Test
    void emptyFieldName() {
        PaymentMethod payment = new PaymentMethod();
        payment.openCreditPage();
        val creditPage = new CreditPage();
        creditPage.fillData(DataHelper.getEmptyFieldName());
        creditPage.fieldNameError(filledText);
        assertEquals("Поле обязательно для заполнения", Notification.filledText);
    }

}
