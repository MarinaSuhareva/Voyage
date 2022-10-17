package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitPage {
    private SelenideElement heading = $$("h3").find(Condition.text("Оплата по карте"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement notificationOK = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");
    private SelenideElement cardNumberError = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement inputInvalid = $(".input__sub");

    public DebitPage() {
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

    public void waitNotificationOk() {
        notificationOK.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitNotificationError() {

        notificationError.shouldBe(visible, Duration.ofSeconds(15));
    }

    public String getInputInvalid() {

        return inputInvalid.getText();
    }

    public void getCardIsIncorrect() {
        cardNumberError.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

}

