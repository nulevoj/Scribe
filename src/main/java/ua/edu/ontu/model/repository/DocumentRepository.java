package ua.edu.ontu.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ontu.model.entity.Document;

import java.util.Optional;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    @Override
    Iterable<Document> findAll();

    @Override
    Optional<Document> findById(Long id);

    @Override
    Document save(Document document);

    @Override
    void delete(Document document);

    @Override
    void deleteById(Long id);

    @Transactional
    void deleteByFileName(String fileName);

    Document findByFileName(String fileName);

    boolean existsByFileName(String fileName);

}
