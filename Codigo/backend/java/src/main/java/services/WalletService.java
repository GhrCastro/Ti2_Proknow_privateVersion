package services;

import java.math.BigDecimal;
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
    private final UsuarioService usuarioService;

    public WalletService(DAO dao) {
        this.walletDao = dao.getJdbiContext().onDemand(WalletDao.class);
        this.transactionDao = dao.getJdbiContext().onDemand(TransactionDao.class);
        this.moedaDao = dao.getJdbiContext().onDemand(MoedaDao.class);
        this.usuarioService = new UsuarioService(dao);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        walletDao.createWalletTable();
        walletDao.createWalletBalancesTable();
        transactionDao.createTable();
        moedaDao.createTable();
    }

    public void createWallet(UUID ownerId) {

        // ESTA FUNCAO NAO ESTA SENDO CHAMADA EM LUGAR ALGUM
        // USUARIOSERVICE ESTÁ INJETANDO DIRETAMENTE OS DADOS

        Wallet wallet = new Wallet(ownerId);
        Moeda pkw = new Moeda("PKW");

        wallet.deposit(pkw, BigDecimal.valueOf(150));
        walletDao.insertWallet(wallet.getWalletId(), ownerId);
        walletDao.insertWalletBalance(wallet.getWalletId(), pkw.getName(), BigDecimal.valueOf(150));

        // ===========================
        // Wallet_balances MOEDA
        // ===========================

        Moeda btc = new Moeda("BTC");
        wallet.deposit(btc, BigDecimal.valueOf(0));
        walletDao.insertWalletBalance(wallet.getWalletId(), btc.getName(), BigDecimal.valueOf(0));

        Moeda eth = new Moeda("ETH");
        wallet.deposit(eth, BigDecimal.valueOf(0));
        walletDao.insertWalletBalance(wallet.getWalletId(), eth.getName(), BigDecimal.valueOf(0));

        Moeda usd = new Moeda("USD");
        wallet.deposit(usd, BigDecimal.valueOf(0));
        walletDao.insertWalletBalance(wallet.getWalletId(), usd.getName(), BigDecimal.valueOf(0));

        Moeda brl = new Moeda("BRL");
        wallet.deposit(brl, BigDecimal.valueOf(0));
        walletDao.insertWalletBalance(wallet.getWalletId(), brl.getName(), BigDecimal.valueOf(0));
    }

    public Wallet getWalletByUserId(UUID userId) {
        UUID walletId = walletDao.findWalletByUserId(userId);

        if (walletId != null) {
            List<CurrencyBalance> balances = walletDao.findWalletBalances(walletId);
            List<Transaction> transactions = walletDao.listWalletTransactions(walletId);
            Wallet wallet = new Wallet();
            wallet.setId(walletId);
            wallet.setUserId(userId);
            wallet.setBalances(balances);
            wallet.setTransactions(transactions);
            return wallet;
        }

        return null;
    }

    public void deposit(UUID recipient, String name, double amount) throws Exception {
        Wallet ownerWallet = getWalletByUserId(UUID.fromString("33121974-2079-4bf7-8fd1-9ec3121aca12")); //admin wallet
        Wallet recipientWallet = getWalletByUserId(recipient);
        Moeda moeda = moedaDao.findMoedaByName(name);

        if (recipientWallet == null) {
            throw new IllegalArgumentException("Recipient wallet not found");
        }
        if (moeda == null) {
            throw new IllegalArgumentException("Currency not found");
        }

        try {
            ownerWallet.withdraw(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(ownerWallet.getWalletId(), moeda.getName(), ownerWallet.getBalance(moeda.getName()));

            recipientWallet.deposit(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(recipientWallet.getWalletId(), moeda.getName(), recipientWallet.getBalance(moeda.getName()));

            Transaction tx = new Transaction(ownerWallet.getWalletId(), recipientWallet.getWalletId(), BigDecimal.valueOf(amount), moeda.getName());
            transactionDao.insert(tx.getId(), tx.getFromWallet(), tx.getToWallet(), tx.getCreatedAt(), tx.getAmount(), tx.getCurrency(), tx.isReversed());

            ownerWallet.getTransactions().add(tx);
            recipientWallet.getTransactions().add(tx);

        } catch (Exception e) {
            System.err.println("Error during deposit: " + e.getMessage());
            e.printStackTrace();

            if (ownerWallet != null && moeda != null) {
                try {
                    ownerWallet.deposit(moeda, BigDecimal.valueOf(amount));
                    walletDao.updateWalletBalance(ownerWallet.getWalletId(), moeda.getName(), ownerWallet.getBalance(moeda.getName()));
                } catch (Exception rollbackException) {
                    System.err.println("Error during rollback: " + rollbackException.getMessage());
                    rollbackException.printStackTrace();
                }
            }
            throw new RuntimeException("Failed to complete deposit transaction", e);
        }
    }

    public void withdraw(UUID userId, String currency, double amount) throws Exception {
        Wallet recipient = getWalletByUserId(userId);
        Wallet admin = getWalletByUserId(UUID.fromString("33121974-2079-4bf7-8fd1-9ec3121aca12"));
        Moeda moeda = moedaDao.findMoedaByName(currency);

        if (recipient == null) {
            throw new IllegalArgumentException("Wallet not found");
        }
        if (moeda == null) {
            throw new IllegalArgumentException("Currency not found");
        }

        recipient.withdraw(moeda, BigDecimal.valueOf(amount));
        walletDao.updateWalletBalance(recipient.getWalletId(), currency, recipient.getBalance(currency));

        admin.deposit(moeda, BigDecimal.valueOf(amount));
        walletDao.updateWalletBalance(admin.getWalletId(), currency, recipient.getBalance(currency));

        Transaction tx = new Transaction(recipient.getWalletId(), admin.getWalletId(), BigDecimal.valueOf(amount), currency);
        transactionDao.insert(tx.getId(), tx.getFromWallet(), tx.getToWallet(), tx.getCreatedAt(), tx.getAmount(),
                tx.getCurrency(), tx.isReversed());
    }

    public void transfer(UUID fromUserId, UUID toUserId, String currency, double amount) throws Exception {
        Wallet fromWallet = getWalletByUserId(fromUserId);
        Wallet toWallet = getWalletByUserId(toUserId);
        Moeda moeda = moedaDao.findMoedaByName(currency);

        if (fromWallet == null) {
            throw new IllegalArgumentException("Owner wallet not found");
        }
        if (toWallet == null) {
            throw new IllegalArgumentException("Recipient wallet not found");
        }
        if (moeda == null) {
            throw new IllegalArgumentException("Currency not found");
        }

        try {
            fromWallet.withdraw(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(fromWallet.getWalletId(), currency, fromWallet.getBalance(currency));

            toWallet.deposit(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(toWallet.getWalletId(), currency, toWallet.getBalance(currency));


            // Recebe badge transaction
            usuarioService.addUserBadge(fromUserId, UUID.fromString("0c64e08b-0c64-4a7d-b2c2-989b59e5f9e6"));

            Transaction tx = new Transaction(fromWallet.getWalletId(), toWallet.getWalletId(), BigDecimal.valueOf(amount), currency);
            transactionDao.insert(tx.getId(), tx.getFromWallet(), tx.getToWallet(), tx.getCreatedAt(), tx.getAmount(), tx.getCurrency(), tx.isReversed());

            fromWallet.getTransactions().add(tx);
            toWallet.getTransactions().add(tx);

        }  catch (Exception e) {
            fromWallet.deposit(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(fromWallet.getOwnerId(), currency, fromWallet.getBalance(currency));
            throw new Exception("Erro ao realizar transferência: " + e.getMessage());
        }
    }

}
