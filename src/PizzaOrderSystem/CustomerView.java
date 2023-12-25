package PizzaOrderSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Node.FacadeInterface;

public class CustomerView extends JFrame implements ActionListener{
    private JButton btnViewMenu, btnPlaceOrder, btnCreateAccount, btnLogin, btnOK, btnCancel,signout;
    private JLabel lblUsername, lblPassword,welcome;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private FacadeInterface model;
    private static final String IMG_PATH = "src/logo.bmp";

    public CustomerView(String title, FacadeInterface model){
        super(title);
        this.model = model;
        setLayout(null);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setSize(x,y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 0);
        btnCreateAccount= new JButton("Create Account");
        btnCreateAccount.setBounds(430,190,130,30);
        btnViewMenu = new JButton("View Menu");
        btnViewMenu.setBounds(30,30,150,30);
        btnPlaceOrder=new JButton("Place order");
        btnPlaceOrder.setBounds(30,70,150,30);
        btnLogin= new JButton("Login");
        btnLogin.setBounds(295,190,130,30);
        lblUsername = new JLabel("Enter Username: ");
        lblUsername.setBounds(300,100,160,25);
        lblPassword = new JLabel("Enter password: ");
        lblPassword.setBounds(300,150,160,25);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField.setBounds(400,100,160,25);

        passwordField = new JPasswordField(10);
        passwordField.setBounds(400,150,160,25);
        passwordField.setEchoChar('*');
        passwordField.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField.setBackground(Color.YELLOW);
        passwordField.setForeground(Color.BLUE);
        passwordField.setToolTipText("Password must contain at least 8 characters");

        BufferedImage img;
        try {
            img = ImageIO.read(new File(IMG_PATH));
            ImageIcon icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);
            label.setBounds(200, 30, 800,50);
            add(label);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }





        add(btnViewMenu);
        add(btnPlaceOrder);
        add(btnCreateAccount);
        add(btnLogin);
        add(lblUsername);
        add(lblPassword);
        add(usernameField);
        add(passwordField);
        signout=new JButton("Sign out");
        signout.setBounds(430,120,130,30);
        signout.addActionListener(this);
        add(signout);
        signout.setVisible(false);

