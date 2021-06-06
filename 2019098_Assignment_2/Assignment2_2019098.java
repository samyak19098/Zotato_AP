import java.util.*;
import java.io.*;

public class Assignment2_2019098{

	public static void main(String[] args) {
		Company app = new Company();
		app.run_application();
	}
}


class Company{
	
	Scanner in = new Scanner(System.in);

	private final ArrayList<restaurant> rlist = new ArrayList<restaurant>();
	private final ArrayList<customer> clist = new ArrayList<customer>();
	private final company_account comp_account;


	
	public Company() {
		
		//hardcoding restaurants
		rlist.add(new authentic("Shah ", " Gujarat",this));
		rlist.add(new restaurant("Ravi " ," Delhi",this));
		rlist.add(new authentic("The Chinese "," Rohini",this));
		rlist.add(new fast_food("Wang ", " Pitampura",this));
		rlist.add(new restaurant("Paradise ", " India",this));
		
		//hardcoding customer
		clist.add(new elite_customer("Ram ", " Delhi",this));
		clist.add(new elite_customer("Sam ", " Pune",this));
		clist.add(new special_customer("Tim ", " mumbai",this));
		clist.add(new customer("Kim ", " Rohini",this));
		clist.add(new customer("Jim "," punjabi bagh",this));
		this.comp_account = new company_account();
	}
	
	public void menu_display() {
		System.out.println("Welcome to Zotato");
		System.out.println("    1) Enter as Restaurant Owner");
		System.out.println("    2) Enter as Customer");
		System.out.println("    3) Check User Details");
		System.out.println("    4) Company Account details");
		System.out.println("    5) Exit");
	}
	
	public void display_all_rest() {
		System.out.println("Choose Restaurant : ");
		for(int i = 0 ; i < rlist.size() ; i++) {
			System.out.print((i + 1) + " " + rlist.get(i).get_name());
			if(!rlist.get(i).get_rest_type().equals("")) {
				System.out.println(" (" + rlist.get(i).get_rest_type() + ")");
			}
			else {
				System.out.println();
			}
		}
	}
	public void display_all_cust() {


		for(int i = 0 ; i < clist.size() ; i++) {
			System.out.print((i + 1) + ". " + clist.get(i).get_name());
			if(!clist.get(i).get_cust_type().equals("")) {
				System.out.println(" (" + clist.get(i).get_cust_type() + ")");
			}
			else {
				System.out.println();
			}
		}
	}
	
