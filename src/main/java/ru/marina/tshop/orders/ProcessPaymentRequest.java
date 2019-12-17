package ru.marina.tshop.orders;

import java.math.BigDecimal;

public class ProcessPaymentRequest {
    private String cardNumber;
    private String cvc;
    private String expirationDate;
    private String cardHolder;
    private BigDecimal chargedAmount;

    public ProcessPaymentRequest() {
    }

    public ProcessPaymentRequest(final String cardNumber, final String cvc, final String expirationDate, final String cardHolder, final BigDecimal chargedAmount) {
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final String expirationDate) {
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
