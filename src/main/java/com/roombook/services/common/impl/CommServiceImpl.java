package com.roombook.services.common.impl;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.roombook.daos.BaseDAO;
import com.roombook.services.common.ICommService;

@Service(value="commService")
public class CommServiceImpl implements ICommService {

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	protected BaseDAO baseDAO;


	@Override
	public void beginTransaction() {
		baseDAO.getSession().beginTransaction();
	}

	@Override
	public void commit() {
		baseDAO.getSession().getTransaction().commit();
	}

	@Override
	public void rollBack() {
		baseDAO.getSession().getTransaction().rollback();
	}
}
