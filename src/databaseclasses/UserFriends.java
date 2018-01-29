/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseclasses;

/**
 *
 * @author abanoub samy
 */
public class UserFriends {
    
    private int userId;
    private int friendId;

    /**
     *
     */
    public UserFriends() {
    }
    
    /**
     *
     * @param userId
     * @param friendId
     */
    public UserFriends(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the friendId
     */
    public int getFriendId() {
        return friendId;
    }

    /**
     * @param friendId the friendId to set
     */
    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
    
    
    
}
