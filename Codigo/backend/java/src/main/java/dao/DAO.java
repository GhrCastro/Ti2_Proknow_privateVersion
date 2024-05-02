package dao;

import java.sql.Connection;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.StatementException;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DAO {
	private Jdbi jdbi;

	public DAO(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	public DAO() { }

	public Jdbi getJdbi() {
		if (jdbi == null) {
			String url = "jdbc:postgresql://proknow-db.postgres.database.azure.com:5432/postgres?user=adm&password=Proknow1!&sslmode=require";
			String username = "adm";
			String password = "Proknow1!";

			jdbi = Jdbi.create(url, username, password);
			jdbi.installPlugin(new SqlObjectPlugin());
		}
		return jdbi;
	}

}
