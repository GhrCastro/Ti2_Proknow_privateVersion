package services;

import java.math.BigDecimal;
import java.util.UUID;
import dao.WalletDao;
import dao.DAO;
import models.Wallet;
import models.Transaction;
import models.Crypto;
import models.Moeda;
import models.PKWCoin;

public class WalletService {

	private final WalletDao walletDao;

	public WalletService(DAO dao) {
        this.walletDao = dao.getJdbiContext().onDemand(WalletDao.class);
        createTableIfNotExists();
    }

	private void createTableIfNotExists() {
		walletDao.createWalletTable();
		walletDao.createWalletBalancesTable();
	}

	public void createWallet(UUID owner) {
		Wallet wallet = new Wallet(owner);
		PKWCoin pkw = new PKWCoin();
		walletDao.insertWallet(wallet.getUserId(), owner);
		wallet.deposit(pkw, BigDecimal.valueOf(150));
		walletDao.insertWalletBalance(wallet.getUserId(), "PKW", BigDecimal.valueOf(150));
	}

	public Wallet getWalletByUserId(UUID owner) {
		return walletDao.findWalletByUserId(owner);
	}

	public void deposit(UUID owner, String currency, double amount) throws Exception {
		Wallet wallet = getWalletByUserId(owner);
		if (wallet != null) {
			wallet.deposit(new Moeda(), BigDecimal.valueOf(amount));
			walletDao.updateWalletBalance(wallet.getUserId(), currency, wallet.getBalance(currency));
		} else {
			throw new Exception("Wallet not found");
		}
	}

	public void withdraw(UUID userId, String currency, double amount) throws Exception {
		Wallet wallet = getWalletByUserId(userId);
		if (wallet != null) {
			wallet.withdraw(new Moeda(), BigDecimal.valueOf(amount));
			walletDao.updateWalletBalance(wallet.getUserId(), currency, wallet.getBalance(currency));
		} else {
			throw new Exception("Wallet not found");
		}
	}
}
