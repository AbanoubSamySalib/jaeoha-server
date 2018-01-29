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
public class NotificationRecievers {
    
    
    private int notifId;
    private int recieverId;

    /**
     *
     */
    public NotificationRecievers() {
    }

    /**
     *
     * @param notifId
     * @param recieverId
     */
    public NotificationRecievers(int notifId, int recieverId) {
        this.notifId = notifId;
        this.recieverId = recieverId;
    }

    /**
     * @return the notifId
     */
    public int getNotifId() {
        return notifId;
    }

    /**
     * @param notifId the notifId to set
     */
    public void setNotifId(int notifId) {
        this.notifId = notifId;
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
