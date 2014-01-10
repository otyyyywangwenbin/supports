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

package test.case08;

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
public class TestCase8_uni_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				List<DataObject> many1s = session.query(DASManager.createCriteria("case8_uni_1.Many1"));
				for (DataObject many1 : many1s) {
					session.deleteEntity(many1);
				}

				List<DataObject> many2s = session.query(DASManager.createCriteria("case8_uni_1.Many2"));
				for (DataObject many2 : many2s) {
					session.deleteEntity(many2);
				}
				return null;
			}
		});
	}

	@Test
	public void testInsert() {
		final DataObject many1_1 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many1");
		many1_1.set("id", "many1_1");
		many1_1.set("name", "many1_1_name");

		final DataObject many1_2 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many1");
		many1_2.set("id", "many1_2");
		many1_2.set("name", "many1_2_name");

		final DataObject many2_1 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many2");
		many2_1.set("id", "many2_1");
		many2_1.set("name", "many2_1_name");

		final DataObject many2_2 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many2");
		many2_2.set("id", "many2_2");
		many2_2.set("name", "many2_2_name");

		List<DataObject> many2s = new ArrayList<DataObject>();
		many2s.add(many2_1);
		many2s.add(many2_2);
		many1_1.set("many2", many2s);
		many1_2.set("many2", many2s);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(many2_1);
				session.insertEntity(many2_2);
				session.insertEntity(many1_1);
				session.insertEntity(many1_2);
				return null;
			}
		});

		DataObject queryRet = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.query(DASManager.createCriteria("case8_uni_1.Many1")).get(0);
			}
		});
		Assert.assertEquals(0, queryRet.getList("many2").size());
		detachedSession(queryRet);

		queryRet = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryRet = session.query(DASManager.createCriteria("case8_uni_1.Many1")).get(0);
				queryRet.get("many2[1]/id");
				return queryRet;
			}
		});
		Assert.assertEquals(2, queryRet.getList("many2").size());
		detachedSession(queryRet);
		System.out.println(queryRet);

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		final DataObject many1_1 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many1");
		many1_1.set("id", "many1_1");
		many1_1.set("name", "many1_1_name");

		final DataObject many1_2 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many1");
		many1_2.set("id", "many1_2");
		many1_2.set("name", "many1_2_name");

		final DataObject many2_1 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many2");
		many2_1.set("id", "many2_1");
		many2_1.set("name", "many2_1_name");

		final DataObject many2_2 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many2");
		many2_2.set("id", "many2_2");
		many2_2.set("name", "many2_2_name");

		List<DataObject> many2s = new ArrayList<DataObject>();
		many2s.add(many2_1);
		many2s.add(many2_2);
		many1_1.set("many2", many2s);
		many1_2.set("many2", many2s);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(many2_1);
				session.insertEntity(many2_2);
				session.insertEntity(many1_1);
				session.insertEntity(many1_2);
				return null;
			}
		});
		
		final DataObject many2_3 = ExtendedDataFactory.eINSTANCE.create("case8_uni_1.Many2");
		many2_3.set("id", "many2_3");
		many2_3.set("name", "many2_3_name");
		many1_1.getList("many2").add(many2_3);
		
		
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(many2_3);
				session.updateEntity(many1_1);
				return null;
			}
		});
	
		DataObject queryRet = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				IDASCriteria criteria = DASManager.createCriteria("case8_uni_1.Many1");
				criteria.add(ExpressionHelper.eq("id", "many1_1"));
				DataObject queryRet = session.query(criteria).get(0);
				queryRet.get("many2[1]/id");
				return queryRet;
			}
		});
		Assert.assertEquals(3, queryRet.getList("many2").size());
		detachedSession(queryRet);
		System.out.println(queryRet);
	}
	
}

/*
 * 修改历史
 * $Log$ 
 */