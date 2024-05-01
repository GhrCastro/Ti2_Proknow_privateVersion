package dao;

import java.sql.Connection;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DAO {
	private static Jdbi jdbi = null;
	
	public static Jdbi getJdbi() {
		if(jdbi == null) {
			String url = "jdbc:postgresql://proknow-server.postgres.database.azure.com:5432/postgres?user=undefined&password=root&sslmode=require";
            String username = "undefined";
            String password = "root";
            
            jdbi = Jdbi.create(url, username, password);
            jdbi.installPlugin(new SqlObjectPlugin());
		}
		return jdbi;
	}
	
}
