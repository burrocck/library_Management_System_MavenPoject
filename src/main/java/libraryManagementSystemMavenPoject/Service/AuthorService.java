package libraryManagementSystemMavenPoject.Service;



import libraryManagementSystemMavenPoject.DAO.AuthorDAO;
import libraryManagementSystemMavenPoject.Entity.Author;

import java.util.List;

public class AuthorService {
    private final AuthorDAO authorDAO;

    public AuthorService(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public void addAuthor(Author author) {
        authorDAO.save(author);
    }

    public void updateAuthor(Author author) {
        authorDAO.update(author);
    }

    public void deleteAuthor(Long id) {
        Author author = authorDAO.findById(id);
        if (author != null) {
            authorDAO.delete(author);
        }
    }

    public Author getAuthor(Long id) {
        return authorDAO.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorDAO.findAll();
    }
}

