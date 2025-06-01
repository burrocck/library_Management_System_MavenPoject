package libraryManagementSystemMavenPoject.Entity;


import jakarta.persistence.EntityManager;
import libraryManagementSystemMavenPoject.DAO.*;
import libraryManagementSystemMavenPoject.Service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LibraryMenu {

    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final BookBorrowingService borrowingService;
    private final Scanner scanner;
    private final UserService userService;
    private User currentUser;


    public LibraryMenu(EntityManager em) {
        this.authorService = new AuthorService(new AuthorDAOImpl(em));
        this.publisherService = new PublisherService(new PublisherDAOImpl(em));
        this.categoryService = new CategoryService(new CategoryDAOImpl(em));
        this.bookService = new BookService(new BookDAOImpl(em));
        this.borrowingService = new BookBorrowingService(new BookBorrowingDAOImpl(em));
        this.scanner = new Scanner(System.in);
        this.userService = new UserService(new UserDAOImpl(em));
    }

    private boolean isLoggedIn = false;

    public void start() {
        loginOrRegister();

        while (true) {
            System.out.println("\n===== KÜTÜPHANE SİSTEMİ MENÜ =====");
            System.out.println("1. Kitap Ekle");
            System.out.println("2. Kitapları Listele");
            System.out.println("3. Kitap Ödünç Al");
            System.out.println("4. Kitap Teslim Et");
            System.out.println("5. Ödünç Alma Geçmişi");
            System.out.println("6. Kitap Ara");
            System.out.println("7. Kitap Güncelle");
            System.out.println("8. Kitap Sil");
            System.out.println("9. Oturumu Kapat");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addBook();
                case 2 -> listBooks();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> showBorrowingHistory();
                case 6 -> searchBook();
                case 7 -> updateBook();
                case 8 -> deleteBook();
                case 9 -> logout();
                case 0 -> {
                    System.out.println("Çıkılıyor...");
                    return;
                }
                default -> System.out.println("Geçersiz seçim.");
            }
        }
    }

    // === 1. USER LOGIN ===
    private void loginOrRegister() {
        while (currentUser == null) {
            System.out.println("\n1. Giriş yap");
            System.out.println("2. Kayıt ol");
            System.out.print("Seçiminiz: ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                login();
            } else if (input.equals("2")) {
                register();
            } else {
                System.out.println("Geçersiz seçim.");
            }
        }
    }

    private void login() {
        System.out.print("Kullanıcı adı: ");
        String username = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("✅ Giriş başarılı! Hoş geldin, " + user.getUsername());
        } else {
            System.out.println("❌ Hatalı kullanıcı adı veya şifre.");
        }
    }

    private void register() {
        System.out.print("Yeni kullanıcı adı: ");
        String username = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(User.Role.USER);

        userService.register(user);
        System.out.println("✅ Kayıt başarılı. Şimdi giriş yapınız.");
    }

    private void logout() {
        isLoggedIn = false;
        System.out.println("🔒 Oturum kapatıldı.");
    }

    private void addBook() {
        System.out.print("Kitap adı: ");
        String name = scanner.nextLine();

        System.out.print("Yayın yılı: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.print("Stok: ");
        int stock = Integer.parseInt(scanner.nextLine());

        // WRİTER
        System.out.print("Yazar adı: ");
        String authorName = scanner.nextLine();

        Author author = new Author();
        author.setName(authorName);
        author.setBirthDate(LocalDate.of(1900, 1, 1)); // örnek
        author.setCountry("Bilinmiyor");
        authorService.addAuthor(author);

        // PUBLISHING HOUSE
        System.out.print("Yayınevi adı: ");
        String pubName = scanner.nextLine();
        Publisher publisher = new Publisher();
        publisher.setName(pubName);
        publisher.setEstablishmentYear(2000);
        publisher.setAddress("Bilinmiyor");
        publisherService.addPublisher(publisher);

        // CATEGORY
        System.out.print("Kategori adı: ");
        String catName = scanner.nextLine();
        Category category = new Category();
        category.setName(catName);
        category.setDescription("Açıklama yok");
        categoryService.addCategory(category);

        Book book = new Book();
        book.setName(name);
        book.setPublicationYear(year);
        book.setStock(stock);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategories(List.of(category));
        bookService.addBook(book);

        System.out.println("✅ Kitap başarıyla eklendi.");
    }

    private void listBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Hiç kitap yok.");
        } else {
            for (Book b : books) {
                System.out.println(b.getId() + " - " + b.getName() + " (" + b.getPublicationYear() + ") | Stok: " + b.getStock());
            }
        }
    }

    private void borrowBook() {
        listBooks();
        System.out.print("Ödünç alınacak kitap ID: ");
        long id = Long.parseLong(scanner.nextLine());

        Book book = bookService.getBook(id);
        if (book == null || book.getStock() <= 0) {
            System.out.println("❌ Kitap bulunamadı veya stokta yok.");
            return;
        }

        System.out.print("Ödünç alanın adı: ");
        String name = scanner.nextLine();

        BookBorrowing borrowing = new BookBorrowing();
        borrowing.setBorrowerName(currentUser.getUsername()); // kullanıcı adı
        borrowing.setBook(book);
        borrowing.setBorrowerName(name);

        borrowingService.borrowBook(borrowing);
        System.out.println("✅ Kitap ödünç alındı.");
    }

    private void returnBook() {
        showBorrowingHistory();
        System.out.print("Teslim edilecek işlem ID: ");
        long id = Long.parseLong(scanner.nextLine());

        borrowingService.returnBook(id);
        System.out.println("✅ Kitap teslim edildi.");
    }

    private void showBorrowingHistory() {
        List<BookBorrowing> borrowings = borrowingService.getAllBorrowings();

        System.out.println("📚 Ödünç Alınan Kitaplar (sadece sizin):");

        boolean found = false;
        for (BookBorrowing b : borrowings) {
            if (b.getBorrowerName().equals(currentUser.getUsername())) {
                System.out.println("- " + b.getBook().getName()
                        + " | Alım: " + b.getBorrowingDate()
                        + " | Teslim: " + (b.getReturnDate() != null ? b.getReturnDate() : "Teslim edilmedi"));
                found = true;
            }
        }

        if (!found) {
            System.out.println("Henüz kitap ödünç almadınız.");
        }
    }
    // === 6. SEARCH BOOK ===

    private void searchBook() {
        System.out.print("Aranacak kitap adı: ");
        String keyword = scanner.nextLine().toLowerCase();

        List<Book> books = bookService.getAllBooks();
        boolean found = false;
        for (Book b : books) {
            if (b.getName().toLowerCase().contains(keyword)) {
                System.out.println(b.getId() + " - " + b.getName() + " | Yazar: " + b.getAuthor().getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("📕 Eşleşen kitap bulunamadı.");
        }
    }
// === 7. UPDATE BOOK ===

    private void updateBook() {
        listBooks();
        System.out.print("Güncellenecek kitap ID: ");
        long id = Long.parseLong(scanner.nextLine());

        Book book = bookService.getBook(id);
        if (book == null) {
            System.out.println("❌ Kitap bulunamadı.");
            return;
        }

        System.out.print("Yeni kitap adı (boş bırakılırsa aynı kalır): ");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) book.setName(newName);

        System.out.print("Yeni yayın yılı (boş: aynı): ");
        String newYear = scanner.nextLine();
        if (!newYear.isBlank()) book.setPublicationYear(Integer.parseInt(newYear));

        System.out.print("Yeni stok (boş: aynı): ");
        String newStock = scanner.nextLine();
        if (!newStock.isBlank()) book.setStock(Integer.parseInt(newStock));

        bookService.updateBook(book);
        System.out.println("✅ Kitap güncellendi.");
    }

// === 8. DELETE BOOK ===

    private void deleteBook() {
        if (currentUser.getRole() != User.Role.ADMIN) {
            System.out.println("❌ Bu işlemi sadece ADMIN kullanıcıları yapabilir.");
            return;
        }

        listBooks();
        System.out.print("Silinecek kitap ID: ");
        long id = Long.parseLong(scanner.nextLine());

        bookService.deleteBook(id);
        System.out.println("🗑️ Kitap silindi.");
    }

}

