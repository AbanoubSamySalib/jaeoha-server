/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementation;

import dao.interfaces.NotificationsDaoInterface;
import database.connection.DatabaseConnectionHandler;
import databaseclasses.Notification;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abanoub samy
 */
public class NotificationsDaoImpl implements NotificationsDaoInterface {
    
    /**
     *
     * @throws RemoteException
     */
    public NotificationsDaoImpl() throws RemoteException {
        
    }

    /**
     *
     * @param t  
     * insert new notification (t) into database that is send to particular user 
     * @return boolean 
     */
    @Override
    public boolean insert(Notification t){
        
        try (Connection conn = DatabaseConnectionHandler.getConnection(); PreparedStatement pst = conn.prepareStatement("INSERT INTO  notifications "
                + "(notifText,toAll,recieverId) "
                + "values (?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            
            pst.setString(1, t.getNotifText());
            pst.setInt(2, t.getToAll());
             pst.setInt(3, t.getRecieverId());
            
            pst.executeUpdate();
            
            System.out.println("notification added successfully");
            
            return true;
        } catch (SQLException ex) {
            System.out.println("Error in notification insertion");
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     *
     * @param t
     * insert new notification (t) from admin to all users of the application
     * @return boolean
     */
    public boolean insertAll(Notification t){
        
        try (Connection conn = DatabaseConnectionHandler.getConnection(); PreparedStatement pst = conn.prepareStatement("INSERT INTO  notifications "
                + "(notifText,toAll) "
                + "values (?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            
            pst.setString(1, t.getNotifText());
            pst.setInt(2, t.getToAll());
            
            
            pst.executeUpdate();
            
            System.out.println("notification added successfully");
            
            return true;
        } catch (SQLException ex) {
            System.out.println("Error in notification insertion");
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     *
     * @param t
     * return notification to user
     * @return Notification
     */
    @Override
    public Notification select(Notification t)  {
        
        try (Connection conn = DatabaseConnectionHandler.getConnection()) {
            
            ResultSet rs = null;
            Notification n = new Notification();
            
            PreparedStatement pst = conn.prepareStatement("select notifId ,notifText "
                    + "   from notifications  ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            rs = pst.executeQuery();
            
            System.out.println("notifications selected successfully");
            rs.last();
            n.setNotifId(rs.getInt(0));
            n.setNotifText(rs.getString(1));
            
            return  n;
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    /**
     *
     * @param t
     * @return
     */
    @Override
    public boolean update(Notification t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *
     * @param t
     * @return
     */
    @Override
    public boolean delete(Notification t){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param rs
     * @return
     */
    @Override
    public ArrayList<Notification> convertToArrayList(ResultSet rs)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
    
}
