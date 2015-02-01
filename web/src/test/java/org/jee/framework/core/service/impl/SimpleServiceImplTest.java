package org.jee.framework.core.service.impl;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.jee.framework.core.service.IBaseService;
import org.jee.test.AbstractTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.msds.passport.entity.User;

public class SimpleServiceImplTest extends AbstractTest{

	@Resource(name="simpleService")
	private IBaseService<User, Long> simpleService;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetEntityPK() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadEntity() {
		User user = simpleService.getEntity(1288189498L);
		Assert.assertNotNull(user);
	}

	@Test
	public void testGetEntitiesPKArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntityStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntitiesStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntitiesIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsUnique() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsExist() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteT() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletePK() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletePKArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFlush() {
		fail("Not yet implemented");
	}

	@Test
	public void testClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testEvict() {
		fail("Not yet implemented");
	}

	@Test
	public void testMerge() {
		fail("Not yet implemented");
	}

	@Test
	public void testPersist() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveOrUpdateAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByCause() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalByCauseString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindIntIntStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testBulkUpdateString() {
		fail("Not yet implemented");
	}

	@Test
	public void testBulkUpdateStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalByCauseStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAllClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAllCollectionOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBySqlString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBySqlStringObjectArrayTypeArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteSqlString() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteSqlStringObjectArrayTypeArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBySqlStringObjectArrayTypeArrayClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBySqlIntIntStringObjectArrayTypeArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBySqlIntIntStringObjectArrayTypeArrayClassOfT() {
		fail("Not yet implemented");
	}

}
