/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseclasses;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
/**
 *
 * @author omnia
 */
public class Users implements Serializable{
    
    private Integer id;
    private String userName;
    private String email;
    private String phone;
    private String country;
    private String gender;
    private byte[] image;
    private String password;
    private String status;
    private Integer active;
    private ArrayList<Users> friends;
    private ArrayList<Notification> notification;
    private ArrayList<Notification> adminNotification;
    private ArrayList<Users> friendRequests;
    
    /**
     *
     */
    public Users() {
         
       
    }

    /**
     * @param userName
     * @param email
     * @param phone
     * @param status
     * @param country
     * @param gender
     * @param active
     * 
     */
    
        public Users(String userName, String email, String phone,String gender,String country, String status, Integer active) {
       // constructor of table
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.gender = gender;
        this.status = status;
        this.active = active;
    }
    
    /**
     *
     * @param id
     * @param userName
     * @param email
     * @param phone
     * @param country
     * @param gender
     * @param image
     * @param password
     * @param status
     * @param active
     * @param friends
     * @param notification
     * @param adminNotification
     * @param friendRequests
     */
    public Users(Integer id, String userName, String email, String phone, String country, String gender, byte[] image, String password, String status, Integer active, ArrayList<Users> friends, ArrayList<Notification> notification, ArrayList<Notification> adminNotification, ArrayList<Users> friendRequests) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.gender = gender;
        this.image = image;
        this.password = password;
        this.status = status;
        this.active = active;
        this.friends = friends;
        this.notification = notification;
        this.adminNotification = adminNotification;
        this.friendRequests = friendRequests;
        
    }

    /**
     *
     * @return
     */
    public byte[] getImage() {
        return image;
    }

    /**
     *
     * @param image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

 
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the active
     */
    public Integer getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Integer active) {
        this.active = active;
    }

    /**
     * @return the friends
     */
    public ArrayList<Users> getFriends() {
        return friends;
    }

    /**
     * @param friends the friends to set
     */
    public void setFriends(ArrayList<Users> friends) {
        this.friends = friends;
    }

    /**
     * @return the notification
     */
    public ArrayList<Notification> getNotification() {
        return notification;
    }

    /**
     * @param notification the notification to set
     */
    public void setNotification(ArrayList<Notification> notification) {
        this.notification = notification;
    }

    /**
     * @return the adminNotification
     */
    public ArrayList<Notification> getAdminNotification() {
        return adminNotification;
    }

    /**
     * @param adminNotification the adminNotification to set
     */
    public void setAdminNotification(ArrayList<Notification> adminNotification) {
        this.adminNotification = adminNotification;
    }

    /**
     * @return the friendRequests
     */
    public ArrayList<Users> getFriendRequests() {
        return friendRequests;
    }

    /**
     * @param friendRequests the friendRequests to set
     */
    public void setFriendRequests(ArrayList<Users> friendRequests) {
        this.friendRequests = friendRequests;
    }

    
   

}