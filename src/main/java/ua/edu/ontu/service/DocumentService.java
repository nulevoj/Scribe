package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ontu.model.entity.Document;
import ua.edu.ontu.model.repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Iterable<Document> findAll() {
        return documentRepository.findAll();
    }

    public Document findById(long id) {
        return documentRepository.findById(id).get();
    }

    public Document findByFileName(String fileName) {
        return documentRepository.findByFileName(fileName);
    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public void delete(String fileName) {
        documentRepository.deleteByFileName(fileName);
    }

    public void delete(Document document) {
        documentRepository.delete(document);
    }

    public void delete(long id) {
        documentRepository.deleteById(id);
    }

    public boolean isExist(String fileName) {
        return documentRepository.existsByFileName(fileName);
    }

    public void update(long id, Document document) {

    }

}
