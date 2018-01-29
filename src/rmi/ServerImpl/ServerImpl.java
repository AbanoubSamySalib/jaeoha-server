/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.ServerImpl;

import com.healthmarketscience.rmiio.RemoteInputStream;
import dao.implementation.ChatDaoImpl;
import dao.implementation.MessagesDaoImpl;
import dao.implementation.NotificationsDaoImpl;
import dao.implementation.UserDaoImpl;
import dao.implementation.UserFriendReqDaoImpl;
import dao.implementation.UserFriendsDaoImpl;
import databaseclasses.Chat;
import databaseclasses.Message;
import databaseclasses.Notification;
import databaseclasses.UserFriendRequests;
import databaseclasses.UserFriends;
import databaseclasses.Users;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import rmi.interfaces.ClientInterface;
import rmi.interfaces.ServerInterface;

/**
 *
 * @author esraa
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    //abanoub
    UserDaoImpl u;

    NotificationsDaoImpl notif = new NotificationsDaoImpl();

    UserFriendReqDaoImpl fr = new UserFriendReqDaoImpl();

    UserFriendsDaoImpl usf = new UserFriendsDaoImpl();

    ArrayList<Users> onlineUsers = new ArrayList<>();

    /**
     *
     * @return
     */
    public ArrayList<Users> getOnlineUsers() {
        return onlineUsers;
    }

    /**
     *
     * @param onlineUsers
     */
    public void setOnlineUsers(ArrayList<Users> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
    ChatDaoImpl chat;
    MessagesDaoImpl message;
    HashMap<Integer, ClientInterface> map;

    /**
     *
     * @return
     */
    public HashMap<Integer, ClientInterface> getMap() {
        return map;
    }

    /**
     *
     * @throws RemoteException
     */
    public ServerImpl() throws RemoteException {
        u = new UserDaoImpl();
        chat = new ChatDaoImpl();
        message = new MessagesDaoImpl();
        map = new HashMap<Integer, ClientInterface>();

    }

    //send notification to all

    /**
     *
     * @param notificationText
     * 
     *  send announcement from server admin to all application users
     * @throws RemoteException
     */
    public void announceAllUsers(String notificationText) throws RemoteException {
        notif.insertAll(new Notification(notificationText, 1));

        if (onlineUsers.size() > 0) {

            for (int i = 0; i < map.size(); i++) {
                if (map.get(onlineUsers.get(i).getId()) != null) {
                    map.get(onlineUsers.get(i).getId()).updateUrAdminNOtification();
                }

            }

        }

    }

    /**
     *
     * @param u
     * send desktop notification to online friends of particular friend 
     * 
     * @throws RemoteException
     */
    public void tellOnlineFriends(Users u) throws RemoteException {

        if (u.getFriends().size() > 0 && onlineUsers.size() > 0) {

            for (int i = 0; i < onlineUsers.size(); i++) {

                for (int j = 0; j < u.getFriends().size(); j++) {

                    if (Objects.equals(u.getFriends().get(j).getId(), onlineUsers.get(i).getId())) {
                        {
                            if (map.get(onlineUsers.get(i).getId()) != null) {
                                map.get(onlineUsers.get(i).getId()).informOnlineFriends(u);
                              //  map.get(onlineUsers.get(i).getId()).updateList();
                                
                            }
                        }
                    }
                }

            }
        }

    }

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    @Override
    public Users signUp(Users user) throws RemoteException {

        boolean checkIfExist = u.checkUserByEmail(user);
        if (checkIfExist) {

            //set user to be online 
            user.setActive(1);
            //set status to be available
            user.setStatus("available");

            u.insert(user);
            //registerUser(user);
            user = u.select(user);
            user.setFriendRequests(myFriendRequests(user));

            user.setFriends(myFriends(user));
            user.setNotification(myNotifications(user));
            user.setAdminNotification(myAdminNotifications(user));
            //  return u.select(user);
            return user;
        } else {

            System.out.println("email is already exist");

            return null;

        }

    }

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    @Override
    public Users signIn(Users user) throws RemoteException {

        boolean exists = u.checkUserByEmailAndPass(user);
        if (exists) {

            user = u.select(user);

            //set user to be online
            user.setActive(1);
            u.update(user);
            user = u.select(user);
            user.setFriendRequests(myFriendRequests(user));

            user.setFriends(myFriends(user));
            user.setNotification(myNotifications(user));
            user.setAdminNotification(myAdminNotifications(user));

            // tellOnlineFriends(user);
            //registerUser(user);
            
            
            if (user.getFriends().size() > 0 && onlineUsers.size() > 0) {

            for (int i = 0; i < onlineUsers.size(); i++) {

                for (int j = 0; j < user.getFriends().size(); j++) {

                    if (Objects.equals(user.getFriends().get(j).getId(), onlineUsers.get(i).getId())) {
                        {
                            if (map.get(onlineUsers.get(i).getId()) != null) {
                                map.get(onlineUsers.get(i).getId()).updateList();
                            }
                        }
                    }
                }

            }
        }
            
            
            return user;

        } else {

            return null;
        }

    }

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean signOut(Users user) throws RemoteException {
        try {

            user.setActive(0);
            u.update(user);
            
            System.out.println("set active =0 success");
            
            for(int i=0;i<onlineUsers.size();i++)
            {
                
                if(onlineUsers.get(i).getId()==user.getId())
                {
                    
                 onlineUsers.remove(i);
                 break;
                }
                
            }
            unregister(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     *
     * @param user
     * @param status
     * 
     *  change user status in database
     * @return Users
     * @throws RemoteException
     */
    @Override
    public Users changeMyStatus(Users user, String status) throws RemoteException {

        user.setStatus(status);

        u.update(user);
        
        user = u.select(user);
        
        
        
            if (user.getFriends().size() > 0 && onlineUsers.size() > 0) {

            for (int i = 0; i < onlineUsers.size(); i++) {

                for (int j = 0; j < user.getFriends().size(); j++) {

                    if (Objects.equals(user.getFriends().get(j).getId(), onlineUsers.get(i).getId())) {
                        {
                            if (map.get(onlineUsers.get(i).getId()) != null) {
                                map.get(onlineUsers.get(i).getId()).updateList();
                            }
                        }
                    }
                }

            }
        }
        
        
        
        return user;

    }

    /**
     *
     * @param user
     * 
     * update user data in database
     * @return Users
     * @throws RemoteException
     */
    @Override
    public Users updateUserData(Users user) throws RemoteException {

        u.update(user);

        return u.select(user);

    }

    /**
     *
     * @param email
     * retrieve user from database 
     * @return Users
     * @throws RemoteException
     */
    @Override
    public Users selectByEmail(String email) throws RemoteException {

        Users u = new Users();

        u.setEmail(email);
        System.out.println(u.getEmail());

        return this.u.select(u);

    }

    /**
     *
     * @param user
     * @param retrivedUser
     * 
     * check if friend request has been sent to that user or not
     * @return boolean
     * @throws RemoteException
     */
    @Override
    public boolean checkRequestedOrNot(Users user, Users retrivedUser) throws RemoteException {

        if (fr.checkIfRequested(user.getId(), retrivedUser.getId())) {

            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @param sender
     * @param reciever
     * send friend request to particular user
     * 
     * @return boolean 
     * @throws RemoteException
     */
    @Override
    public boolean sendFriendRequest(Users sender, Users reciever) throws RemoteException {
        UserFriendRequests t = new UserFriendRequests(sender.getId(), reciever.getId());

        if (map.get(reciever.getId()) != null) {
            map.get(reciever.getId()).updateUrRequestNOtification();
        }

        return fr.insert(t);

    }

    /**
     *
     * @param reciever
     * @param sender
     * 
     * @return boolean
     * @throws RemoteException
     */
    @Override
    public boolean deleteRequest(Users reciever, Users sender) throws RemoteException {
        UserFriendRequests t = new UserFriendRequests(sender.getId(), reciever.getId());
        return fr.delete(t);

    }

    /**
     *
     * @param user
     * @param friend
     * 
     * accept friend request sent to particular user
     * 
     * @return boolean 
     * @throws RemoteException
     */
    @Override
    public boolean acceptRequest(Users user, Users friend) throws RemoteException {

        UserFriends uf = new UserFriends(user.getId(), friend.getId());

        usf.insert(uf);

//        UserFriends uf2 = new UserFriends(friend.getId(), user.getId());
//        
//        usf.insert(uf2);
        System.out.println(friend.getId());
        notif.insert(new Notification(user.getUserName() + " accepted your request", 0, friend.getId()));

        if (map.get(friend.getId()) != null) {
            map.get(friend.getId()).updateUrResponseNOtification();
        }

        //  announceAllUsers();
        return deleteRequest(user, friend);

    }

    /**
     *
     * @param user
     * 
     * retrieve friend requests of particular user
     * @return ArrayList Users
     * @throws RemoteException
     */
    @Override
    public ArrayList<Users> myFriendRequests(Users user) throws RemoteException {

        return u.getFriendRequests(user);
    }

    /**
     *
     * @param user
     * retrieve list of friends of particular user
     * 
     * @return ArrayList Users
     * @throws RemoteException
     */
    @Override
    public ArrayList<Users> myFriends(Users user) throws RemoteException {

        return u.getUserFriends(user);
    }

    /**
     *
     * @param user
     * retrieve list of notification of particular user
     * @return ArrayList Notification
     * @throws RemoteException
     */
    @Override
    public ArrayList<Notification> myNotifications(Users user) throws RemoteException {

        return u.getMyNotifications(user);

    }

    /**
     *
     * @param user
     * 
     * retrieve admin announcement of particular user
     * @return Arraylist Notification
     * @throws RemoteException
     */
    @Override
    public ArrayList<Notification> myAdminNotifications(Users user) throws RemoteException {

        return u.getAdminNotifications(user);

    }

    //esraaa

    /**
     *
     * @param clientRemoteObject
     * @param user
     * 
     * insert remote object of particular user into hash map 
     * and put user in online ArrayList
     * 
     * @return boolean
     * @throws RemoteException
     */
    @Override
    public boolean register(ClientInterface clientRemoteObject, Users user) throws RemoteException {

        tellOnlineFriends(user);

        onlineUsers.add(user);

        map.put(user.getId(), clientRemoteObject);

        return true;
    }

    /**
     *
     * @param user
     * 
     * remove remote object of particular user from hash map
     * and remove user from online Arraylist
     * @return boolean
     * @throws RemoteException
     */
    @Override
    public boolean unregister(Users user) throws RemoteException {
     
        
        if (user.getFriends().size() > 0 && onlineUsers.size() > 0) {

            for (int i = 0; i < onlineUsers.size(); i++) {

                for (int j = 0; j < user.getFriends().size(); j++) {

                    if (Objects.equals(user.getFriends().get(j).getId(), onlineUsers.get(i).getId())) {
                        {
                            if (map.get(onlineUsers.get(i).getId()) != null) {
                                map.get(onlineUsers.get(i).getId()).updateList();
                            }
                        }
                    }
                }

            }
        }
        
        
        if (map.get(user.getId()) != null) {
            map.remove(user.getId());
        }
        //smsm shlt elly t7t 
        System.out.println("user removed successfully from map");
        //set me offline
       // user.setActive(0);
        //u.update(user);
        return true;
    }

    /**
     *
     * @param user object of class user represent the user for which the method select all of his individual Chats
     * @return arraylist of chats
    * @throws RemoteException
     */
    @Override
    public ArrayList<Chat> get_allIndividualChats(Users user) throws RemoteException {

        return chat.select_allChats(user, "0");
    }

    /**
     *
      * @param user object of class user represent the user for which the method select all of his group Chats
     * @return arraylist of chats
      * @throws RemoteException
     */
    @Override
    public ArrayList<Chat> get_allGroupChats(Users user) throws RemoteException {

        return chat.select_allChats(user, "1");
    }

    /**
     *
      * @param user object of class user represent the user for which the method select all of his recent chats
     * @return arraylist of chats
     
     * @throws RemoteException
     */
    @Override
    public ArrayList<Chat> get_allRecentChats(Users user) throws RemoteException {
        return chat.select_allRecentChats(user);
    }

    /**
     *
     * @param user object of class user represent the user for which the method select all of his chats
     * @param friend 
     * @return chat

     * @throws RemoteException
     */
    @Override
    public Chat get_individualChat(Users user, Users friend) throws RemoteException//chat between user and his friend
    {
        Chat c = chat.select_individualChat(user, friend);
        if (c == null) {
            c = create_individualChat(user, friend);
        }
        return c;
    }

    /**
     *
     * @param user the application user
     * @param friend the other user which the chat is created for
     * @return the created chat
     * @throws RemoteException
     */
    @Override
    public Chat create_individualChat(Users user, Users friend) throws RemoteException//chat between user and his friend
    {
        Chat c = new Chat(user.getId() + "," + friend.getId(), 0);//hyset asm l chat b concate l 2 ids 34an y3rf y select l id tany 34an y insert f chat_members tabel
        System.err.println("chatname" + c.getChatName());
        if (chat.insert(c) == true) {
            System.err.println("chatname" + c.getChatName());
            c = chat.selectChatByName(c);
            if (c != null) {
                ArrayList<Users> members = new ArrayList<>();
                members.add(user);
                members.add(friend);
                if (chat.insert_chatMemberTable(c, members) == true) {
                    return c;
                } else {
                    // insertion failed
                }
            } else {
                //empty chat
            }
        } else {
            c = null;
            //insertion failed
        }
        return c;
    }

    /**
     *
      * @param c chat object carried the id of chat to be selected.
     * @return arraylist of selected messages.
     * @throws RemoteException
     */
    @Override
    public ArrayList<Message> getChatMessage(Chat c) throws RemoteException {
        return message.selectChatMessage(c);
    }

    /**
     *
     * @param m object of message to be sent.
     * @throws RemoteException
     */
    @Override
    public void sendMessage(Message m) throws RemoteException {
        //1-a7otha fl db
        message.insert(m);
        //b3d kda a selctha
        m = message.select(m);
        //2- ab3tha l kol so7bha

        //a- ageb list l nas l fl chat 
        Chat c = new Chat();
        System.err.println(m.getChatId());
        c.setChatId(m.getChatId());

        ArrayList<Users> members = u.selectChatUsers(c);

        System.out.println("befor calling tell to other");
//b- anady 3la func recieve mnhm kolhm l ft7een wl aflen kda kda hyft7o yl2oha
        tellOthers(members, m);

    }

    void tellOthers(ArrayList<Users> coll, Message m) throws RemoteException {
        for (Users t : coll) {
            if (t.getActive() == 1) {
                if (map.get(t.getId()) != null) {
                    map.get(t.getId()).RecieveMessage(m);//hna fe exception
                }
            }

        }
    }

    //esraaa

    /**
     *
    * @param chat
     * retrieve all Users who exists in particular chat 
     * 
     * @return ArrayList Users
     * @throws java.rmi.RemoteException
     */
    @Override
    public ArrayList<Users> getChatUsers(Chat chat) throws RemoteException {
        return u.selectChatUsers(chat);
    }

    //esraaa

    /**
     *
     * @param c chat object contains chat information 
     * @param members members of chat group
     * @return created chat 
     * @throws RemoteException
     */
    @Override
    public Chat createChatGroup(Chat c, ArrayList<Users> members) throws RemoteException {

        System.err.println("ksdkl");
        if (chat.insertGroupChat(c) == true)//1)create l chat asln
        {
            System.err.println("hnaaaksdkl");
            c = chat.selectChatByDate_name(c);//select chat by name and date
            if (c != null) {
                if (chat.insert_chatMemberTable(c, members) == true) {
                    return c;
                } else {
                    // insertion failed
                }
            } else {
                //empty chat
            }
        } else {
            c = null;
            //insertion failed
        }
        return c;
    }
    
    /**
     *
     * @param ris
     * @param c
     * @param fileName
     * @param sender
     * 
     *  send file from user to specified friend
     * 
     * 
     * @throws RemoteException
     */
    @Override
    public void tellOther(RemoteInputStream ris, Chat c,String fileName,Users sender) throws RemoteException {
 
        
        ArrayList<Users> users = getChatUsers(c);
         Users u = null; 
        for(int i=0 ;i< users.size();i++)
        {
            
            if(users.get(i).getId()!=sender.getId())
            {
                
             u = users.get(i);
             break;
            }
            
        }
            
            map.get(u.getId()).recieve(ris,fileName);




    }

    /**
     *
     * @param c
     * @param sender
     * 
     *  send file request from user to specified friend
     * @throws RemoteException
     */
    @Override
    public void fileRequest(Chat c, Users sender) throws RemoteException {



    ArrayList<Users> users = getChatUsers(c);
         Users u = null; 
        for(int i=0 ;i< users.size();i++)
        {
            
            if(!Objects.equals(users.get(i).getId(), sender.getId()))
            {
                
             u = users.get(i);
             
                System.out.println("el userName fy file request bta3 elly byb3t hwa :"+u.getUserName());
             break;
            }
            
        }
            
        map.get(u.getId()).fileRequest(sender);


    }

    /**
     *
     * @param sender
     *  ignore file request sent by user to specified friend
     * 
     * @throws RemoteException
     */
    @Override
    public void ignore(Users sender) throws RemoteException {

         map.get(sender.getId()).refused();

    }

    /**
     *
     * @param sender
     * 
     * accept file request sent by user to specified friend
     * @throws RemoteException
     */
    @Override
    public void acceptFile(Users sender) throws RemoteException {


              map.get(sender.getId()).accept();


    }

   

 
   






            


    

   
    
    


}
