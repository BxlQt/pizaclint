package PizzaOrderSystem;
import java.util.ArrayList;

public class Menu {
	private ArrayList<Drink> drinks;
	private ArrayList<Appetizer> appetizers;
	private ArrayList<Pizza> pizzas;

	public Menu(){
		drinks = new ArrayList<>();
		appetizers = new ArrayList<>();
		pizzas = new ArrayList<>();
		create();
	}

	public void create(){
		define(new Drink("coke", Size.small, 1.49));
		define(new Drink("pepsi", Size.medium, 1.79));
		define(new Appetizer("fries", Size.small, 1.99));
		Pizza p = new Pizza("America's Favorite Feast", Size.large, 7.99);
		define(p);
//		p.setSpecial(p.convertPrice(p.getPrice()/2));
		p.setSpecial(p.getPrice()/2);
		define(new Pizza("Bacon Cheeseburger Feast", Size.small, 5.99));
		define(new Pizza("Honolulu Hawaiian Pizza", Size.medium, 6.99));
	}

	public void define(Item item){
		if(item instanceof Drink) drinks.add((Drink)item);
		if(item instanceof Appetizer) appetizers.add((Appetizer)item);
		if(item instanceof Pizza) pizzas.add((Pizza)item);
	}

	public void remove(Item item){
		if(item instanceof Drink) drinks.remove((Drink)item);
		if(item instanceof Appetizer) appetizers.remove((Appetizer)item);
		if(item instanceof Pizza) pizzas.remove((Pizza)item);
	}

	public void setDailySpecial(Item item){
		if(getDailySpecial() != null){
			Item it = getDailySpecial();
			it.setSpecial(0);
		}
		//		item.setSpecial(item.convertPrice(item.getPrice()/2));
		item.setSpecial(item.getPrice()/2);
	}

	public Item getDailySpecial(){
		Item item = null;
		for(Drink d: drinks){
			if(d.getSpecial() != 0) item = d;
		}
		for(Appetizer a: appetizers){
			if(a.getSpecial() != 0) item = a;
		}
		for(Pizza p: pizzas){
			if(p.getSpecial() != 0) item = p;
		}
		return item;
	}

	public ArrayList<Drink> getDrinks(){
		return drinks;
	}

	public ArrayList<Appetizer> getAppetizers(){
		return appetizers;
	}

	public ArrayList<Pizza> getPizzas(){
		return pizzas;
	}
}