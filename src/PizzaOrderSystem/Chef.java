package PizzaOrderSystem;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Chef extends UnicastRemoteObject implements Employee, ChefInterFace{
	private String name,password;
	
	public Chef(String name, String password) throws RemoteException {
		// TODO Auto-generated constructor stub
		super();
		this.name = name;
		this.password = password;
	}
	public Chef()throws RemoteException{
		super();
	}

	@Override
	public String notifyClient(String message) throws RemoteException {
		System.out.println(message+". Message Received");
		return message+". Message Received";
	}
	public boolean markOrder(String ordernumber){
		
		return false;
	}

}