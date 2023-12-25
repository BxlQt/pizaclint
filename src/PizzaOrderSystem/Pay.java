package PizzaOrderSystem;
public class Pay {
	public void paytheorder(Order or){
		or.orderStatus=OrderStatus.paid;
	}

	public boolean validcardnum(String num){
		if(num.isEmpty()){
			return false;
		}
		if(num.matches("^-?\\d+$")&&num.length()==12){
			return true;
		}
		return false;
	}

	public boolean validdate(String date){
		if(date.isEmpty()){
			return false;
		}
		if(date.matches("^-?\\d+$")&&date.length()==4){
			if(Integer.parseInt(date.substring(0,2))<=12 &&Integer.parseInt(date)>=1115){
				return true;
			}
		}
		return false;
	}

	public boolean validName(String name){
		if( name.isEmpty()){
			return false;
		}
		if(name.matches("[a-zA-Z]+")){
			return true;
		}
		return false;
	}
	//	public static void main(String []args){
	//		Pay pay=new Pay();
	//		System.out.println(pay.validName("12345678"));
	//		System.out.println(pay.validName("dwa#s"));
	//	}
}