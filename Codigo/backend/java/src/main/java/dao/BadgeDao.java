package dao;

import models.Badge;
import java.util.List;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.time.LocalDate;
import java.text.SimpleDateFormat;

// Fimport javafx.scene.chart.PieChart.Data;


public interface BadgeDao {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS badges (id INTEGER PRIMARY KEY, name VARCHAR, description VARCHAR , enable bool, linkImage String)")
    public void createTable();
    
    @SqlUpdate("INSERT INTO badges (id, name, description, enable, linkImage, howToUnlock) VALUES (:id, :name, :description, :enable, :linkImage)")
    public void insert(@Bind("id") int id, @Bind("name") String name, @Bind("description") String description, @Bind("enable") Boolean enable,  @Bind("linkImage") String linkImage);

    @SqlQuery("SELECT * FROM badge WHERE id = 1")
    public List<Badge> listBadge();    
}