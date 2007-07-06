package salve.agent;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import salve.DependencyLibrary;
import salve.Key;
import salve.Locator;
import salve.agent.model.EmailSender;
import salve.agent.model.User;
import salve.agent.model.UserStore;

// TODO align "mailsender" and "emailsender" names in code
public class PojoInstrumentorTest {
	private ClassPool pool;

	private Locator locator;

	private Class userClass;

	@Before
	public void before() throws Exception {
		initDependencyLibrary();
		initPool();
		initUserClass();
	}

	private void initUserClass() throws Exception {
		CtClass user1 = pool.get("salve.agent.model.User");
		CtClass user2 = new PojoInstrumentor(user1).instrument();
		userClass = user2.toClass();
	}

	private void initDependencyLibrary() {
		locator = EasyMock.createMock(Locator.class);
		DependencyLibrary.clear();
		DependencyLibrary.addLocator(locator);
	}

	private void initPool() {
		pool = new ClassPool(ClassPool.getDefault());
		pool.appendClassPath(new ClassClassPath(PojoInstrumentorTest.class));
	}

	@Test
	public void testPojoInstrumentor() throws Exception {
		try {

			User user = (User) userClass.newInstance();

			user.setName("jon doe");
			user.setEmail("jon@doe.com");

			EmailSender es = EasyMock.createMock(EmailSender.class);
			UserStore us = EasyMock.createMock(UserStore.class);

			// expect two lookups of email sender, one for each invocation of
			// user#register()
			EasyMock.expect(
					locator.locate(new Key(EmailSender.class, User.class,
							"mailSender"))).andReturn(es).times(2);

			// expect a single lookup of store as it should be cached in the
			// field
			EasyMock.expect(
					locator
							.locate(new Key(UserStore.class, User.class,
									"store"))).andReturn(us).times(1);

			// setup expectations from sideffects of user#register()
			es.send(User.SYSTEM_EMAIL, user.getEmail(), User.REG_EMAIL);
			EasyMock.expectLastCall().times(2);
			es.send(User.SYSTEM_EMAIL, User.SYSTEM_EMAIL, User.REGGED_EMAIL);
			EasyMock.expectLastCall().times(2);
			us.save(user);
			EasyMock.expectLastCall().times(2);

			// execute the test
			EasyMock.replay(locator, es, us);

			user.register();
			user.register();

			EasyMock.verify(locator, es, us);

			// make sure we replaced field write with a noop for an injected
			// field
			user.setStore(new UserStore() {

				public void save(User person) {
				}
			});
			Assert.assertTrue(user.getStore() == us);

			// make sure we replaced field write with a noop for a removed field
			user.setMailSender(new EmailSender() {

				public void send(String from, String to, String msg) {
				}

			});

			EasyMock.reset(locator);
			EasyMock.expect(
					locator.locate(new Key(EmailSender.class, User.class,
							"mailSender"))).andReturn(es);
			EasyMock.replay(locator);
			Assert.assertTrue(user.getMailSender() == es);
			EasyMock.verify(locator);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}