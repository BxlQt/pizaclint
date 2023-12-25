package PizzaOrderSystem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CustomerInterFace extends Remote {
    public boolean AutoPay() throws RemoteException;
    public boolean paythebill(Order or,String num,String date,String name)throws RemoteException;
    public int createAccount(String username,String password,String name,String phone,String addr, AccountType type,String CardNumber,String ExpirationTime, String NameOnCard)throws RemoteException;
    public void placeOrder(ArrayList<Item> items,OrderType orderType, String name)throws RemoteException;
    public Account getaccount()throws RemoteException;
    public String getname()throws RemoteException;
    public String getUsername()throws RemoteException;
    public Order getOrder()throws RemoteException;

}
