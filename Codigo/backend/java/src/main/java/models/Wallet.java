package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet {
	private UUID id;
	private UUID userId;
	private List<CurrencyBalance> balances;
	private List<Transaction> transactions;

	public Wallet() {
	}

	public Wallet(UUID userId) {
		id = UUID.randomUUID();
		this.userId = userId;
		this.balances = new ArrayList<CurrencyBalance>();
		this.transactions = new ArrayList<Transaction>();
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
		balances.add(new CurrencyBalance(currency.getName(), amount));
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

				Moeda currency = new Moeda(tx.getCurrency());
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

	public UUID getOwnerId() {
		return userId;
	}

	public UUID getWalletId() {
		return id;
	}

	public void setWalletOwner(UUID userId) {
		this.userId = userId;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setBalances(List<CurrencyBalance> balances) {
		this.balances = balances;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setId(UUID walletId) {
		this.id = walletId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Wallet{" + "id=" + id + ", userId=" + userId + ", balances=" + balances + '}';
	}
}
