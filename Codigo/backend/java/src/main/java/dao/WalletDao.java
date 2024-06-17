package dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import models.Wallet;
import models.CurrencyBalance;
import models.Transaction;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface WalletDao {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS wallets (id UUID PRIMARY KEY, user_id UUID)")
    void createWalletTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS wallet_balances (wallet_id UUID, currency VARCHAR, amount NUMERIC, PRIMARY KEY (wallet_id, currency))")
    void createWalletBalancesTable();

    @SqlUpdate("INSERT INTO wallets (id, user_id) VALUES (:id, :userId)")
    void insertWallet(@Bind("id") UUID id, @Bind("userId") UUID userId);

    @SqlUpdate("INSERT INTO wallet_balances (wallet_id, currency, amount) VALUES (:walletId, :currency, :amount)")
    void insertWalletBalance(@Bind("walletId") UUID walletId, @Bind("currency") String currency,
            @Bind("amount") BigDecimal amount);

    @SqlQuery("SELECT id FROM wallets WHERE user_id = :userId")
    @RegisterBeanMapper(Wallet.class)
    UUID findWalletByUserId(@Bind("userId") UUID userId);

    @SqlQuery("SELECT id, user_id AS userId FROM wallets WHERE id = :id")
    @RegisterBeanMapper(Wallet.class)
    Wallet findWalletById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM wallet_balances WHERE wallet_id = :walletId")
    @RegisterBeanMapper(CurrencyBalance.class)
    List<CurrencyBalance> findWalletBalances(@Bind("walletId") UUID walletId);

    @SqlUpdate("UPDATE wallet_balances SET amount = :amount WHERE wallet_id = :walletId AND currency = :currency")
    void updateWalletBalance(@Bind("walletId") UUID walletId, @Bind("currency") String currency,
            @Bind("amount") BigDecimal amount);

    @SqlQuery("SELECT * FROM transactions WHERE from_wallet = :walletId OR to_wallet = :walletId")
    @RegisterBeanMapper(Transaction.class)
    List<Transaction> listWalletTransactions(@Bind("walletId") UUID walletId);

}
