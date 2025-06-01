package libraryManagementSystemMavenPoject.Service;



import libraryManagementSystemMavenPoject.DAO.BookBorrowingDAO;
import libraryManagementSystemMavenPoject.Entity.BookBorrowing;

import java.time.LocalDate;
import java.util.List;

public class BookBorrowingService {
    private final BookBorrowingDAO bookBorrowingDAO;

    public BookBorrowingService(BookBorrowingDAO bookBorrowingDAO) {
        this.bookBorrowingDAO = bookBorrowingDAO;
    }

    public void borrowBook(BookBorrowing borrowing) {
        borrowing.setBorrowingDate(LocalDate.now());
        borrowing.setReturnDate(null);
        bookBorrowingDAO.save(borrowing);
    }

    public void returnBook(Long borrowingId) {
        BookBorrowing borrowing = bookBorrowingDAO.findById(borrowingId);
        if (borrowing != null && borrowing.getReturnDate() == null) {
            borrowing.setReturnDate(LocalDate.now());
            bookBorrowingDAO.update(borrowing);
        }
    }

    public List<BookBorrowing> getAllBorrowings() {
        return bookBorrowingDAO.findAll();
    }

    public BookBorrowing getBorrowing(Long id) {
        return bookBorrowingDAO.findById(id);
    }

}

