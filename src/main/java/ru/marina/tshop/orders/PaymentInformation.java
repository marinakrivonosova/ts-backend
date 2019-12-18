package ru.marina.tshop.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;

import java.time.YearMonth;

public class PaymentInformation {
    private String cardNumber;
    private String cvc;
    @JsonSerialize(using = StringSerializer.class)
    @JsonDeserialize(using = YearMonthDeserializer.class)
    private YearMonth expirationDate;
    private String cardHolder;

    public PaymentInformation() {
    }

    public PaymentInformation(final String cardNumber, final String cvc, final YearMonth expirationDate, final String cardHolder) {
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expirationDate = expirationDate;
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(final String cvc) {
        this.cvc = cvc;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(final String cardHolder) {
        this.cardHolder = cardHolder;
    }
}
