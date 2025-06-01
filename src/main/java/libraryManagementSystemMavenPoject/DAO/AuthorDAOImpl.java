package libraryManagementSystemMavenPoject.DAO;

import jakarta.persistence.EntityManager;
import libraryManagementSystemMavenPoject.Entity.Author;

public class AuthorDAOImpl extends GenericDAOImpl<Author, Long> implements AuthorDAO {
    public AuthorDAOImpl(EntityManager entityManager) {
        super(entityManager, Author.class);
    }
}

