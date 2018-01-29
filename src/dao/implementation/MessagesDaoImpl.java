/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementation;

import dao.interfaces.MessagesDaoInterface;
import database.connection.DatabaseConnectionHandler;
import databaseclasses.Chat;
import databaseclasses.Message;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abanoub samy
 */
public class MessagesDaoImpl  implements MessagesDaoInterface{
    
    /**
     *
     */
    public MessagesDaoImpl()
    {
            
    }

    /**
     *
     * @param m object of message carried message information.
     * @return boolean true if insertion succeed and false if insertion filed
     */
    @Override
    public boolean insert(Message m)  {
         
       
          Connection con=DatabaseConnectionHandler.getConnection();
          
       try {
           PreparedStatement pstm = con.prepareStatement("insert into messages (messageText,messageDate,senderId,chatId,messageColor,fontSize,fontType) values(?,?,?,?,?,?,?)");
           pstm.setString(1, m.getMessageText());
           pstm.setTimestamp(2, m.getMessageTime());
          
//           if(m.getFileType()!=null)
//           {
//               pstm.setInt(4,m.getFileType());
//           }
//           else
//           {
//               pstm.setNull(4, Types.INTEGER);//hystha b null
//           }
           pstm.setInt(3, m.getSenderId());
           pstm.setInt(4, m.getChatId());
           pstm.setString(5, m.getMessageColor());
          
           if(m.getFontSize()!=null)
           {
                pstm.setInt(6, m.getFontSize());
           }
           else
           {
               pstm.setNull(6, Types.INTEGER);
           }
          
           pstm.setString(7, m.getFontType());
          
           pstm.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(MessagesDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
          
          return true;
    }

    /**
     *
     * @param m message object contained the messaged id to be selected
     * @return object of message carried selected information
     */
    @Override
    public Message select(Message m)  {
     
        System.err.println("jsd"+ m.getMessageTime());
               System.err.println("chid"+m.getChatId());
         Message message=null;
         try (Connection con=DatabaseConnectionHandler.getConnection()){
           PreparedStatement pstm=con.prepareStatement("select * from messages where chatId=? order by messageDate desc");
           
           pstm.setInt(1, m.getChatId());
           ResultSet rs=pstm.executeQuery();
           if(rs.isBeforeFirst())
           {rs.next();
               System.err.println("jsd"+rs.getInt(1));
               System.err.println(rs.getInt(5));
            message=new Message(rs.getInt(1),rs.getString(2),rs.getTimestamp(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getInt(7),rs.getString(8));
          
           
           
           
           }
           else
           {
               System.out.println("dao.implementation.MessagesDaoImpl.select()");
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(ChatDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
     return message;
    }

    
    @Override
    public boolean update(Message t) {
       
        return false;
    }

    @Override
    public boolean delete(Message t) {

        return false;
    }

    /**
     *
     * @param rs resultset of the database
     * @return arraylist of chats which the resultset is converted to
     */
    public ArrayList<Message> convertToArrayList(ResultSet rs)  {
        
       ArrayList<Message> message=new ArrayList<Message>();
        try {
            while(rs.next())
            {
               Message mesg=new Message();
               mesg.setMessageId(rs.getInt(1));
               mesg.setMessageText(rs.getString(2));
               mesg.setMessageTime(rs.getTimestamp(3));
               mesg.setSenderId(rs.getInt(4));
               mesg.setChatId(rs.getInt(5));
               mesg.setMessageColor(rs.getString(6));
               mesg.setFontSize(rs.getInt(7));
               mesg.setFontType(rs.getString(8));
               
               message.add(mesg);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessagesDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    /**
     *
     * @param c chat object carried the id of chat to be selected.
     * @return arraylist of selected messages.
     */
    @Override
    public ArrayList<Message> selectChatMessage(Chat c)  {
       
        ArrayList<Message>msg=null;
      try (Connection conn = DatabaseConnectionHandler.getConnection())
      {
            ResultSet rs=null;
            //esraaa
            PreparedStatement pst = conn.prepareStatement(" select * from messages where chatId=? order by messageDate "
                    ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            pst.setInt(1, c.getChatId());
            rs=pst.executeQuery();
            msg=convertToArrayList(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(MessagesDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
             

        return msg;
    }



}
