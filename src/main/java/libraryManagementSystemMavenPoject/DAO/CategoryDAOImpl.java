package libraryManagementSystemMavenPoject.DAO;

import jakarta.persistence.EntityManager;
import libraryManagementSystemMavenPoject.Entity.Category;

public class CategoryDAOImpl extends GenericDAOImpl<Category, Long> implements CategoryDAO {
    public CategoryDAOImpl(EntityManager entityManager) {
        super(entityManager, Category.class);
    }
}
