package com.jsp.lms.entity.model;

import java.time.LocalDate;

public class Book {
	private int bookId;
	private String title;
	private String author;
	private double price;
	private LocalDate publishedDate;
	private LocalDate issuedDate;
	private Status status;
	private int libId;
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public LocalDate getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}
	public LocalDate getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(LocalDate issuedDate) {
		this.issuedDate = issuedDate;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getLibId() {
		return libId;
	}
	public void setLibId(int libId) {
		this.libId = libId;
	}
	public String toString() {
		String s="Bookid = "+bookId+"\nTitle = "+title+"\nAuthor Name = "+author+"\nPrice = "+price+"\nPublished Date = "+publishedDate+"\nIssued Date = "+issuedDate+"\nStatus = "+status+"\nLibrary ID = "+libId;
		return s;
	}
	
}
