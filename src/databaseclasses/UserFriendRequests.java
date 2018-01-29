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
public class UserFriendRequests {
    
    
    private int senderId;
    private int recieverId;

    /**
     *
     */
    public UserFriendRequests() {
    }

    /**
     *
     * @param senderId
     * @param recieverId
     */
    public UserFriendRequests(int senderId, int recieverId) {
        this.senderId = senderId;
        this.recieverId = recieverId;
    }

    /**
     * @return the senderId
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * @param senderId the senderId to set
     */
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    /**
     * @return the recieverId
     */
    public int getRecieverId() {
        return recieverId;
    }

    /**
     * @param recieverId the recieverId to set
     */
    public void setRecieverId(int recieverId) {
        this.recieverId = recieverId;
    }
    
    
    
}
