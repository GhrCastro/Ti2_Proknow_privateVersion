package dao;

<<<<<<< HEAD
=======
import java.util.List;
import java.util.UUID;

>>>>>>> service-and-application-layers
import models.Usuario;
import java.time.LocalDate;
import java.util.List;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.Date;
<<<<<<< HEAD
import java.text.SimpleDateFormat;
import java.util.UUID;

public interface UsuarioDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, name VARCHAR, cpf VARCHAR , email VARCHAR, salary FLOAT, cellNumber VARCHAR, password VARCHAR, expenses VARCHAR, regDate DATE)")
    public void createTable();
    
    @SqlUpdate("INSERT INTO user (id, name, cpf, email, salary, cellNumber, password, expenses, regDate) VALUES (:id, :name, :cpf, :email, :salary, :cellNumber, :password, :expenses, :regDate)")

    public static void insert(@Bind("id") UUID id, @Bind("name") String name, @Bind("cpf") String cpf, @Bind("email") String email,  @Bind("salary") Double salary,  @Bind("cellNumber") String cellNumber,  @Bind("password") String password,  @Bind("expenses") Double expenses,  @Bind("regDate") LocalDate regDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @SqlQuery("SELECT * FROM user WHERE id = 1")
    public List<Usuario> listUsuarios();
    
=======

public interface UsuarioDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS users (id UUID PRIMARY KEY, name VARCHAR, cpf VARCHAR, email VARCHAR, password VARCHAR, reg_date DATE)")
    void createTable();

    @SqlUpdate("INSERT INTO users (id, name, cpf, email, password, reg_date) VALUES (:id, :name, :cpf, :email, :password, :regDate)")
    void insert(@Bind("id") UUID id, @Bind("name") String name, @Bind("cpf") String cpf, @Bind("email") String email, @Bind("password") String password, @Bind("regDate") Date regDate);

    @SqlUpdate("UPDATE users SET name = :name, cpf = :cpf, email = :email, password = :password, reg_date = :regDate WHERE id = :id")
    void update(@Bind("id") UUID id, @Bind("name") String name, @Bind("cpf") String cpf, @Bind("email") String email, @Bind("password") String password, @Bind("regDate") Date regDate);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    void delete(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Usuario findById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM users")
    List<Usuario> listUsuarios();

>>>>>>> service-and-application-layers
}

