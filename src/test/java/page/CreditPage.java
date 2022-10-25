package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CreditPage {
    final SelenideElement heading = $$("h3").find(Condition.text("Кредит по данным карты"));
    final private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    final private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    final private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    final private SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    final private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    final private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    final private SelenideElement notificationOK = $(".notification_status_ok");
    final private SelenideElement notificationError = $(".notification_status_error");


    final private SelenideElement cardNumberError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    final private SelenideElement monthError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    final private SelenideElement yearError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    final private SelenideElement ownerError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    final private SelenideElement cvcError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");


    public CreditPage() {
        heading.shouldBe(visible);
    }

    public void fillData(Card card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        ownerField.setValue(card.getName());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }


    public void waitNotificationOk(String error) {
        notificationOK.shouldHave(text(error)).shouldBe(visible, Duration.ofSeconds(15));

    }

    public void waitNotificationError(String error) {
        notificationError.shouldHave(text(error)).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void getCardIsIncorrect(String error) {
        cardNumberError.shouldHave(text(error)).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void fieldMonthError(String error) {
        monthError.shouldHave(text(error)).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void fieldYearError(String error) {
        yearError.shouldHave(text(error)).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void fieldNameError(String error) {
        ownerError.shouldHave(text(error)).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void fieldCVVError(String error) {
        cvcError.shouldHave(text(error)).shouldBe(visible, Duration.ofSeconds(15));
    }

}
