package ua.edu.ontu.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ontu.model.entity.Document;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    @Override
    Iterable<Document> findAll();

    @Override
    Document save(Document document);

    @Override
    void delete(Document document);

    @Transactional
    void deleteByFileName(String fileName);

    Document findByFileName(String fileName);

    boolean existsByFileName(String fileName);

}
