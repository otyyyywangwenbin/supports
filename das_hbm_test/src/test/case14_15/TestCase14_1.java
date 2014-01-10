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

package test.case14_15;

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

public class TestCase14_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case14_1.One"));
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		final DataObject one = ExtendedDataFactory.eINSTANCE.create("case14_1.One");
		one.set("id", "id_01");
		one.set("name", "name_01");
		one.set("name2", "name2_01");

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(one);
				return null;
			}
		});

		List<DataObject> ret = (List<DataObject>) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.query(DASManager.createCriteria("case14_1.One"));
			}
		});
		int size = ret.size();
		Assert.assertEquals(1, size);
		Assert.assertEquals("name_01", ret.get(0).getString("name"));
		Assert.assertEquals("name_01", ret.get(0).getString("name2"));

		
		one.set("name", "name_01_u");
		one.set("name2", "name2_01_u");

		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.updateEntity(one);
				return null;
			}
		});

		ret = (List<DataObject>) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				return session.query(DASManager.createCriteria("case14_1.One"));
			}
		});
		size = ret.size();
		Assert.assertEquals(1, size);
		Assert.assertEquals("name_01_u", ret.get(0).getString("name"));
		Assert.assertEquals("name_01_u", ret.get(0).getString("name2"));
		
	}

}

/*
 * 修改历史
 * $Log$ 
 */