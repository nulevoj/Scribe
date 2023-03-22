package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ontu.dto.NewDocumentDto;
import ua.edu.ontu.model.entity.Document;
import ua.edu.ontu.service.repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public boolean isExist(String fileName) {
        return documentRepository.existsByFileName(fileName);
    }

    public Iterable<Document> findAll() {
        return documentRepository.findAll();
    }

    public Document findById(Long id) {
        return documentRepository.findById(id).get();
    }

    public Document findByFileName(String fileName) {
        return documentRepository.findByFileName(fileName);
    }

    public Document saveDto(NewDocumentDto documentDto) {
        Document document = new Document();
        document.setFileName(documentDto.getFile().getOriginalFilename());
        document.setDescription(documentDto.getDescription());
        return save(document);
    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public boolean update(Long id, Document fresh) {
        Document document = findById(id);
        if (document == null) {
            return false;
        }
        document.setDescription(fresh.getDescription());
        save(document);
        return true;
    }

    public void delete(Document document) {
        documentRepository.delete(document);
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    public void delete(String fileName) {
        documentRepository.deleteByFileName(fileName);
    }

}
