package Node;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public int createaccount(String username, String password, String name, String phone, String addr, PizzaOrderSystem.AccountType type, String CardNumber, String ExpirationTime, String NameOnCard ) throws RemoteException;
    public boolean validaccount(String username, String password) throws RemoteException;
    public String getname(String username) throws RemoteException;

    public String getphone(String username)throws RemoteException;

    public String getaddr(String username)throws RemoteException;

    public String gettype(String username)throws RemoteException;

    public String getCardNumber(String username)throws RemoteException;
    public String getExpirationTime(String username)throws RemoteException;
    public String getNameOnCard(String username) throws RemoteException;

    public  void register(ClientInterface Obj) throws RemoteException;

    public void callback() throws RemoteException;
}
