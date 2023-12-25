package PizzaOrderSystem;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChefInterFace extends Remote{
    public boolean markOrder(String ordernumber) throws RemoteException;
    public String notifyClient(String message) throws RemoteException;
}
