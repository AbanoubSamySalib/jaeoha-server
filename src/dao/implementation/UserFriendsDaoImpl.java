/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementation;

import dao.interfaces.UserFriendsDaoInterface;
import database.connection.DatabaseConnectionHandler;
import databaseclasses.UserFriends;
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
public class UserFriendsDaoImpl implements UserFriendsDaoInterface{

    /**
     *
     * @param t
     * 
     * insert new friend into database
     * 
     * @return boolean
     */
    @Override
    public boolean insert(UserFriends t)  {

 try (Connection conn = DatabaseConnectionHandler.getConnection(); PreparedStatement pst = conn.prepareStatement("INSERT INTO  user_friends "
                + "( userId ,friendId) "
                + "values (?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {

            pst.setInt(1, t.getUserId());
            pst.setInt(2, t.getFriendId());

            pst.executeUpdate();

            System.out.println("user added successfully");

            return true;
        } catch (SQLException ex) {
            System.out.println("Error in user insertion");
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public UserFriends select(UserFriends t)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public boolean update(UserFriends t)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public boolean delete(UserFriends t)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param rs
     * @return
     */
    @Override
    public ArrayList<UserFriends> convertToArrayList(ResultSet rs)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
