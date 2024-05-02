package dao;

import java.util.List;
import models.Crypto;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface CryptoDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, name VARCHAR, ticker VARCHAR, priceUsd NUMERIC)")
    void createTable();
    
    @SqlUpdate("INSERT INTO crypto (id, name, ticker, priceUsd) VALUES (:id, :name, :ticker, :priceUsd)")
    void insert(@Bind("id") int id, @Bind("name") String name, @Bind("ticker") String ticker, @Bind("priceUsd") double priceUsd);

    @SqlQuery("SELECT * FROM crypto WHERE id = 1")
    List<Crypto> listCryptos();
    
}