	public void run_application() {
	
		while(true) {
			this.menu_display();
			int option = in.nextInt();
			if(option == 1) {
				this.display_all_rest();
				int option11 = in.nextInt();
				while(true) {
					this.display_particular_rest(option11);
					int option111 = in.nextInt();
					if(option111 == 1) {
						this.add_item(option11);
					}
					else if(option111 == 2) {
						this.edit_item(option11);
					}
					else if(option111 == 3) {
						rlist.get(option11 - 1).show_reward_points();
					}
					else if(option111 == 4) {
						System.out.print("Enter offer on total bill value : ");
						int discnt = in.nextInt();
						rlist.get(option11 - 1).set_rest_discount(discnt);
					}
					else if(option111 == 5) {
						break;
					}
				}	
			}
			else if(option == 2) {
				this.display_all_cust();
				int cust_num_chosen = in.nextInt();
				while(true) {
					this.display_particular_cust(cust_num_chosen);
					int cust_option_chosen = in.nextInt();
					if(cust_option_chosen == 1) {
						this.display_all_rest();
						int choose_res = in.nextInt();
						System.out.println("Chooseitem by code : ");
						for(int i = 0 ; i < rlist.get(choose_res - 1).get_food_item_list().size(); i++) {
							rlist.get(choose_res - 1).get_food_item_list().get(i).print_food_item();
						}
						int chosen_item_code = in.nextInt();
						System.out.println("Enter item quantity - ");
						int item_quantity = in.nextInt();
						for(int i = 0 ; i < rlist.get(choose_res - 1).get_food_item_list().size(); i++) {
							if(chosen_item_code == rlist.get(choose_res - 1).get_food_item_list().get(i).get_food_id()) {
								clist.get(cust_num_chosen - 1).get_cust_cart().add_to_cart(rlist.get(choose_res - 1).get_food_item_list().get(i), item_quantity);
							}
						}
						System.out.println("Items added to cart");
					}
					else if(cust_option_chosen == 2) {
						
						clist.get(cust_num_chosen - 1).get_cust_cart().set_currently_ordering_from(clist.get(cust_num_chosen - 1).get_cust_cart().get_cart_food_list().get(0).get_food_rest());
						clist.get(cust_num_chosen - 1).get_cust_cart().print_cart_before_checkout();
						System.out.println("1) Proceed to checkout");
						int proceed_signal = in.nextInt();
						if(proceed_signal == 1) {
							if(clist.get(cust_num_chosen - 1).get_cust_cart().get_cart_total_bill_value() <= (clist.get(cust_num_chosen - 1).get_cust_account().get_customer_wallet() + clist.get(cust_num_chosen - 1).get_cust_account().get_customer_reward_points())) {
								clist.get(cust_num_chosen - 1).get_cust_cart().suffient_checkout_cart();
							}
							else {
								while(clist.get(cust_num_chosen - 1).get_cust_cart().calculate_cart_bill() > (clist.get(cust_num_chosen - 1).get_cust_account().get_customer_wallet() + clist.get(cust_num_chosen - 1).get_cust_account().get_customer_reward_points())) {
									System.out.println("INSUFFICIENT BALANCE !! PLEASE DELETE AN ITEM TO PROCEED");
									clist.get(cust_num_chosen - 1).get_cust_cart().print_items();
									System.out.println("ENTER ID OF CHOSEN ITEM ALONGWITH QUANTITY TO DELETE");
									int id_to_delete = in.nextInt();
									int qty_to_delete = in.nextInt();
									clist.get(cust_num_chosen - 1).get_cust_cart().delete_item(id_to_delete, qty_to_delete);
								}
								clist.get(cust_num_chosen - 1).get_cust_cart().set_cart_total_bill_value(clist.get(cust_num_chosen - 1).get_cust_cart().calculate_cart_bill()); ;
								System.out.println("READY FOR CHECKOUT !!");
								System.out.println("1) Proceed to checkout");
								int proceed = in.nextInt();
								if(proceed == 1) {
									clist.get(cust_num_chosen - 1).get_cust_cart().suffient_checkout_cart();
								}

							}
						}

					}
					
					else if(cust_option_chosen == 3) {
						System.out.println("Total Rewards : " + clist.get(cust_num_chosen - 1).get_cust_account().get_customer_reward_points());
					}
					else if(cust_option_chosen == 4) {
						for(customer_order c_ord : clist.get(cust_num_chosen - 1).get_customer_recent_order()) {
							c_ord.print_order();
						}
					}
					else if(cust_option_chosen == 5) {
						break;
					}

				}
				
			}
			
			else if(option == 3) {
				System.out.println("1) Customer List");
				System.out.println("2) Restaurant List");
				int user_detail_input = in.nextInt();
				if(user_detail_input == 1) {
					this.display_all_cust();
					int which_cust = in.nextInt();
					print_user_cust_detail(which_cust);
				}
				else if(user_detail_input == 2) {
					this.display_all_rest();
					int which_rest = in.nextInt();
					print_user_rest_detail(which_rest);
				}
				
			}
			else if(option == 4) {
				System.out.println("Total Company Balance : Rs. " +this.get_comp_account().get_revenue_from_restaurant() + "/- ");
				System.out.println("Total Delivery Charges collected : Rs." +  this.get_comp_account().get_delivery_revenue() + "/- ");
			}
			else if(option == 5) {
				break;
			}
			
			
		}
	}
	
