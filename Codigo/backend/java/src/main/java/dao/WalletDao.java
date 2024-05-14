package dao;

import java.math.BigDecimal;
import java.util.UUID;
import models.Wallet;
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
    void insertWalletBalance(@Bind("walletId") UUID walletId, @Bind("currency") String currency, @Bind("amount") BigDecimal amount);

    @SqlQuery("SELECT * FROM wallets WHERE user_id = :userId")
    Wallet findWalletByUserId(@Bind("userId") UUID userId);

    @SqlUpdate("UPDATE wallet_balances SET amount = :amount WHERE wallet_id = :walletId AND currency = :currency")
    void updateWalletBalance(@Bind("walletId") UUID walletId, @Bind("currency") String currency, @Bind("amount") BigDecimal amount);
}
