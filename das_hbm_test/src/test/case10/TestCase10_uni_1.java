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

package test.case10;

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

public class TestCase10_uni_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case10_uni_1.Many"));
				session.delete(DASManager.createCriteria("case10_uni_1.One"));
				return null;
			}
		});
	}

	@Test
	public void testInsert() {
		final DataObject one = ExtendedDataFactory.eINSTANCE.create("case10_uni_1.One");
		one.set("id", "one_id_01");
		one.set("name", "one_name_01");

		final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case10_uni_1.Many");
		many1.set("id", "many_id_01");
		many1.set("name", "many_name_01");
		final DataObject many2 = ExtendedDataFactory.eINSTANCE.create("case10_uni_1.Many");
		many2.set("id", "many_id_02");
		many2.set("name", "many_name_02");

		List<DataObject> manys = new ArrayList<DataObject>();
		manys.add(many1);
		manys.add(many2);
		one.set("many", manys);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one);
				return null;
			}
		});

		DataObject queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryOne = session.query(DASManager.createCriteria("case10_uni_1.One")).get(0);
				queryOne.get("many[1]/id"); // NOTE 触发延迟加载
				return queryOne;
			}
		});
		int size = queryOne.getList("many").size();
		Assert.assertEquals(2, size);
		Assert.assertEquals(true, queryOne.get("many[1]/name") != null);
		Assert.assertEquals(true, queryOne.get("many[2]/name") != null);
		Assert.assertEquals("one_name_01", queryOne.get("name"));
	}

	@Test
	public void testUpdate() {
		final DataObject one = ExtendedDataFactory.eINSTANCE.create("case10_uni_1.One");
		one.set("id", "one_id_01");
		one.set("name", "one_name_01");

		final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case10_uni_1.Many");
		many1.set("id", "many_id_01");
		many1.set("name", "many_name_01");
		final DataObject many2 = ExtendedDataFactory.eINSTANCE.create("case10_uni_1.Many");
		many2.set("id", "many_id_02");
		many2.set("name", "many_name_02");

		List<DataObject> manys = new ArrayList<DataObject>();
		manys.add(many1);
		manys.add(many2);
		one.set("many", manys);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one);
				return null;
			}
		});

		one.set("name", "one_name_01_u");
		many1.set("name", "many_name_01_u");
		many2.set("name", "many_name_02_u");

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.updateEntity(one);
				return null;
			}
		});
		
		DataObject queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryOne = session.query(DASManager.createCriteria("case10_uni_1.One")).get(0);
				queryOne.get("many[1]/id"); // NOTE 触发延迟加载
				return queryOne;
			}
		});
		int size = queryOne.getList("many").size();
		Assert.assertEquals(2, size);
		Assert.assertEquals(true, queryOne.getString("many[1]/name").endsWith("_u"));
		Assert.assertEquals(true, queryOne.getString("many[2]/name").endsWith("_u"));
		Assert.assertEquals("one_name_01_u", queryOne.get("name"));
	}
}

/*
 * 修改历史
 * $Log$ 
 */