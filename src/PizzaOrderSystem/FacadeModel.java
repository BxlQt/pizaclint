package PizzaOrderSystem;

import Node.FacadeInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class FacadeModel  implements FacadeInterface,Serializable {
	private Menu menu;
	//	private OrderCollection orders;
	private Customer customer;

	private ArrayList <ChefInterFace> inter=new ArrayList<>();
	private ArrayList<ManagerInterface> IManager = new ArrayList<>();
	private ArrayList<CustomerInterFace> ICust = new ArrayList<>();
	private Account account;
	private Pay pay;
	private ArrayList<Order> orders=new ArrayList<Order>();
	boolean flag=false;
	private StoreManager sm;
	public FacadeModel() throws RemoteException{
//		sm = new StoreManager("admin","admin","1234");
		menu = new Menu();
		//	orders=new OrderCollection();
		account=new Account();
		pay=new Pay();
	}

	public boolean paythebill(Order or,String num,String date, String name){
		if(pay.validcardnum(num) &&pay.validdate(date)&&pay.validName(name)){
			pay.paytheorder(or);
			return true;
		}
		return false;
	}
	public void createCustomer(String name,String address,String phone) throws RemoteException{
		customer=new Customer(name,address,phone);
		flag=true;
	}
	public void setCustomername(String name){
		customer.setName(name);
	}
	public void setflag(boolean flag){
		this.flag=flag;
	}
	public void createCustomer(String name,String address,String phone,String username)throws RemoteException{
		customer=new Customer(name,address,phone,username);
		flag=true;
	}

	public void deleteCustomer(){
		customer=null;
	}

	public boolean getflag(){
		return flag;
	}
	public String showDrinks(){
		if(menu.getDrinks().size() != 0){
			String s = "";
			for(Drink d: menu.getDrinks()){
				s += d.toString() + "\n";
			}
			return s;
		}
		else return null;
	}

	public int createaccount(String username,String password,String name,String phone,String addr, AccountType type,String CardNumber,String ExpirationTime, String NameOnCard) throws RemoteException{
		customer=new Customer(name,addr,phone);
		flag=true;
		return customer.createAccount(username, password, name, phone, addr, type,CardNumber,ExpirationTime,NameOnCard);
	}

	public String showAppetizers(){
		if(menu.getAppetizers().size() != 0){
			String s = "";
			for(Appetizer a: menu.getAppetizers()){
				s += a.toString() + "\n";
			}
			return s;
		}
		else return null;
	}

	public String showPizzas(){
		if(menu.getPizzas().size() != 0){
			String s = "";
			for(Pizza p: menu.getPizzas()){
				s += p.toString() + "\n";
			}
			return s;
		}
		else return null;

	}

	public String showSpecial(){
		if(menu.getDailySpecial() != null)
			return menu.getDailySpecial().toString();
		else return null;
	}

	public void defineMenu(int type, String name, Size size, String price){
		if(type == 1){
			menu.define(new Drink(name, size, Double.parseDouble(price)));
		}
		if(type == 2){
			menu.define(new Appetizer(name, size, Double.parseDouble(price)));
		}
		if(type == 3){
			menu.define(new Pizza(name, size, Double.parseDouble(price)));
		}
	}

	public void defineMenu(Item item){
		if (item instanceof Drink) menu.define((Drink) item);
		if (item instanceof Appetizer) menu.define((Appetizer) item);
		if (item instanceof Pizza) menu.define((Pizza) item);
	}

	public ArrayList<Item> getMenuList(){
		ArrayList<Item> list = new ArrayList<>();
		list.addAll(menu.getDrinks());
		list.addAll(menu.getAppetizers());
		list.addAll(menu.getPizzas());
		return list;
	}

	public void remove(Item item){
		menu.remove(item);
	}


	public void setDailySpecial(Item item){
		menu.setDailySpecial(item);
	}

	public ArrayList<Order> getOrders(){
		return orders;

	}
	public void recordOrder(Order order){
		//		System.out.println("mei jia jin qu?");
		orders.add(order);

	}

	public Account getaccount(){
		return account;
	}

	public boolean checkAccount(String username, char[] password){
		String pass=new String(password);
		if(account.validaccount(username,pass)){
			return true;
		}
		return false;
	}

	public Customer getCustomer(){
		return customer;
	}
	public StoreManager getStoreManager(){
		return sm;
	}


	@Override
	public synchronized void register(ChefInterFace obj) throws RemoteException {
		if(!inter.contains(obj)){
			inter.add(obj);
			System.out.println("Client is registered successfully");
			callback();
		}
	}
	
	@Override
	public synchronized void register(ManagerInterface obj) throws RemoteException {
		if(!IManager.contains(obj)){
			IManager.add(obj);
			System.out.println("Manager is registered successfully");
			callback();
		}
	}
	
	
	@Override
	public synchronized void register(CustomerInterFace obj) throws RemoteException {
		if(!ICust.contains(obj)){
			ICust.add(obj);
			System.out.println("Customer is registered successfully");
			callback();
		}
	}

	@Override
	public synchronized void callback() throws RemoteException {
		System.out.println("Starting callback");
		for (int i = 0; i < inter.size(); i++) {
			ChefInterFace client = (ChefInterFace) inter.get(i);
			client.notifyClient("You are in");
		}
		System.out.println("CallBack completed");
	}


	public void markOrder(Order order,OrderStatus status){
		order.markStatus(status);
	}
}