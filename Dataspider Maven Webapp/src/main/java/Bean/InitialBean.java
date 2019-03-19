package Bean;

/**
 * @author Cheyanlie
 * 2019年2月9日
 * 下午5:28:26
 * uninitial object
 */
public class InitialBean {
	
	//message
	public String address1;//地址
	public String address2;//地址
	public String address3;//地址
	public int area;//面积
	public String toward;//朝向
	public int bedroom;//卧室数量
	public int livingroom;//客厅数量
	public int bathroom;//卫生间数量
	/**/
	public String subway_house;
	public String decoration;
	public String two_bathroom;//双卫生间
	public String is_key;//随时看房
	public String central_heating;//集中供暖
	public String rent_period_month;//月租
	public String is_new;//上新
	public String deposit_1_pay_1;//押一付一
	public int price;
	
	public InitialBean(String address1, String address2, String address3, int area, String toward,
			int bedroom, int livingroom, int bathromm, String subway_house, String decoration, String two_bathroom,
			String is_key, String central_heating, String rent_period_month, String is_new, String deposit_1_pay_1,
			int price) {
		super();
	
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.area = area;
		this.toward = toward;
		this.bedroom = bedroom;
		this.livingroom = livingroom;
		this.bathroom = bathromm;
		this.subway_house = subway_house;
		this.decoration = decoration;
		this.two_bathroom = two_bathroom;
		this.is_key = is_key;
		this.central_heating = central_heating;
		this.rent_period_month = rent_period_month;
		this.is_new = is_new;
		this.deposit_1_pay_1 = deposit_1_pay_1;
		this.price = price;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getToward() {
		return toward;
	}
	public void setToward(String toward) {
		this.toward = toward;
	}
	public int getBedroom() {
		return bedroom;
	}
	public void setBedroom(int bedroom) {
		this.bedroom = bedroom;
	}
	public int getLivingroom() {
		return livingroom;
	}
	public void setLivingroom(int livingroom) {
		this.livingroom = livingroom;
	}
	public int getBathroom() {
		return bathroom;
	}
	public void setBathroom(int bathroom) {
		this.bathroom = bathroom;
	}
	public String getSubway_house() {
		return subway_house;
	}
	public void setSubway_house(String subway_house) {
		this.subway_house = subway_house;
	}
	public String getDecoration() {
		return decoration;
	}
	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}
	public String getTwo_bathroom() {
		return two_bathroom;
	}
	public void setTwo_bathroom(String two_bathroom) {
		this.two_bathroom = two_bathroom;
	}
	public String getIs_key() {
		return is_key;
	}
	public void setIs_key(String is_key) {
		this.is_key = is_key;
	}
	public String getCentral_heating() {
		return central_heating;
	}
	public void setCentral_heating(String central_heating) {
		this.central_heating = central_heating;
	}
	public String getRent_period_month() {
		return rent_period_month;
	}
	public void setRent_period_month(String rent_period_month) {
		this.rent_period_month = rent_period_month;
	}
	public String getIs_new() {
		return is_new;
	}
	public void setIs_new(String is_new) {
		this.is_new = is_new;
	}
	public String getDeposit_1_pay_1() {
		return deposit_1_pay_1;
	}
	public void setDeposit_1_pay_1(String deposit_1_pay_1) {
		this.deposit_1_pay_1 = deposit_1_pay_1;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}		
	public InitialBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