	public void print_user_cust_detail(int cus_number) {
		for(int i = 0 ; i < clist.size(); i++) {
			if((i + 1) == cus_number) {
				System.out.print(clist.get(i).get_name());
				if(clist.get(i).get_cust_type().equals("Elite") || clist.get(i).get_cust_type().equals("Special") ) {
					System.out.print("(" + clist.get(i).get_cust_type() + "), ");
				}
				System.out.print(clist.get(i).get_address() + ", ");
				System.out.print(clist.get(i).get_cust_account().get_customer_wallet()+"/-");
			}
		}
		System.out.println();
	}
	public void print_user_rest_detail(int res_number) {
		for(int i = 0 ; i < rlist.size(); i++) {
			if((i + 1) == res_number) {
				System.out.print(rlist.get(i).get_name());
				if(rlist.get(i).get_rest_type().equals("Authentic") || rlist.get(i).get_rest_type().equals("Fast-Food") ) {
					System.out.print("(" + rlist.get(i).get_rest_type() + "), ");
				}
				System.out.print(rlist.get(i).get_address() + ", ");
				System.out.print(rlist.get(i).get_num_orders_taken() + " orders taken");
			}
		}
		System.out.println();
	}
	public void add_item(int x) {
		
		System.out.println("Enter food item details ");
		
		System.out.println("Food Name : ");
		String food_name = in.next();
		
		System.out.println("Item Price :");
		int price = in.nextInt();
		
		System.out.println("Item quantity :");
		int quantity = in.nextInt();
		
		System.out.println("Item Category :");
		String item_category = in.next();
		
		System.out.println("Offer :");
		int item_offer = in.nextInt();	
		
		food_item f = new food_item(food_name,price,quantity,item_category,item_offer);
		f.set_food_rest(rlist.get(x - 1));
		System.out.println(f.get_food_id() + " - " + f.get_food_name() + " - " + f.get_food_price() + " - " + f.get_food_quantity() + " - " + f.get_food_offer() + "% off - " + f.get_food_category());
//		f.print_food_item();
		rlist.get(x - 1).add_item_rest(f);
	}
	
	public void edit_item(int x) {
		
		
		System.out.println("Choose item by code");
		for(int i = 0 ; i < rlist.get(x - 1).get_food_item_list().size(); i++) {
			rlist.get(x - 1).get_food_item_list().get(i).print_food_item();
		}
		int item_to_edit = in.nextInt();
		
		if(rlist.get(x - 1).get_food_item_list().size() == 0) {
			System.out.println("NO ITEM FOUND");
		}
		else {
			System.out.println("Choose an attribute to edit : ");
			System.out.println("1) Name");
			System.out.println("2) Price");
			System.out.println("3) Quantity");
			System.out.println("4) Category");
			System.out.println("5) Offer");
			
			int attribute = in.nextInt();
			for(int i = 0 ; i < rlist.get(x - 1).get_food_item_list().size(); i++) {
				
				if(rlist.get(x - 1).get_food_item_list().get(i).get_food_id() == item_to_edit) {

					if(attribute == 1) {
						System.out.print("Enter new name :");
						String new_name = in.next();
						rlist.get(x - 1).get_food_item_list().get(i).set_food_name(new_name);
					}
					else if(attribute == 2) {
						System.out.print("Enter new price :");
						int new_price = in.nextInt();
						rlist.get(x - 1).get_food_item_list().get(i).set_food_price(new_price);
					}
					else if(attribute == 3) {
						System.out.print("Enter new quantity :");
						int new_qty = in.nextInt();
						rlist.get(x - 1).get_food_item_list().get(i).set_food_quantity(new_qty);
					}
					else if(attribute == 4) {
						System.out.print("Enter new category :");
						String new_cat = in.next();
						rlist.get(x - 1).get_food_item_list().get(i).set_food_category(new_cat);
					}
					else if(attribute == 5) {
						System.out.print("Enter new offer :");
						int new_offer = in.nextInt();
						rlist.get(x - 1).get_food_item_list().get(i).set_food_offer(new_offer);
					}
					rlist.get(x - 1).get_food_item_list().get(i).print_food_item();
				}
			}
			
		}
		
	}

	
	public void display_particular_rest(int rest_id_num) {
		System.out.println("Welcome " + rlist.get(rest_id_num - 1).get_name());
		System.out.println("1) Add item");
		System.out.println("2) Edit item");
		System.out.println("3) Print Rewards");
		System.out.println("4) Discount on bill value");
		System.out.println("5) Exit");

	}
	
	public void display_particular_cust(int cust_num) {

		System.out.println("Welcome  " + clist.get(cust_num - 1).get_name());
		System.out.println("Customer Menu");
		System.out.println("1) Select Restaurant");
		System.out.println("2) Checkout Cart");
		System.out.println("3) Reward won");
		System.out.println("4) Print recent orders");
		System.out.println("5) Exit");
	}
	

	public company_account get_comp_account() {
		return comp_account;
	}
	
}

class company_account {
	
	private double revenue_from_restaurant;
	private int delivery_revenue;

	public company_account() {

		this.revenue_from_restaurant = 0;
		this.delivery_revenue = 0;
	}

