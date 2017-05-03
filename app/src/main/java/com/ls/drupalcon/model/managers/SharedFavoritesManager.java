package com.ls.drupalcon.model.managers;

import com.ls.drupalcon.model.Model;
import com.ls.drupalcon.model.PreferencesManager;
import com.ls.drupalcon.model.dao.EventDao;
import com.ls.drupalcon.model.dao.SharedFavoritesDao;
import com.ls.drupalcon.model.dao.SharedScheduleDao;
import com.ls.drupalcon.model.data.Event;
import com.ls.drupalcon.model.data.FriendsFavoriteItem;
import com.ls.utils.L;

import java.util.ArrayList;
import java.util.List;

public class SharedFavoritesManager {
    private SharedScheduleDao sharedScheduleDao;
    private SharedFavoritesDao sharedFavoritesDao;

    public SharedFavoritesManager() {
        sharedFavoritesDao = new SharedFavoritesDao();
        sharedScheduleDao = new SharedScheduleDao();
    }

    private List<FriendsFavoriteItem> getAllFriendsFavorite() {
        List<FriendsFavoriteItem> allSafe = sharedFavoritesDao.getAllSafe();
        return allSafe;
    }

    public ArrayList<Long> getFavoriteEventIds() {

        ArrayList<Long> favoriteEventIds = new ArrayList<>();
        for (FriendsFavoriteItem favorite : getAllFriendsFavorite()) {
            if (favorite.getSharedScheduleCode() == (Model.instance().getSharedScheduleManager().getCurrentScheduleId()))
                favoriteEventIds.add(favorite.getEventId());
        }
        L.e("getFavoriteEventIds = " + favoriteEventIds);
        return favoriteEventIds;
    }

    public ArrayList<Long> getMyEventIds() {

        ArrayList<Long> favoriteEventIds = new ArrayList<>();
        for (FriendsFavoriteItem favorite : getAllFriendsFavorite()) {
            if (favorite.getSharedScheduleCode() == (PreferencesManager.getInstance().getMyScheduleCode()))
                favoriteEventIds.add(favorite.getEventId());
        }
        L.e("getMyEventIds = " + favoriteEventIds);
        return favoriteEventIds;
    }

    public List<Event> getAllFriendsFavoriteEvent() {
        EventManager eventManager = Model.instance().getEventManager();
        EventDao eventDao = eventManager.getEventDao();
        List<Event> events = eventDao.selectEventsByIdsSafe(getFavoriteEventIds());
        return events;
    }

    public void saveFavorite(long id) {
        SharedScheduleManager sharedScheduleManager = Model.instance().getSharedScheduleManager();
        long currentScheduleId = sharedScheduleManager.getCurrentScheduleId();
        sharedFavoritesDao.saveDataSafe(new FriendsFavoriteItem(id, currentScheduleId));
    }

    public void deleteFavorite(long id) {
        sharedFavoritesDao.deleteDataSafe(id);
    }

    public void saveFavoritesSafe(ArrayList<FriendsFavoriteItem> items) {
        L.e("saveFavoritesSafe = " + items.toString());
        sharedFavoritesDao.saveDataSafe(items);
    }

    public void saveFavoritesSafe1(ArrayList<FriendsFavoriteItem> items) {
        L.e("saveFavoritesSafe = " + items.toString());
        sharedFavoritesDao.saveOrUpdateDataSafe(items);
    }

    public void saveFavoriteSafe(FriendsFavoriteItem item) {
        L.e("saveFavoriteSafe = " + item);
        sharedFavoritesDao.saveOrUpdateSafe(item);
    }

    public void deleteAll(){
        sharedFavoritesDao.deleteAllSafe();
    }


    public List<FriendsFavoriteItem> getAllFavoritesSafe() {
        return sharedFavoritesDao.getAllSafe();
    }

//    public SharedFavoritesDao getFriendsDao() {
//        return sharedFavoritesDao;
//    }
}
