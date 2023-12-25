package Node;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public String notifyClient(String message) throws RemoteException;
}
