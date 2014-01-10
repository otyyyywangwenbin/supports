/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2012 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2013年12月19日
 *******************************************************************************/

package test;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;

import com.eos.common.connection.ConnectionHelper;
import com.eos.common.transaction.ITransactionManager;
import com.eos.common.transaction.TransactionManagerFactory;
import com.eos.das.entity.DASManager;
import com.eos.das.entity.IDASSession;
import com.primeton.das.entity.impl.hibernate.collection.AbstractPersistentCollection;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * TODO 此处填写 class 信息
 * 
 * @author yourname (mailto:yourname@primeton.com)
 */

public class AbstractTestCase {
	@BeforeClass
	public static void abstractBeforeClass() {
		Initer.init();
	}

	@Before
	public void abstractBefore() {
		System.out.println("Abstract before...");
		File caseDir = new File(getClass().getResource("/" + getClass().getName().replace('.', '/') + ".class").getFile()).getParentFile();
		System.out.println(caseDir.getAbsolutePath());
		Loader.loadModelFiles(caseDir);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void detachedSession(DataObject dataObject) {
		List<Property> properties = dataObject.getInstanceProperties();
		for (Property property : properties) {
			Object object = dataObject.get(property);
			if (object instanceof AbstractPersistentCollection) {
				detachedSession(dataObject, property.getName(), (List) object);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void detachedSession(DataObject dataObject, String propertyName,
			List oldList) {
		List newList = new ArrayList();
		for (int i = 0, size = oldList.size(); i < size; i++) {
			Object object = oldList.get(i);
			if (object instanceof DataObject) {
				detachedSession((DataObject) object);
			}
			newList.add(object);
		}
		dataObject.set(propertyName, newList);
	}

	// ///////////
	protected static interface IDASSessionCallback {
		Object doInSession(IDASSession session) throws Throwable;
	}

	@SuppressWarnings("deprecation")
	protected Object execute(IDASSessionCallback callback) {
		Object ret = null;
		if (callback == null) {
			return ret;
		}
		Connection conn = null;
		IDASSession session = null;

		ITransactionManager tx = TransactionManagerFactory.getTransactionManager();
		try {
			tx.begin();
			conn = ConnectionHelper.getConnection();
			session = DASManager.createDasSession(conn);
			ret = callback.doInSession(session);
			tx.commit();
		} catch (Throwable e) {
			tx.rollback();
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new RuntimeException(e);
		} finally {
			if (session != null) {
				session.close();
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

}

/*
 * 修改历史
 * $Log$ 
 */