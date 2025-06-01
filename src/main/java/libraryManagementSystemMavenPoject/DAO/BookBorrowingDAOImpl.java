package libraryManagementSystemMavenPoject.DAO;

import jakarta.persistence.EntityManager;
import libraryManagementSystemMavenPoject.Entity.BookBorrowing;


public class BookBorrowingDAOImpl extends GenericDAOImpl<BookBorrowing, Long> implements BookBorrowingDAO {
    public BookBorrowingDAOImpl(EntityManager entityManager) {
        super(entityManager, BookBorrowing.class);
    }
}
