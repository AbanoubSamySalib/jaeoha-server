/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import databaseclasses.UserFriendRequests;
import databaseclasses.Users;
import java.rmi.RemoteException;

/**
 *
 * @author abanoub samy
 */
public interface UserFriendReqDaoInterface extends DaoInterface<UserFriendRequests>{
    
    /**
     *
     * @param userId
     * @param userRetrievedId
     * @return
     */
    public boolean checkIfRequested(int userId,int userRetrievedId);
    
    
    
}
