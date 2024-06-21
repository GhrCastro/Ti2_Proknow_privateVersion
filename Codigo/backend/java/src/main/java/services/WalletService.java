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

    public void deposit(UUID owner, UUID recipient, String currency, double amount) throws Exception {
        Usuario fromUser = usuarioService.getUsuarioById(owner);
        Usuario toUser = usuarioService.getUsuarioById(recipient);

        System.out.println(fromUser);
        System.out.println("\n");
        System.out.println(toUser);

        Wallet ownerWallet = null;
        Wallet recipientWallet = null;
        Moeda moeda = null;
        try {
            ownerWallet = getWalletByUserId(fromUser.getId());
            recipientWallet = getWalletByUserId(toUser.getId());
            moeda = moedaDao.findMoedaByName(currency);

            if (ownerWallet == null) {
                throw new IllegalArgumentException("Owner wallet not found");
            }
            if (recipientWallet == null) {
                throw new IllegalArgumentException("Recipient wallet not found");
            }
            if (moeda == null) {
                throw new IllegalArgumentException("Currency not found");
            }

            ownerWallet.withdraw(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(ownerWallet.getWalletId(), currency, ownerWallet.getBalance(currency));

            recipientWallet.deposit(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(recipientWallet.getWalletId(), currency,
                    recipientWallet.getBalance(currency));

            Transaction tx = new Transaction(ownerWallet.getOwnerId(), recipientWallet.getWalletId(),
                    BigDecimal.valueOf(amount), currency);
            transactionDao.insert(tx.getId(), tx.getFromWallet(), tx.getToWallet(), tx.getCreatedAt(), tx.getAmount(),
                    tx.getCurrency(), tx.isReversed());

        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error during deposit: " + e.getMessage());
            e.printStackTrace();

            if (ownerWallet != null && moeda != null) {
                try {
                    ownerWallet.deposit(moeda, BigDecimal.valueOf(amount));
                    walletDao.updateWalletBalance(ownerWallet.getWalletId(), currency,
                            ownerWallet.getBalance(currency));
                } catch (Exception rollbackException) {
                    System.err.println("Error during rollback: " + rollbackException.getMessage());
                    rollbackException.printStackTrace();
                }
            }
            throw new RuntimeException("Failed to complete deposit transaction", e);
        }
    }

    public void withdraw(UUID userId, String currency, double amount) throws Exception {
        Wallet wallet = getWalletByUserId(userId);
        Moeda moeda = moedaDao.findMoedaByName(currency);
        if (wallet != null && moeda != null) {
            wallet.withdraw(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(wallet.getOwnerId(), currency, wallet.getBalance(currency));
            Transaction tx = new Transaction(null, wallet.getOwnerId(), BigDecimal.valueOf(amount), currency);
            transactionDao.insert(tx.getId(), tx.getToWallet(), tx.getFromWallet(), tx.getCreatedAt(), tx.getAmount(),
                    tx.getCurrency(), tx.isReversed());
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

            // Recebe badge transaction
            // usuarioService.addUserBadge(fromUserId, UUID.fromString("0c64e08b-0c64-4a7d-b2c2-989b59e5f9e6"));

            Transaction tx = new Transaction(fromWallet.getOwnerId(), toWallet.getOwnerId(), BigDecimal.valueOf(amount),
                    currency);
            transactionDao.insert(tx.getId(), tx.getToWallet(), tx.getFromWallet(), tx.getCreatedAt(), tx.getAmount(),
                    tx.getCurrency(), tx.isReversed());

        } catch (Exception e) {
            fromWallet.deposit(moeda, BigDecimal.valueOf(amount));
            walletDao.updateWalletBalance(fromWallet.getOwnerId(), currency, fromWallet.getBalance(currency));
            throw new Exception("Erro ao realizar transferência: " + e.getMessage());
        }
    }

}
