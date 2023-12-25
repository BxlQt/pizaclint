package PizzaOrderSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
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


//import CustomerView.AutoPay;
//import CustomerView.Payment;



public class CashierView extends JFrame implements ActionListener{
    private JButton btnPlaceOrder,btnLogin,btnOK,btnCancel,signout,btnPrintOrder,btnPrint;
    private FacadeModel model;
    private JLabel lblUsername,lblPassword,welcome;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public CashierView(String title, FacadeModel model){
        super(title);
        this.model = model;
        setLayout(null);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setSize(x,y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(x, 0);

        btnLogin= new JButton("Login");
        btnLogin.setBounds(295,120,130,30);
        btnPlaceOrder = new JButton("Place Order");
        btnPlaceOrder.setBounds(30,30,150,30);
        btnPrintOrder=new JButton("Print Receipt");
        btnPrintOrder.setBounds(30,70,150,30);
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
        add(btnPlaceOrder);
        add(btnPrintOrder);
        signout=new JButton("Sign out");
        signout.setBounds(430,120,130,30);
        signout.addActionListener(this);
        add(signout);
        signout.setVisible(false);
        btnPlaceOrder.setVisible(false);
        btnPrintOrder.setVisible(false);

        btnLogin.addActionListener(this);
        btnPlaceOrder.addActionListener(this);
        btnPrintOrder.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnPlaceOrder){
            new PlaceOrderFrame();
        }
        if(e.getSource() == btnPrintOrder){
            new PrintOrderFrame();
        }
        if(e.getSource()==signout){
            //System.out.println("aaaaaaaaa");
            model.deleteCustomer();
            add(usernameField);
            //passwordField.setVisible(false);
            add(passwordField);
            //lblUsername.setVisible(false);
            add(lblUsername);
            //btnLogin.setVisible(false);
            add(btnLogin);
            add(lblPassword);
            remove(welcome);
            model.setflag(false);
            signout.setVisible(false);
            btnPlaceOrder.setVisible(false);
            btnPrintOrder.setVisible(false);
            usernameField.setText("");
            passwordField.setText("");
            repaint();
        }

        if(e.getSource()==btnLogin){
            char[] password = passwordField.getPassword();
            String name= usernameField.getText();
            if (model.checkAccount(name,password)) {
                String nameinfo=model.getaccount().getname(name);
                String phoneinfo=model.getaccount().getphone(name);
                String addrinfo=model.getaccount().getaddr(name);
                String typeinfo=model.getaccount().gettype(name);
                //System.out.println(typeinfo);
                if(typeinfo.equals(AccountType.cashier.name())){
                    //if(model.getaccountinfo(username))
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
                    remove(lblPassword);
                    welcome = new JLabel("Welcome Cashier "+ name+"!");
                    welcome.setFont(new Font("Arial", Font.BOLD, 14));
                    welcome.setBounds(500,40,800,25);
                    add(welcome);
                    signout.setVisible(true);
                    btnPlaceOrder.setVisible(true);
                    btnPrintOrder.setVisible(true);
                    //
                    //IMPORTANT  !!

                    // FOR CUSTOMER WHO LOGGED INTO ACCOUNT, CUSTOMER OBJECT IS CREATED HERE , BEFORE THIS STATEMENT, THE CUSTOMER IS NOT CREATED.              --JIEFU
                    //

                    repaint(); // IMPORTANT NO COMMENT!
                }else{
                    JOptionPane.showMessageDialog(this,
                            "Your account is not a cashier account, please try again.", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Your password and username do not match. Please try again.", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
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
            list = new JList<>(model.getMenuList().toArray());
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
            }
            if(e.getSource()==btnDelivery){
                type=OrderType.delivery;
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
                        if(model.getflag()==true&&!model.getCustomer().getUsername().isEmpty()){
                            //						System.out.println("Login in");
                            model.getCustomer().placeOrder(items,type,model.getCustomer().getname());
                            //System.out.println(model.getOrders().size());
                            model.getCustomer().getOrder().calTotal();
                            model.recordOrder(model.getCustomer().getOrder());
                            ordernumber=model.getCustomer().getOrder().getOrderNumber();
                            //						Object[] options = {"Pay now",
                            //			                    "Change Order",
                            //			                    };
                            //						String s="Order is placed. The order number is " +model.getCustomer().or.orderNumber+". And total is $"+df2.format(model.getCustomer().or.total)+ "Choose to pay now or change your order";
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
                            System.out.println("No login");
                            System.out.println(type);
                            Order or=new Order(items,type,"");
                            model.recordOrder(or);
                            //						System.out.println("Cun le");
                            or.calTotal();
                            JOptionPane.showMessageDialog(this, "Order is placed. The order number is " +or.getOrderNumber()+". And total is $"+df2.format(or.getTotal())+ " Please pay right now" );
                            //						System.out.println("size"+model.getOrders().size());
                            //int index=model.getOrders().size()-1;
                            new Payment(or,or.orderType);



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
                model.getCustomer().getOrder().orderStatus=OrderStatus.paid;
                dispose();
            }

            if(e.getSource()==Paywithanothercard){
                if(model.getCustomer().getOrder().orderStatus.name().equals("unpaid")){
                    //String orNUm=model.getCustomer().or.orderNumber;
                    new Payment(model.getCustomer().getOrder(),model.getCustomer().getOrder().orderType);
                    // System.out.println(model.getCustomer().or.orderNumber+"  "+model.getCustomer().or.orderStatus);
                }else{
                    JOptionPane.showMessageDialog(this,
                            "Your order has paied", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }


            if(e.getSource()==cancel){
                dispose();
            }

        }


    }

    public class PrintOrderFrame extends JFrame implements ActionListener{
        JList<Object> list;
        public PrintOrderFrame()
        {
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setSize(500,500);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - getHeight()) / 2);
            setLocation(x, y);

            list = new JList<>(model.getOrders().toArray());
            JScrollPane scrollPane = new JScrollPane(list);

            JPanel pane = new JPanel();
            btnPrint = new JButton("Print");
            btnCancel = new JButton("Cancel");
            pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
            pane.add(btnPrint);
            pane.add(btnCancel);

            JLabel label = new JLabel("Please select order to print");
            label.setPreferredSize(new Dimension(30,20));
            add(label);
            add(scrollPane);
            add(pane);

            btnPrint.addActionListener(this);
            btnCancel.addActionListener(this);
            setVisible(true);
        }
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnPrint){
                //System.out.println("Print");
                if(list.getSelectedValue()!=null){
                    new PrintFrame(list.getSelectedValue());
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(this, "You did not select any order.");
                }
            }
            if(e.getSource() == btnCancel){
                dispose();
            }
        }
    }

    public class PrintFrame extends JFrame implements ActionListener{

        public PrintFrame(Object o){
            super("Print Receipt");
            Order or= (Order) o;
            or.calTotal();
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setSize(500,500);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - getHeight()) / 2);
            setLocation(x, y);

            JTextArea textItems = new JTextArea("Order Number: " + or.getOrderNumber() + " \n\n" + or.getOrderInfo() + "\n"
                    + "Total: " + or.getTotal());

            //			add(btnOK);
            add(textItems);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //			if(e.getSource() == btnOK){
            //				dispose();
            //			}
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
                if(model.getflag()==true&&!model.getCustomer().getUsername().isEmpty()){
                    order.setName(NameOnCard.getText());
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
            }
            if(e.getSource()==cancel){
                dispose();
            }

        }
    }
}
