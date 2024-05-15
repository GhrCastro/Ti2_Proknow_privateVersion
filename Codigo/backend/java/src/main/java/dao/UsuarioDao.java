package dao;

import java.util.List;
import java.util.UUID;

import models.Badge;
import models.UserBadge;
import models.Usuario;
import java.time.LocalDate;
import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.Date;
import java.util.LinkedList;

public interface UsuarioDao {
    
    //Metodos tabela users
    @SqlUpdate("CREATE TABLE IF NOT EXISTS users (id UUID PRIMARY KEY, name VARCHAR(200), cpf VARCHAR(200), email VARCHAR(200), salary DOUBLE PRECISION, cellnumber VARCHAR(200), password VARCHAR(200), expenses DOUBLE PRECISION, reg_date DATE)")
    void createTable();

    @SqlUpdate("INSERT INTO users (id, name, cpf, email, salary, cellNumber, password, expenses, reg_date) VALUES (:id, :name, :cpf, :email, :salary, :cellNumber, :password, :expenses, :regDate)")
    void insert(@Bind("id") UUID id, @Bind("name") String name, @Bind("cpf") String cpf, @Bind("email") String email,
            @Bind("salary") double salary, @Bind("cellNumber") String cellNumber, @Bind("password") String password,
            @Bind("expenses") double expenses, @Bind("regDate") Date regDate);

    @SqlUpdate("UPDATE users SET name = :name, cpf = :cpf, email = :email, salary = :salary, cellNumber = :cellnumber, password = :password, expenses = :expenses, reg_date = :regDate WHERE id = :id")
    void update(@Bind("id") UUID id, @Bind("name") String name, @Bind("cpf") String cpf, @Bind("email") String email,
            @Bind("salary") double salary, @Bind("cellNumber") String cellNumber, @Bind("password") String password,
            @Bind("expenses") double expenses, @Bind("regDate") Date regDate);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    void delete(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Usuario findById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM users WHERE email = :email ")
    Usuario findByEmail(@Bind("email") String email);

    @SqlQuery("SELECT * FROM users")
    List<Usuario> listUsuarios();
    //---------------------------------------------//

    //Metodos tabela users_badges
    @SqlUpdate("CREATE TABLE IF NOT EXISTS users_badges (user_id UUID, badge_id UUID, PRIMARY KEY(user_id, badge_id))")
    void createUserBadgesTable();

    @SqlUpdate("INSERT INTO users_badges (user_id, badge_id) VALUES( :user_id, :badge_id)")
    void insertUserBadge(@Bind("user_id") UUID user_id, @Bind("badge_id") UUID badge_id);
    
    @SqlUpdate("DELETE FROM users_badges WHERE user_id = :user_id AND badge_id = :badge_id")
    void deleteUserBadge(@Bind("user_id") UUID user_id, @Bind("badge_id") UUID badge_id);

    @SqlQuery("SELECT * FROM users_badges WHERE user_id = :user_id")
    List<UserBadge> listAllUsersBadges(@Bind("user_id") UUID user_id);

    @SqlQuery("SELECT * FROM users_badges WHERE user_id = :user_id AND badge_id = :badge_id")
    UserBadge findUserBadge(@Bind("user_id") UUID user_id, @Bind("badge_id") UUID badge_id);

    //-------------------------------------//

}
