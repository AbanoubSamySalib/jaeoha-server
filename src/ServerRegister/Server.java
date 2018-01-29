/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerRegister;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.ServerImpl.ServerImpl;

/**
 *
 * @author abanoub samy
 */
public class Server {

    private Registry reg;
    private ServerImpl obj;

    /**
     *
     * @return ServerImpl object which is the server remote object
     */
    public ServerImpl getObj() {
        return obj;
    }

    /**
     *
     * @param obj
     */
    public void setObj(ServerImpl obj) {
        this.obj = obj;
    }
    
    /**
     *
     * @return
     */
    public Registry getReg() {
        return reg;
    }

    /**
     *
     * @param reg
     */
    public void setReg(Registry reg) {
        this.reg = reg;
    }
    
    /**
     *   bind remote object to registry on the default port in a service called chat 
     *  for the client application to use it
     */
    public Server() {

        try {
             obj = new ServerImpl();
             reg = LocateRegistry.createRegistry(1099);
             reg.rebind("chat", obj);//esraa

            System.out.println("bind successfull");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

    }

}
