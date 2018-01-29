/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementation;

import dao.interfaces.NotificationRecieverDaoInterface;
import database.connection.DatabaseConnectionHandler;
import databaseclasses.NotificationRecievers;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abanoub samy
 */
public class NotificationRecieversDaoImpl implements NotificationRecieverDaoInterface{

    @Override
    public boolean insert(NotificationRecievers t) {
  try (Connection conn = DatabaseConnectionHandler.getConnection(); PreparedStatement pst = conn.prepareStatement("INSERT INTO  notifreceiver "
                + "(notifId,recieverId) "
                + "values (?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            
            pst.setInt(1, t.getNotifId());
             pst.setInt(2, t.getRecieverId());
            
            pst.executeUpdate();
            
            System.out.println("notification reciever added successfully");
            
            return true;
        } catch (SQLException ex) {
            System.out.println("Error in user insertion");
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }


    }

    @Override
    public NotificationRecievers select(NotificationRecievers t)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(NotificationRecievers t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(NotificationRecievers t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<NotificationRecievers> convertToArrayList(ResultSet rs)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
