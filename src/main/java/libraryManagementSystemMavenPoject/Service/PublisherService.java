package libraryManagementSystemMavenPoject.Service;



import libraryManagementSystemMavenPoject.DAO.PublisherDAO;
import libraryManagementSystemMavenPoject.Entity.Publisher;

import java.util.List;

public class PublisherService {
    private final PublisherDAO publisherDAO;

    public PublisherService(PublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    public void addPublisher(Publisher publisher) {
        publisherDAO.save(publisher);
    }

    public void updatePublisher(Publisher publisher) {
        publisherDAO.update(publisher);
    }

    public void deletePublisher(Long id) {
        Publisher publisher = publisherDAO.findById(id);
        if (publisher != null) {
            publisherDAO.delete(publisher);
        }
    }

    public Publisher getPublisher(Long id) {
        return publisherDAO.findById(id);
    }

    public List<Publisher> getAllPublishers() {
        return publisherDAO.findAll();
    }
}

