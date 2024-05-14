package dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import models.Transaction;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TransactionDao {
    
    @SqlUpdate("CREATE TABLE IF NOT EXISTS transactions (id UUID PRIMARY KEY, from_wallet UUID, to_wallet UUID, created_at TIMESTAMP, amount NUMERIC, currency VARCHAR, is_reversed BOOLEAN)")
    void createTable();
    
    @SqlUpdate("INSERT INTO transactions (id, from_wallet, to_wallet, created_at, amount, currency, is_reversed) VALUES (:id, :fromWallet, :toWallet, :createdAt, :amount, :currency, :isReversed)")
    void insert(@Bind("id") UUID id, @Bind("fromWallet") UUID fromWallet, @Bind("toWallet") UUID toWallet, @Bind("createdAt") Date createdAt, @Bind("amount") BigDecimal amount, @Bind("currency") String currency, @Bind("isReversed") boolean isReversed);
    
    @SqlQuery("SELECT * FROM transactions WHERE from_wallet = :walletId OR to_wallet = :walletId")
    List<Transaction> listTransactions(@Bind("walletId") UUID walletId);
}