        btnViewMenu.addActionListener(this);
        btnPlaceOrder.addActionListener(this);
        btnCreateAccount.addActionListener(this);
        btnLogin.addActionListener(this);
        //signout.addActionListener
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==signout){
            //System.out.println("aaaaaaaaa");
            try {
				model.deleteCustomer();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            add(usernameField);
            //passwordField.setVisible(false);
            add(passwordField);
            //lblUsername.setVisible(false);
            add(lblUsername);
            //btnLogin.setVisible(false);
            add(btnLogin);
            add(btnCreateAccount);
            add(lblPassword);
            remove(welcome);
            try {
				model.setflag(false);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            repaint();
            signout.setVisible(false);
        }
        if(e.getSource() == btnViewMenu){
            new ViewMenuFrame();
        }
        if(e.getSource() == btnCreateAccount){
            new CreateAccountFrame();
        }
        if(e.getSource() == btnPlaceOrder){
            new PlaceOrderFrame();
        }
        if(e.getSource()==btnLogin){
            char[] password = passwordField.getPassword();
            String name= usernameField.getText();
            try {
				if (model.checkAccount(name,password)) {
				    String nameinfo=model.getaccount().getname(name);
				    String phoneinfo=model.getaccount().getphone(name);
				    String addrinfo=model.getaccount().getaddr(name);
				    String typeinfo=model.getaccount().gettype(name);

				    if(typeinfo.equals(AccountType.customer.name())){

				        JOptionPane.showMessageDialog(this,"You have logged in successfully.");
				        //user
				        //usernameField.setVisible(false);
				        remove(usernameField);
				        //passwordField.setVisible(false);
				        remove(passwordField);
				        //lblUsername.setVisible(false);
				        remove(lblUsername);
				        //btnLogin.setVisible(false);
				        remove(btnLogin);
				        remove(btnCreateAccount);
				        remove(lblPassword);
				        welcome = new JLabel("Welcome to 2333 pizza order system, "+ name+"!");
				        welcome.setFont(new Font("Arial", Font.BOLD, 14));
				        welcome.setBounds(400,170,800,25);
				        add(welcome);
				        signout.setVisible(true);
				        //
				        //IMPORTANT  !!
				        try {
				            model.createCustomer(nameinfo,addrinfo,phoneinfo,name);
				        } catch (RemoteException e1) {
				            e1.printStackTrace();
				        }
				        // FOR CUSTOMER WHO LOGGED INTO ACCOUNT, CUSTOMER OBJECT IS CREATED HERE , BEFORE THIS STATEMENT, THE CUSTOMER IS NOT CREATED.              --JIEFU
				        //

				        repaint(); // IMPORTANT NO COMMENT!
				    }else{
				        JOptionPane.showMessageDialog(this,
				                "Your account is not an customer account, please try again.", "Error Message",
				                JOptionPane.ERROR_MESSAGE);
				    }
				} else {
				    JOptionPane.showMessageDialog(this,
				            "Your password and username do not match. Please try again.", "Error Message",
				            JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException | RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }


    }

    private class CreateAccountFrame extends JFrame implements ActionListener{
        private JButton btnOK,cancel;
        private JFrame frame;
        private JTextField un, pass,name, phone, addr,CardNumber, ExpirationTime, NameOnCard;
        public CreateAccountFrame()
        {
            //super("Create Account");
            frame = new JFrame("Create Account");
            setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth=1;
            setSize(500,500);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - getHeight()) / 2);
            setLocation(x, y);
            cancel=new JButton("Cancel");
            cancel.addActionListener(this);
            btnOK = new JButton("Create");
            btnOK.addActionListener(this);
            un = new JTextField(15);
            un.setFont(new Font("Arial", Font.BOLD, 14));
            un.addActionListener(this);
            pass = new JTextField(15);
            pass.setFont(new Font("Arial", Font.BOLD, 14));
            pass.addActionListener(this);
            JLabel label1 = new JLabel("Username (required)");
            add(label1,constraints);
            JLabel label2 = new JLabel("Password (required)");
            constraints.gridx = 1;
            constraints.gridy = 0;
            add(label2,constraints);
            constraints.gridx = 0;
            constraints.gridy = 1;
            add(un,constraints);
            constraints.gridx = 1;
            constraints.gridy = 1;
            add(pass,constraints);
            JLabel label3 = new JLabel("Name (required)");
            constraints.gridx = 0;
            constraints.gridy = 2;
            add(label3,constraints);
            JLabel label4 = new JLabel("Phone");
            constraints.gridx = 1;
            constraints.gridy = 2;
            add(label4,constraints);
            JLabel label5 = new JLabel("Address");
            constraints.gridx = 2;
            constraints.gridy = 2;
            add(label5,constraints);
            name = new JTextField(15);
            name.setFont(new Font("Arial", Font.BOLD, 14));
            name.addActionListener(this);
            constraints.gridx = 0;
            constraints.gridy = 3;
            add(name,constraints);
            phone = new JTextField(15);
            phone.setFont(new Font("Arial", Font.BOLD, 14));
            phone.addActionListener(this);
            constraints.gridx = 1;
            constraints.gridy = 3;
            add(phone,constraints);
            addr = new JTextField(15);
            addr.setFont(new Font("Arial", Font.BOLD, 14));
            addr.addActionListener(this);
            constraints.gridx = 2;
            constraints.gridy = 3;
            add(addr,constraints);
            JLabel label6 = new JLabel("Card Number");
            constraints.gridx = 0;
            constraints.gridy = 4;
            add(label6,constraints);
            JLabel label7 = new JLabel("Expiration Time MMYY");
            constraints.gridx = 1;
            constraints.gridy = 4;
            add(label7,constraints);
            JLabel label8 = new JLabel("Name On Card");
            constraints.gridx = 2;
            constraints.gridy = 4;
            add(label8,constraints);
            CardNumber=new JTextField(12);
            CardNumber.addActionListener(this);
            //CardNumber, ExpirationTime, NameOnCard;
            constraints.gridx = 0;
            constraints.gridy = 5;
            add(CardNumber,constraints);
            ExpirationTime=new JTextField(4);
            ExpirationTime.addActionListener(this);
            //CardNumber, ExpirationTime, NameOnCard;
            constraints.gridx = 1;
            constraints.gridy = 5;
            add(ExpirationTime,constraints);
            NameOnCard=new JTextField(15);
            NameOnCard.addActionListener(this);
            //CardNumber, ExpirationTime, NameOnCard;
            constraints.gridx = 2;
            constraints.gridy = 5;
            add(NameOnCard,constraints);
            constraints.gridx = 0;
            constraints.gridy = 6;
            add(btnOK,constraints);
            constraints.gridx = 1;
            constraints.gridy = 6;
            add(cancel,constraints);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            //System.out.println(un.getText());
            //System.out.println(un.getText()+" "+pass.getText()+" "+name.getText()+" "+phone.getText()+" "+addr.getText()+" "+(PaymentType) type.getSelectedItem());
            if(e.getSource()==btnOK){
                //
                //IMPORTANT  !!
                if(CardNumber.getText().length()==12||CardNumber.getText().isEmpty() ){
                    try {
                        model.createCustomer(name.getText(),addr.getText(),phone.getText());
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    // FOR CUSTOMER WHO JUST CREATED ACCOUNT, CUSTOMER OBJECT IS CREATED HERE , BEFORE THIS STATEMENT, THE CUSTOMER IS NOT CREATED.              --JIEFU
                    //
                    int flag = 0;
					try {
						flag = model.getCustomer().createAccount(un.getText(), pass.getText(), name.getText(), phone.getText(), addr.getText(),AccountType.customer,CardNumber.getText(),ExpirationTime.getText(), NameOnCard.getText());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    if(flag==1){
                        JOptionPane.showMessageDialog(this,
                                "Username or Password cannot be empty", "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                    }else if(name.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(this,
                                "Name cannot be empty", "Error Message",
                                JOptionPane.ERROR_MESSAGE);

                    }else if(flag==2){
                        JOptionPane.showMessageDialog(this,
                                "Username already exists", "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(this,"Account created successfully");
                        dispose();
                    }
                }else{
                    JOptionPane.showMessageDialog(this,
                            "Card Number is invalid, it should be 12 numbers", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }

            }else if(e.getSource()==cancel){
                dispose();
            }

        }
    }

    public class ViewMenuFrame extends JFrame implements ActionListener
    {
        public ViewMenuFrame()
        {
            super("Menu");
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setSize(500,500);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - getHeight()) / 2);
            setLocation(x, y);


            btnOK = new JButton("OK");
            btnOK.addActionListener(this);

            JTextArea textAreaSpecial = null;
			try {
				textAreaSpecial = new JTextArea(model.showSpecial());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            JTextArea textAreaDrink = null;
			try {
				textAreaDrink = new JTextArea(model.showDrinks());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            JTextArea textAreaAppetizer = null;
			try {
				textAreaAppetizer = new JTextArea(model.showAppetizers());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            JTextArea textAreaPizza = null;
			try {
				textAreaPizza = new JTextArea(model.showPizzas());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            textAreaSpecial.setEditable(false);
            textAreaDrink.setEditable(false);
            textAreaAppetizer.setEditable(false);
            textAreaPizza.setEditable(false);

            //set borders
            TitledBorder titleSpecial, titleDrink, titleAppetizer, titlePizza;
            titleSpecial = BorderFactory.createTitledBorder("Today's Daily Special");
            titleDrink = BorderFactory.createTitledBorder("Drink");
            titleAppetizer = BorderFactory.createTitledBorder("Appetizer");
            titlePizza = BorderFactory.createTitledBorder("Pizza");
            textAreaSpecial.setBorder(titleSpecial);
            textAreaDrink.setBorder(titleDrink);
            textAreaAppetizer.setBorder(titleAppetizer);
            textAreaPizza.setBorder(titlePizza);

            add(textAreaSpecial);
            add(textAreaDrink);
            add(textAreaAppetizer);
            add(textAreaPizza);
            add(btnOK);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource() == btnOK){
                dispose();
            }
        }
    }

    public class PlaceOrderFrame extends JFrame implements ActionListener{
        JButton btnAdd,btnpay,btnDel;
        JTextArea textAreaItems;
        private String ordernumber="";
        JList<Object> list,listS;
        ArrayList<Item> items=new ArrayList<Item>();
        OrderType type=OrderType.instore;
        JRadioButton btnPickup;
        JRadioButton btnDelivery;

        DefaultListModel listModel;

        public PlaceOrderFrame(){
            super("Place Order");
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setSize(600,600);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - getHeight()) / 2);
            setLocation(x, y);
            listModel = new DefaultListModel();
            try {
				list = new JList<>(model.getMenuList().toArray());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            listS = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(list);
            JScrollPane scrollPane1 =new JScrollPane(listS);

            //			textAreaItems = new JTextArea();
            //			TitledBorder titleItems;
            //			titleItems = BorderFactory.createTitledBorder("Item ordered");
            //			textAreaItems.setBorder(titleItems);



            btnPickup = new JRadioButton("Pickup");
            btnDelivery = new JRadioButton("Delivery");
            ButtonGroup groupType = new ButtonGroup();
            groupType.add(btnPickup);
            groupType.add(btnDelivery);

            JPanel pane = new JPanel();
            btnOK = new JButton("Place Now");
            btnCancel = new JButton("Cancel");
            btnDel=new JButton("Delete");
            btnAdd= new JButton("Add");
            pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
            //			pane.add(textAreaItems);
            pane.add(btnPickup);
            pane.add(btnDelivery);
            pane.add(btnOK);

            pane.add(btnAdd);
            pane.add(btnDel);
            pane.add(btnCancel);

            JLabel label = new JLabel("Please select item to order");
            label.setPreferredSize(new Dimension(10,20));
            JLabel label1 = new JLabel("Item Ordered");
            label1.setPreferredSize(new Dimension(30,20));

            add(label);
            add(scrollPane);
            add(label1);
            add(scrollPane1);
            add(pane);

            btnOK.addActionListener(this);
            btnCancel.addActionListener(this);
            btnAdd.addActionListener(this);
            btnDel.addActionListener(this);
            btnPickup.addActionListener(this);
            btnDelivery.addActionListener(this);
            setVisible(true);
        }
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==btnPickup){
                type=OrderType.instore;
                //System.out.println("lol");
            }
            if(e.getSource()==btnDelivery){
                type=OrderType.delivery;
                //System.out.println(type);
            }



            if(e.getSource() == btnOK){
                //Order or=new Order(items, OrderType.instore);
                if(items.size()==0){
                    JOptionPane.showMessageDialog(this,"Please select at least one item");
                    //dispose();
                }else{
                    DecimalFormat df2 = new DecimalFormat(".##");
                    Object[] options1= {"Confirm", "Cancel"};
                    String s1="Click confirm to place your order";
                    int choice1=JOptionPane.showOptionDialog(this, s1, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1,options1[0]);
                    if(choice1==0){
                        try {
							if(model.getflag()==true&&!model.getCustomer().getUsername().isEmpty()){
							    //						System.out.println("Login in");
							    model.getCustomer().placeOrder(items,type,model.getCustomer().getname());
							    //System.out.println(model.getOrders().size());
							    model.getCustomer().getOrder().calTotal();
							    model.recordOrder(model.getCustomer().getOrder());
							    ordernumber=model.getCustomer().getOrder().getOrderNumber();
							    //
							    JOptionPane.showMessageDialog(this, "Order is placed. The order number is " +model.getCustomer().getOrder().getOrderNumber()+". And total is $"+df2.format(model.getCustomer().getOrder().getTotal())+ " Please pay right now" );
							    //int choice=JOptionPane.showOptionDialog(this, s, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,options[0]);
							    if(model.getflag()==true&&model.getCustomer().AutoPay()){
							        AutoPay auto=new AutoPay(model.getCustomer().getOrder());
							    }else if(model.getflag()==true&&!model.getCustomer().getUsername().isEmpty()){
							        if(model.getCustomer().getOrder().orderStatus.name().equals("unpaid")){
							            //String orNUm=model.getCustomer().or.orderNumber;
							            new Payment(model.getCustomer().getOrder(),model.getCustomer().getOrder().orderType);

							            // System.out.println(model.getCustomer().or.orderNumber+"  "+model.getCustomer().or.orderStatus);
							        }else{
							            JOptionPane.showMessageDialog(this,
							                    "Your order has paied", "Error Message",
							                    JOptionPane.ERROR_MESSAGE);
							        }
							    }else{
							        int index=model.getOrders().size()-1;
							        new Payment(model.getOrders().get(index),model.getOrders().get(index).orderType);

							    }
							}else{
							    //System.out.println("No login");
							    Order or=new Order(items,type,"");
							    model.recordOrder(or);
							    //						System.out.println("Cun le");
							    or.calTotal();
							    JOptionPane.showMessageDialog(this, "Order is placed. The order number is " +or.getOrderNumber()+". And total is $"+df2.format(or.getTotal())+ " Please pay right now" );
							    //						System.out.println("size"+model.getOrders().size());
							    int index=model.getOrders().size()-1;
							    new Payment(model.getOrders().get(index),model.getOrders().get(index).orderType);



							}
						} catch (HeadlessException | RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}dispose();
                    }
                    else{

                    }


                }
            }
            if(e.getSource() == btnCancel){
                dispose();
            }
            if(e.getSource()==btnpay){
                try {
					if(model.getflag()==true&&model.getCustomer().AutoPay()){
					    AutoPay auto=new AutoPay(model.getCustomer().getOrder());
					    //				}else if(model.getflag()==true&&!model.getCustomer().AutoPay()){
					    //					JOptionPane.showMessageDialog(this,
					    //							"The Saved Payment Information is invalid, please reenter your card information", "Error Message",
					    //							JOptionPane.ERROR_MESSAGE);
					    //					if(model.getCustomer().getOrder().orderStatus.name().equals("unpaid")){
					    //						//String orNUm=model.getCustomer().or.orderNumber;
					    //						new Payment(model.getCustomer().getOrder());
					    //						// System.out.println(model.getCustomer().or.orderNumber+"  "+model.getCustomer().or.orderStatus);
					    //					}else{
					    //						JOptionPane.showMessageDialog(this,
					    //								"Your order has paied", "Error Message",
					    //								JOptionPane.ERROR_MESSAGE);
					    //					}
					}else if(model.getflag()==true&&!model.getCustomer().getUsername().isEmpty()){
					    if(model.getCustomer().getOrder().orderStatus.name().equals("unpaid")){
					        //String orNUm=model.getCustomer().or.orderNumber;
					        new Payment(model.getCustomer().getOrder(),model.getCustomer().getOrder().orderType);
					        // System.out.println(model.getCustomer().or.orderNumber+"  "+model.getCustomer().or.orderStatus);
					    }else{
					        JOptionPane.showMessageDialog(this,
					                "Your order has paied", "Error Message",
					                JOptionPane.ERROR_MESSAGE);
					    }
					}else{
					    int index=model.getOrders().size()-1;
					    new Payment(model.getOrders().get(index),model.getOrders().get(index).orderType);

					}
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

            }
            if(e.getSource()== btnAdd){
                //System.out.println("what");
                if(list.getSelectedValue()==null){
                    JOptionPane.showMessageDialog(this, "Please select an item");
                }
                else{
                    Item item = (Item)list.getSelectedValue();
                    items.add(item);
                    listModel.addElement(item);
                    //System.out.println("what");
                }
            }
            if(e.getSource()==btnDel){
                if(list.getSelectedValue()==null){
                    JOptionPane.showMessageDialog(this, "Please select an item");
                }
                else{
                    //System.out.println("del");
                    Item item = (Item)listS.getSelectedValue();
                    if(items.contains(item)){
                        int count=items.indexOf(item);
                        listModel.remove(count);
                        items.remove(item);}
                    else{
                        JOptionPane.showMessageDialog(this, "You can't remove that");
                    }
                    //String x=item.getName();
                }

            }

        }

    }

    public class AutoPay extends JFrame implements ActionListener{
        private JButton autopay,cancel,Paywithanothercard;
        public AutoPay(Order order){
            super("Auto-Pay the Bill");
            setLayout(new GridBagLayout());
            DecimalFormat df2 = new DecimalFormat(".##");
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth=1;
            setSize(180,80);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - getHeight()) / 2);
            setLocation(x, y);
            cancel=new JButton("Cancel");
            cancel.addActionListener(this);
            autopay = new JButton("Make a Payment");
            autopay.addActionListener(this);
            JLabel label1 = new JLabel(order.getOrderNumber()+". The total is $"+df2.format(order.getTotal()));
            add(label1,constraints);
            JLabel label2 = new JLabel("Would you like to use saved payment information to make a payment?");
            constraints.gridx = 1;
            constraints.gridy = 0;
            add(label2,constraints);
            constraints.gridx = 0;
            constraints.gridy = 1;
            add(cancel,constraints);
            constraints.gridx = 2;
            constraints.gridy = 1;
            add(autopay,constraints);
            Paywithanothercard=new JButton(" Pay with another Card");
            Paywithanothercard.addActionListener(this);
            constraints.gridx = 1;
            constraints.gridy = 1;
            add(Paywithanothercard,constraints);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(e.getSource()==autopay){
                try {
					model.getCustomer().getOrder().orderStatus=OrderStatus.paid;
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                JOptionPane.showMessageDialog(this,"Payment succeed");
                dispose();
            }

            if(e.getSource()==Paywithanothercard){
                try {
					if(model.getCustomer().getOrder().orderStatus.name().equals("unpaid")){
					    //String orNUm=model.getCustomer().or.orderNumber;
					    new Payment(model.getCustomer().getOrder(),model.getCustomer().getOrder().orderType);
					    // System.out.println(model.getCustomer().or.orderNumber+"  "+model.getCustomer().or.orderStatus);
					}else{
					    JOptionPane.showMessageDialog(this,
					            "Your order has paied", "Error Message",
					            JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                dispose();
            }


            if(e.getSource()==cancel){
                dispose();
            }

        }


    }
    public class Payment extends JFrame implements ActionListener{
        private JLabel Space;
        private JTextField CardNumber, ExpirationTime, NameOnCard,Address;
        private JPanel panel;
        private JButton pay,cancel;
        private Order order;
        private OrderType type;
        public Payment(Order order,OrderType type){
            super("Pay the Bill");
            this.order=order;
            this.type=type;
            setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth=1;
            setSize(500,500);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - getHeight()) / 2);
            setLocation(x, y);
            cancel=new JButton("Cancel");
            cancel.addActionListener(this);
            pay = new JButton("Make a Payment");
            pay.addActionListener(this);
            CardNumber = new JTextField(15);
            CardNumber.setFont(new Font("Arial", Font.BOLD, 14));
            CardNumber.addActionListener(this);
            ExpirationTime = new JTextField(15);
            ExpirationTime.setFont(new Font("Arial", Font.BOLD, 14));
            ExpirationTime.addActionListener(this);
            NameOnCard = new JTextField(15);
            NameOnCard.setFont(new Font("Arial", Font.BOLD, 14));
            NameOnCard.addActionListener(this);
            JLabel label1 = new JLabel("Credit Card Number(12 digit)");
            add(label1,constraints);
            JLabel label2 = new JLabel("Expiration Time(MMYY)");
            constraints.gridx = 1;
            constraints.gridy = 0;
            add(label2,constraints);
            JLabel label3 = new JLabel("Name on Card");
            constraints.gridx = 2;
            constraints.gridy = 0;
            add(label3,constraints);
            constraints.gridx = 0;
            constraints.gridy = 1;
            add(CardNumber,constraints);
            constraints.gridx = 1;
            constraints.gridy = 1;
            add(ExpirationTime,constraints);
            constraints.gridx = 2;
            constraints.gridy = 1;
            add(NameOnCard,constraints);
            constraints.gridx = 1;
            constraints.gridy = 2;
            //	add(cancel,constraints);
            constraints.gridx = 2;
            constraints.gridy = 3;
            add(pay,constraints);
            constraints.gridx = 4;
            constraints.gridy = 4;
            Space=new JLabel("");
            add(Space,constraints);

            JLabel label4 = new JLabel("Address(Required if Delivery)");
            constraints.gridx = 0;
            constraints.gridy = 2;
            add(label4,constraints);
            Address = new JTextField(15);
            Address.setFont(new Font("Arial", Font.BOLD, 14));
            Address.addActionListener(this);
            constraints.gridx = 0;
            constraints.gridy = 3;
            add(Address,constraints);



            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(e.getSource()==pay){
                try {
					if(model.getflag()==true&&!model.getCustomer().getUsername().isEmpty()){
					    order.setName(model.getCustomer().getname());
					    order.setAddress(Address.getText());
					    if(type==OrderType.delivery){
					        boolean payflag=model.getCustomer().paythebill(model.getCustomer().getOrder(),CardNumber.getText(),ExpirationTime.getText(),NameOnCard.getText());
					        boolean addressflag=false;
					        if(Address.getText().isEmpty()){
					            addressflag=true;
					        }
					        if(payflag==true && addressflag==false){

					            JOptionPane.showMessageDialog(this,"Payment succeed!");
					            dispose();
					        }
					        else if( addressflag==true){
					            JOptionPane.showMessageDialog(this,"Address required");
					        }
					        else{
					            JOptionPane.showMessageDialog(this,"Invalid card info");
					        }
					    }
					    else{
					        boolean payflag=model.getCustomer().paythebill(model.getCustomer().getOrder(),CardNumber.getText(),ExpirationTime.getText(),NameOnCard.getText());

					        if(payflag==true){

					            JOptionPane.showMessageDialog(this,"Payment succeed!");
					            dispose();
					        }

					        else{
					            JOptionPane.showMessageDialog(this,"Invalid card info");
					        }
					    }

					}else{

					    int index=model.getOrders().size()-1;

					    if(type==OrderType.delivery){

					        order.setName(NameOnCard.getText());
					        order.setAddress(Address.getText());
					        boolean addressflag=false;
					        if(Address.getText().isEmpty()){
					            addressflag=true;
					        }
					        boolean payflag=model.paythebill(model.getOrders().get(index),CardNumber.getText(),ExpirationTime.getText(),NameOnCard.getText());
					        if(payflag==true && addressflag==false){

					            JOptionPane.showMessageDialog(this,"Payment succeed!");
					            dispose();
					        }
					        else if( addressflag==true){
					            JOptionPane.showMessageDialog(this,"Address required");
					        }
					        else{
					            JOptionPane.showMessageDialog(this,"Invalid card info");
					        }
					    }
					    else{

					        order.setName(NameOnCard.getText());
					        order.setAddress(Address.getText());


					        boolean payflag=model.paythebill(model.getOrders().get(index),CardNumber.getText(),ExpirationTime.getText(),NameOnCard.getText());
					        if(payflag==true ){

					            JOptionPane.showMessageDialog(this,"Payment succeed!");
					            dispose();
					        }

					        else{
					            JOptionPane.showMessageDialog(this,"Invalid card info");
					        }
					    }

					}
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            if(e.getSource()==cancel){
                dispose();
            }

        }
    }
    public static void main(String args[]) throws RemoteException, NotBoundException {
		String host = args[0];
		
		final Registry registry = LocateRegistry.getRegistry(host,Integer.parseInt(args[1]));
		FacadeInterface model=(FacadeInterface) registry.lookup("Service");
		//model.deleteCustomer();
		CustomerInterFace mi=new Customer(null, null, null);
		model.register(mi);
		System.out.println("Customer Registered.");

		new CustomerView("Customer View",model);
    }

}