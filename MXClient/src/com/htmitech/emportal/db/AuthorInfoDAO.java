package com.htmitech.emportal.db;

import com.htmitech.emportal.entity.AuthorInfo;

public interface AuthorInfoDAO {
	
	public boolean insertAllAuthor(AuthorInfo[] authorArr);
	
	public AuthorInfo[] getAuthorForOaSelect();
	
	public AuthorInfo[] getAllAuthor();
	
}
