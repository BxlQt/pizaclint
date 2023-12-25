package PizzaOrderSystem;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ManagerInterface extends Remote{
	public int createEmployeeAccount(String username, String password, String name, 
			String phone, String addr, AccountType at) throws RemoteException;
	public void define(Item item) throws RemoteException;
	public void create() throws RemoteException;
	public void remove(Item item) throws RemoteException;
	public void setDailySpecial(Item item) throws RemoteException;
	public String getName() throws RemoteException;
}
