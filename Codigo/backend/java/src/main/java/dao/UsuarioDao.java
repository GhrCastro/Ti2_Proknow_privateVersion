package dao;

import java.util.List;
import java.util.UUID;
import java.util.Date;

import models.UserBadge;
import models.Usuario;
import models.Wallet;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UsuarioDao {

        // Metodos tabela users
        @SqlUpdate("CREATE TABLE IF NOT EXISTS users (id UUID PRIMARY KEY, name VARCHAR(200), cpf VARCHAR(200), email VARCHAR(200), salary DOUBLE PRECISION, cellnumber VARCHAR(200), password VARCHAR(200), expenses DOUBLE PRECISION, reg_date DATE, wallet_id UUID, FOREIGN KEY (wallet_id) REFERENCES wallets(id))")
        void createTable();

        @SqlUpdate("INSERT INTO users (id, name, cpf, email, salary, cellNumber, password, expenses, reg_date, wallet_id) VALUES (:id, :name, :cpf, :email, :salary, :cellNumber, :password, :expenses, :regDate, :walletId)")
        void insert(@Bind("id") UUID id, @Bind("name") String name, @Bind("cpf") String cpf,
                        @Bind("email") String email,
                        @Bind("salary") double salary, @Bind("cellNumber") String cellNumber,
                        @Bind("password") String password,
                        @Bind("expenses") double expenses, @Bind("regDate") Date regDate,
                        @Bind("walletId") UUID walletId);

        @SqlUpdate("UPDATE users SET name = :name, cpf = :cpf, email = :email, salary = :salary, cellNumber = :cellnumber, password = :password, expenses = :expenses, reg_date = :regDate, wallet_id = :walletId WHERE id = :id")
        void update(@Bind("id") UUID id, @Bind("name") String name, @Bind("cpf") String cpf,
                        @Bind("email") String email,
                        @Bind("salary") double salary, @Bind("cellNumber") String cellNumber,
                        @Bind("password") String password,
                        @Bind("expenses") double expenses, @Bind("regDate") Date regDate,
                        @Bind("walletId") UUID walletId);

        @SqlUpdate("DELETE FROM users WHERE id = :id")
        void delete(@Bind("id") UUID id);

        @SqlQuery("SELECT * FROM users WHERE id = :id")
        @RegisterBeanMapper(Usuario.class)
        Usuario findById(@Bind("id") UUID id);

        @SqlQuery("SELECT * FROM users WHERE email = :email ")
        @RegisterBeanMapper(Usuario.class)
        Usuario findByEmail(@Bind("email") String email);

        @SqlQuery("SELECT * FROM users")
        @RegisterBeanMapper(Usuario.class)
        List<Usuario> listUsuarios();

        // ---------------------------------------------//

        // Metodos tabela users_badges
        @SqlUpdate("CREATE TABLE IF NOT EXISTS users_badges (user_id UUID, badge_id UUID, PRIMARY KEY(user_id, badge_id))")
        void createUserBadgesTable();

        // enable badge para o usuario
        @SqlUpdate("INSERT INTO users_badges (user_id, badge_id) VALUES( :user_id, :badge_id)")
        void insertUserBadge(@Bind("user_id") UUID user_id, @Bind("badge_id") UUID badge_id);
        // badge cadastro = c081aab6-f162-49b4-b5b5-f5ba9b8e9214
        // badge video = 8d3b5e64-738f-4d54-88a0-f1ccbf7146f2
        // badge transação = 0c64e08b-0c64-4a7d-b2c2-989b59e5f9e6

        // Retorna todos os ids das badges que o usuario possui
        @SqlQuery("SELECT * FROM users_badges WHERE user_id = :user_id")
        @RegisterBeanMapper(UserBadge.class)
        List<UserBadge> listAllUsersBadges(@Bind("user_id") UUID user_id);

        @SqlUpdate("DELETE FROM users_badges WHERE user_id = :user_id AND badge_id = :badge_id")
        void deleteUserBadge(@Bind("user_id") UUID user_id, @Bind("badge_id") UUID badge_id);

        @SqlQuery("SELECT * FROM users_badges WHERE user_id = :user_id AND badge_id = :badge_id")
        @RegisterBeanMapper(UserBadge.class)
        UserBadge findUserBadge(@Bind("user_id") UUID user_id, @Bind("badge_id") UUID badge_id);

        // -------------------------------------//
}
