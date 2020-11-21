package eu.getmangos.controllers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import eu.getmangos.entities.Corpse;

@ApplicationScoped
public class CorpseController {
    @Inject private Logger logger;

    @PersistenceContext(name = "CHAR_PU")
    private EntityManager em;

    /**
     * Create a new corpse in the database.
     * @param corpse The corpse to be created.
     * @throws DAOException An exception is raised if the corpse can't be created.
     */
    @Transactional
    public void create(Corpse corpse) throws DAOException {
        logger.debug("create() entry.");

        try {
            em.persist(corpse);
        } catch (Exception e) {
            logger.debug("Exception raised while creating the corpse: "+e.getMessage());
            throw new DAOException("Error while creating the corpse.");
        }

        logger.debug("create() exit.");
    }

    /**
     * Update a corpse in the database.
     * @param corpse The corpse to be updated.
     * @throws DAOException An exception is raised if the corpse can't be updated.
     */
    @Transactional
    public void update(Corpse corpse) throws DAOException {
        logger.debug("update() entry.");

        Corpse exist = find(corpse.getGuid());

        if(exist == null) {
            throw new DAOException("The entity does not exist");
        }

        try {
            em.merge(corpse);
        } catch (Exception e) {
            logger.debug("Exception raised while updating the corpse: "+e.getMessage());
            throw new DAOException("Error while updating the corpse.");
        }

        logger.debug("update() exit.");
    }

    /**
     * Retrieves a corpse based on its ID.
     * @param guid The ID of the corpse to retrieve.
     * @return The matching corpse or null if not found.
     * @throws DAOException
     */
    public Corpse find(Integer guid) throws DAOException {
        logger.debug("find() entry.");

        if (guid == null) {
            throw new DAOException("ID is null.");
        }

        try {
            Corpse corpse = em.createNamedQuery("Corpse.findById", Corpse.class).setParameter("guid", guid).getSingleResult();
            return corpse;
        } catch (NoResultException nre) {
            logger.debug("No result received for entity with guid "+guid);
            return null;
        } finally {
            logger.debug("find() exit.");
        }
    }

    /**
     * Delete a corpse based from the database.
     * @param id The ID of the corpse to be deleted.
     * @throws DAOException An exception is raised if the auction can't be deleted.
     */
    @Transactional
    public void delete(Integer guid, Integer instance) throws DAOException {
        logger.debug("delete() entry.");

        if (guid == null || instance == null) {
            throw new DAOException("ID is null.");
        }

        em.createNamedQuery("Corpse.delete").setParameter("guid", guid).setParameter("instance", instance).executeUpdate();

        logger.debug("delete() exit.");
    }

    /**
     * Retrieves the list of all corpses using paging.
     * @param page The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of corpses corresponding to the search.
     */
    @SuppressWarnings("unchecked")
    public List<Corpse> findAll(int page, int pageSize) {
        logger.debug("findAll() entry.");

        List<Corpse> list = em.createNamedQuery("Corpse.findAll")
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * Retrieves the list of all corpses for a given instance using paging.
     * @param page     The page to be queried.
     * @param pageSize The size of the page.
     * @return A list of corpses corresponding to the search.
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
    public List<Corpse> findAllByInstance(Integer instance, int page, int pageSize) throws DAOException {
        logger.debug("findAll() entry.");

        if(instance == null) {
            throw new DAOException("Entity ID is null");
        }

        List<Corpse> list = em.createNamedQuery("Corpse.findByInstance")
                                .setParameter("instance", instance)
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .getResultList();

        logger.debug("findAll() exit.");

        return list;
    }

    /**
     * Delete a corpse for a given instance from the database.
     * @param instance The ID of the instance for which all respawns have to be deleted.
     * @throws DAOException An exception is raised if the respawns can't be deleted.
     */
    @Transactional
    public void deleteAllByInstance(Integer instance) throws DAOException {
        logger.debug("deleteAllByInstance() entry.");

        if (instance == null) {
            throw new DAOException("Instance ID is null.");
        }

        em.createNamedQuery("Corpse.deleteByInstance").setParameter("instance", instance).executeUpdate();

        logger.debug("deleteAllByInstance() exit.");
    }
}
