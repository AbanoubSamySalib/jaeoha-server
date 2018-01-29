/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.interfaces;

import com.healthmarketscience.rmiio.RemoteInputStream;
import databaseclasses.Chat;
import databaseclasses.Message;
import databaseclasses.Notification;
import databaseclasses.Users;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author esraa
 */
public interface ServerInterface extends Remote {

    
    //abanoub

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    
    public Users signUp(Users user) throws RemoteException;

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public Users signIn(Users user) throws RemoteException;
    
    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public boolean signOut(Users user) throws RemoteException;
    
    /**
     *
     * @param user
     * @param retrivedUser
     * @return
     * @throws RemoteException
     */
    public boolean checkRequestedOrNot(Users user ,Users retrivedUser)throws RemoteException;
    
    /**
     *
     * @param sender
     * @param reciever
     * @return
     * @throws RemoteException
     */
    public boolean sendFriendRequest(Users sender,Users reciever)throws RemoteException;
    
    /**
     *
     * @param user
     * @param friend
     * @return
     * @throws RemoteException
     */
    public boolean acceptRequest(Users user,Users friend)throws RemoteException;
   
    /**
     *
     * @param reciever
     * @param sender
     * @return
     * @throws RemoteException
     */
    public boolean deleteRequest(Users reciever,Users sender)throws RemoteException;
    
    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public ArrayList<Users> myFriendRequests(Users user) throws RemoteException;
    
    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public ArrayList<Notification> myNotifications(Users user) throws RemoteException;
    
    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public ArrayList<Notification> myAdminNotifications(Users user) throws RemoteException;
    
    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public ArrayList<Users> myFriends(Users user) throws RemoteException;
    
    /**
     *
     * @param email
     * @return
     * @throws RemoteException
     */
    public Users selectByEmail(String email) throws RemoteException;

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public Users updateUserData(Users user)throws RemoteException;

    /**
     *
     * @param user
     * @param status
     * @return
     * @throws RemoteException
     */
    public Users changeMyStatus(Users user,String status)throws RemoteException;
    
    
    //esraaaa

    /**
     *
     * @param clientRemoteObject
     * @param user
     * @return
     * @throws RemoteException
     */
    
 public boolean register(ClientInterface clientRemoteObject,Users user)throws RemoteException;//clientInterfaceImpl_remoteObject

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public boolean unregister(Users user)throws RemoteException;//delete rempte object from hashmap
    
    /**
     *
     * @param user
     * @param friend
     * @return
     * @throws RemoteException
     */
    public Chat get_individualChat(Users user,Users friend)throws RemoteException;//chat between user and his friend

    /**
     *
     * @param user
     * @param friend
     * @return
     * @throws RemoteException
     */
    public Chat create_individualChat(Users user,Users friend)throws RemoteException;//chat between user and his friend/////lsaaaaaaaa
    
    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public ArrayList<Chat> get_allIndividualChats(Users user)throws RemoteException;//l7d dlw2ty m4 hnst5dmha

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public ArrayList<Chat> get_allGroupChats(Users user)throws RemoteException;

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    public ArrayList<Chat> get_allRecentChats(Users user)throws RemoteException;

    /**
     *
     * @param chat
     * @param members
     * @return
     * @throws RemoteException
     */
    public Chat createChatGroup(Chat chat,ArrayList<Users> members)throws RemoteException;

    /**
     *
     * @param chat
     * @return
     * @throws RemoteException
     */
    public  ArrayList<Users> getChatUsers(Chat chat) throws RemoteException;

    /**
     *
     * @param c
     * @return
     * @throws RemoteException
     */
    public ArrayList<Message> getChatMessage(Chat c)throws RemoteException;

    /**
     *
     * @param m
     * @throws RemoteException
     */
    public void sendMessage(Message m)throws RemoteException;
    
    
    //saveeee

    /**
     *
     * @param ris
     * @param c
     * @param fileName
     * @param sender
     * @throws RemoteException
     */
    public void tellOther(RemoteInputStream ris,Chat c,String fileName,Users sender) throws RemoteException;
    
    /**
     *
     * @param c
     * @param sender
     * @throws RemoteException
     */
    public void fileRequest(Chat c ,Users sender)throws RemoteException;
    
    /**
     *
     * @param sender
     * @throws RemoteException
     */
    public void ignore(Users sender)throws RemoteException;
     
    /**
     *
     * @param sender
     * @throws RemoteException
     */
    public void acceptFile(Users sender)throws RemoteException;
    
    
}
