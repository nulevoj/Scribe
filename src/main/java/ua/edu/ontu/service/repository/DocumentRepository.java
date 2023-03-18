package ua.edu.ontu.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ontu.model.entity.Document;

import java.util.Optional;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    boolean existsByFileName(String fileName);

    @Override
    Iterable<Document> findAll();

    @Override
    Optional<Document> findById(Long id);

    Document findByFileName(String fileName);

    @Override
    Document save(Document document);

    @Override
    void delete(Document document);

    @Override
    void deleteById(Long id);

    @Transactional
    void deleteByFileName(String fileName);

}