	public double get_revenue_from_restaurant() {
		return revenue_from_restaurant;
	}
	public void set_revenue_from_restaurant(double revenue_from_restaurant) {
		this.revenue_from_restaurant = revenue_from_restaurant;
	}
	public int get_delivery_revenue() {
		return delivery_revenue;
	}
	public void set_delivery_revenue(int delivery_revenue) {
		this.delivery_revenue = delivery_revenue;
	}

}

interface reward_implementer{
	
	public int calculate_reward(double bill_val);
	
}

class User {
	
	private final String name;
	private final String address;
	
	public User(String _name, String _address) {
		this.name = _name;
		this.address = _address;
	}
	public String get_name() {
		return this.name;
	}
	public String get_address() {
		return this.address;
	}
	
}

class restaurant extends User implements reward_implementer{
//	
	private int rest_account_balance;
	private String res_type;
	private int rest_id_num;
	private static int rest_num;
	private int num_orders_taken;
	private ArrayList<food_item> rest_food_list;
	private int rest_reward_points;
	private final Company company_res;
	public restaurant(String inp_name, String inp_address, Company comp) {
		
		super(inp_name,inp_address);
		this.company_res = comp;
//		this.name = inp_name;
//		this.address = inp_address;
		this.rest_account_balance = 0;
		this.res_type = "";
		rest_num++;
		this.rest_id_num = rest_num;
		this.num_orders_taken = 0;
		this.rest_food_list = new ArrayList<food_item>();
		this.rest_reward_points = 0;
	}

	public int calculate_reward(double bill_value) {
		return 5*((int)(bill_value/100));
	}
	public int get_rest_discount() {
		return -1;
	}
	public void set_rest_discount(int d) {
		System.out.println("INVALID QUERY FOR THE RESTAURANT");
	}
	//adds item to a restaurant
	public void add_item_rest(food_item f) {
		this.rest_food_list.add(f);
	}
	
	public void show_reward_points() {
		System.out.println("Reward Points : " + this.rest_reward_points);
	}
	
	public int get_reward_point() {
		return this.rest_reward_points;
	}
	

	public int get_rest_account() {
		return this.rest_account_balance;
	}
	public void set_rest_account(int x) {
		this.rest_account_balance = x;
	}
	public void set_rest_type(String restype) {
		this.res_type = restype;
	}
	public void set_reward_points(int points) {
		this.rest_reward_points = points;
	}
	public String get_rest_type() {
		return this.res_type;
	}
	public int get_res_id_num() {

		return this.rest_id_num;
	}
	public ArrayList<food_item> get_food_item_list(){
		return this.rest_food_list;
	}

	public int get_num_orders_taken() {
		return num_orders_taken;
	}

	public void set_num_orders_taken(int num_orders_taken) {
		this.num_orders_taken = num_orders_taken;
	}

	public Company get_company_res() {
		return company_res;
	}
	
}

class fast_food extends restaurant{

	private int fast_food_rest_discount;
	public fast_food(String inp_name, String address, Company comp) {
		super(inp_name, address,comp);
		set_rest_type("Fast-Food");
		this.fast_food_rest_discount = 0;
	}
	
	@Override
	public int calculate_reward(double bill_value) {
		return 10*((int)(bill_value/150));
	}
	
	@Override
	public int get_rest_discount() {
		return this.fast_food_rest_discount;
	}
	@Override
	public void set_rest_discount(int d) {
		this.fast_food_rest_discount = d;
	}

}

class authentic extends restaurant{
	
	private int authentic_rest_discount;
	public authentic(String inp_name, String address,Company comp) {
		super(inp_name, address,comp);
		set_rest_type("Authentic");
		this.authentic_rest_discount = 0;
	}
	@Override
	public int calculate_reward(double bill_value) {
		return 25*((int)(bill_value/200));
	}
	public int get_rest_discount() {
		return this.authentic_rest_discount;
	}
	@Override
	public void set_rest_discount(int d) {
		this.authentic_rest_discount = d;
	}
}

class customer extends User{
	
	
//	private final String name;
//	private final String address;
	private static int cust_num;
	private int cust_id_num;
	private String cust_type;
	private cart cust_cart;
	private int delivery_charge;
	private int cust_discount;
	private customer_account cust_account;
	private ArrayList<customer_order> customer_recent_order;
	private final Company company;
	
