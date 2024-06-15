package dao;

import java.sql.Connection;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.core.statement.StatementException;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import models.Badge;
import models.UserBadge;
import models.Usuario;
import models.Wallet;

public class DAO {
	private Jdbi jdbi;

	public DAO() {
		this.jdbi = setupJdbi();
	}

	private Jdbi setupJdbi() {
		if (jdbi == null) {

<<<<<<< HEAD
<<<<<<< HEAD
			

			String url = "jdbc:postgresql://proknow.postgres.database.azure.com:5432/proknow?user=andreLuiz&password=Proknow123&sslmode=require";
			String username = "andreLuiz";
			String password = "Proknow123";
=======
			String url = "jdbc:postgresql://proknow-db.postgres.database.azure.com:5432/postgres?user=adm&password=Proknow1!&sslmode=require";
			String username = "adm";
			String password = "Proknow1!";
>>>>>>> 04cee937af574fcc24c8d159a0e0db5e22443e92
=======
			// String url = "jdbc:postgresql://proknow-db.postgres.database.azure.com:5432/postgres?user=adm&password=Proknow1!&sslmode=require";
			// String username = "adm";
			// String password = "Proknow1!";

			String url =
			"jdbc:postgresql://proknow.postgres.database.azure.com:5432/proknow?user=andreLuiz&password=Proknow123&sslmode=require";
			String username = "andreLuiz";
			String password = "Proknow123";
>>>>>>> 3282ce5059862ed4beadefc4a49cb21fe51bf6df

			jdbi = Jdbi.create(url, username, password);
			jdbi.installPlugin(new SqlObjectPlugin());
			jdbi.registerRowMapper(BeanMapper.factory(Usuario.class));
			jdbi.registerRowMapper(BeanMapper.factory(UserBadge.class));
			jdbi.registerRowMapper(BeanMapper.factory(Badge.class));
			jdbi.registerRowMapper(BeanMapper.factory(Wallet.class));
		}
		return jdbi;
	}

	public Jdbi getJdbiContext() {
		return jdbi;
	}
}
