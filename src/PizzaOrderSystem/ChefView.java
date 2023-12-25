package PizzaOrderSystem;
import Node.*;

    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Font;
    import java.awt.Toolkit;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
import java.io.Serializable;
import java.math.RoundingMode;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
    import java.util.ArrayList;

    import javax.swing.BorderFactory;
    import javax.swing.BoxLayout;
    import javax.swing.DefaultListModel;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JList;
    import javax.swing.JOptionPane;
    import javax.swing.JPanel;
    import javax.swing.JPasswordField;
    import javax.swing.JScrollPane;
    import javax.swing.JTextArea;
    import javax.swing.JTextField;
    import javax.swing.border.TitledBorder;



public class ChefView extends JFrame implements ActionListener,Serializable {
        private FacadeInterface model;
        private JButton  btnLogin,btnMarkOrder,btnOK,btnCancel,signout,btnViewOrder;
        private JLabel lblUsername, lblPassword,welcome;
        private JTextField usernameField;
        private JPasswordField passwordField;
        private ArrayList<Order> Orders=new ArrayList<Order>();
        DefaultListModel unmarkedlistModel = new DefaultListModel();
        DefaultListModel markedlistModel = new DefaultListModel();
        JList<Object> unmarkedlist;
        JList<Object> markedlist;

