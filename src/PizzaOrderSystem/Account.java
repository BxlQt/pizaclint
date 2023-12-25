package PizzaOrderSystem;

import Node.ClientInterface;
import Node.ServerInterface;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import Swing.CelsiusConverter;

public class Account implements AccountInterFace,Serializable {
	//private String username;
	//private String password;
	//private String name;
	//private String phone;
	//private String addr;
	//private PaymentType type;
	private ArrayList <ClientInterface> inter=new ArrayList<>();
	private Map<String, ArrayList<Object>> account;
	public Account() throws RemoteException{
		//		username="";
		//		password="";
		//		phone="";
		//		addr="";
		//		type=null;
		super();
		account=new HashMap<String, ArrayList<Object>>();
		//createaccount("administrator", "1234", "Store Manager", "1234567", "Earth", AccountType.manager);
		//createaccount("bcde", "123", "Jeff", "1234567", "Earth", PaymentType.cash);
		//modiftypassword("abc", "234","Jeff", "1234567", "Earth", PaymentType.cash);
		try {
			File file=new File("info.data");
			if(file.exists()){
				ObjectInputStream objectInputStream =new ObjectInputStream(new FileInputStream("info.data"));
				try{
					account=(HashMap<String, ArrayList<Object>>) objectInputStream.readObject();
					objectInputStream.close();
				}catch (EOFException e){
					System.err.println("Faild to read from file");
				}

			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		ArrayList <Object> list2=new ArrayList<Object>();
		list2.add("1234");
		list2.add("Store Manager");
		list2.add("1234567");
		list2.add("Earth");
		list2.add(PizzaOrderSystem.AccountType.manager);
		account.put("admin", list2);
	}

	public int createaccount(String username, String password, String name, String phone, String addr, PizzaOrderSystem.AccountType type, String CardNumber, String ExpirationTime, String NameOnCard ){
		if(!username.isEmpty()&&!password.isEmpty()&&!account.containsKey(username)){
			ArrayList <Object> list2=new ArrayList<Object>();
			list2.add(password);
			list2.add(name);
			list2.add(phone);
			list2.add(addr);
			list2.add(type);
			list2.add(CardNumber);
			list2.add(ExpirationTime);
			list2.add(NameOnCard);
			account.put(username, list2);
			//			File file = new File("info.data");
			//			if(file.exists()){
			//				file.delete();
			//			}
			try {
				PrintWriter writer = new PrintWriter("info.data", "UTF-8");
				writer.close();
				ObjectOutputStream objectOutputStream =new ObjectOutputStream(new FileOutputStream("info.data"));
				objectOutputStream.writeObject(account);
				objectOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}else if(username.isEmpty()||password.isEmpty()){
			return 1;
		}else{
			return 2;
		}
	}

	public boolean validaccount(String username, String password){
		try {
			File file=new File("info.data");
			if(file.exists()){
				ObjectInputStream objectInputStream =new ObjectInputStream(new FileInputStream("info.data"));
				try{
					account.putAll((HashMap<String, ArrayList<Object>>) objectInputStream.readObject());
					objectInputStream.close();
				}catch (EOFException e){
					System.err.println("Faild to read from file");
				}

			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
//		for (Entry<String, ArrayList<Object>> entry : account.entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue().toString();
//			System.out.println(key+" "+value);
//			// use key and value
//		}
		if(account.containsKey(username)){
			for(Object o:account.get(username)){
				if(o.equals(password)){
					return true;
				}
				break;
			}
		}
		return false;
	}

	public String getname(String username){
		String ret="";
		if(account.containsKey(username)){
			//System.out.println(account.get(username).size());
			return (String) account.get(username).get(1);
		}
		return "";

	}

	public String getphone(String username){
		String ret="";
		if(account.containsKey(username)){
			//System.out.println(account.get(username).size());
			return (String) account.get(username).get(2);
		}
		return "";

	}
	
	public String getaddr(String username){
		String ret="";
		if(account.containsKey(username)){
			//System.out.println(account.get(username).size());
			return (String) account.get(username).get(3);
		}
		return "";

	}
	public String gettype(String username){
		String ret="";
		if(account.containsKey(username)){
			//System.out.println(account.get(username).size());
			return  account.get(username).get(4)+"";
		}
		return "";

	}
	
	public String getCardNumber(String username){
		String ret="";
		if(account.containsKey(username)){
			//System.out.println(account.get(username).size());
			return (String) account.get(username).get(5);
		}
		return "";

	}
	public String getExpirationTime(String username){
		String ret="";
		if(account.containsKey(username)){
			//System.out.println(account.get(username).size());
			return (String) account.get(username).get(6);
		}
		return "";

	}
	public String getNameOnCard(String username){
		String ret="";
		if(account.containsKey(username)){
			//System.out.println(account.get(username).size());
			return (String) account.get(username).get(7);
		}
		return "";

	}



	//	public boolean modiftypassword(String username, String newpass, String name,String phone,String addr, PaymentType type){
	//		if(account.containsKey(username)){
	//			int i=0;
	//			String loc="";
	//			for(Object o:account.get(username)){
	//				if(i!=0){
	//					loc=loc+o;
	//				}
	//				i=1;
	//			}
	//			if(loc.equals(name+phone+addr+type)){
	//				ArrayList <Object> temp=new ArrayList<Object>();
	//				temp.add(newpass);
	//				temp.add(name);
	//				temp.add(phone);
	//				temp.add(addr);
	//				temp.add(type);
	//				account.put(username, temp);
	//				return true;
	//			}
	//		}
	//		return false;
	//	}

	//	public static void main(String args[]){
	//		Account ac=new Account();
	//		ac.createaccount("abc", "123", "Jeff", "1234567", "Earth", PaymentType.cash);
	//		ac.createaccount("bcde", "123", "Jeff", "1234567", "Earth", PaymentType.cash);
	//		ac.modiftypassword("abc", "234","Jeff", "1234567", "Earth", PaymentType.cash);
	//		System.out.println(ac.validaccount("abc", "234"));
	//	    for (Entry<String, ArrayList<Object>> entry : ac.account.entrySet()) {
	//	        String key = entry.getKey();
	//	        String value = entry.getValue().toString();
	//	        System.out.println(key+" "+value);
	//	        // use key and value
	//	    }
	//
	//	}
}