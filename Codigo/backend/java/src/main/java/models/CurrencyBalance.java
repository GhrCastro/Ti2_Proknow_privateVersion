package models;

import java.math.BigDecimal;

public class CurrencyBalance {
	private Moeda currency;
	private BigDecimal amount;

	public CurrencyBalance(Moeda currency, BigDecimal amount) {
		this.currency = currency;
		this.amount = amount;
	}

	// Getters and Setters
	public String getCurrency() {
		return currency.getName();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
