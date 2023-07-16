package com.gl.RoleBasedAuthorizationCode.service;

import java.util.List;

import com.gl.RoleBasedAuthorizationCode.model.Book;

public interface LibraryService {

	public List<Book> findAll();

	public Book findById(int theId);

	public void save(Book theBook);

	public void deleteById(int theId);

	public List<Book> searchBy(String name, String author);

}

