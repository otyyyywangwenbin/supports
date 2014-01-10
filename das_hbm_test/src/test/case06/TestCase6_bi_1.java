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

package test.case06;

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

public class TestCase6_bi_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case6_bi_1.One1"));
				session.delete(DASManager.createCriteria("case6_bi_1.One2"));
				return null;
			}
		});
	}

	@Test
	public void testInsert() {
		final DataObject one2 = ExtendedDataFactory.eINSTANCE.create("case6_bi_1.One2");
		one2.set("id", "one2_id_01");
		one2.set("name", "one2_name_01");
		final DataObject one1 = ExtendedDataFactory.eINSTANCE.create("case6_bi_1.One1");
		one1.set("id", "one1_id_01");
		one1.set("name", "one1_name_01");
		one1.set("one2", one2);
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one2);
				session.insertEntity(one1);
				return null;
			}
		});

		final DataObject queryRet = ExtendedDataFactory.eINSTANCE.create("case6_bi_1.One1");
		queryRet.set("id", "one1_id_01");
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.expandEntity(queryRet);
				session.expandRelation(queryRet, "one2");
				return null;
			}
		});
		Assert.assertEquals((String) one1.get("name"), (String) queryRet.get("name"));
		Assert.assertEquals((String) one2.get("id"), (String) queryRet.get("one2/id"));
		// 注意 queryRet/one2/one1 != queryRet，只能做到有值
		Assert.assertEquals(true, queryRet.get("one2/one1") != null);

		
		DataObject queryRet2 = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.query(DASManager.createCriteria("case6_bi_1.One2")).get(0);
			}
		});
		Assert.assertEquals(true, queryRet2.get("one1/one2") == queryRet2);
	}

	@Test
	public void testUpdate() {
		final DataObject one2 = ExtendedDataFactory.eINSTANCE.create("case6_bi_1.One2");
		one2.set("id", "one2_id_01");
		one2.set("name", "one2_name_01");
		final DataObject one1 = ExtendedDataFactory.eINSTANCE.create("case6_bi_1.One1");
		one1.set("id", "one1_id_01");
		one1.set("name", "one1_name_01");
		one1.set("one2", one2);
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one2);
				session.insertEntity(one1);
				return null;
			}
		});

		one1.set("name", "one1_name_01_update");
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.updateEntity(one1);
				return null;
			}
		});

		final DataObject queryRet = ExtendedDataFactory.eINSTANCE.create("case6_bi_1.One1");
		queryRet.set("id", "one1_id_01");
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.expandEntity(queryRet);
				return null;
			}
		});
		Assert.assertEquals((String) one1.get("name"), (String) queryRet.get("name"));
		Assert.assertEquals((String) one2.get("id"), (String) queryRet.get("one2/id"));
	}

	@Test
	public void testDelete() {
		final DataObject one2 = ExtendedDataFactory.eINSTANCE.create("case6_bi_1.One2");
		one2.set("id", "one2_id_01");
		one2.set("name", "one2_name_01");
		final DataObject one1 = ExtendedDataFactory.eINSTANCE.create("case6_bi_1.One1");
		one1.set("id", "one1_id_01");
		one1.set("name", "one1_name_01");
		one1.set("one2", one2);
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one2);
				session.insertEntity(one1);
				return null;
			}
		});

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.deleteEntity(one2);
				session.deleteEntity(one1);
				return null;
			}
		});

		int count = (Integer) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.count(DASManager.createCriteria("case6_bi_1.One1"));
			}
		});
		Assert.assertEquals(0, count);
	}
}

/*
 * 修改历史
 * $Log$ 
 */