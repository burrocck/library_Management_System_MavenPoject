package libraryManagementSystemMavenPoject.DAO;

import jakarta.persistence.EntityManager;
import libraryManagementSystemMavenPoject.Entity.Publisher;




public class PublisherDAOImpl extends GenericDAOImpl<Publisher, Long> implements PublisherDAO {
    public PublisherDAOImpl(EntityManager entityManager) {
        super(entityManager, Publisher.class);
    }
}
