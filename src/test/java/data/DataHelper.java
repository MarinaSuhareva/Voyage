package data;

import com.github.javafaker.Faker;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;


public class DataHelper {
    private DataHelper() {
    }

    private static String getMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getBygoneMonth() {
        int subtractMonth = LocalDate.now().getMonthValue();
        return LocalDate.now().minusMonths(subtractMonth - 1).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getBygoneYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getName() {
        Faker faker = new Faker();
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    private static String getCvc() {
        Faker faker = new Faker();
        return faker.numerify("###");
    }

    private static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    private static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getApprovedCardStatus() {
        return "APPROVED";
    }

    public static String getDeclinedCardStatus() {
        return "DECLINED";
    }

    public static Card getInvalidCvc() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getName(), "000");
    }

    public static Card getInvalidCvc2() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getName(), " ");
    }

    public static Card getNonExistentCard() {
        return new Card("4444 4444 4444 4444", getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getInvalidCard() {
        return new Card("4444 4444 4444 444 ", getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getShortNameInOwnerApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "Li", getCvc());
    }


    public static Card getInvalidMonthApprovedCard() {
        return new Card(getApprovedCardNumber(), "13", getYear(), getName(), getCvc());
    }


    public static Card getBygoneMonthApprovedCard() {
        return new Card(getApprovedCardNumber(), getBygoneMonth(), getYear(), getName(), getCvc());
    }


    public static Card getIncompleteField() {
        return new Card("4444 4444 4444 4441", "3", getYear(), getName(), getCvc());
    }

    public static Card getCharactersInFieldOwnerApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "$#@*&!", getCvc());
    }

    public static Card getCyrillicInApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "Сергей Есенин", getCvc());
    }

    public static Card getBygoneYearApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getBygoneYear(), getName(), getCvc());
    }

    public static Card getInvalidYearApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), "00", getName(), getCvc());
    }


    public static Card getEmptyFieldMonth() {
        return new Card(getApprovedCardNumber(), " ", getYear(), getName(), getCvc());
    }


    public static Card getEmptyFieldYear() {
        return new Card(getApprovedCardNumber(), getMonth(), " ", getName(), getCvc());
    }

    public static Card getEmptyFieldNumber() {
        return new Card(" ", getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getEmptyFieldName() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), " ", getCvc());
    }
}

