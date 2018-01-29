/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import databaseclasses.Chat;
import databaseclasses.Notification;
import databaseclasses.Users;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author abanoub samy
 */
public interface UserDaoInterface extends DaoInterface<Users>{
    
    /**
     *
     * @param user
     * @return
     */
    public ArrayList<Users> getUserFriends(Users user);
    
    /**
     *
     * @param user
     * @return
     */
    public ArrayList<Users> getFriendRequests(Users user);
    
    /**
     *
     * @param user
     * @return
     */
    public ArrayList<Notification>getMyNotifications(Users user);
    
    /**
     *
     * @param user
     * @return
     */
    public ArrayList<Notification>getAdminNotifications(Users user);
    
    /**
     *
     * @param user
     * @return
     */
    public boolean checkUserByEmail(Users user);
    
    /**
     *
     * @param user
     * @return
     */
    public boolean checkUserByEmailAndPass(Users user);
    
    /**
     *
     * @param c
     * @return
     */
    public ArrayList<Users> selectChatUsers(Chat c);
    
    
    
    
    
    
}
