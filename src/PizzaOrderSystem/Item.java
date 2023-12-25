package PizzaOrderSystem;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Item {
	private String name;
	private Size size;
	private double price, special;
	
	public Item(String name, Size size, double price){
		this.name = name;
		this.size = size;
		this.special = 0;
//		this.price = convertPrice(price);
		this.price = price;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public void setSize(Size size){
		this.size = size;
	}
	
	public void setSpecial(double special){
//		this.special = convertPrice(special);
		this.special = special;
	}
	
	public void setPrice(double price){
//		this.price = convertPrice(price);
		this.price = price;
	}
	
	public double convertPrice(double price){
		String s = String.valueOf(price);
		int index = 0;
		if(s.contains(".")){
			index = s.indexOf(".") + 3;
			s = s.substring(0,index);
		}
		return Double.parseDouble(s);
	}

	public String getName(){
		return name;
	}
	
	public Size getSize(){
		return size;
	}
	
	public double getPrice(){
		return price;
	}

	
	public double getSpecial(){
		return special;
	}
	
	public String toString(){
		DecimalFormat df2 = new DecimalFormat(".##");
		df2.setRoundingMode(RoundingMode.DOWN);
		if(special == 0) return name + "(" + size + ") " + "$" + df2.format(price);
		else return name + "(" + size + ") " + "$" + df2.format(special);
	}
}