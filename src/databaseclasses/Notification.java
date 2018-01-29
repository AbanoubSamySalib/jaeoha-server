/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseclasses;

import java.io.Serializable;

/**
 *
 * @author abanoub samy
 */
public class Notification implements Serializable{
    
    private Integer notifId;
    private String notifText;
    private Integer toAll;
    private Integer recieverId;

    /**
     *
     */
    public Notification() {
    }

    /**
     *
     * @param notifText
     * @param toAll
     */
    public Notification(String notifText, Integer toAll) {
       
        this.notifText = notifText;
        this.toAll = toAll;
        
    }

    /**
     *
     * @param notifText
     * @param toAll
     * @param recieverId
     */
    public Notification( String notifText, Integer toAll, Integer recieverId) {
      
        this.notifText = notifText;
        this.toAll = toAll;
        this.recieverId = recieverId;
    }

    /**
     * @return the notifId
     */
    public Integer getNotifId() {
        return notifId;
    }

    /**
     * @param notifId the notifId to set
     */
    public void setNotifId(Integer notifId) {
        this.notifId = notifId;
    }

    /**
     * @return the notifText
     */
    public String getNotifText() {
        return notifText;
    }

    /**
     * @param notifText the notifText to set
     */
    public void setNotifText(String notifText) {
        this.notifText = notifText;
    }

    /**
     * @return the toAll
     */
    public Integer getToAll() {
        return toAll;
    }

    /**
     * @param toAll the toAll to set
     */
    public void setToAll(Integer toAll) {
        this.toAll = toAll;
    }

    /**
     * @return the recieverId
     */
    public Integer getRecieverId() {
        return recieverId;
    }

    /**
     * @param recieverId the recieverId to set
     */
    public void setRecieverId(Integer recieverId) {
        this.recieverId = recieverId;
    }

   
}