package dao;

import java.math.BigDecimal;
import java.util.List;
import models.Crypto;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface CryptoDao {
	
    @SqlUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, name VARCHAR, ticker VARCHAR, priceUsd NUMERIC)")
    void createTable();
    
    @SqlUpdate("INSERT INTO crypto (id, name, symbol, priceUsd) VALUES (:id, :name, :symbol, :priceUsd)")
    void insert(@Bind("id") int id, @Bind("name") String name, @Bind("symbol") String ticker, @Bind("priceUsd") BigDecimal priceUsd);

    @SqlQuery("SELECT * FROM crypto WHERE id = 1")
    List<Crypto> listCryptos();

    
}
