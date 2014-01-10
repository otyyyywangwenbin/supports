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
public class TestCase9_uni_3 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				List<DataObject> many1s = session.query(DASManager.createCriteria("case9_uni_3.Many1"));
				for (DataObject many1 : many1s) {
					session.deleteEntity(many1);
				}

				List<DataObject> many2s = session.query(DASManager.createCriteria("case9_uni_3.Many2"));
				for (DataObject many2 : many2s) {
					session.deleteEntity(many2);
				}
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDelete() {
		final DataObject many1_1 = ExtendedDataFactory.eINSTANCE.create("case9_uni_3.Many1");
		many1_1.set("id", "many1_1");
		many1_1.set("name", "many1_1_name");

		final DataObject many2_1 = ExtendedDataFactory.eINSTANCE.create("case9_uni_3.Many2");
		many2_1.set("id", "many2_1");
		many2_1.set("name", "many2_1_name");

		final DataObject many2_2 = ExtendedDataFactory.eINSTANCE.create("case9_uni_3.Many2");
		many2_2.set("id", "many2_2");
		many2_2.set("name", "many2_2_name");

		List<DataObject> many2s = new ArrayList<DataObject>();
		many2s.add(many2_1);
		many2s.add(many2_2);
		many1_1.set("many2", many2s);

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(many2_1);
				session.insertEntity(many2_2);
				session.insertEntity(many1_1);
				return null;
			}
		});

		DataObject queryRet = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				IDASCriteria criteria = DASManager.createCriteria("case9_uni_3.Many1");
				criteria.add(ExpressionHelper.eq("id", "many1_1"));
				DataObject queryRet = session.query(criteria).get(0);
				queryRet.get("many2[1]/id");
				return queryRet;
			}
		});
		Assert.assertEquals(2, queryRet.getList("many2").size());
		detachedSession(queryRet);
		System.out.println(queryRet);
		
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.deleteEntity(many1_1);
				return null;
			}
		});
		
		List<DataObject> queryList = (List<DataObject>) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				IDASCriteria criteria = DASManager.createCriteria("case9_uni_3.Many2");
				return session.query(criteria);
			}
		});
		Assert.assertEquals(0, queryList.size());
		
	}
}

/*
 * 修改历史
 * $Log$ 
 */