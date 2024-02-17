package com.jsp.lms.entity.dao;

import com.jsp.lms.entity.model.Book;
import com.jsp.lms.entity.model.Library;

public interface Dao {
	public boolean addLibrary(Library lib);
	public boolean addBook(Book book);
	public boolean isLibraryIdPresent(int lib_id);
	public int generateLibraryID();
	int generateBookID();
}
