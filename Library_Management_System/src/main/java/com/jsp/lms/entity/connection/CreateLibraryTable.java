package com.jsp.lms.entity.connection;


import java.sql.SQLException;
import java.sql.Statement;

public class CreateLibraryTable {
	CreateLibraryTable(){
		try {
			Statement st=new ConnectToDatabase().connect().createStatement();
			st.execute("create table Library(Lib_id int primary key,Name varchar(100),Location varchar(30),Email varchar(50),PhoneNo bigint,LibrarianName varchar(50));");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new CreateLibraryTable();
	}
}
