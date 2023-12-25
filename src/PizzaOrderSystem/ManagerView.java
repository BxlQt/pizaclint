package PizzaOrderSystem;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
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


public class ManagerView extends JFrame implements ActionListener, Serializable{
	private JButton  btnCreateAccount, btnDefineMenu, btnModifyMenu,
	btnSetDailySpecial, btnLogin, btnOK, btnCancel,signout, btnViewMenu;
	private JLabel lblUsername, lblPassword, welcome;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private FacadeInterface model;

	public ManagerView(String title, FacadeInterface model){
		super(title);
		this.model = model;
		setLayout(null);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setSize(x,y-25);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(x, y+25);

		btnCreateAccount= new JButton("Create Account");
		btnCreateAccount.setBounds(30,150,150,30);
		btnDefineMenu = new JButton("Define Menu");
		btnDefineMenu.setBounds(30,30,150,30);
		btnModifyMenu = new JButton("Modify Menu");
		btnModifyMenu.setBounds(30,70,150,30);
		btnSetDailySpecial = new JButton("Set Daily Special");
		btnSetDailySpecial.setBounds(30,110,150,30);
		btnViewMenu = new JButton("View Menu");
		btnViewMenu.setBounds(30,190,150,30);
		btnLogin= new JButton("Login");
		btnLogin.setBounds(295,120,130,30);
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

		add(btnCreateAccount);
		add(btnDefineMenu);
		add(btnModifyMenu);
		add(btnSetDailySpecial);
		add(btnViewMenu);
		add(lblUsername);
		add(lblPassword);
		add(usernameField);
		add(passwordField);
		add(btnLogin);
		signout=new JButton("Sign out");
		signout.setBounds(430,120,130,30);
		signout.addActionListener(this);
		add(signout);
		signout.setVisible(false);
		btnDefineMenu.setVisible(false);
		btnModifyMenu.setVisible(false);
		btnSetDailySpecial.setVisible(false);
		btnCreateAccount.setVisible(false);
		btnViewMenu.setVisible(false);

		btnCreateAccount.addActionListener(this);
		btnDefineMenu.addActionListener(this);
		btnModifyMenu.addActionListener(this);
		btnSetDailySpecial.addActionListener(this);
		btnLogin.addActionListener(this);
		btnViewMenu.addActionListener(this);

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
			signout.setVisible(false);
			btnDefineMenu.setVisible(false);
			btnModifyMenu.setVisible(false);
			btnSetDailySpecial.setVisible(false);
			btnCreateAccount.setVisible(false);
			btnViewMenu.setVisible(false);
			btnViewMenu.setVisible(true);
			usernameField.setText("");
			passwordField.setText("");
			repaint();
		}
		if(e.getSource() == btnCreateAccount){
			new CreateAccountFrame();
		}
		if(e.getSource() == btnDefineMenu){
			new DefineMenuFrame();
		}
		if(e.getSource() == btnModifyMenu){
			new ModifyMenuFrame();
		}
		if(e.getSource() == btnSetDailySpecial){
			new SetDailySpecialFrame();
		}
		if(e.getSource() == btnViewMenu){
			new ViewMenuFrame();
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
					//System.out.println(typeinfo);
					if(typeinfo.equals(AccountType.manager.name())){
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
						welcome = new JLabel("Welcome "+ name+"!");
						welcome.setFont(new Font("Arial", Font.BOLD, 14));
						welcome.setBounds(500,40,800,25);
						add(welcome);
						signout.setVisible(true);
						btnCreateAccount.setVisible(true);
						btnDefineMenu.setVisible(true);
						btnModifyMenu.setVisible(true);
						btnSetDailySpecial.setVisible(true);
						btnViewMenu.setVisible(true);


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
								"Your account is not a manager account, please try again.", "Error Message",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this,
							"Your password and username do not match. Please try again.", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class CreateAccountFrame extends JFrame implements ActionListener{
		private JButton btnOK,cancel;
		private JFrame frame;
		private JTextField un, pass,name, phone, addr,at;
		private JRadioButton btnChef,btnCashier;
		int ac = 0;

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

			btnChef = new JRadioButton("Chef");
			btnCashier = new JRadioButton("Cashier");


			ButtonGroup groupType = new ButtonGroup();
			groupType.add(btnChef);
			groupType.add(btnCashier);


			JPanel paneType = new JPanel();
			paneType.setLayout(new BoxLayout(paneType, BoxLayout.X_AXIS));
			paneType.add(new Label("Select Account type: "));


			paneType.add(btnChef);
			paneType.add(btnCashier);

			constraints.gridx = 0;
			constraints.gridy = 4;
			add(paneType,constraints);

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
			constraints.gridx = 0;
			constraints.gridy = 0;
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
			constraints.gridx = 0;
			constraints.gridy = 6;
			add(btnOK,constraints);
			constraints.gridx = 1;
			constraints.gridy = 6;
			add(cancel,constraints);
			pack();
			setVisible(true);
			btnChef.addActionListener(this);
			btnCashier.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			//System.out.println(un.getText());
			//System.out.println(un.getText()+" "+pass.getText()+" "+name.getText()+" "+phone.getText()+" "+addr.getText()+" "+(PaymentType) type.getSelectedItem());
			if(e.getSource() == btnChef)  ac = 1;
			if(e.getSource() == btnCashier)  ac = 2;
			if(e.getSource()==btnOK){

				int flag =0;
				int flagSuccess = 0;
				boolean ValidType = false;
				//
				//IMPORTANT  !!


				//                if(flag==1 || name.getText().trim().isEmpty() || at.getText().trim().isEmpty()){
				//                    JOptionPane.showMessageDialog(this,
				//                            "Please fill in the required informations.", "Error Message",
				//                            JOptionPane.ERROR_MESSAGE);
				//                }else if(flag==2){
				//                    JOptionPane.showMessageDialog(this,
				//                            "Username already exists", "Error Message",
				//                            JOptionPane.ERROR_MESSAGE);
				//                }else

				if(ac==0){
					JOptionPane.showMessageDialog(this,
							"Please select a valid account type. Choose Chef or Cashier.", "Error Message",
							JOptionPane.ERROR_MESSAGE);
					flagSuccess = 1;
				}
				else if(name.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this,
							"Name cannot be empty", "Error Message",
							JOptionPane.ERROR_MESSAGE);
					flagSuccess = 1;
				}
				else if(pass.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this,
							"Password cannot be empty", "Error Message",
							JOptionPane.ERROR_MESSAGE);
					flagSuccess = 1;
				}
				else if(un.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this,
							"Username cannot be empty", "Error Message",
							JOptionPane.ERROR_MESSAGE);
					flagSuccess = 1;
				}

				if(flagSuccess == 0){
					if(ac==1){
						try {
							flag=model.getStoreManager().createEmployeeAccount(un.getText(), pass.getText(), name.getText(), phone.getText(), addr.getText(),AccountType.chef);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}else if(ac==2){
						try {
							flag=model.getStoreManager().createEmployeeAccount(un.getText(), pass.getText(), name.getText(), phone.getText(), addr.getText(),AccountType.cashier);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					if(flag == 2){
						JOptionPane.showMessageDialog(this,
								"Username already exists", "Error Message",
								JOptionPane.ERROR_MESSAGE);
						flagSuccess = 1;
					}
					JOptionPane.showMessageDialog(this,"Account created successfully");
					dispose();
				}

				//
			}else if(e.getSource()==cancel){
				dispose();
			}

		}
	}


	public class DefineMenuFrame extends JFrame implements ActionListener{
		private TextField name, price;
		private JRadioButton btnDrink, btnAppetizer, btnPizza, btnSmall, btnMedium, btnLarge;
		int type = 0;
		Size size = null;

		public DefineMenuFrame(){
			super("Define Menu");
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			setSize(500,500);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - getHeight()) / 2);
			setLocation(x, y);

			name = new TextField(20);
			price = new TextField(20);
			btnOK = new JButton("OK");
			btnCancel = new JButton("Cancel");

			//radio button for selecting type
			btnDrink = new JRadioButton("Drink");
			btnAppetizer = new JRadioButton("Appetizer");
			btnPizza = new JRadioButton("Pizza");
			ButtonGroup groupType = new ButtonGroup();
			groupType.add(btnDrink);
			groupType.add(btnAppetizer);
			groupType.add(btnPizza);

			//make type button horizontal layout
			JPanel paneType = new JPanel();
			paneType.setLayout(new BoxLayout(paneType, BoxLayout.X_AXIS));
			paneType.add(new Label("Select item type: "));
			paneType.add(btnDrink);
			paneType.add(btnAppetizer);
			paneType.add(btnPizza);


			//radio button for selecting size
			btnSmall = new JRadioButton("Small");
			btnMedium = new JRadioButton("Medium");
			btnLarge = new JRadioButton("Large");
			ButtonGroup groupSize = new ButtonGroup();
			groupSize.add(btnSmall);
			groupSize.add(btnMedium);
			groupSize.add(btnLarge);


			//make size button horizontal
			JPanel paneSize = new JPanel();
			paneSize.setLayout(new BoxLayout(paneSize, BoxLayout.X_AXIS));
			paneSize.add(new Label("Select item size: "));
			paneSize.add(btnSmall);
			paneSize.add(btnMedium);
			paneSize.add(btnLarge);

			//make ok and cancel horizontal
			JPanel pane = new JPanel();
			pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
			pane.add(btnOK);
			pane.add(btnCancel);

			add(paneType);
			add(new Label("Enter item name: "));
			add(name);
			add(paneSize);
			add(new Label("Enter item price: "));
			add(price);
			add(pane);

			btnDrink.addActionListener(this);
			btnAppetizer.addActionListener(this);
			btnPizza.addActionListener(this);
			btnSmall.addActionListener(this);
			btnMedium.addActionListener(this);
			btnLarge.addActionListener(this);
			btnOK.addActionListener(this);
			btnCancel.addActionListener(this);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == btnDrink)  type = 1;
			if(e.getSource() == btnAppetizer) type = 2;
			if(e.getSource() == btnPizza) type = 3;
			if(e.getSource() == btnSmall) size = Size.small;
			if(e.getSource() == btnMedium) size = Size.medium;
			if(e.getSource() == btnLarge) size = Size.large;


