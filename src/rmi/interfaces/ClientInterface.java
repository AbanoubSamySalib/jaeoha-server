/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.interfaces;

import com.healthmarketscience.rmiio.RemoteInputStream;
import databaseclasses.Message;
import databaseclasses.Users;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author esraa
 */
public interface ClientInterface extends Remote {

    /**
     *
     * @param u
     * @throws RemoteException
     */
    public void informOnlineFriends(Users u) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void updateUrAdminNOtification() throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void updateUrRequestNOtification() throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void updateUrResponseNOtification() throws RemoteException;

    /**
     *
     * @param message
     * @throws RemoteException
     */
    public void RecieveMessage(Message message) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void RecieveServerRemoteObject()throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void stopScene()throws RemoteException;
     
     
     
     //smsm 

    /**
     *
     * @throws RemoteException
     */
     
     public void updateList()throws RemoteException;
     
    /**
     *
     * @param u
     * @throws RemoteException
     */
    public void fileRequest(Users u)throws RemoteException;
     
    /**
     *
     * @throws RemoteException
     */
    public void refused()throws RemoteException;
     
    /**
     *
     * @throws RemoteException
     */
    public void accept()throws RemoteException;
     
     //smsm
             
     
     //saveeeeee

    /**
     *
     * @param ris
     * @param fileName
     * @throws RemoteException
     */
     public void recieve(RemoteInputStream ris,String fileName)throws RemoteException;
}
