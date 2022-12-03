package com.erudite.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.erudite.model.UserModel;

public class UserDAO {
	Connection con;
	ResultSet rs;
	Statement st;
	
	private UserModel dataUser;
	
	public UserDAO() {
		
		
		String url="jdbc:mysql://localhost:3306/erudite_presensi";
		String username="root";
		String password="";
		
		try {

			Class.forName("com.mysql.jdbc.Driver"); 
			con=DriverManager.getConnection(url,username,password);
			

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			

		}catch (SQLException e) {
			e.printStackTrace();
			

		}
		
		
		
	}
	
	
	public ResultSet executeQuery (String query) {
		try {
			st=con.createStatement();
			rs=st.executeQuery(query);
		}catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	
	public void registerUser(UserModel User) {
		String query= String.format("INSERT INTO users(name,email,password,role) VALUES ('%s','%s','%s')",User.getName(),User.getEmail(),User.getPassword());
		try {
			st=con.createStatement();
			st.executeUpdate(query);
		}catch (SQLException e) {
			//TODO Aut0-generated catch block
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	
	public ArrayList<UserModel> login(UserModel User) {
		String query = String.format("SELECT * FROM users WHERE email = '%s' AND password = '%s'", User.getEmail(), User.getPassword());
		
		ResultSet sd = this.executeQuery(query);
		
		ArrayList<UserModel> userData = new ArrayList<>();

		try {
			while(sd.next()) {
				UserModel user=new UserModel();
				
				user.setEmail(sd.getString("email"));
				user.setRole(sd.getString("role"));
				user.setName(sd.getString("name"));

				userData.add(user);
			}
			}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userData;
	}
}
