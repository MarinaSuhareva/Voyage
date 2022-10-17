package page;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentMethod {
    private final SelenideElement heading = $$("h2").find(exactText("Путешествие дня"));
    private final SelenideElement debitButton = $$("button").find(exactText("Купить"));
    private final SelenideElement creditButton = $$("button").find(exactText("Купить в кредит"));

    public PaymentMethod() {
        heading.shouldBe(visible);
    }

    public DebitPage openDebitPage() {
        debitButton.click();
        return new DebitPage();
    }

    public CreditPage openCreditPage() {
        creditButton.click();
        return new CreditPage();
    }
}