	public customer(String inp_name, String inp_address,Company comp) {
		super(inp_name,inp_address);
		this.set_cust_cart(new cart(this));
		this.company = comp;
		cust_num++;
		this.cust_id_num = cust_num;
		this.cust_type = "";
		this.delivery_charge = 40;
		this.cust_discount = 0;
		this.cust_account = new customer_account();
		this.customer_recent_order = new ArrayList<customer_order>();
	}

	
	public int get_cust_id_num() {
		return this.cust_id_num;
	}
	public void set_cust_type(String cus_type) {
		this.cust_type = cus_type;
	}
	public String get_cust_type() {
		return this.cust_type;
	}
	public int get_delivery_charge() {
		return this.delivery_charge;
	}
	public void set_delivery_charge(int x) {
		this.delivery_charge = x;
	}
	public void set_cust_discount(int x) {
		this.cust_discount = x;
	}
	public int get_cust_discount() {
		return this.cust_discount;
	}
	public customer_account get_cust_account() {
		return this.cust_account;
	}
	public cart get_cust_cart() {
		return cust_cart;
	}
	public void set_cust_cart(cart cust_cart) {
		this.cust_cart = cust_cart;
	}
	public ArrayList<customer_order> get_customer_recent_order() {
		return customer_recent_order;
	}
	public void set_customer_recent_order(ArrayList<customer_order> customer_recent_order) {
		this.customer_recent_order = customer_recent_order;
	}
	public Company get_company_cust() {
		return company;
	}
}

class elite_customer extends customer{
	

	public elite_customer(String inp_name, String inp_address,Company comp) {
		super(inp_name,inp_address,comp);
		set_cust_type("Elite");
		set_delivery_charge(0);
		set_cust_discount(50);
	}

}



class special_customer extends customer{
	
	public special_customer(String inp_name, String inp_address, Company comp) {
		super(inp_name,inp_address,comp);
		set_cust_type("Special");
		set_delivery_charge(20);
		set_cust_discount(25);
	}
	
}


class cart {

	private customer cart_customer;
	private ArrayList<food_item> cart_food_list;
	private ArrayList<Integer> cart_food_quantity;
	private double cart_total_bill_value;
	private int order_number;
	private restaurant currently_ordering_from;
	private int cart_delivery;
	
	
	public cart(customer c) {
		this.set_cart_customer(c);
		this.cart_food_list = new ArrayList<food_item>();
		this.cart_food_quantity = new ArrayList<Integer>();
		this.set_cart_total_bill_value(0);
		this.set_cart_order_number(0);
		this.set_cart_delivery(0);
	}
	
	
	public void add_to_cart(food_item f, int quantity) {
		
		int flag = 0;
		for(int i = 0 ; i < this.cart_food_list.size(); i++) {
			if(f.get_food_id() == this.cart_food_list.get(i).get_food_id()) {
				flag = 1;
				int curval = this.cart_food_quantity.get(i);
				this.cart_food_quantity.set(i, curval + quantity);
			}
		}
		if(flag == 0) {
			this.cart_food_list.add(f);
			this.cart_food_quantity.add(quantity);
		}
		for(food_item fi : f.get_food_rest().get_food_item_list()) {
			if(f.get_food_id() == fi.get_food_id()) {
				int curqty = fi.get_food_quantity();
				fi.set_food_quantity(curqty - quantity);
			}
		}
		double toadd = f.get_food_price() * quantity;
		toadd = (toadd - ((toadd * f.get_food_offer())/100));
		if(f.get_food_rest().get_rest_type().equals("Fast-Food") || f.get_food_rest().get_rest_type().equals("Authentic")) {
			double bill_disc = ((toadd * f.get_food_rest().get_rest_discount())/100);
			toadd -= bill_disc;
		}
		if(f.get_food_rest().get_rest_type().equals("Authentic")) {
			if(toadd > 100) {
				toadd -= 50;
			}
		}
		if(this.cart_customer.get_cust_type().equals("Elite")) {
			if(toadd > 200) {
				toadd -= 50;
			}
			
		}
		else if(this.cart_customer.get_cust_type().contentEquals("Special")){
			if(toadd > 200) {
				toadd -= 25;
			}
			this.cart_delivery = 20;
			toadd += 20;
		}
		else {
			toadd += 40;
			this.cart_delivery = 40;
		}
		
		this.cart_total_bill_value += toadd;
		
	}
	
