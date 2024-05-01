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

	public Transaction(UUID fromWallet, UUID toWallet, BigDecimal amount, String currency) {
		this.id = UUID.randomUUID();
		this.fromWallet = fromWallet;
		this.toWallet = toWallet;
		this.amount = amount;
		this.currency = currency;
		this.createdAt = new Date();
	}
}
