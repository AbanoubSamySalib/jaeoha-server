/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementation;

import dao.interfaces.ChatDaoInterface;
import database.connection.DatabaseConnectionHandler;
import databaseclasses.Chat;
import databaseclasses.Users;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abanoub samy
 */
public class ChatDaoImpl implements ChatDaoInterface{
    
    /**
     *
     */
    public ChatDaoImpl()
    {
        
        
        
    }

    /**
     *
     * @param c which is an object of chat class
     * @return boolean true if insertion operation succeed and false if insertion operation failed.
     */
    @Override
    public boolean insert(Chat c)  {
      boolean b=false;
       try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("insert into chat (chatName,chatType) values(?,?)");
           pstm.setString(1, c.getChatName());
           pstm.setInt(2, c.getChatType());
           pstm.executeUpdate();
           b=true;
          } catch (SQLException ex) {
              b=false;
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return b;
    
    }

         //esraaa

    /**
     *
     @param c which is an object of chat class
     * @return boolean true if insertion operation succeed and false if insertion operation failed.
   */
    public boolean insertGroupChat(Chat c)  {
      boolean b=false;
       try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("insert into chat (chatName,chatType,chatCreationDate) values(?,?,?)");
           pstm.setString(1, c.getChatName());
           pstm.setInt(2, c.getChatType());
           pstm.setTimestamp(3,Timestamp.from(Instant.now()));
           pstm.executeUpdate();
           b=true;
          } catch (SQLException ex) {
              b=false;
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return b;
    
    }

    /**
     *
      @param t which is an object of chat class
     * @return chat return the selected chat if exists and null if not.
     */
    @Override
    public Chat select(Chat t)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
      @param t which is an object of chat class carried information to be updated
     * @return boolean true if update operation succeed and false if update operation failed.
     */
    @Override
    public boolean update(Chat t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
      @param t which is an object of chat class
     * @return boolean true if deletion operation succeed and false if deletion operation failed.
     */
    @Override
    public boolean delete(Chat t)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param user object of class user represent the user for which the method select all of his chats
     * @param type type of chat ( for one to one chat and 1 for group chat).
     * @return arraylist of chats
     */
    @Override
    public ArrayList<Chat> select_allChats(Users user,String type) {
        
        ArrayList individualChats=null;
        try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("select * from `chat` where chatId in(select chatId from `chat_members` where memberId=? ) && chatType=?");
           pstm.setInt(1, user.getId());
           pstm.setString(2, type);
           ResultSet rs=pstm.executeQuery();
           individualChats=convertToArrayList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return individualChats;
    }



    //esraaaaaaaaaa

    /**
     *
     * @param rs resultset of the database
     * @return arraylist of chats which the resultset is converted to
     */
    @Override
    public ArrayList<Chat> convertToArrayList(ResultSet rs)  {
        ArrayList<Chat> chat = null;
        try {
            chat=new ArrayList();
            while (rs.next()) {
                Chat c = new Chat();
                c.setChatId(rs.getInt("chatId"));
                c.setChatName(rs.getString("chatName"));
                c.setChatType(rs.getInt("chatType"));//hnaa
                chat.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chat;
    }
    
    //esraaa
    //h7wlhm l array feha distinct values bl trteb

    /**
     *
     * @param rs
     * @return
     */
    public ArrayList<Chat> convertToArrayListSpecial(ResultSet rs)  {
        ArrayList<Chat> chat = null;
        try {
            HashMap map=new HashMap();
            chat=new ArrayList();
            while (rs.next()) {
              if(map.get(rs.getInt("chatId"))==null)
              {
                map.put(rs.getInt("chatId"), rs.getInt("chatId"));
                Chat c = new Chat();
                c.setChatId(rs.getInt("chatId"));
                c.setChatName(rs.getString("chatName"));
                c.setChatType(rs.getInt("chatType"));//hnaa
                chat.add(c);
              }

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chat;
    }
//esraaa

    /**
     *
     * @param user object of class user represent the user for which the method select all of his recent chats
     * @return arraylist of chats
     */
    @Override
    public ArrayList<Chat> select_allRecentChats(Users user) {
       
        ArrayList chats=null;
        try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("(SELECT    chat.chatId,chat.chatName,chat.chatType FROM messages inner join chat on chat.chatId=messages.chatId where chat.chatId in(select chatId from chat_members where memberId=?)  order by messages.messageDate desc)");
           
           pstm.setInt(1, user.getId());
           ResultSet rs=pstm.executeQuery();
            chats=convertToArrayListSpecial(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
     return chats;
    
    }

    /**
     *
     * @param user object of class user represent the user for which the method select all of his chats
     * @return arraylist of chats

     */
    @Override
    public Chat select_individualChat(Users user, Users friend) {
      
         Chat chat=null;
         try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("select * from chat where chatId in(select chatId from chat_members where memberId=? or memberId=? group by chatId having count(chatId)=2) and chatType=?");
           pstm.setInt(1, user.getId());
           pstm.setInt(2, friend.getId());
           pstm.setInt(3, 0);
           ResultSet rs=pstm.executeQuery();
           if(rs.isBeforeFirst())
           {rs.next();
           chat=new Chat(rs.getInt(1),rs.getString(2),rs.getInt(3));
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return chat;
    }
    
    /**
     *
     * @param user the user of application
     * @param friend the other user of which the chat is created with
     * @return 0 if the insertion failed and non zero if the insertion succeed
     */
    @Override
    public int insert_individualChat(Users user, Users friend) {
      
         int rs=0;
         try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("insert into chat (chatName,chatType) values(?,?)");
           pstm.setString(1, friend.getUserName());
           pstm.setInt(2, 0);
            rs=pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return rs;
    }

   
    @Override
    public boolean insert_chatMemberTable(Chat chat, ArrayList<Users> users) {
         boolean b=true;
       try (Connection con=DatabaseConnectionHandler.getConnection()){
           con.setAutoCommit( false );
           PreparedStatement pstm=con.prepareStatement("insert into chat_members (chatId,memberId) values(?,?)");
          
           for(int i=0;i<users.size();i++)
           {
                pstm.setInt(1, chat.getChatId());
                pstm.setInt(2, users.get(i).getId());
                pstm.addBatch();

           }
           int[] updateCounts= pstm.executeBatch();
           
           for(int i=0;i<updateCounts.length;i++)//check an kolo tmam 34an y commit
           {
               if(updateCounts[i]==Statement.EXECUTE_FAILED)
               {
                   b=false;
               }
           }
           if(b==true)
           con.commit();
          } catch (SQLException ex) {
              b=false;
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return b;
    }

    /**
     *
     * @param chat 
     * @return chat object carried select information
     */
    @Override
    public Chat selectChatByName(Chat chat) {
       Chat c=null;
         try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("select * from chat where chatName=?");
           pstm.setString(1, chat.getChatName());
           ResultSet rs=pstm.executeQuery();
           rs.next();
           c=new Chat(rs.getInt(1),rs.getString(2),rs.getInt(3));
           } catch (SQLException ex) {
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return c;
    
    }

   
       //esraa

    /**
     *
     * @param chat
     * @return
     */

   
    public Chat selectChatByDate_name(Chat chat) {
       Chat c=null;
         try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("select * from chat where chatName=? order by chatCreationDate Desc");
            pstm.setString(1, chat.getChatName());
           ResultSet rs=pstm.executeQuery();
           rs.next();
           c=new Chat(rs.getInt(1),rs.getString(2),rs.getInt(3));
            } catch (SQLException ex) {
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return c;
    
    }

 
}
