package libraryManagementSystemMavenPoject.Service;





import libraryManagementSystemMavenPoject.DAO.CategoryDAO;
import libraryManagementSystemMavenPoject.Entity.Category;

import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public void addCategory(Category category) {
        categoryDAO.save(category);
    }

    public void updateCategory(Category category) {
        categoryDAO.update(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryDAO.findById(id);
        if (category != null) {
            categoryDAO.delete(category);
        }
    }

    public Category getCategory(Long id) {
        return categoryDAO.findById(id);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }
}

