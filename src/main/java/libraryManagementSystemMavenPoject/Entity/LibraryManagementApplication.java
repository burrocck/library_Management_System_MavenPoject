package libraryManagementSystemMavenPoject.Entity;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LibraryManagementApplication {


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();

        LibraryMenu menu = new LibraryMenu(em);
        menu.start();

        em.close();
        emf.close();
    }
}