        public ChefView(String title, FacadeInterface model){
            super(title);
            this.model = model;
            setLayout(null);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - getHeight()) / 2);
            setSize(x,y-25);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocation(0, y+25);

            btnLogin= new JButton("Login");
            btnLogin.setBounds(295,120,130,30);
            btnMarkOrder = new JButton("Mark Order");
            btnMarkOrder.setBounds(30,30,150,30);
            btnViewOrder = new JButton("View Order");
            btnViewOrder.setBounds(30,80,150,30);
            lblUsername = new JLabel("Enter Username: ");
            lblUsername.setBounds(300,30,160,25);
            lblPassword = new JLabel("Enter password: ");
            lblPassword.setBounds(300,80,160,25);

            usernameField = new JTextField(15);
            usernameField.setFont(new Font("Arial", Font.BOLD, 14));
            usernameField.setBounds(400,30,160,25);

            passwordField = new JPasswordField(10);
            passwordField.setBounds(400,80,160,25);
            passwordField.setEchoChar('*');
            passwordField.setFont(new Font("Arial", Font.BOLD, 14));
            passwordField.setBackground(Color.YELLOW);
            passwordField.setForeground(Color.BLUE);
            passwordField.setToolTipText("Password must contain at least 8 characters");

            add(lblUsername);
            add(lblPassword);
            add(usernameField);
            add(passwordField);
            add(btnLogin);
            add(btnMarkOrder);
            add(btnViewOrder);
            btnMarkOrder.setVisible(false);
            btnViewOrder.setVisible(false);
            signout=new JButton("Sign out");
            signout.setBounds(430,120,130,30);
            signout.addActionListener(this);
            add(signout);
            signout.setVisible(false);

            btnLogin.addActionListener(this);
            btnMarkOrder.addActionListener(this);
            btnViewOrder.addActionListener(this);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == signout) {
                    //System.out.println("aaaaaaaaa");
                    try {
                        model.deleteCustomer();
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    add(usernameField);
                    //passwordField.setVisible(false);
                    add(passwordField);
                    //lblUsername.setVisible(false);
                    add(lblUsername);
                    //btnLogin.setVisible(false);
                    add(btnLogin);
                    add(lblPassword);
                    remove(welcome);
                    try {
                        model.setflag(false);
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    signout.setVisible(false);
                    btnMarkOrder.setVisible(false);
                    usernameField.setText("");
                    passwordField.setText("");
                    repaint();
                }
                if (e.getSource() == btnMarkOrder) {
                    try {
                        new MarkOrderFrame();
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }
                if (e.getSource() == btnViewOrder) {
                    try {
                        new ViewOrderFrame();
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }

                if (e.getSource() == btnLogin) {
                    char[] password = passwordField.getPassword();
                    String name = usernameField.getText();
                    try {
                        if (model.checkAccount(name, password)) {
                            String nameinfo = model.getaccount().getname(name);
                            String phoneinfo = model.getaccount().getphone(name);
                            String addrinfo = model.getaccount().getaddr(name);
                            String typeinfo = model.getaccount().gettype(name);
                            //System.out.println(typeinfo);
                            if (typeinfo.equals(AccountType.chef.name())) {
                                //if(model.getaccountinfo(username))
                                JOptionPane.showMessageDialog(this, "You have logged in successfully.");
                                //user
                                //usernameField.setVisible(false);
                                remove(usernameField);
                                //passwordField.setVisible(false);
                                remove(passwordField);
                                //lblUsername.setVisible(false);
                                remove(lblUsername);
                                //btnLogin.setVisible(false);
                                remove(btnLogin);
                                remove(lblPassword);
                                welcome = new JLabel("Welcome Chef " + name + "!");
                                welcome.setFont(new Font("Arial", Font.BOLD, 14));
                                welcome.setBounds(500, 40, 800, 25);
                                add(welcome);
                                signout.setVisible(true);
                                btnMarkOrder.setVisible(true);
                                btnViewOrder.setVisible(true);
                                //IMPORTANT  !!
                                model.createCustomer(nameinfo, addrinfo, phoneinfo, name);
                                // FOR CUSTOMER WHO LOGGED INTO ACCOUNT, CUSTOMER OBJECT IS CREATED HERE , BEFORE THIS STATEMENT, THE CUSTOMER IS NOT CREATED.              --JIEFU
                                //

                                repaint(); // IMPORTANT NO COMMENT!
                            } else {
                                JOptionPane.showMessageDialog(this,
                                        "Your account is not a chef account, please try again.", "Error Message",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Your password and username do not match. Please try again.", "Error Message",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }
        }

        public class MarkOrderFrame extends JFrame implements ActionListener{
            JList<Object> list;




            public MarkOrderFrame() throws RemoteException {
                super("Mark Order");
                setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
                setSize(500,500);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - getHeight()) / 2);
                setLocation(x, y);

                TitledBorder titleUnmarked,titleMarked;

                titleUnmarked = BorderFactory.createTitledBorder("Uncomplete Order");
                titleMarked = BorderFactory.createTitledBorder("Marked Order");

                //			System.out.println(Orders.length);

                for(int i=0;i<model.getOrders().size();i++){
                    //System.out.println(model.getOrders().get(i));
                    Order order = model.getOrders().get(i);
                    if(!markedlistModel.contains(order)){
                        unmarkedlistModel.addElement(order);
                        Orders.add(order);
                    }
                }

                unmarkedlist = new JList<>(unmarkedlistModel);
                JScrollPane unMarkedscrollPane = new JScrollPane(unmarkedlist);
                unMarkedscrollPane.setBorder(titleUnmarked);
                markedlist = new JList<>(markedlistModel);
                JScrollPane MarkedscrollPane = new JScrollPane(markedlist);
                MarkedscrollPane.setBorder(titleMarked);

                JPanel pane = new JPanel();
                btnOK = new JButton("OK");
                btnCancel = new JButton("Cancel");
                pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
                pane.add(btnOK);
                pane.add(btnCancel);

                JLabel label = new JLabel("Please choose the order you want to mark");
                label.setPreferredSize(new Dimension(30,20));
                add(label);
                add(unMarkedscrollPane);
                add(MarkedscrollPane);
                add(pane);

                btnOK.addActionListener(this);
                btnCancel.addActionListener(this);
                setVisible(true);
            }


            public void actionPerformed(ActionEvent e) {
                try {
                    if (e.getSource() == btnOK) {
                        if (unmarkedlist.getSelectedValue() != null) {
                            Order order = (Order) unmarkedlist.getSelectedValue();
                            if (Orders.contains(order)) {
                                if (order.orderType == OrderType.delivery) {
                                    model.markOrder(order, OrderStatus.delivered);
                                } else {
                                    model.markOrder(order, OrderStatus.completed);
                                }
                                DecimalFormat df2 = new DecimalFormat(".##");
                                df2.setRoundingMode(RoundingMode.DOWN);
                                unmarkedlistModel.remove(Orders.indexOf(order));
                                Orders.remove(order);
                                markedlistModel.addElement(order);
                                JOptionPane.showMessageDialog(this, order.getOrderNumber() + " has marked complete.");
                            }
                            //dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Please select one order to mark");
                        }
                    }
                    if (e.getSource() == btnCancel) {
                        dispose();
                    }
                }catch (Exception eee){

                }

            }






        }
        public class ViewOrderFrame extends JFrame implements ActionListener{
            JList<Object> list;
            JTextArea orderDetail;

            public ViewOrderFrame() throws RemoteException {
                super("View Order");
                setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
                setSize(500,500);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - getHeight()) / 2);
                setLocation(x, y);

                list = new JList<>(model.getOrders().toArray());
                JScrollPane scrollPane = new JScrollPane(list);

                JPanel pane = new JPanel();
                orderDetail=new JTextArea();
                btnOK = new JButton("OK");
                btnCancel = new JButton("Cancel");
                pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
                pane.add(btnOK);
                pane.add(btnCancel);

                JLabel label = new JLabel("Please choose the order you want to view");
                label.setPreferredSize(new Dimension(30,20));
                add(label);
                add(scrollPane);
                add(orderDetail);
                add(pane);

                btnOK.addActionListener(this);
                btnCancel.addActionListener(this);
                setVisible(true);
            }
            public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnOK) {
                        orderDetail.setText("");

                        if (list.getSelectedValue() != null) {
                            Order order = (Order) list.getSelectedValue();
                            orderDetail.append("Customer: " + order.getName() + "\n");
                            orderDetail.append("Address: " + order.getAddress() + "\n");
                            orderDetail.append("Detail: ");
                            for (int i = 0; i < order.getItems().size(); i++) {
                                orderDetail.append(i + 1 + ": " + order.getItems().get(i) + "\n");
                            }


                        } else {
                            JOptionPane.showMessageDialog(this, "Please select one order to view");
                        }
                    }
                    if (e.getSource() == btnCancel) {
                        dispose();
                    }



            }
        }
	public static void main(String args[]) throws RemoteException, NotBoundException {
		String host = args[0];
		final Registry registry = LocateRegistry.getRegistry(host,Integer.parseInt(args[1]));
		FacadeInterface model=(FacadeInterface) registry.lookup("Service");
		model.deleteCustomer();
		ChefInterFace client=new Chef();
		model.register(client);
		model.createaccount("chef","234","chef","1111111111","fort",AccountType.chef,"123456789012","1215","Chef");

		System.out.println("Client Registered.");
		new ChefView("Chef View",model);
	}
}