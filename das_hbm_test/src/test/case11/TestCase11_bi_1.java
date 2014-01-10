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

package test.case11;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.AbstractTestCase;

import com.eos.das.entity.DASManager;
import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.IDASSession;
import com.primeton.ext.data.sdo.helper.ExtendedDataFactory;

import commonj.sdo.DataObject;

/**
 * TODO 此处填写 class 信息
 * 
 * @author yourname (mailto:yourname@primeton.com)
 */

public class TestCase11_bi_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case11_bi_1.Many"));
				session.delete(DASManager.createCriteria("case11_bi_1.One"));
				return null;
			}
		});
	}

	@Test
	public void test() {
		final DataObject one = ExtendedDataFactory.eINSTANCE.create("case11_bi_1.One");
		one.set("id", "one_id_01");
		one.set("name", "one_name_01");

		final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case11_bi_1.Many");
		many1.set("id", "many_id_01");
		many1.set("name", "many_name_01");
		final DataObject many2 = ExtendedDataFactory.eINSTANCE.create("case11_bi_1.Many");
		many2.set("id", "many_id_02");
		many2.set("name", "many_name_02");

		List<DataObject> manys = new ArrayList<DataObject>();
		many1.set("one", one);
		manys.add(many1);
		many2.set("one", one);
		manys.add(many2);
		one.set("many", manys);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one);
				session.insertEntity(many1);
				session.insertEntity(many2);
				return null;
			}
		});

		DataObject queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryOne = session.query(DASManager.createCriteria("case11_bi_1.One")).get(0);
				queryOne.get("many[1]/id"); // NOTE 触发延迟加载
				return queryOne;
			}
		});
		int size = queryOne.getList("many").size();
		Assert.assertEquals(2, size);
		Assert.assertEquals(true, queryOne.get("many[1]/name") != null);
		Assert.assertEquals(true, queryOne.get("many[2]/name") != null);
		Assert.assertEquals("one_name_01", queryOne.get("name"));

		final DataObject one2 = ExtendedDataFactory.eINSTANCE.create("case11_bi_1.One");
		one2.set("id", "one_id_02");
		one2.set("name", "one_name_02");
		many2.set("one", one2);
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one2);
				session.updateEntity(many2);
				return null;
			}
		});
		
		queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				IDASCriteria criteria = DASManager.createCriteria("case11_bi_1.One");
				criteria.add(ExpressionHelper.eq("id", "one_id_01"));
				DataObject queryOne = session.query(criteria).get(0);
				queryOne.get("many[1]/id"); // NOTE 触发延迟加载
				return queryOne;
			}
		});
		size = queryOne.getList("many").size();
		Assert.assertEquals(1, size);
		Assert.assertEquals(true, queryOne.getString("many[1]/name").equals("many_name_01"));
		Assert.assertEquals("one_name_01", queryOne.get("name"));
		
		queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				IDASCriteria criteria = DASManager.createCriteria("case11_bi_1.One");
				criteria.add(ExpressionHelper.eq("id", "one_id_02"));
				DataObject queryOne = session.query(criteria).get(0);
				queryOne.get("many[1]/id"); // NOTE 触发延迟加载
				return queryOne;
			}
		});
		size = queryOne.getList("many").size();
		Assert.assertEquals(1, size);
		Assert.assertEquals(true, queryOne.getString("many[1]/name").equals("many_name_02"));
		Assert.assertEquals("one_name_02", queryOne.get("name"));
		
	}
}

/*
 * 修改历史
 * $Log$ 
 */