package dao;

import java.util.List;
import models.Badges;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.Date;
import java.text.SimpleDateFormat;
import javafx.scene.chart.PieChart.Data;

public interface BadgesDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS badges (id INTEGER PRIMARY KEY, name VARCHAR, description VARCHAR , enable bool, linkImage String, howToUnlock VARCHAR)")
    void createTable();
    
    @SqlUpdate("INSERT INTO badges (id, name, description, enable, linkImage, howToUnlock) VALUES (:id, :name, :description, :enable, :linkImage, :howToUnlock)")
    void insert(@Bind("id") int id, @Bind("name") String name, @Bind("description") String description, @Bind("enable") Boolean enable,  @Bind("linkImage") String linkImage,  @Bind("howToUnlock") String howToUnlock);

    @SqlQuery("SELECT * FROM badge WHERE id = 1")
    List<Badges> listBadges();    
}