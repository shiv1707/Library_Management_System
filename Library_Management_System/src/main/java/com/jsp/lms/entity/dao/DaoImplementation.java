package com.jsp.lms.entity.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.jsp.lms.entity.connection.ConnectToDatabase;
import com.jsp.lms.entity.model.Book;
import com.jsp.lms.entity.model.Library;
import com.jsp.lms.entity.model.Status;

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
			st.execute("select MAX(bookid) from book;");
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

	@Override
	public String getBookByID(int bookId) {
		// TODO Auto-generated method stub
		String title=null;
		try {
			Connection con=new ConnectToDatabase().connect();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select title from book where bookid="+bookId);
			if(rs.next()) {
				title=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return title;
	}

	@Override
	public LinkedList<Book> getBookByLibId(int lib_id) {
		// TODO Auto-generated method stub
		LinkedList<Book> books=new LinkedList<>();
		try {
			Statement st=new ConnectToDatabase().connect().createStatement();
			ResultSet rs=st.executeQuery("select * from book where lib_id="+lib_id);
			while(rs.next()) {
				Book book=new Book();
				book.setBookId(rs.getInt(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getDouble(4));
				book.setPublishedDate(rs.getDate(5).toLocalDate());
				book.setIssuedDate(rs.getDate(6).toLocalDate());
				String status=rs.getString(7);
				if(status.equals(Status.AVAILABLE.toString())) {
					book.setStatus(Status.AVAILABLE);
				}else if(status.equals(Status.ISSUED.toString())) {
					book.setStatus(Status.ISSUED);
				}else {
					book.setStatus(Status.LOST);
				}
				book.setLibId(lib_id);
				books.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public LinkedList<Book> getBookByAuthor(String AuthorName) {
		// TODO Auto-generated method stub
		LinkedList<Book> books=new LinkedList<>();
		try {
			Statement st=new ConnectToDatabase().connect().createStatement();
			ResultSet rs=st.executeQuery("select * from book where author= '"+AuthorName+"'");
			while(rs.next()) {
				Book book=new Book();
				book.setBookId(rs.getInt(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(AuthorName);
				book.setPrice(rs.getDouble(4));
				book.setPublishedDate(rs.getDate(5).toLocalDate());
				book.setIssuedDate(rs.getDate(6).toLocalDate());
				String status=rs.getString(7);
				if(status.equals(Status.AVAILABLE.toString())) {
					book.setStatus(Status.AVAILABLE);
				}else if(status.equals(Status.ISSUED.toString())) {
					book.setStatus(Status.ISSUED);
				}else {
					book.setStatus(Status.LOST);
				}
				book.setLibId(rs.getInt(8));
				books.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
		
	}
	
	
}
