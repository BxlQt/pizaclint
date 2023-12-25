package Node;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface{

    public ClientImpl() throws RemoteException{
        super();
    }

    @Override
    public String notifyClient(String message) throws RemoteException {
        System.out.println(message+". Message Received");
        return message+". Message Received";
    }
}
