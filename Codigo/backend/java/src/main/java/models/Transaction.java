package models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Transaction {
	private UUID id;
	private UUID fromWallet;
	private UUID toWallet;
	private Date createdAt;
	private BigDecimal amount;
	private String currency;
	private boolean isReversed;

	public Transaction() { }

	public Transaction(UUID fromWallet, UUID toWallet, BigDecimal amount, String currency) {
		this.id = UUID.randomUUID();
		this.fromWallet = fromWallet;
		this.toWallet = toWallet;
		this.amount = amount;
		this.currency = currency;
		this.createdAt = new Date();
		this.isReversed = false;
	}

	public void reverse() {
		this.isReversed = true;
	}

	// Getters
	public UUID getId() {
		return id;
	}

	public UUID getFromWallet() {
		return fromWallet;
	}

	public UUID getToWallet() {
		return toWallet;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public boolean isReversed() {
		return isReversed;
	}

	// Setters
	public void setId(UUID id) {
		this.id = id;
	}

	public void setFromWallet(UUID fromWallet) {
		this.fromWallet = fromWallet;
	}

	public void setToWallet(UUID toWallet) {
		this.toWallet = toWallet;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setReversed(boolean reversed) {
		isReversed = reversed;
	}
}
