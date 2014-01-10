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

package test.case01;

import org.junit.Test;

import test.AbstractTestCase;

import com.eos.das.entity.IDASSession;
import com.primeton.ext.data.sdo.helper.ExtendedDataFactory;
import commonj.sdo.DataObject;

/**
 * TODO 此处填写 class 信息
 * 
 * @author yourname (mailto:yourname@primeton.com)
 */

public class TestCase1 extends AbstractTestCase {
	@Test
	public void testInsert() {
		final DataObject sub1 = ExtendedDataFactory.eINSTANCE.create("case1.Sub1");
		sub1.set("parent_id", "parent_id_01");
		sub1.set("parent_name", "parnet_name_01");
		sub1.set("sub1_name", "sub1_name_01");
		System.out.println(sub1);
		execute(new IDASSessionCallback() {
			public Object doInSession(IDASSession session) throws Throwable {
				session.insertEntity(sub1);
				return null;
			}
		});
	}
}

/*
 * 修改历史
 * $Log$ 
 */