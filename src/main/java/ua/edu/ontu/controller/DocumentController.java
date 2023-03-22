package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.edu.ontu.dto.NewDocumentDto;
import ua.edu.ontu.model.entity.Document;
import ua.edu.ontu.service.DocumentService;
import ua.edu.ontu.service.FileService;

import java.io.IOException;

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

    @GetMapping("/new")
    public String newPage(@ModelAttribute("document") NewDocumentDto documentDto,
                          Model model) {
        model.addAttribute("document", documentDto);
        return "document/new";
    }

    @PostMapping
    public String create(@ModelAttribute("document") NewDocumentDto documentDto,
                         RedirectAttributes redirectAttributes) {
        if (documentDto.getFile().isEmpty()) {
            redirectAttributes.addFlashAttribute("document", documentDto);
            return "redirect:/document/new?emptyFile";
        }
        if (documentService.isExist(documentDto.getFile().getOriginalFilename())) {
            redirectAttributes.addFlashAttribute("document", documentDto);
            return "redirect:/document/new?fileExist";
        }
        try {
            fileService.saveInSourcePath(documentDto.getFile());
            Document document = documentService.saveDto(documentDto);
            return "redirect:/document/" + document.getDocumentId() + "?success";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("document", documentDto);
            return "redirect:/document/new?error";
        }
    }

    @GetMapping("/{id}")
    public String documentPage(@PathVariable("id") Long id, Model model) {
        Document document = documentService.findById(id);
        model.addAttribute("document", document);
        return "document/info";
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
