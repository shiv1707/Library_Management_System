package com.jsp.lms.entity.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jsp.lms.entity.connection.ConnectToDatabase;
import com.jsp.lms.entity.model.Book;
import com.jsp.lms.entity.model.Library;

public class DaoImplementation implements Dao {

	@Override
	public boolean addLibrary(Library library) {
		// TODO Auto-generated method stub
		
		Connection con=new ConnectToDatabase().connect();
		try {
			PreparedStatement ps=con.prepareStatement("insert into library values(?,?,?,?,?,?);");
			ps.setInt(1, library.getLibId());
			ps.setString(2, library.getName());
			ps.setString(3, library.getLocation());
			ps.setString(4, library.getEmail());
			ps.setLong(5, library.getPhoneNo());
			ps.setString(6, library.getLibrarianName());
			int count=ps.executeUpdate();
			if(count>0) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addBook(Book book) {
		// TODO Auto-generated method stub
		
		Connection con=new ConnectToDatabase().connect();
		try {
			PreparedStatement ps=con.prepareStatement("insert into book values(?,?,?,?,?,?,?,?);");
			ps.setInt(1, book.getBookId());
			ps.setString(2, book.getTitle());
			ps.setString(3, book.getAuthor());
			ps.setDouble(4,book.getPrice());
			ps.setObject(5,Date.valueOf(book.getPublishedDate()));
			ps.setObject(6,Date.valueOf(book.getIssuedDate()));
			ps.setString(7, book.getStatus().toString());
			ps.setInt(8,book.getLibId());
			int count=ps.executeUpdate();
			if(count>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isLibraryIdPresent(int lib_id) {
		// TODO Auto-generated method stub
		Connection con=new ConnectToDatabase().connect();
		try {
			Statement st=con.createStatement();
			st.execute("select lib_id from library;");
			ResultSet rs=st.getResultSet();
			while(rs.next()) {
				if(lib_id==rs.getInt(1)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int generateLibraryID() {
		// TODO Auto-generated method stub
		int id=1;
		try {
			Statement st=new ConnectToDatabase().connect().createStatement();
			ResultSet rs=st.executeQuery("Select MAX(lib_id) from library ");
			
			if(rs.next()) {
				id=rs.getInt(1)+1;
				}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}

	@Override
	public int generateBookID() {
		// TODO Auto-generated method stub
		int id=1;
		try {
			Statement st=new ConnectToDatabase().connect().createStatement();
			st.execute("select MAX(lib_id) from book;");
			ResultSet rs=st.getResultSet();
			if(rs.next()) {
				id=rs.getInt(1)+1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
}
