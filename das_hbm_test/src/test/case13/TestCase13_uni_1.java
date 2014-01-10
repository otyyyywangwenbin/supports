/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2013 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2013年12月19日
 *******************************************************************************/

package test.case13;

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

public class TestCase13_uni_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case13_uni_1.Many"));
				session.delete(DASManager.createCriteria("case13_uni_1.One"));
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsert() {
		{
			final DataObject one = ExtendedDataFactory.eINSTANCE.create("case13_uni_1.One");
			one.set("id", "one_id_01");
			one.set("name", "one_name_01");

			final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case13_uni_1.Many");
			many1.set("id", "many_id_01");
			many1.set("name", "many_name_01");
			final DataObject many2 = ExtendedDataFactory.eINSTANCE.create("case13_uni_1.Many");
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
		}
		{
			final DataObject one = ExtendedDataFactory.eINSTANCE.create("case13_uni_1.One");
			one.set("id", "one_id_02");
			one.set("name", "one_name_02");

			final DataObject many1 = ExtendedDataFactory.eINSTANCE.create("case13_uni_1.Many");
			many1.set("id", "many_id_03");
			many1.set("name", "many_name_03");

			List<DataObject> manys = new ArrayList<DataObject>();
			manys.add(many1);
			one.set("many", manys);

			execute(new IDASSessionCallback() {
				public Object doInSession(IDASSession session) throws Throwable {
					session.insertEntity(one);
					return null;
				}
			});
		}		
		
		
		List<DataObject> ret = (List<DataObject>)execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.query(DASManager.createCriteria("case13_uni_1.One"));
			}
		});
		int size = ret.size();
		Assert.assertEquals(2, size);
		for (int i=0;i<ret.size();i++) {
			if (ret.get(i).getString("id").equals("one_id_01")) {
				Assert.assertEquals(2, ret.get(i).getInt("many_size"));
			}
			if (ret.get(i).getString("id").equals("one_id_02")) {
				Assert.assertEquals(1, ret.get(i).getInt("many_size"));
			}
		}
	}

}

/*
 * 修改历史
 * $Log$ 
 */