package services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import dao.MoedaDao;
import dao.TransactionDao;
import dao.WalletDao;
import dao.DAO;
import models.*;

public class WalletService {

    private final WalletDao walletDao;
    private final TransactionDao transactionDao;
    private final MoedaDao moedaDao;

    public WalletService(DAO dao) {
        this.walletDao = dao.getJdbiContext().onDemand(WalletDao.class);
        this.transactionDao = dao.getJdbiContext().onDemand(TransactionDao.class);
        this.moedaDao = dao.getJdbiContext().onDemand(MoedaDao.class);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        walletDao.createWalletTable();
        walletDao.createWalletBalancesTable();
        transactionDao.createTable();
        moedaDao.createTable();
    }

    public void createWallet(UUID ownerId) {
        Wallet wallet = new Wallet(ownerId);
        Moeda pkw = new Moeda("PKW");

        wallet.deposit(pkw, BigDecimal.valueOf(150));
        walletDao.insertWallet(wallet.getWalletId(), ownerId);
        walletDao.insertWalletBalance(wallet.getWalletId(), pkw.getName(), BigDecimal.valueOf(150));
    }


    public Wallet getWalletByUserId(UUID userId) {

        Wallet wallet = walletDao.findWalletByUserId(userId);

        if (wallet != null) {
            List<CurrencyBalance> balances = walletDao.findWalletBalances(wallet.getOwnerId());
            wallet.setBalances(balances);
            List<Transaction> transactions = walletDao.listWalletTransactions(wallet.getOwnerId());
            wallet.setTransactions(transactions);
        }
        return wallet;
    }


    public void deposit(UUID owner, String currency, double amount) throws Exception {
        Wallet wallet = getWalletByUserId(owner);
        Moeda moeda = moedaDao.findMoedaByName(currency);
        if (wallet != null && moeda != null) {
            wallet.deposit(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(wallet.getOwnerId(), currency, wallet.getBalance(currency));

            Transaction tx = new Transaction(wallet.getOwnerId(), null, BigDecimal.valueOf(amount), currency);
            transactionDao.insert(tx.getId(), tx.getToWallet(), tx.getFromWallet(), tx.getCreatedAt(), tx.getAmount(), tx.getCurrency(), tx.isReversed());
        } else {
            throw new Exception("Wallet or currency not found");
        }
    }

    public void withdraw(UUID userId, String currency, double amount) throws Exception {
        Wallet wallet = getWalletByUserId(userId);
        Moeda moeda = moedaDao.findMoedaByName(currency);
        if (wallet != null && moeda != null) {
            wallet.withdraw(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(wallet.getOwnerId(), currency, wallet.getBalance(currency));
            Transaction tx = new Transaction(null, wallet.getOwnerId(), BigDecimal.valueOf(amount), currency);
            transactionDao.insert(tx.getId(), tx.getToWallet(), tx.getFromWallet(), tx.getCreatedAt(), tx.getAmount(), tx.getCurrency(), tx.isReversed());
        } else {
            throw new Exception("Wallet or currency not found");
        }
    }

    public void transfer(UUID fromUserId, UUID toUserId, String currency, double amount) throws Exception {
        Wallet fromWallet = getWalletByUserId(fromUserId);
        Wallet toWallet = getWalletByUserId(toUserId);
        Moeda moeda = moedaDao.findMoedaByName(currency);
        if (fromWallet == null || toWallet == null || moeda == null) {
            throw new Exception("Wallet or currency not found");
        }

        try {
            fromWallet.withdraw(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(fromWallet.getOwnerId(), currency, fromWallet.getBalance(currency));

            toWallet.deposit(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(toWallet.getOwnerId(), currency, toWallet.getBalance(currency));

            Transaction tx = new Transaction(fromWallet.getOwnerId(), toWallet.getOwnerId(), BigDecimal.valueOf(amount), currency);
            transactionDao.insert(tx.getId(), tx.getToWallet(), tx.getFromWallet(), tx.getCreatedAt(), tx.getAmount(), tx.getCurrency(), tx.isReversed());
        } catch (Exception e) {
            fromWallet.deposit(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(fromWallet.getOwnerId(), currency, fromWallet.getBalance(currency));
            throw new Exception("Erro ao realizar transferência: " + e.getMessage());
        }
    }
}
