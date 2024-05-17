package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet {
	private UUID userId;
	private List<CurrencyBalance> balances;
	private List<Transaction> transactions;

	public Wallet(UUID userId) {
		this.setWalletOwner(userId);
		this.balances = new ArrayList<>();
		this.transactions = new ArrayList<>();
	}

	public void deposit(Moeda currency, BigDecimal amount) {
		for (CurrencyBalance balance : balances) {
			if (balance.getCurrency().equals(currency.getName())) {
				balance.setAmount(balance.getAmount().add(amount));
				Transaction tx = new Transaction(null, this.userId, amount, currency.getName());
				transactions.add(tx);

				return;
			}
		}
		balances.add(new CurrencyBalance(currency, amount));
	}

	public void withdraw(Moeda currency, BigDecimal amount) throws Exception {
		for (CurrencyBalance balance : balances) {
			if (balance.getCurrency().equals(currency.getName())) {
				if (balance.getAmount().compareTo(amount) >= 0) {
					balance.setAmount(balance.getAmount().subtract(amount));
					Transaction tx = new Transaction(this.userId, null, amount, currency.getName());
					transactions.add(tx);

					return;
				} else {
					throw new Exception("Insufficient funds");
				}
			}
		}
	}
	

	public void reverseTransaction(UUID transactionID) throws Exception {
		for (Transaction tx : transactions) {
			if (tx.getId().equals(transactionID) && !tx.isReversed()) {
				BigDecimal amount = tx.getAmount();

				Moeda currency = new Moeda();
				if (tx.getFromWallet() != null) {
					deposit(currency, amount);
				} else if (tx.getToWallet() != null) {
					withdraw(currency, amount);
				}

				tx.reverse();
			}
		}

		throw new Exception("Transaction not found or already reversed");
	}

	public BigDecimal getBalance(String currency) {
		for (CurrencyBalance balance : balances) {
			if (balance.getCurrency().equals(currency)) {
				return balance.getAmount();
			}
		}
		return BigDecimal.ZERO;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setWalletOwner(UUID userId) {
		this.userId = userId;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

}

class CurrencyBalance {
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
