package Node;


import PizzaOrderSystem.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FacadeInterface extends Remote {
    public int createaccount(String username, String password, String name, String phone, String addr, AccountType type, String CardNumber, String ExpirationTime, String NameOnCard) throws RemoteException;
    public boolean paythebill(Order or, String num, String date, String name) throws RemoteException;
    public void createCustomer(String name,String address,String phone) throws RemoteException;
    public void setflag(boolean flag)throws RemoteException;
    public void createCustomer(String name,String address,String phone,String username)throws RemoteException;
    public void deleteCustomer() throws RemoteException;
    public boolean getflag()throws RemoteException;
    public String showDrinks()throws RemoteException;
    public String showAppetizers()throws RemoteException;
    public String showPizzas()throws RemoteException;
    public String showSpecial()throws RemoteException;
    public void defineMenu(int type, String name, Size size, String price)throws RemoteException;
    public void defineMenu(Item item)throws RemoteException;
    public ArrayList<Item> getMenuList()throws RemoteException;
    public void remove(Item item)throws RemoteException;
    public void setDailySpecial(Item item)throws RemoteException;
    public ArrayList<Order> getOrders()throws RemoteException;
    public void recordOrder(Order order)throws RemoteException;
    public Account getaccount()throws RemoteException;
    public boolean checkAccount(String username, char[] password)throws RemoteException;
    public Customer getCustomer()throws RemoteException;
    public StoreManager getStoreManager()throws RemoteException;
    public void markOrder(Order order,OrderStatus status)throws RemoteException;;
    public void register(ChefInterFace obj) throws RemoteException;
    public void register(ManagerInterface obj) throws RemoteException;
    public void register(CustomerInterFace obj) throws RemoteException;
    public void callback() throws RemoteException;

    }
