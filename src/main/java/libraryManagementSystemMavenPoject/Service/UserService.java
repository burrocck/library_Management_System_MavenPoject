package libraryManagementSystemMavenPoject.Service;


import libraryManagementSystemMavenPoject.DAO.UserDAO;
import libraryManagementSystemMavenPoject.Entity.User;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void register(User user) {
        userDAO.save(user);
    }

    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
