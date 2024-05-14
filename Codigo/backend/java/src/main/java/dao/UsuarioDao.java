package dao;

import java.util.List;
import models.Usuario;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.Date;
import java.text.SimpleDateFormat;

import javafx.scene.chart.PieChart.Data;

public interface UsuarioDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, name VARCHAR, cpf VARCHAR , email VARCHAR, salary FLOAT, cellNumber VARCHAR, password VARCHAR, expenses VARCHAR, regDate DATE)")
    public void createTable();
    
    @SqlUpdate("INSERT INTO user (id, name, cpf, email, salary, cellNumber, password, expenses, regDate) VALUES (:id, :name, :cpf, :email, :salary, :cellNumber, :password, :expenses, :regDate)")
    public void insert(@Bind("id") int id, @Bind("name") String name, @Bind("cpf") String cpf, @Bind("email") String email,  @Bind("salary") Double salary,  @Bind("cellNumber") String cellNumber,  @Bind("password") String password,  @Bind("expenses") Double expenses,  @Bind("regDate") Date regDate);

    @SqlQuery("SELECT * FROM user WHERE id = 1")
    public List<Usuario> listUsuarios();
    
}
