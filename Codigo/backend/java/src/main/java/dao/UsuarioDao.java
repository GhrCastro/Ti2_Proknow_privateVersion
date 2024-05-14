package dao;

import models.Usuario;
import java.time.LocalDate;
import java.util.List;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.Date;
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
    
}

