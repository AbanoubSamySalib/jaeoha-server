/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import databaseclasses.Users;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author abanoub samy
 * @param <T>
 */
public interface DaoInterface<T>  {

    /**
     *
     * @param t
     * @return
     */
    public boolean insert(T t);

    /**
     *
     * @param t
     * @return
     */
    public T  select(T t);

    /**
     *
     * @param t
     * @return
     */
    public boolean update(T t);

    /**
     *
     * @param t
     * @return
     */
    public boolean delete(T t);
    
    /**
     *
     * @param rs
     * @return
     */
    public ArrayList<T> convertToArrayList(ResultSet rs);

}
