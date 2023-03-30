package ua.edu.ontu.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ontu.dto.NewTemplateDto;
import ua.edu.ontu.model.entity.Template;
import ua.edu.ontu.model.service.repository.TemplateRepository;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public boolean isExist(String filename) {
        return templateRepository.existsByFilename(filename);
    }

    public Iterable<Template> findAll() {
        return templateRepository.findAll();
    }

    public Template findById(Long id) {
        return templateRepository.findById(id).get();
    }

    public Template findByFilename(String filename) {
        return templateRepository.findByFilename(filename);
    }

    public Template saveDto(NewTemplateDto templateDto) {
        Template template = new Template();
        template.setFilename(templateDto.getFile().getOriginalFilename());
        template.setDescription(templateDto.getDescription());
        return save(template);
    }

    public Template save(Template template) {
        return templateRepository.save(template);
    }

    public boolean update(Long id, Template fresh) {
        Template template = findById(id);
        if (template == null) {
            return false;
        }
        template.setDescription(fresh.getDescription());
        save(template);
        return true;
    }

    public void delete(Template template) {
        templateRepository.delete(template);
    }

    public void delete(Long id) {
        templateRepository.deleteById(id);
    }

    public void delete(String filename) {
        templateRepository.deleteByFilename(filename);
    }

}
