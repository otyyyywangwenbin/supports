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

package test.case09;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.AbstractTestCase;

import com.eos.das.entity.DASManager;
import com.eos.das.entity.IDASSession;
import com.primeton.ext.data.sdo.helper.ExtendedDataFactory;

import commonj.sdo.DataObject;

/**
 * TODO 此处填写 class 信息
 * 
 * @author yourname (mailto:yourname@primeton.com)
 */

public class TestCase9_uni_2 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case9_uni_2.One1"));
				session.delete(DASManager.createCriteria("case9_uni_2.One2"));
				return null;
			}
		});
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testDelete() {
		final DataObject one2 = ExtendedDataFactory.eINSTANCE.create("case9_uni_2.One2");
		one2.set("id", "one2_id_01");
		one2.set("name", "one2_name_01");
		final DataObject one1 = ExtendedDataFactory.eINSTANCE.create("case9_uni_2.One1");
		// one1.set("id", "one1_id_01");
		one1.set("name", "one1_name_01");
		one1.set("one2", one2);
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one2);
				session.insertEntity(one1);
				return null;
			}
		});

		final DataObject queryOne = ExtendedDataFactory.eINSTANCE.create("case9_uni_2.One1");
		queryOne.set("id", "one2_id_01");
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.expandEntity(queryOne);
				return null;
			}
		});
		Assert.assertEquals((String) one1.get("name"), (String) queryOne.get("name"));
		Assert.assertEquals((String) one2.get("id"), (String) queryOne.get("one2/id"));
		
		
		
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.deleteEntity(queryOne);
				return null;
			}
		});
	
		
		
		List<DataObject> queryRet = (List<DataObject>) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.query(DASManager.createCriteria("case9_uni_2.One2"));
			}
		});
		Assert.assertEquals(0, queryRet.size());
	}
}

/*
 * 修改历史
 * $Log$ 
 */