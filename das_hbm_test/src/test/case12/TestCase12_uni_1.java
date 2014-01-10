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

package test.case12;

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

public class TestCase12_uni_1 extends AbstractTestCase {
	@Before
	public void before() {
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.delete(DASManager.createCriteria("case12_uni_1.Address"));
				session.delete(DASManager.createCriteria("case12_uni_1.Person"));
				return null;
			}
		});
	}

	@Test
	public void testInsert() {
		final DataObject person = ExtendedDataFactory.eINSTANCE.create("case12_uni_1.Person");
		person.set("name", "name_01");

		final DataObject address1 = ExtendedDataFactory.eINSTANCE.create("case12_uni_1.Address");
		address1.set("person", person);
		address1.set("type", "HOME");
		address1.set("street", "home_street");

		final DataObject address2 = ExtendedDataFactory.eINSTANCE.create("case12_uni_1.Address");
		address2.set("person", person);
		address2.set("type", "MAILING");
		address2.set("street", "mailing_street");

		person.set("address", address1);
		person.set("mailingAddress", address2);
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(person);
				return null;
			}
		});

		DataObject queryOne = (DataObject) execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				DataObject queryOne = session.query(DASManager.createCriteria("case12_uni_1.Person")).get(0);
				session.expandEntity(queryOne.getDataObject("address"));
				session.expandEntity(queryOne.getDataObject("mailingAddress"));
				return queryOne;
			}
		});
		System.out.println(queryOne.get("address"));
		Assert.assertEquals("home_street", queryOne.getString("address/street"));
		System.out.println(queryOne);
	}
}

/*
 * 修改历史
 * $Log$ 
 */