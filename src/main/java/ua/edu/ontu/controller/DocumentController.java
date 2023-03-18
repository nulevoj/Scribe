package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.ontu.model.entity.Document;
import ua.edu.ontu.service.DocumentService;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    public String allDocumentsPage(Model model) {
        model.addAttribute("document", new Document());
        return "document/all";
    }

    @GetMapping("/{id}")
    public String documentPage(@PathVariable("id") Long id, Model model) {
        Document document = documentService.findById(id);
        model.addAttribute("document", document);
        return "document/info";
    }

    @GetMapping("/new")
    public String newPage(Model model) {
        model.addAttribute("document", new Document());
        return "document/new";
    }

    @PostMapping
    public String create(@ModelAttribute("document") Document document) {
        documentService.save(document);
        return "redirect:/document";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Document document = documentService.findById(id);
        model.addAttribute("document", document);
        return "document/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("document") Document document) {
        documentService.update(id, document);
        return "redirect:/document/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        documentService.delete(id);
        return "redirect:/document";
    }

}
