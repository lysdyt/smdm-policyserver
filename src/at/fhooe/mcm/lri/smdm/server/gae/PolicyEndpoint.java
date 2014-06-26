package at.fhooe.mcm.lri.smdm.server.gae;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "policyendpoint", namespace = @ApiNamespace(ownerDomain = "fhooe.at", ownerName = "fhooe.at", packagePath = "mcm.lri.smdm"))
public class PolicyEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPolicy")
	public CollectionResponse<Policy> listPolicy(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Policy> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Policy as Policy");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Policy>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Policy obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Policy> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPolicy")
	public Policy getPolicy(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Policy policy = null;
		try {
			policy = mgr.find(Policy.class, id);
		} finally {
			mgr.close();
		}
		return policy;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param policy the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPolicy")
	public Policy insertPolicy(Policy policy) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPolicy(policy)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(policy);
		} finally {
			mgr.close();
		}
		return policy;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param policy the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePolicy")
	public Policy updatePolicy(Policy policy) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPolicy(policy)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(policy);
		} finally {
			mgr.close();
		}
		return policy;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePolicy")
	public void removePolicy(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Policy policy = mgr.find(Policy.class, id);
			mgr.remove(policy);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPolicy(Policy policy) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Policy item = mgr.find(Policy.class, policy.getVersionId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
