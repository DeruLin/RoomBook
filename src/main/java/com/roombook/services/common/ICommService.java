package com.roombook.services.common;


import org.hibernate.Session;

public interface ICommService {
	
	public void beginTransaction();

	public void commit();

	public void rollBack();
	
}
