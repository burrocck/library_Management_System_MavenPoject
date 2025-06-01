package libraryManagementSystemMavenPoject.DAO;

import jakarta.persistence.EntityManager;
import libraryManagementSystemMavenPoject.Entity.Book;


public class BookDAOImpl extends GenericDAOImpl<Book, Long> implements BookDAO {
    public BookDAOImpl(EntityManager entityManager) {
        super(entityManager, Book.class);
    }
}
