/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementation;

import dao.interfaces.UserFriendReqDaoInterface;
import database.connection.DatabaseConnectionHandler;
import databaseclasses.UserFriendRequests;
import databaseclasses.Users;
import java.rmi.RemoteException;
import java.sql.Blob;
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
public class UserFriendReqDaoImpl implements UserFriendReqDaoInterface {

    /**
     *
     * @param userId
     * @param userRetrievedId
     * 
     * check if friend request has been sent to user who appeared in search result
     * 
     * @return boolean
     */
    @Override
    public boolean checkIfRequested(int userId, int userRetrievedId)  {

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            ResultSet rs = null;

            PreparedStatement pst = conn.prepareStatement("select * from user_friend_requests where senderId = ? and recieverId = ? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setInt(1, userId);
            pst.setInt(2, userRetrievedId);

            rs = pst.executeQuery();
            if (rs.next()) {
                return true;

            } else {

                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    /**
     *
     * @param t
     * 
     * insert friend request into database
     * @return boolean
     */
    @Override
    public boolean insert(UserFriendRequests t)  {

        try (Connection conn = DatabaseConnectionHandler.getConnection(); PreparedStatement pst = conn.prepareStatement("INSERT INTO  user_friend_requests"
                + "( recieverId ,senderId) "
                + "values (?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {

            pst.setInt(1, t.getRecieverId());
            pst.setInt(2, t.getSenderId());

            pst.executeUpdate();

            System.out.println("request added successfully");

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
    public UserFriendRequests select(UserFriendRequests t)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public boolean update(UserFriendRequests t)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param t
     * delete friend request from database
     * 
     * @return boolean 
     */
    @Override
    public boolean delete(UserFriendRequests t)  {

        try (Connection conn = DatabaseConnectionHandler.getConnection(); PreparedStatement pst = conn.prepareStatement(" delete from   user_friend_requests "
                + "  where recieverId = ? and senderId = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
              pst.setInt(1, t.getRecieverId());
            pst.setInt(2, t.getSenderId());

            pst.executeUpdate();

            System.out.println("requestdeleted successflly");

            return true;
        } catch (SQLException ex) {

            System.out.println("error in delete query");
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }

    }

    /**
     *
     * @param rs
     * @return
     */
    @Override
    public ArrayList<UserFriendRequests> convertToArrayList(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
