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

package test.case07;

import java.util.ArrayList;
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

public class TestCase7_uni_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case7_uni_1.Many"));
				session.delete(DASManager.createCriteria("case7_uni_1.One"));
				return null;
			}
		});
	}

	@Test
	public void testInsert() {
		final DataObject one = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.One");
		one.set("id", "one_id_01");
		one.set("name", "one_name_01");

		final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.Many");
		many1.set("id", "many_id_01");
		many1.set("name", "many_name_01");
		final DataObject many2 = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.Many");
		many2.set("id", "many_id_02");
		many2.set("name", "many_name_02");

		List<DataObject> manys = new ArrayList<DataObject>();
		manys.add(many1);
		manys.add(many2);
		one.set("many", manys);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(many1);
				session.insertEntity(many2);
				session.insertEntity(one);
				return null;
			}
		});

		DataObject queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryOne = session.query(DASManager.createCriteria("case7_uni_1.One")).get(0);
				return queryOne;
			}
		});
		// NOTE 脱离session后的延迟加载对象必须从原有List复制一遍, 以避免出现SessionClosed的异常
		int size = queryOne.getList("many").size();
		Assert.assertEquals(0, size);
		detachedSession(queryOne);
		System.out.println(queryOne);

		queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryOne = session.query(DASManager.createCriteria("case7_uni_1.One")).get(0);
				queryOne.get("many[1]/id"); // NOTE 触发延迟加载
				return queryOne;
			}
		});
		size = queryOne.getList("many").size();
		Assert.assertEquals(2, size);
		Assert.assertEquals(true, queryOne.get("many[1]/name") != null);
		Assert.assertEquals(true, queryOne.get("many[2]/name") != null);

		Assert.assertEquals("one_name_01", queryOne.get("name"));
	}

	@Test
	public void testUpdate() {
		final DataObject one = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.One");
		one.set("id", "one_id_01");
		one.set("name", "one_name_01");

		final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.Many");
		many1.set("id", "many_id_01");
		many1.set("name", "many_name_01");
		final DataObject many2 = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.Many");
		many2.set("id", "many_id_02");
		many2.set("name", "many_name_02");

		List<DataObject> manys = new ArrayList<DataObject>();
		manys.add(many1);
		manys.add(many2);
		one.set("many", manys);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(many1);
				session.insertEntity(many2);
				session.insertEntity(one);
				return null;
			}
		});

		one.set("name", "one_name_01_update");
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.updateEntity(one);
				return null;
			}
		});

		DataObject queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryOne = session.query(DASManager.createCriteria("case7_uni_1.One")).get(0);
				return queryOne;
			}
		});
		int size = queryOne.getList("many").size();
		Assert.assertEquals(0, size);

		queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryOne = session.query(DASManager.createCriteria("case7_uni_1.One")).get(0);
				queryOne.get("many[1]/id");
				return queryOne;
			}
		});
		size = queryOne.getList("many").size();
		Assert.assertEquals(2, size);
		Assert.assertEquals(true, queryOne.get("many[1]/name") != null);
		Assert.assertEquals(true, queryOne.get("many[2]/name") != null);
		Assert.assertEquals("one_name_01_update", queryOne.get("name"));
	}

	@Test
	public void testDelete() {
		final DataObject one = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.One");
		one.set("id", "one_id_01");
		one.set("name", "one_name_01");

		final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.Many");
		many1.set("id", "many_id_01");
		many1.set("name", "many_name_01");
		final DataObject many2 = ExtendedDataFactory.eINSTANCE.create("case7_uni_1.Many");
		many2.set("id", "many_id_02");
		many2.set("name", "many_name_02");

		List<DataObject> manys = new ArrayList<DataObject>();
		manys.add(many1);
		manys.add(many2);
		one.set("many", manys);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(many1);
				session.insertEntity(many2);
				session.insertEntity(one);
				return null;
			}
		});

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.deleteEntity(many1);
				session.deleteEntity(many2);
				session.deleteEntity(one);
				return null;
			}
		});

		int count = (int) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.count(DASManager.createCriteria("case7_uni_1.One"));
			}
		});
		Assert.assertEquals(0, count);

		count = (int) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.count(DASManager.createCriteria("case7_uni_1.Many"));
			}
		});
		Assert.assertEquals(0, count);
	}

}

/*
 * 修改历史
 * $Log$ 
 */