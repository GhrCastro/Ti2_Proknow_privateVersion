package models;

import java.math.BigDecimal;

public class CurrencyBalance {
	private String currency;
	private BigDecimal amount;

	public CurrencyBalance() { }

	public CurrencyBalance(String currency, BigDecimal amount) {
		this.currency = currency;
		this.amount = amount;
	}

	// Getters and Setters
	public String getCurrency() {
		return currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setCurrency(String currency) { this.currency = currency; }
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