	public void suffient_checkout_cart() {
		
		int boughtit = 0;
		if(cart_food_quantity.size() != 0) {
			for(int i = 0 ; i < cart_food_quantity.size() ; i++) {
				boughtit += cart_food_quantity.get(i);
			}
		}
		if(cart_food_list.size() == 0 || boughtit == 0) {
			this.cart_delivery = 0;
			this.cart_total_bill_value = 0;
			System.out.println("EMPTY CART");
		}
		else {
			double todeduct = cart_total_bill_value;
			if(todeduct > cart_customer.get_cust_account().get_customer_reward_points()) {
				todeduct -= cart_customer.get_cust_account().get_customer_reward_points();
				cart_customer.get_cust_account().set_customer_reward_point(0);
				double wallet_set_amt = cart_customer.get_cust_account().get_customer_wallet() - todeduct;
				cart_customer.get_cust_account().set_customer_wallet(wallet_set_amt);
				
				int points_to_add = currently_ordering_from.calculate_reward(cart_total_bill_value);

				int res_cur_points = currently_ordering_from.get_reward_point();
				currently_ordering_from.set_reward_points(res_cur_points + points_to_add);
				double cust_cur_points = cart_customer.get_cust_account().get_customer_reward_points();
				cart_customer.get_cust_account().set_customer_reward_point(cust_cur_points + points_to_add);
			}
			else {
				double cur_points_cust = cart_customer.get_cust_account().get_customer_reward_points();
				double cust_rew_points_set_amt = cur_points_cust - todeduct;
				cart_customer.get_cust_account().set_customer_reward_point(cust_rew_points_set_amt);
				
				int points_to_add = currently_ordering_from.calculate_reward(cart_total_bill_value);

				int res_cur_points = currently_ordering_from.get_reward_point();
				currently_ordering_from.set_reward_points(res_cur_points + points_to_add);
				double cust_cur_points = cart_customer.get_cust_account().get_customer_reward_points();
				cart_customer.get_cust_account().set_customer_reward_point(cust_cur_points + points_to_add);
				
			}
			int num_res_order = this.cart_food_list.get(0).get_food_rest().get_num_orders_taken() + 1;
			this.cart_food_list.get(0).get_food_rest().set_num_orders_taken(num_res_order);
			int item_bought = 0;
			for(int i = 0 ; i < cart_food_quantity.size() ; i++) {
				item_bought += cart_food_quantity.get(i);
			}
			
			ArrayList<order_element> elements = new ArrayList<order_element>();
			for(int i = 0 ;i < cart_food_list.size(); i++) {
				elements.add(new order_element(cart_food_list.get(i).get_food_name(), cart_food_quantity.get(i), cart_food_list.get(i).get_food_rest().get_name()));
			}
			customer_order order = new customer_order(elements, cart_total_bill_value, cart_food_list.get(0).get_food_rest().get_name(), this.cart_delivery);
			if(cart_customer.get_customer_recent_order().size() < 10) {
				cart_customer.get_customer_recent_order().add(order);
			}
			else {
				cart_customer.get_customer_recent_order().remove(0);
				cart_customer.get_customer_recent_order().add(order);
			}
			
			int del_comp = cart_customer.get_company_cust().get_comp_account().get_delivery_revenue();
			cart_customer.get_company_cust().get_comp_account().set_delivery_revenue(del_comp + this.cart_delivery);
			double rev_comp = cart_food_list.get(0).get_food_rest().get_company_res().get_comp_account().get_revenue_from_restaurant();
			double togivecomp =  ( ((double)((cart_total_bill_value) * 1)) / ((double)(100)) );
			cart_food_list.get(0).get_food_rest().get_company_res().get_comp_account().set_revenue_from_restaurant(rev_comp + togivecomp);
			
			System.out.println(item_bought + " items succesfully bought for INR " + this.cart_total_bill_value + "/-");
			this.cart_total_bill_value = 0;
			cart_food_list.clear();
			cart_food_quantity.clear();

		}
		
	}
	
	public void delete_item(int food_id_val, int del_quant) {
		for(int i = 0 ; i < cart_food_list.size(); i++) {
			if(cart_food_list.get(i).get_food_id() == food_id_val) {
				int curquant = cart_food_quantity.get(i);
				cart_food_quantity.set(i, curquant - del_quant);
				for(int j = 0 ;j < cart_food_list.get(i).get_food_rest().get_food_item_list().size() ; j++) {
					if(cart_food_list.get(i).get_food_rest().get_food_item_list().get(j).get_food_id() == food_id_val) {
						int current = cart_food_list.get(i).get_food_rest().get_food_item_list().get(j).get_food_quantity();
						cart_food_list.get(i).get_food_rest().get_food_item_list().get(j).set_food_quantity(current + del_quant);
					}
				}
			}
		}

		
		int item_bought = 0;
		for(int i = 0 ; i < cart_food_quantity.size() ; i++) {
			item_bought += cart_food_quantity.get(i);
		}
		
		if(item_bought == 0) {		
			this.cart_total_bill_value -= this.cart_delivery;
			this.cart_delivery = 0;
			this.cart_total_bill_value = 0;
		}
		
	}
	
