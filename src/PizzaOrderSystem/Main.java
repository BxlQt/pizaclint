package PizzaOrderSystem;

import java.rmi.RemoteException;

public class Main{
//
	public static void main(String[] args)throws RemoteException {
		FacadeModel model = new FacadeModel();
		CustomerView customerView = new CustomerView("Customer View", model);
		ChefView chefView = new ChefView("Chef View", model);
		ManagerView managerView = new ManagerView("Manager View", model);
		CashierView cashierView = new CashierView("Cashier View", model);
	}
}