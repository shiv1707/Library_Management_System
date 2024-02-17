package com.jsp.lms.entity.connection;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateBookTable {
	CreateBookTable(){
		try {
			Statement st=new ConnectToDatabase().connect().createStatement();
			st.execute("create table Book(BookID int primary key,Title varchar(50) not null,Author varchar(50),Price numeric(8,2),PublishedDate date,IssuedDate date,Status varchar(20),Lib_id int,Foreign Key(Lib_id) references Library(Lib_id));");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public static void main(String[] args) {
		new CreateBookTable();
	}
}