	public void print_cart_before_checkout() {
		System.out.println("Items in Cart - ");
		for(int i = 0 ; i < cart_food_list.size() ; i++) {
			if(cart_food_quantity.get(i) != 0) {
				System.out.println(cart_food_list.get(i).get_food_id() + "  " + cart_food_list.get(i).get_food_rest().get_name() + " - " + cart_food_list.get(i).get_food_name() + " - " + cart_food_list.get(i).get_food_price() + " - " + cart_food_quantity.get(i) + " - " + cart_food_list.get(i).get_food_offer() + "% off");
			}
		}
		System.out.println("Delivery Charge - "  + this.cart_delivery + "/-");
		System.out.println("Total order value - INR " + this.cart_total_bill_value + "/-");
	}
	
	public double calculate_cart_bill() {
		double bill = 0;
		for(int i = 0 ; i < cart_food_list.size(); i++) {
			if(cart_food_quantity.get(i) > 0) {
				double add_to_bill  = ((((double)(cart_food_list.get(i).get_food_price() * (100 - cart_food_list.get(i).get_food_offer())) / (double)100)) * cart_food_quantity.get(i));
				bill += add_to_bill;
			}
		}
		double temp_bill = bill;

		if(cart_food_list.get(0).get_food_rest().get_rest_type().equals("Fast-Food") || cart_food_list.get(0).get_food_rest().get_rest_type().equals("Authentic")) {
			double bill_disc = ((temp_bill * cart_food_list.get(0).get_food_rest().get_rest_discount())/100);
			temp_bill -= bill_disc;
		}
		if(cart_food_list.get(0).get_food_rest().get_rest_type().equals("Authentic")) {
			if(temp_bill > 100) {
				temp_bill -= 50;
			}
		}
		if(this.cart_customer.get_cust_type().equals("Elite")) {
			if(temp_bill > 200) {
				temp_bill -= 50;
			}
			
		}
		else if(this.cart_customer.get_cust_type().contentEquals("Special")){
			if(temp_bill > 200) {
				temp_bill -= 25;
			}
//			this.cart_delivery += 20;
			temp_bill += 20;
		}
		else {
			temp_bill += 40;
//			this.cart_delivery += 40;
		}
		
		return temp_bill;
		
		
	}
	
	public void print_items() {

		System.out.println("Items in Cart - ");
		for(int i = 0 ; i < cart_food_list.size() ; i++) {
			System.out.println(cart_food_list.get(i).get_food_id() + "  " + cart_food_list.get(i).get_food_rest().get_name() + " - " + cart_food_list.get(i).get_food_name() + " - " + cart_food_list.get(i).get_food_price() + " - " + cart_food_quantity.get(i) + " - " + cart_food_list.get(i).get_food_offer() + "% off");
		}
	}
	
	
	//getter setters 	
	public double get_cart_total_bill_value() {
		return cart_total_bill_value;
	}

	public void set_cart_total_bill_value(double cart_total_bill_value) {
		this.cart_total_bill_value = cart_total_bill_value;
	}

	public customer get_cart_customer() {
		return cart_customer;
	}

	public void set_cart_customer(customer cart_customer) {
		this.cart_customer = cart_customer;
	}

	public int get_cart_order_number() {
		return order_number;
	}

	public void set_cart_order_number(int order_number) {
		this.order_number = order_number;
	}

	public int get_cart_delivery() {
		return cart_delivery;
	}

	public void set_cart_delivery(int cart_delivery) {
		this.cart_delivery = cart_delivery;
	}
	public ArrayList<food_item> get_cart_food_list(){
		return this.cart_food_list;
	}
	public ArrayList<Integer> get_cart_quantity_list(){
		return this.get_cart_quantity_list();
	}

	public restaurant get_currently_ordering_from() {
		return currently_ordering_from;
	}

