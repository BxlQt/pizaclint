package Node;

import PizzaOrderSystem.AccountType;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String []args) throws RemoteException, NotBoundException, MalformedURLException {
        String host = args[0];
        final Registry registry = LocateRegistry.getRegistry(host,Integer.parseInt(args[1]));
        ServerInterface inter=(ServerInterface) registry.lookup("Service");
        inter.createaccount("jeff","1234","Jeffer","1234567890","Fort Collins", AccountType.chef,"123456789012","1115","Jeffery");
        System.out.println(inter.getname("jeff")+" "+inter.gettype("jeff")+" "+inter.getaddr("jeff"));
        ClientInterface client=new ClientImpl();
        inter.register(client);
        System.out.println("Client Registered.");
        while(true){

        }
    }
}
