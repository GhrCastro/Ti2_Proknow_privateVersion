package dao;

import java.sql.Connection;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.core.statement.StatementException;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import models.UserBadge;
import models.Usuario;

public class DAO {
	private Jdbi jdbi;

	public DAO() {
		this.jdbi = setupJdbi();
	}

	private Jdbi setupJdbi() {
		if (jdbi == null) {
			String url = "jdbc:postgresql://proknow-db.postgres.database.azure.com:5432/postgres?user=adm&password=Proknow1!&sslmode=require";
			String username = "adm";
			String password = "Proknow1!";

			

			jdbi = Jdbi.create(url, username, password);
			jdbi.installPlugin(new SqlObjectPlugin());
			jdbi.registerRowMapper(BeanMapper.factory(Usuario.class));
			jdbi.registerRowMapper(BeanMapper.factory(UserBadge.class));
		}
		return jdbi;
	}	

	public Jdbi getJdbiContext() {
		return jdbi;
	}
}
