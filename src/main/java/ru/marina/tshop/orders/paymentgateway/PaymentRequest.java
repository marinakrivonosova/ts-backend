package ru.marina.tshop.orders.paymentgateway;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;

import java.math.BigDecimal;
import java.time.YearMonth;

public class PaymentRequest {
    private String cardNumber;
    private String cvc;
    @JsonDeserialize(using = YearMonthDeserializer.class)
    private YearMonth expirationDate;
    private String cardHolder;
    @JsonDeserialize(using = NumberDeserializers.BigDecimalDeserializer.class)
    private BigDecimal chargedAmount;

    public PaymentRequest() {
    }

    public PaymentRequest(final String cardNumber, final String cvc, final YearMonth expirationDate, final String cardHolder, final BigDecimal chargedAmount) {
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expirationDate = expirationDate;
        this.cardHolder = cardHolder;
        this.chargedAmount = chargedAmount;
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

    public BigDecimal getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(final BigDecimal chargedAmount) {
        this.chargedAmount = chargedAmount;
    }
}
