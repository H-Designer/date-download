package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.InitialBean;
import Util.DBUtil;

/**
 * @author Cheyanlie
 * 2019年2月12日
 * 下午6:19:22
 * 写入一条数据
 */
public class InsertDao {
	public void insert(InitialBean initialBean) {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "insert into house (address1,address2,address3,area,toward,bedroom,livingroom,bathroom,subway_house,decoration,two_bathroom,is_key,central_heating,rent_period_month,is_new,deposit_1_pay_1,price) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,initialBean.getAddress1());
			preparedStatement.setString(2,initialBean.getAddress2());
			preparedStatement.setString(3,initialBean.getAddress3());
			preparedStatement.setInt(4,initialBean.getArea());
			preparedStatement.setString(5,initialBean.getToward());
			preparedStatement.setInt(6,initialBean.getBedroom());
			preparedStatement.setInt(7,initialBean.getLivingroom());
			preparedStatement.setInt(8,initialBean.getBathroom());
			preparedStatement.setString(9,initialBean.getSubway_house());
			preparedStatement.setString(10,initialBean.getDecoration());
			preparedStatement.setString(11,initialBean.getTwo_bathroom());
			preparedStatement.setString(12,initialBean.getIs_key());
			preparedStatement.setString(13,initialBean.getCentral_heating());
			preparedStatement.setString(14,initialBean.getRent_period_month());
			preparedStatement.setString(15,initialBean.getIs_new());
			preparedStatement.setString(16,initialBean.getDeposit_1_pay_1());
			preparedStatement.setInt(17,initialBean.getPrice());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			connection.close();
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 
	}
}