	public void set_currently_ordering_from(restaurant currently_ordering_from) {
		this.currently_ordering_from = currently_ordering_from;
	}
	
}





class customer_account {
	
	private double customer_wallet;
	private double customer_reward_points;
	
	public customer_account() {
		this.customer_wallet = 1000;
		this.customer_reward_points = 0;
	}
	
	public void set_customer_wallet(double x) {
		this.customer_wallet = x;
	}
	public void set_customer_reward_point(double x) {
		this.customer_reward_points = x;
	}
	public double get_customer_wallet() {
		return this.customer_wallet;
	}
	public double get_customer_reward_points() {
		return this.customer_reward_points;
	}
}

class customer_order {
	private final ArrayList<order_element> order_list;
	private final double order_bill;
	private final String order_restaurant;
	private final int order_delivery;
	
	public customer_order(ArrayList<order_element> inp_list, double inp_bill, String inp_rest, int inp_delivery) {
		this.order_list = inp_list;
		this.order_bill = inp_bill;
		this.order_restaurant = inp_rest;
		this.order_delivery = inp_delivery;
	}
	//"Bought item: “ <item name> ”, quantity: “ <item quantity> ” for Rs “ <item price> ” from
	//Restaurant “ <Restaurant Name> ” and Delivery Charge: “ <amount>
	public void print_order() {
		System.out.println("Bought item with quantity : ");
		for(int i = 0 ; i < order_list.size(); i++) {
			System.out.println(order_list.get(i).get_item_name() + " , " + order_list.get(i).get_item_quantity());
		}
		System.out.print("For Rs. " + order_bill);
		System.out.print("  , From restaurant  : " + order_restaurant);
		System.out.println("  , And delivery charge : " + order_delivery);
	}
	public ArrayList<order_element> getOrder_list() {
		return order_list;
	}

	public double getOrder_bill() {
		return order_bill;
	}

	public String getOrder_restaurant() {
		return order_restaurant;
	}
	public int getOrder_delivery() {
		return order_delivery;
	}


	
}

class order_element {
	
	private String item_name;
	private int item_quantity;
	private String rest_name;
	
	public order_element(String name, int quantity, String resto_name) {
		this.set_item_name(name);
		this.set_item_quantity(quantity);
		this.set_rest_name(resto_name);
	}

	public int get_item_quantity() {
		return item_quantity;
	}

	public void set_item_quantity(int item_quantity) {
		this.item_quantity = item_quantity;
	}

	public String get_rest_name() {
		return rest_name;
	}

	public void set_rest_name(String rest_name) {
		this.rest_name = rest_name;
	}

	public String get_item_name() {
		return item_name;
	}

	public void set_item_name(String item_name) {
		this.item_name = item_name;
	}
}



class food_item{
	private static int food_item_number;
	private final int food_id;
	private String food_name;
	private int food_price;
	private int food_quantity;
	private String food_category;
	private int food_offer;
	private restaurant food_rest;
	
	public food_item(String inp_name, int inp_price, int inp_qty, String inp_category, int inp_offer) {
		food_item_number++;
		this.food_name = inp_name;
		this.food_price = inp_price;
		this.food_quantity = inp_qty;
		this.food_category = inp_category;
		this.food_offer = inp_offer;
		this.food_id = food_item_number;
	}
	

	public void print_food_item() {
		System.out.println(this.food_id + " " + this.food_rest.get_name() + " - " + this.food_name + " - " + this.food_price + " - " + this.food_quantity + " - " + this.food_offer + "% off - " + this.food_category);
	}

	
	public String get_food_name() {
		return this.food_name;
	}
	public int get_food_price() {
		return this.food_price;
	}
	public int get_food_quantity() {
		return this.food_quantity;
	}
	public String get_food_category() {
		return this.food_category;
	}
	public int get_food_offer() {
		return this.food_offer;
	}
	public int get_food_id() {
		return this.food_id;
	}
	
	public void set_food_name(String fname) {
		this.food_name = fname;
	}
	public void set_food_price(int fprice) {
		this.food_price = fprice;
	}
	public void set_food_quantity(int fqty) {
		this.food_quantity = fqty;
	}
	public void set_food_category(String fcat) {
		this.food_category = fcat;
	}
	public void set_food_offer(int foffer) {
		this.food_offer = foffer;
	}
	public void set_food_rest(restaurant r) {
		this.food_rest = r;
	}
	public restaurant get_food_rest() {
		return this.food_rest;
	}
}

