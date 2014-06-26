package at.fhooe.mcm.lri.smdm.server.gae;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * An app interacts with JPA using an instance of the EntityManager class. You
 * get this instance by instantiating and calling a method on an instance of the
 * EntityManagerFactory class. The factory uses the JPA configuration
 * (identified by the name "transactions-optional") to create EntityManager
 * instances.
 * 
 * Because an EntityManagerFactory instance takes time to initialize, it's a
 * good idea to reuse a single instance as much as possible. An easy way to do
 * this is to create a singleton wrapper class with a static instance, as
 * follows:
 * 
 * @author Lorenz
 * 
 */
public final class EMF {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	// Tip: "transactions-optional" refers to the name of the configuration set
	// in the persistence.xml file. If your app uses multiple configuration
	// sets, you'll have to extend this code to call
	// Persistence.createEntityManagerFactory() as desired. Your code should
	// cache a singleton instance of each EntityManagerFactory.

	private EMF() {
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}
}