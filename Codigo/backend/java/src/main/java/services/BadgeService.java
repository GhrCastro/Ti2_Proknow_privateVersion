package services;

import java.util.List;
import java.util.UUID;

import dao.DAO;
import dao.BadgeDao;
import models.Badge;

public class BadgeService {
    private final BadgeDao badgeDao;

    public BadgeService(DAO dao) {
		this.badgeDao = dao.getJdbiContext().onDemand(BadgeDao.class);
		createTableIfNotExists();
	}

    public void createTableIfNotExists() {
        badgeDao.createTable();
    }

    public Badge getBadgeById(UUID id) {
        return badgeDao.findById(id);
    }

    public List<Badge> getAllBadges() {
        return badgeDao.listBadge();
    }

    public void deleteBadge(UUID id) {
        badgeDao.delete(id);
    }

    public void addBadge(Badge badge){
        badgeDao.insert(badge.getId(),badge.getName(), badge.getDescription(),badge.getEnable(),badge.getLinkImage());
    }

    public void updateBadge (UUID id){
        badgeDao.updateBadge(id);
    }
}
