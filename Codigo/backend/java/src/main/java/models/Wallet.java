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
		this.balances = new ArrayList<>();
		this.transactions = new ArrayList<>();
	}

	public void deposit(Moeda moeda, BigDecimal amount) {
		for (CurrencyBalance balance : balances) {
			if (balance.getCurrency().equals(moeda.getSymbol())) {
				System.out.println("\nEntrei aqui em deposito\n" + balance.getAmount());
				var value =balance.getAmount().add(amount);
				balance.setAmount(value);
				break;
			}
		}
		balances.add(new CurrencyBalance(moeda.getName(), amount));
	}

	public void withdraw(Moeda moeda, BigDecimal amount) throws Exception {
		for (CurrencyBalance balance : balances) {
			if (balance.getCurrency().equals(moeda.getSymbol())) {
				if (balance.getAmount().compareTo(amount) >= 0) {
					System.out.println("\nEntrei aqui em withdrawl\n " + balance.getAmount());
					var value = balance.getAmount().subtract(amount);
					balance.setAmount(value);
					break;
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
				System.out.println(currency + ": " + balance.getAmount());
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
