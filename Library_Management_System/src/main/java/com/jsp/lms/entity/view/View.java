package com.jsp.lms.entity.view;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

import com.jsp.lms.entity.dao.Dao;
import com.jsp.lms.entity.dao.DaoImplementation;
import com.jsp.lms.entity.model.Book;
import com.jsp.lms.entity.model.Library;
import com.jsp.lms.entity.model.Status;

public class View {
	static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) {
		
		Dao lms=new DaoImplementation();
		System.out.println("WELCOME TO LIBRARY MANAGEMENT SYSTEM");
		boolean stop=true;
		while(stop) {
			System.out.println("Choose \n1. For Enter Library Details \n2. For Keeping Book in Library \n3. Find Book by book id \n4. Find Books by library id \n5. Find Books by Author Name \n6. Update Details");
			int choose=sc.nextInt();
			switch(choose) {
			case 1:{
				Library lib=new Library();
				System.out.println("Enter Name of Library");
				sc.nextLine();
				lib.setName(sc.nextLine().toUpperCase());
				System.out.println("Enter Location of Library");
				lib.setLocation(sc.nextLine().toUpperCase());
				System.out.println("Enter Email ID of Library");
				lib.setEmail(sc.nextLine());
				System.out.println("Enter Phone Number");
				lib.setPhoneNo(sc.nextLong());
				System.out.println("Enter Librarian Name");
				sc.nextLine();
				lib.setLibrarianName(sc.nextLine().toUpperCase());
				lib.setLibId(lms.generateLibraryID());
				if(lms.addLibrary(lib)) {
					System.out.println("Library Added Successfully");
				}
				break;
			}
			case 2:{
				System.out.println("How many Book you want to keep");
				int count=sc.nextInt();
				while(count>0) {
					System.out.println("In which Library ID you want to keep book");
					int id=sc.nextInt();
					if(lms.isLibraryIdPresent(id)) {
						Book book=new Book();
						
						book.setBookId(lms.generateBookID());
						System.out.println("Enter Title of Book");
						sc.nextLine();
						book.setTitle(sc.nextLine().toUpperCase());
						System.out.println("Enter Author Name");
						book.setAuthor(sc.nextLine().toUpperCase());
						System.out.println("Enter Price");
						book.setPrice(sc.nextDouble());
						System.out.println("Enter Published Date (yyyy-mm-dd)");
						sc.nextLine();
						book.setPublishedDate(LocalDate.parse(sc.nextLine()));
						System.out.println("Enter Issued Date (yyyy-mm-dd)");
						book.setIssuedDate(LocalDate.parse(sc.nextLine()));
						System.out.println("Choose the Status \n1. LOST \n2. ISSUED \n3. AVAILABLE");
						int status=sc.nextInt();
						switch(status) {
						case 1: {
							book.setStatus(Status.LOST);
							break;
						}
						case 2:{
							book.setStatus(Status.ISSUED);
							break;
						}
						case 3:{
							book.setStatus(Status.AVAILABLE);
							break;
						}
						default:break;
						}
						
						book.setLibId(id);
						if(lms.addBook(book)) {
							System.out.println("Book Added Successfully");
						}
						count--;
					}else {
						System.out.println("This Library has not build yet, Select Another");
					}
				}
				break;
			}
			case 3:{
				System.out.println("Enter Book ID");
				int bookId=sc.nextInt();
				if(lms.getBookByID(bookId)==null) {
					System.out.println("No Book Available on this book-id");
				}else {
					System.out.println(bookId+" ---- "+lms.getBookByID(bookId));
				}
				break;
			}
			case 4:{
				System.out.println("Enter Library ID");
				int libraryid=sc.nextInt();
				LinkedList<Book> book=lms.getBookByLibId(libraryid);
				int count=1;
				if(book.size()>0) {
					for(Book e:book) {
						System.out.println("For Book "+count+"----------------->");
						System.out.println(e);
						System.out.println("------------------------------------------------");
						count++;
					}
				}else {
					System.out.println("No Books present at this library");
				}
				break;
			}
			case 5:{
				System.out.println("Enter Author Name");
				sc.nextLine();
				String author=sc.nextLine().toUpperCase();
				LinkedList<Book> book=lms.getBookByAuthor(author);
				int count=1;
				if(book.size()>0) {
					for(Book e:book) {
						System.out.println("For Book "+count+"----------------->");
						System.out.println(e);
						System.out.println("------------------------------------------------");
						count++;
					}	
				}else {
					System.out.println("No Books written by this Author");
				}
				break;
				
			}
			case 6:{
				System.out.println("Where you want to update, Choose \n1. For Library \n2. For Book");
				int choice2=sc.nextInt();
				switch(choice2) {
				case 1:{
					System.out.println("Which Library ID you want to update");
					int choice3=sc.nextInt();
					if(lms.isLibraryIdPresent(choice3)) {
						System.out.println("In Library What you want to change? \n1. Name of Library \n2. Location of Library \n3. Email Id of Library \n4. Phone number of library \n5. Librarian Name of library");
						int choice4=sc.nextInt();
						if(lms.updateLibraryTable(choice3, choice4)>0) {
							System.out.println("Updated Successfully");
						}
					}else {
						System.out.println("No Library present at this library id");
					}
					break;
				}
				case 2:{
					System.out.println("Which book ID you want to update?");
					int choice3=sc.nextInt();
					if(lms.isBookIdPresent(choice3)) {
						System.out.println("In Book What you want to change? \n1. Title \n2. Author \n3. Price \n4. Published Date \n5. Issued Date \n6. Status \n7. Library ID");
						int choice4=sc.nextInt();
						if(lms.updateBookTable(choice3, choice4)>0) {
							System.out.println("Updated Successfully");
						}
					}else {
						System.out.println("No Book present at this book id");
					}
					break;
				}
				default:{
					break;
				}
				}
			}
			default: break;
			}
			
			System.out.println("Do you want to continue yes or no");
			if(sc.next().equals("no")) {
				stop=false;
			}
		}
	}
}
