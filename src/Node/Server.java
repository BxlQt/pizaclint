package Node;

import PizzaOrderSystem.Account;
import PizzaOrderSystem.FacadeModel;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public Server(int port) throws RemoteException, AlreadyBoundException {
        FacadeInterface fac=new FacadeModel();
        FacadeInterface remote = (FacadeInterface) UnicastRemoteObject.exportObject(fac, 0);
        final Registry registry = LocateRegistry.getRegistry(port);
        registry.rebind("Service",remote);
        System.err.println("Server is running");
    }
    
    public static void main(String []args) throws Exception {
        new Server(Integer.parseInt(args[0]));
    }
}
