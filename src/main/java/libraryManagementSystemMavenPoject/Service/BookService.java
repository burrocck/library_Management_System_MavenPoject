package libraryManagementSystemMavenPoject.Service;



import libraryManagementSystemMavenPoject.DAO.BookDAO;
import libraryManagementSystemMavenPoject.Entity.Book;

import java.util.List;

public class BookService {
    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void addBook(Book book) {
        bookDAO.save(book);
    }

    public void updateBook(Book book) {
        bookDAO.update(book);
    }

    public void deleteBook(Long id) {
        Book book = bookDAO.findById(id);
        if (book != null) {
            bookDAO.delete(book);
        }
    }

    public Book getBook(Long id) {
        return bookDAO.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }
}

