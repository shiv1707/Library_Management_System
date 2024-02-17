package com.jsp.lms.entity.view;

import java.time.LocalDate;
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
		System.out.println("Choose \n1.For Enter Library Details \n2.For Keeping Book in Library");
		int choose=sc.nextInt();
		switch(choose) {
		case 1:{
			Library lib=new Library();
			System.out.println("Enter Name of Library");
			sc.nextLine();
			lib.setName(sc.nextLine());
			System.out.println("Enter Location of Library");
			lib.setLocation(sc.nextLine());
			System.out.println("Enter Email ID of Library");
			lib.setEmail(sc.nextLine());
			System.out.println("Enter Phone Number");
			lib.setPhoneNo(sc.nextLong());
			System.out.println("Enter Librarian Name");
			sc.nextLine();
			lib.setLibrarianName(sc.nextLine());
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
					book.setTitle(sc.nextLine());
					System.out.println("Enter Author Name");
					book.setAuthor(sc.nextLine());
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
		default: break;
		}
	}
}
