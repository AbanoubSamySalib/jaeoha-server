/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementation;

import dao.interfaces.UserDaoInterface;
import database.connection.DatabaseConnectionHandler;
import databaseclasses.Chat;
import databaseclasses.Notification;
import databaseclasses.Users;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author abanoub samy
 */
public class UserDaoImpl implements UserDaoInterface {

    /**
     *
     */
    public UserDaoImpl() {
    }

    // check user email if exists

    /**
     *
     * @param user
     * 
     * check if user exists in database or not
     * 
     * @return boolean
     */
    @Override
    public boolean checkUserByEmail(Users user) {

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            ResultSet rs = null;

            PreparedStatement pst = conn.prepareStatement("select * from users where email = ? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, user.getEmail());

            rs = pst.executeQuery();
            if (rs.next()) {
                return false;

            } else {

                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }

    }

    /**
     *
     * @param t
     * insert new user into database
     * @return boolean
     */
    @Override
    public boolean insert(Users t) {

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            PreparedStatement pst = conn.prepareStatement("INSERT INTO  users"
                    + "( userName ,email,phoneNo,gender,country,password,status,photo,active) "
                    + "values (?,?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, t.getUserName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getPhone());
            pst.setString(4, t.getGender());
            pst.setString(5, t.getCountry());
            pst.setString(6, t.getPassword());
            pst.setString(7, "Available");
            if (t.getImage() == null) {
                //hygeb default image
                //Blob b=new SerialBlob(this.getDefaultImage()) ;
                // pst.setBlob(8,b);
                pst.setNull(8, Types.BOOLEAN);
            } else {
                Blob b = new SerialBlob(t.getImage());
                pst.setBlob(8, b);
            }
            pst.setInt(9, t.getActive());
            pst.executeUpdate();

            System.out.println("user added successfully");

            return true;
        } catch (SQLException ex) {
            System.out.println("Error in user insertion");
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    //select user data when trying to log in 

    /**
     *
     * @param t
     * get particular user data from database
     * @return Users
     */
    @Override
    public Users select(Users t) {

        boolean found = false;
        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            ResultSet rs = null;

            PreparedStatement pst = conn.prepareStatement("select id ,userName ,email,phoneNo,gender,"
                    + "country,password,status,photo,active"
                    + "   from users where email = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, t.getEmail());

            rs = pst.executeQuery();

            System.out.println("user selected successfully");

            while (rs.next()) {
                found = true;
                t.setId(rs.getInt(1));
                t.setUserName(rs.getString(2));
                t.setEmail(rs.getString(3));
                t.setPhone(rs.getString(4));
                t.setGender(rs.getString(5));

                t.setCountry(rs.getString(6));

                t.setPassword(rs.getString(7));
                t.setStatus(rs.getString(8));
                //  Blob b=rs.getBlob("photo");
                // t.setImage(b.getBytes(1, (int) b.length()));
                t.setImage(null);
                t.setActive(rs.getInt(10));
            }
            if (found) {
                return t;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     *
     * @param t
     * update user's data in database 
     * @return boolean
     */
    @Override
    public boolean update(Users t) {

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {
            PreparedStatement pst = conn.prepareStatement(" update  users set  userName = ? , email = ? ,"
                    + " phoneNo = ? , gender = ? , country = ? , password = ? ,  "
                    + "status = ? , photo = ? ,active = ? "
                    + "  where id = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, t.getUserName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getPhone());
            pst.setString(4, t.getGender());
            pst.setString(5, t.getCountry());
            pst.setString(6, t.getPassword());
            pst.setString(7, t.getStatus());
//             if(t.getImage()==null)
//            {
//                //hygeb default image
//               Blob b=new SerialBlob(this.getDefaultImage()) ;
//               pst.setBlob(8,b);
//            }
//            else
//            {
//                 Blob b=new SerialBlob(t.getImage()) ;
//                 pst.setBlob(8,b);
//            }

            if (t.getImage() == null) {
                //hygeb default image
                //Blob b=new SerialBlob(this.getDefaultImage()) ;
                // pst.setBlob(8,b);
                pst.setNull(8, Types.BOOLEAN);
            } else {
                Blob b = new SerialBlob(t.getImage());
                pst.setBlob(8, b);
            }

            pst.setInt(9, t.getActive());
            pst.setInt(10, t.getId());

            pst.executeUpdate();

            System.out.println("updated successfully");

            return true;
        } catch (SQLException ex) {
            System.out.println("error in update query");
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    /**
     *
     * @param t
     * delete particular user from database
     * @return boolean 
     */
    @Override
    public boolean delete(Users t) {

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            PreparedStatement pst = conn.prepareStatement(" delete from   users "
                    + "  where id = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setInt(1, t.getId());

            pst.executeUpdate();

            System.out.println("user deleted successflly");

            return true;
        } catch (SQLException ex) {

            System.out.println("error in delete query");
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    /**
     *
     * @param user
     *  retrieve user friends from database 
     * @return ArrayList Users
     */
    @Override
    public ArrayList<Users> getUserFriends(Users user) {

        ArrayList<Users> friends = new ArrayList<>();

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            ResultSet rs = null;

//            PreparedStatement pst = conn.prepareStatement("select id ,userName ,email,status,photo,active from users,user_friends where users.id = user_friends.friendId\n"
//                    + "and user_friends.userId = ?;",
//                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);//feha 8alta
            PreparedStatement pst = conn.prepareStatement("select id ,userName ,email,status,photo,active from users where id in(select friendId from user_friends where userId=?)or id in(select userId from user_friends where friendId=?)",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);//3dlt hnaaaa
            pst.setInt(1, user.getId());
            pst.setInt(2, user.getId());
            rs = pst.executeQuery();

            System.out.println("friend selected successfully");

            friends = convertToArrayList(rs);
            user.setFriends(friends);

        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return friends;

    }

    /**
     *
     * @param rs
     * convert result data retrieved from database produced in result set to ArrayList of Users
     * @return ArrayList Users
     */
    @Override
    public ArrayList<Users> convertToArrayList(ResultSet rs) {
        ArrayList<Users> users = new ArrayList<>();
        try {

            while (rs.next()) {

                Users u = new Users();
                u.setId(rs.getInt(1));
                System.err.println(rs.getInt(1));
                u.setUserName(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setStatus(rs.getString(4));
                if (rs.getBlob(5) != null) {
                    Blob b = rs.getBlob("photo");
                    u.setImage(b.getBytes(1, (int) b.length()));
                } else {
                    u.setImage(null);
                }
                u.setActive(rs.getInt(6));

                users.add(u);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    /**
     *
     * @param user
     * 
     * retrieve list of Users who sent friend requests to particular user
     * @return ArrayList Users
     */
    @Override
    public ArrayList<Users> getFriendRequests(Users user) {

        ArrayList<Users> userWhoRequested = new ArrayList<>();

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            ResultSet rs = null;

            PreparedStatement pst = conn.prepareStatement("select id ,userName ,email,status,photo,active from users,user_friend_requests where users.id =user_friend_requests.senderId\n"
                    + "and user_friend_requests.recieverId =?;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setInt(1, user.getId());
//            pst.setInt(1, 4);

            rs = pst.executeQuery();

            System.out.println("user who requested selected successfully");

            userWhoRequested = convertToArrayList(rs);

        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userWhoRequested;

    }

    /**
     *
     * @param user
     * 
     * check for user if exists in database 
     * 
     * @return boolean
     */
    @Override
    public boolean checkUserByEmailAndPass(Users user) {

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            ResultSet rs = null;

            PreparedStatement pst = conn.prepareStatement("select * from users where email = ? and password = ? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());

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

    // return responsesss

    /**
     *
     * @param user
     * 
     * retrieve notifications of particular user
     * @return ArrayList Notification
     */
    @Override
    public ArrayList<Notification> getMyNotifications(Users user) {

        ArrayList<Notification> retrievedNotifications = new ArrayList<>();
        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            ResultSet rs = null;

//            PreparedStatement pst = conn.prepareStatement("select notifText  from notifications,notifreceiver where notifications.notifId = notifreceiver.notifId and notifreceiver.recieverId = ?",
//                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            PreparedStatement pst = conn.prepareStatement("select notifText  from notifications where recieverId = ? order by notifId desc",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //   pst.setInt(1, user.getId());
            pst.setInt(1, user.getId());

            rs = pst.executeQuery();

            System.out.println("notifications selected successfully");

            while (rs.next()) {

                Notification n = new Notification();
                n.setNotifText(rs.getString(1));
                retrievedNotifications.add(n);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retrievedNotifications;

    }

    /**
     *
     * @param c
     * retrieve all Users who exists in particular chat 
     * 
     * @return ArrayList Users
     */
    @Override
    public ArrayList<Users> selectChatUsers(Chat c) {

        ArrayList<Users> userWhoRequested = new ArrayList<>();

        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            System.err.println("chatid=" + c.getChatId());
            ResultSet rs = null;
            PreparedStatement pst = conn.prepareStatement("select id,userName,email,status,photo,active from users where id in (select memberId from chat_members where chatId=?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setInt(1, c.getChatId());
            rs = pst.executeQuery();
            userWhoRequested = convertToArrayList(rs);

        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userWhoRequested;

    }

    //return admin notificationsss

    /**
     *
     * @param user
     * 
     * retrieve notification admin announcement of particular user 
     * 
     * @return ArrayList Notification
     */
    @Override
    public ArrayList<Notification> getAdminNotifications(Users user) {

        ArrayList<Notification> retrievedNotifications = new ArrayList<>();
        try (Connection conn = DatabaseConnectionHandler.getConnection()) {

            ResultSet rs = null;

            PreparedStatement pst = conn.prepareStatement("select notifText  from notifications where  toAll = 1 order by notifId desc",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            rs = pst.executeQuery();

            System.out.println(" admin notifications selected successfully");

            while (rs.next()) {

                Notification n = new Notification();
                n.setNotifText(rs.getString(1));
                retrievedNotifications.add(n);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retrievedNotifications;

    }

    private byte[] getDefaultImage() {
        File imgPath = null;
        try {
            System.err.println("path:" + getClass().toString());
            imgPath = new File(getClass().getResource("user.png").toString());
            BufferedImage bufferedImage = ImageIO.read(imgPath);
            WritableRaster raster = bufferedImage.getRaster();
            DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
            //from byte[] to byte
            return data.getData();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
