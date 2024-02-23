package com.jsp.lms.entity.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.jsp.lms.entity.connection.ConnectToDatabase;
import com.jsp.lms.entity.model.Book;
import com.jsp.lms.entity.model.Library;
import com.jsp.lms.entity.model.Status;

public class DaoImplementation implements Dao {
	static Scanner sc=new Scanner(System.in);
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

	@Override
	public int updateLibraryTable(int libID, int choice) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			boolean flagChoice=true;
			Statement st=new ConnectToDatabase().connect().createStatement();
			while(flagChoice) {
				switch(choice) {
				case 1:{
					System.out.println("What is your new library name?");
					count=st.executeUpdate("update library set name='"+sc.nextLine()+"' where lib_id="+libID);
					flagChoice=false;
					break;
				}
				case 2:{
					System.out.println("What is your new Location Name for library?");
					count=st.executeUpdate("update library set location='"+sc.nextLine()+"' where lib_id="+libID );
					flagChoice=false;
					break;
				}
				case 3:{
					System.out.println("What is your new Email ID for library");
					count=st.executeUpdate("update library set email='"+sc.nextLine()+"' where lib_id="+libID);
					flagChoice=false;
					break;
				}
				case 4:{
					System.out.println("What is your new phone number for library");
					count=st.executeUpdate("update library set phoneno="+sc.nextLong()+" where lib_id="+libID);
					flagChoice=false;
					break;
				}
				case 5:{
					System.out.println("What is your new Librarian Name");
					count=st.executeUpdate("update library set librarianname='"+sc.nextLine()+"' where lib_id="+libID);
					flagChoice=false;
					break;
				}
				default:{
					System.out.println("You have entered wrong choice, please choose correct option");
				}
			}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return count;
		
		
	}

	@Override
	public int updateBookTable(int bookID, int choice) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			Statement st=new ConnectToDatabase().connect().createStatement();
			boolean flagChoice=true;
			while(flagChoice) {
				switch(choice) {
				case 1:{
					System.out.println("What Title you want to change?");
					
					count=st.executeUpdate("update book set title='"+sc.nextLine()+"' where bookid="+bookID);
					flagChoice=false;
					break;
				}
				case 2:{
					System.out.println("What Author Name you want?");
					
					count=st.executeUpdate("update book set author='"+sc.nextLine()+"' where bookid="+bookID);
					flagChoice=false;
					break;
				}
				case 3:{
					System.out.println("What is the new price you want");
					count=st.executeUpdate("update book set price="+sc.nextDouble()+" where bookid="+bookID);
					flagChoice=false;
					break;
				}
				case 4:{
					System.out.println("What is the new published Date in yyyy-mm-dd");
					
					count=st.executeUpdate("update book set publisheddate='"+Date.valueOf(LocalDate.parse(sc.nextLine()))+"' where bookid="+bookID);
					flagChoice=false;
					break;
				}
				case 5:{
					System.out.println("What is the new issued Date in yyyy-mm-dd");
				
					count=st.executeUpdate("update book set issueddate='"+Date.valueOf(LocalDate.parse(sc.nextLine()))+"' where bookid="+bookID);
					flagChoice=false;
					break;
				}
				case 6:{
					
					boolean flagStatus=true;
					while(flagStatus) {
						System.out.println("Choose the status you want to change \n1. Available \n2. Issued \n3. Lost");
						switch(sc.nextInt()) {
						case 1:{
							
							count=st.executeUpdate("update book set status='"+Status.AVAILABLE.toString()+"' where bookid="+bookID);
							flagStatus=false;
							flagChoice=false;
							break;
						}case 2:{
							
							count=st.executeUpdate("update book set status='"+Status.ISSUED.toString()+"' where bookid="+bookID);
							flagStatus=false;
							flagChoice=false;
							break;
						}
						case 3:{
							
							count=st.executeUpdate("update book set status='"+Status.LOST.toString()+"' where bookid="+bookID);
							flagStatus=false;
							flagChoice=false;
							break;
						}
						default:{
							System.out.println("Wrong Choice Entered, Please select from below only");
							
							
						}
					}
					}
					
				}
				case 7:{
					System.out.println("Which lib_id now you want to update");
					count=st.executeUpdate("update book set lib_id="+sc.nextInt()+" where bookid="+bookID);
					flagChoice=false;
					break;
				}
				
				default:{
					System.out.println("Wrong Choice Entered, Choose Correct Option");
				}
			}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean isBookIdPresent(int bookId) {
		// TODO Auto-generated method stub
		Connection con=new ConnectToDatabase().connect();
		try {
			Statement st=con.createStatement();
			st.execute("select bookid from book;");
			ResultSet rs=st.getResultSet();
			while(rs.next()) {
				if(bookId==rs.getInt(1)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	
}
