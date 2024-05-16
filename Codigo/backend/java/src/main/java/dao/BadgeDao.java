package dao;

import models.Badge;
import java.util.List;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.UUID;

public interface BadgeDao {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS badges (id UUID PRIMARY KEY, name VARCHAR, description VARCHAR , enable bool, linkImage VARCHAR)")
    void createTable();

    @SqlUpdate("INSERT INTO badges (id, name, description, enable, linkImage) VALUES (:id, :name, :description, :enable, :linkImage)")
    void insert(@Bind("id") UUID id, @Bind("name") String name, @Bind("description") String description,
            @Bind("enable") Boolean enable, @Bind("linkImage") String linkImage);

    @SqlQuery("SELECT * FROM badges")
    List<Badge> listBadge();

    @SqlUpdate("DELETE FROM badges WHERE id = :id")
    void delete(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM badges WHERE id = :id")
    Badge findById(@Bind("id") UUID id);

}