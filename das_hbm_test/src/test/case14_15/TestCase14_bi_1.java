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

package test.case14_15;

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

public class TestCase14_bi_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case14_bi_1.Many"));
				session.delete(DASManager.createCriteria("case14_bi_1.One"));
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		final DataObject one = ExtendedDataFactory.eINSTANCE.create("case14_bi_1.One");
		one.set("id", "one_id_01");
		one.set("name", "one_name_01");

		final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case14_bi_1.Many");
		many1.set("id", "many_id_01");
		many1.set("name", "many_name_01");
		final DataObject many2 = ExtendedDataFactory.eINSTANCE.create("case14_bi_1.Many");
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
				session.insertEntity(many1);
				session.insertEntity(many2);
				return null;
			}
		});

		List<DataObject> queryRet = (List<DataObject>) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.query(DASManager.createCriteria("case14_bi_1.Many"));
			}
		});
		Assert.assertEquals(2, queryRet.size());
		for (int i=0;i<queryRet.size();i++) {
			Assert.assertEquals(true, queryRet.get(i).get("one")==null);
		}
		
		final DataObject one2 = ExtendedDataFactory.eINSTANCE.create("case14_bi_1.One");
		one2.set("id", "one_id_02");
		one2.set("name", "one_name_02");
		many1.set("one", one2);
		
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.updateEntity(many1);
				return null;
			}
		});

		queryRet = (List<DataObject>) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.query(DASManager.createCriteria("case14_bi_1.Many"));
			}
		});
		Assert.assertEquals(2, queryRet.size());
		for (int i=0;i<queryRet.size();i++) {
			Assert.assertEquals(true, queryRet.get(i).get("one")==null);
		}
	}
}

/*
 * 修改历史
 * $Log$ 
 */