			if(e.getSource() == btnOK){
				if(type != 0 && name.getText().length() != 0 && size != null && price.getText().length() != 0){
					try {
						model.defineMenu(type, name.getText(), size, price.getText());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}
				else{
					JOptionPane.showMessageDialog(this,"Please enter item information.");
				}
			}

			if(e.getSource() == btnCancel){
				dispose();
			}
		}

	}

	public class ModifyMenuFrame extends JFrame implements ActionListener{
		JList<Object> list;
		JButton btnDelete;

		public ModifyMenuFrame(){
			super("Modify Menu");
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			setSize(500,500);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - getHeight()) / 2);
			setLocation(x, y);

			try {
				list = new JList<>(model.getMenuList().toArray());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JScrollPane scrollPane = new JScrollPane(list);

			JPanel pane = new JPanel();
			btnOK = new JButton("OK");
			btnCancel = new JButton("Cancel");
			btnDelete = new JButton("Delete");
			pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
			pane.add(btnOK);
			pane.add(btnDelete);
			pane.add(btnCancel);

			JLabel label = new JLabel("Please select item to modify");
			label.setPreferredSize(new Dimension(30,20));
			add(label);
			add(scrollPane);
			add(pane);

			btnOK.addActionListener(this);
			btnCancel.addActionListener(this);
			btnDelete.addActionListener(this);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnOK){
				if(list.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Please select an item to modify");
				}
				else{
					new EditItemFrame(list.getSelectedValue());
					dispose();
				}

			}
			if(e.getSource() == btnCancel){
				dispose();
			}
			if(e.getSource() == btnDelete){
				if(list.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this, "Please select an item to delete");
				}
				else{
					try {
						model.remove((Item)list.getSelectedValue());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "Item has deleted");
					dispose();
				}
			}
		}

	}

	public class EditItemFrame extends JFrame implements ActionListener{
		private Item item;
		private TextField name, price;
		private JRadioButton btnDrink, btnAppetizer, btnPizza, btnSmall, btnMedium, btnLarge;
		int type = 0;
		Size size = null;

		public EditItemFrame(Object o){
			super("Edit Item");
			item = (Item) o;
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			setSize(500,500);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - getHeight()) / 2);
			setLocation(x, y);

			name = new TextField(20);
			name.setText(item.getName());
			price = new TextField(20);
			if(item.getSpecial() == 0) price.setText(String.valueOf(item.getPrice()));
			else price.setText(String.valueOf(item.getSpecial()));

			//radio button for selecting type
			btnDrink = new JRadioButton("Drink");
			btnAppetizer = new JRadioButton("Appetizer");
			btnPizza = new JRadioButton("Pizza");
			ButtonGroup groupType = new ButtonGroup();
			groupType.add(btnDrink);
			groupType.add(btnAppetizer);
			groupType.add(btnPizza);
			if(item instanceof Drink){
				btnDrink.setSelected(true);
				type = 1;
			}
			if(item instanceof Appetizer){
				btnAppetizer.setSelected(true);
				type = 2;
			}
			if(item instanceof Pizza){
				btnPizza.setSelected(true);
				type = 3;
			}

			//make type button horizontal layout
			JPanel paneType = new JPanel();
			paneType.setLayout(new BoxLayout(paneType, BoxLayout.X_AXIS));
			paneType.add(new Label("Item type: "));
			paneType.add(btnDrink);
			paneType.add(btnAppetizer);
			paneType.add(btnPizza);

			//radio button for selecting size
			btnSmall = new JRadioButton("Small");
			btnMedium = new JRadioButton("Medium");
			btnLarge = new JRadioButton("Large");
			ButtonGroup groupSize = new ButtonGroup();
			groupSize.add(btnSmall);
			groupSize.add(btnMedium);
			groupSize.add(btnLarge);
			if(item.getSize() == Size.small){
				btnSmall.setSelected(true);
				size = Size.small;
			}
			if(item.getSize() == Size.medium){
				btnMedium.setSelected(true);
				size = Size.medium;
			}
			if(item.getSize() == Size.large){
				btnLarge.setSelected(true);
				size = Size.large;
			}


			//make size button horizontal
			JPanel paneSize = new JPanel();
			paneSize.setLayout(new BoxLayout(paneSize, BoxLayout.X_AXIS));
			paneSize.add(new Label("Item size: "));
			paneSize.add(btnSmall);
			paneSize.add(btnMedium);
			paneSize.add(btnLarge);

			JPanel pane = new JPanel();
			btnOK = new JButton("OK");
			btnCancel = new JButton("Cancel");
			pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
			pane.add(btnOK);
			pane.add(btnCancel);

			add(paneType);
			add(new Label("Item name: "));
			add(name);
			add(paneSize);
			add(new Label("Item price: "));
			add(price);
			add(pane);

			btnDrink.addActionListener(this);
			btnAppetizer.addActionListener(this);
			btnPizza.addActionListener(this);
			btnSmall.addActionListener(this);
			btnMedium.addActionListener(this);
			btnLarge.addActionListener(this);
			btnOK.addActionListener(this);
			btnCancel.addActionListener(this);
			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnDrink)  type = 1;
			if(e.getSource() == btnAppetizer) type = 2;
			if(e.getSource() == btnPizza) type = 3;
			if(e.getSource() == btnSmall) size = Size.small;
			if(e.getSource() == btnMedium) size = Size.medium;
			if(e.getSource() == btnLarge) size = Size.large;


			if(e.getSource() == btnOK){
				if(type != 0 && name.getText().length() != 0 && size != null && price.getText().length() != 0){
					item.setName(name.getText());
					if(item.getSpecial() == 0){
						item.setPrice(Double.parseDouble(price.getText()));
					}
					else{
						item.setSpecial(Double.parseDouble(price.getText()));
					}
					item.setSize(size);
					dispose();
				}
				else{
					JOptionPane.showMessageDialog(this,"Please enter item information.");
				}
			}

			if(e.getSource() == btnCancel){
				dispose();
			}

		}

	}

	public class SetDailySpecialFrame extends JFrame implements ActionListener{
		JList<Object> list;

		public SetDailySpecialFrame(){
			super("Set Daily Special");
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			setSize(500,500);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - getHeight()) / 2);
			setLocation(x, y);

			try {
				list = new JList<>(model.getMenuList().toArray());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JScrollPane scrollPane = new JScrollPane(list);

			JPanel pane = new JPanel();
			btnOK = new JButton("OK");
			btnCancel = new JButton("Cancel");
			pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
			pane.add(btnOK);
			pane.add(btnCancel);

			JLabel label = new JLabel("Please select the daily special item");
			label.setPreferredSize(new Dimension(30,20));
			add(label);
			add(scrollPane);
			add(pane);

			btnOK.addActionListener(this);
			btnCancel.addActionListener(this);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnOK){
				if(list.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Please select the daily special item");
				}
				else{
					Item item = (Item)list.getSelectedValue();
					try {
						model.setDailySpecial(item);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DecimalFormat df2 = new DecimalFormat(".##");
					df2.setRoundingMode(RoundingMode.DOWN);
					JOptionPane.showMessageDialog(this, item.getName() + "'s price has been changed from $" + df2.format(item.getPrice()) + " to $" + df2.format(item.getSpecial()));
					dispose();
				}
			}
			if(e.getSource() == btnCancel){
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

			JTextArea textAreaSpecial;
			try {
				textAreaSpecial = new JTextArea(model.showSpecial());
				JTextArea textAreaDrink = new JTextArea(model.showDrinks());
				JTextArea textAreaAppetizer = new JTextArea(model.showAppetizers());
				JTextArea textAreaPizza = new JTextArea(model.showPizzas());

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
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == btnOK){
				dispose();
			}
		}
	}

	public static void main(String args[]) throws RemoteException, NotBoundException {
		String host = args[0];
		final Registry registry = LocateRegistry.getRegistry(host,Integer.parseInt(args[1]));
		FacadeInterface model=(FacadeInterface) registry.lookup("Service");
		model.deleteCustomer();
		ManagerInterface mi=new StoreManager("admin", "admin", "1234");
		model.register(mi);
		System.out.println("Manager Registered.");
		new ManagerView("Manager View",model);
	}
}