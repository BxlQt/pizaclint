package PizzaOrderSystem;
import java.io.Serializable;
import java.util.ArrayList;

public class Cashier implements Employee,Serializable {
	private String name,password;
	private Order order;

	public Cashier(String name, String password) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.password = password;
	}

}