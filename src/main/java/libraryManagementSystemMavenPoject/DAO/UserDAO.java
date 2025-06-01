package libraryManagementSystemMavenPoject.DAO;


import libraryManagementSystemMavenPoject.Entity.User;

public interface UserDAO {
    void save(User user);
    User findByUsername(String username);
}

