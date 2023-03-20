package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.edu.ontu.model.entity.Document;
import ua.edu.ontu.service.DocumentService;
import ua.edu.ontu.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FileService fileService;

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
    public String newPage() {
        return "document/new";
    }

    @PostMapping
    public String create(@RequestParam("file") MultipartFile file,
                         @RequestParam("description") String description) {
        if (file.isEmpty()) {
            return "redirect:/document/new?emptyFile";
        }
        Document document;
        document = documentService.findByFileName(file.getOriginalFilename());
        if (document != null) {
            return "redirect:/document/new?fileExist";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = fileService.createInSourcePath(file.getOriginalFilename());
            Files.write(path, bytes);

            document = new Document();
            document.setFileName(file.getOriginalFilename());
            document.setDescription(description);
            documentService.save(document);

            return "redirect:/document?success";
        } catch (IOException e) {
            e.printStackTrace();
        }
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
