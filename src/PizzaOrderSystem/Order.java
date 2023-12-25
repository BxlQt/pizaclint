package PizzaOrderSystem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;


public class Order {

	private String orderNumber;
	public OrderType orderType;
	public OrderStatus orderStatus;
	private ArrayList<Item> items;
	private double total;
	private String customerName;
	private String daddress;

	public Order(ArrayList<Item> items, OrderType orderType, String name){
		this.items=items;
		orderNumber= new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		this.orderStatus=orderStatus.unpaid;
		this.orderType=orderType;
		this.customerName=name;
	}
	public void calTotal(){
		if(items.size()==0){
			total=0;
		}
		else{
			for(int i=0;i<items.size();i++){
				if(items.get(i).getSpecial()==0){
					this.total+=items.get(i).getPrice();}
				else{
					this.total+=items.get(i).getSpecial();
				}
			}
		}
	}

	public String getOrderNumber(){
		return orderNumber;
	}
	public double getTotal(){
		return total;
	}

	public void markStatus(OrderStatus status){
		this.orderStatus=status;
	}
	public ArrayList<Item> getItems(){
		return items;
	}

	//	public void addItems(Item i){
	//		items.add(i);
	//	}

	public String getOrderInfo(){
		String output = "";
		for(int i=0;i<items.size();i++){
			output += items.get(i) + "\n";
		}
		return output;
	}
	public void setName(String name){
		this.customerName=name;
	}

	public void setAddress(String address){
		this.daddress=address;
	}
	public String getAddress(){
		return daddress;
	}
	public String toString(){
		StringBuffer ITEMS = new StringBuffer("  " + items.size() + " Items: ");
		for(int i=0;i<items.size();i++){
			ITEMS.append(i+1 + ":" + items.get(i).getName() + " ");
		}
		String result=orderNumber+"   OrderType:"+ orderType+ "   OrderStatus:" + orderStatus;
		return result;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return customerName;
	}
}