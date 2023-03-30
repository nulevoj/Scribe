package ua.edu.ontu.model.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ontu.model.entity.Template;

import java.util.Optional;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Long> {

    boolean existsByFilename(String filename);

    @Override
    Iterable<Template> findAll();

    @Override
    Optional<Template> findById(Long id);

    Template findByFilename(String filename);

    @Override
    Template save(Template template);

    @Override
    void delete(Template template);

    @Override
    void deleteById(Long id);

    @Transactional
    void deleteByFilename(String filename);

}
