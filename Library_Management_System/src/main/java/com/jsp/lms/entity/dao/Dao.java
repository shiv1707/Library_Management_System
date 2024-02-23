package com.jsp.lms.entity.dao;

import java.util.LinkedList;
import java.util.List;

import com.jsp.lms.entity.model.Book;
import com.jsp.lms.entity.model.Library;

public interface Dao {
	public boolean addLibrary(Library lib);
	public boolean addBook(Book book);
	public boolean isLibraryIdPresent(int lib_id);
	public boolean isBookIdPresent(int bookId);
	public int generateLibraryID();
	int generateBookID();
	String getBookByID(int bookId);
	LinkedList<Book> getBookByLibId(int lib_id);
	LinkedList<Book> getBookByAuthor(String AuthorName);
	int updateLibraryTable(int libID,int choice);
	int updateBookTable(int bookID,int choice);
}
