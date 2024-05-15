package models;

import java.util.UUID;

public class UserBadge {
    
    private UUID user_id;
    
    private UUID badge_id;

    public UserBadge(){
        this.user_id = null;
        this.badge_id = null;
    }
    
    public UserBadge(UUID user_id, UUID badge_id ){
        this.user_id = user_id;
        this.badge_id = badge_id;
    }
    
    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public UUID getBadge_id() {
        return badge_id;
    }
    
    public void setBadge_id(UUID badge_id) {
        this.badge_id = badge_id;
    }
}
