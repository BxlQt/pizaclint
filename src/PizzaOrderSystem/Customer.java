package PizzaOrderSystem;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class Customer extends UnicastRemoteObject implements CustomerInterFace, Serializable {
	private String name;
	private String address;
	private String phone;
	private Account ac=new Account();
	private Order or;
	private Pay pay=new Pay();
	private String username;


	public Customer(String name,String address,String phone)throws RemoteException{
		this.name=name;
		this.address=address;
		this.phone=phone;;
	}

	public Customer(String name,String address,String phone, String username)throws RemoteException{
		this.name=name;
		this.address=address;
		this.phone=phone;;
		this.username=username;
	}

	public boolean AutoPay(){
		if(!username.isEmpty()){
			String CardNumber=ac.getCardNumber(username);
			String ExpirationTime=ac.getExpirationTime(username);
			String NameOnCard=ac.getNameOnCard(username);
			//System.out.println(pay.validcardnum(CardNumber));
			if(pay.validcardnum(CardNumber)&&pay.validdate(ExpirationTime)&&pay.validName(NameOnCard)){
				return true;
			}
		}
		return false;
	}

	public boolean paythebill(Order or,String num,String date,String name){
		if(pay.validcardnum(num) &&pay.validdate(date)&&pay.validName(name)){
			pay.paytheorder(or);
			return true;}
		return false;
	}
	public int createAccount(String username,String password,String name,String phone,String addr, AccountType type,String CardNumber,String ExpirationTime, String NameOnCard){
		int ret=ac.createaccount(username,password,name,phone,addr,type,CardNumber,ExpirationTime,NameOnCard);
		if(ret==0){
			this.username=username;
		}
		return ret;
	}


	public void placeOrder(ArrayList<Item> items,OrderType orderType, String name){
		if(items.size()==0){
			throw new IllegalArgumentException();
		}else{
			or=new Order(items,orderType,name);
		}
	}
//	public void placeOnlineOrder(ArrayList<Item> items){
//		if(items.size()==0){
//			throw new IllegalArgumentException();
//		}else{
//			or=new Order(items,OrderType.online);
//
//		}
//	}

	public Account getaccount(){
		return ac;
	}

	public String getname(){
		return name;
	}
	public String getUsername(){
		return username;
	}
	public Order getOrder(){
		return or;
	}
	public void setName(String xxx){
		name=xxx;
	}


	//	public void payBill(String cardNumber,){
	//		Payment p=new Payment();
	//	}
}