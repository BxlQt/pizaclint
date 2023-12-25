package PizzaOrderSystem;

import java.io.Serializable;
import java.rmi.RemoteException;

public class StoreManager implements Employee, ManagerInterface, Serializable{
	private String name,username,password;
	private Menu menu;
	Account ac;
	public StoreManager(String name, String username, String password) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.password = password;
		ac=new Account();
	}
	
	public StoreManager() throws RemoteException{
		super();
	}
	
	public int createEmployeeAccount(String username, String password, String name, String phone, String addr, AccountType at) throws RemoteException{
		int ret=ac.createaccount(username,password,name,phone,addr,at,"","","");
		if(ret==0){
			this.username=username;
		}
		return ret;
	}
	
	public void define(Item item) throws RemoteException{
		menu.define(item);
	}
	
	public void create() throws RemoteException{
		menu.create();
	}

	public void remove(Item item) throws RemoteException{
		menu.remove(item);
	}
	
	public void setDailySpecial(Item item) throws RemoteException{
		menu.setDailySpecial(item);
	}

	public String getName() throws RemoteException{
		return name;
	}
	
}