package salve.spring.txn;

import junit.framework.Assert;

import org.aopalliance.aop.Advice;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor;

import salve.depend.DependencyLibrary;
import salve.depend.Locator;
import salve.loader.BytecodePool;

// TODO factor out a bunch of this common code
public class TransactionalInstrumentorTest extends Assert {
	private static String METHODBEAN_NAME = "salve/spring/txn/TransactionalMethodBean";
	private static String CLASSBEAN_NAME = "salve/spring/txn/TransactionalClassBean";
	private static Class<?> methodBeanClass;
	private static Class<?> classBeanClass;

	private static PlatformTransactionManager ptm;
	private static TransactionAttributeSourceAdvisor adv;
	private static Locator locator;

	@Before
	public void initMocks() {
		EasyMock.reset(locator, ptm);
	}

	@Test
	public void testArgs() throws Exception {
		TransactionalMethodBean bean = (TransactionalMethodBean) methodBeanClass
				.newInstance();
		MockTransactionStatus status = new MockTransactionStatus();

		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);
		ptm.commit(status);
		EasyMock.replay(locator, ptm);
		Object[] array1 = new Object[0];
		assertTrue(bean.args2(1, array1, null) == array1);
		EasyMock.verify(locator, ptm);

		// ////////////////////////////////////////

		EasyMock.reset(locator, ptm);
		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);
		ptm.commit(status);
		EasyMock.replay(locator, ptm);
		double[] array2 = new double[0];
		assertTrue(bean.args2(2, null, array2) == array2);
		EasyMock.verify(locator, ptm);

	}

	@SuppressWarnings("static-access")
	@Test
	public void testClinit() throws Exception {
		TransactionalMethodBean bean = (TransactionalMethodBean) methodBeanClass
				.newInstance();
		assertTrue(bean.CLINIT_FORCER != 0);

	}

	@Test
	public void testReturnValue() throws Exception {
		TransactionalMethodBean bean = (TransactionalMethodBean) methodBeanClass
				.newInstance();

		MockTransactionStatus status = new MockTransactionStatus();
		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);

		ptm.commit(status);
		EasyMock.replay(locator, ptm);
		Object token = new Object();
		Object token2 = bean.ret(token);
		assertTrue(token == token2);
		EasyMock.verify(locator, ptm);
	}

	@Test
	public void testSimple() throws Exception {
		TransactionalMethodBean bean = (TransactionalMethodBean) methodBeanClass
				.newInstance();

		MockTransactionStatus status = new MockTransactionStatus();
		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);

		ptm.commit(status);
		EasyMock.replay(locator, ptm);
		bean.simple();
		EasyMock.verify(locator, ptm);
	}

	@Test
	public void testThrowsChecked() throws Exception {
		TransactionalMethodBean bean = (TransactionalMethodBean) methodBeanClass
				.newInstance();

		MockTransactionStatus status = new MockTransactionStatus();
		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);

		ptm.rollback(status);
		EasyMock.replay(locator, ptm);
		try {
			bean.exception(1, null);
			fail("Expected exception to be thrown");
		} catch (IndexOutOfBoundsException e) {

		}
		EasyMock.verify(locator, ptm);
	}

	@Test
	public void testThrowsNormalReturn() throws Exception {
		TransactionalMethodBean bean = (TransactionalMethodBean) methodBeanClass
				.newInstance();

		MockTransactionStatus status = new MockTransactionStatus();
		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);

		ptm.commit(status);
		EasyMock.replay(locator, ptm);
		Object token = new Object();
		Object token2 = bean.exception(0, token);
		assertTrue(token == token2);
		EasyMock.verify(locator, ptm);
	}

	@Test
	public void testThrowsUnchecked() throws Exception {
		TransactionalMethodBean bean = (TransactionalMethodBean) methodBeanClass
				.newInstance();

		MockTransactionStatus status = new MockTransactionStatus();
		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);

		ptm.rollback(status);
		EasyMock.replay(locator, ptm);
		try {
			bean.exception(2, null);
			fail("Expected exception to be thrown");
		} catch (IllegalStateException e) {
			// expected
		}
		EasyMock.verify(locator, ptm);
	}

	@SuppressWarnings("static-access")
	@Test
	public void testTransactionalClassInstrumentation() throws Exception {

		// test constructor is instrumented

		MockTransactionStatus status = new MockTransactionStatus();
		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);

		ptm.commit(status);
		EasyMock.replay(locator, ptm);
		TransactionalClassBean bean = (TransactionalClassBean) classBeanClass
				.newInstance();
		assertTrue(bean.CLINIT_FORCER != 0);
		EasyMock.verify(locator, ptm);

		// test method is instrumented

		EasyMock.reset(locator, ptm);
		EasyMock.expect(locator.locate(AdviserUtil.AdviserKey.INSTANCE))
				.andReturn(adv);
		EasyMock.expect(
				ptm
						.getTransaction((TransactionDefinition) EasyMock
								.anyObject())).andReturn(status);

		ptm.commit(status);
		EasyMock.replay(locator, ptm);
		bean.method();
		EasyMock.verify(locator, ptm);

	}

	@BeforeClass
	public static void initClass() throws Exception {
		loadBeans();
		initDependencyLibrary();
	}

	private static void initDependencyLibrary() {
		DependencyLibrary.clear();

		ptm = EasyMock.createMock(PlatformTransactionManager.class);
		adv = new MockTransactionAttributeSourceAdvisor();
		locator = EasyMock.createMock(Locator.class);
		DependencyLibrary.addLocator(locator);
	}

	private static void loadBeans() throws Exception {
		ClassLoader loader = TransactionalInstrumentorTest.class
				.getClassLoader();
		BytecodePool pool = new BytecodePool().addLoaderFor(loader);
		TransactionalInstrumentor inst = new TransactionalInstrumentor();

		methodBeanClass = pool.instrumentIntoClass(METHODBEAN_NAME, inst);
		classBeanClass = pool.instrumentIntoClass(CLASSBEAN_NAME, inst);
	}

	private static class MockTransactionAttributeSourceAdvisor extends
			TransactionAttributeSourceAdvisor {
		@Override
		public Advice getAdvice() {
			return new MockTransactionSupport();
		}
	}

	private static class MockTransactionStatus implements TransactionStatus {

		public Object createSavepoint() throws TransactionException {

			return null;
		}

		public boolean hasSavepoint() {

			return false;
		}

		public boolean isCompleted() {

			return false;
		}

		public boolean isNewTransaction() {

			return false;
		}

		public boolean isRollbackOnly() {

			return false;
		}

		public void releaseSavepoint(Object savepoint)
				throws TransactionException {

		}

		public void rollbackToSavepoint(Object savepoint)
				throws TransactionException {

		}

		public void setRollbackOnly() {

		}

	}

	private static class MockTransactionSupport extends
			TransactionAspectSupport implements Advice {
		@Override
		public PlatformTransactionManager getTransactionManager() {
			return ptm;
		}
	}

}
