package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet {
    private UUID userId;
    private List<CurrencyBalance> balances;

    public Wallet(UUID userId) {
        this.setUserId(userId);
        this.balances = new ArrayList<>();
    }

    public void deposit(Crypto currency, BigDecimal amount) {
        for (CurrencyBalance balance : balances) {
            if (balance.getCurrency().equals(currency)) {
                balance.setAmount(balance.getAmount().add(amount));
                return;
            }
        }
        balances.add(new CurrencyBalance(currency, amount));
    }

    public void withdraw(String currency, BigDecimal amount) throws Exception {
        for (CurrencyBalance balance : balances) {
            if (balance.getCurrency().equals(currency)) {
                if (balance.getAmount().compareTo(amount) >= 0) {
                    balance.setAmount(balance.getAmount().subtract(amount));
                    return;
                } else {
                    throw new Exception("Insufficient funds");
                }
            }
        }
        throw new Exception("Currency not found");
    }

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

}

class CurrencyBalance {
    private Crypto currency;
    private BigDecimal amount;

    public CurrencyBalance(Crypto currency, BigDecimal amount) {
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
