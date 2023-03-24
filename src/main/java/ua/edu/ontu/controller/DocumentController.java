package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import ua.edu.ontu.dto.NewDocumentDto;
import ua.edu.ontu.model.entity.Document;
import ua.edu.ontu.service.DocumentService;
import ua.edu.ontu.service.FileService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FileService fileService;

    @GetMapping
    public String allDocumentsPage(Model model) {
        model.addAttribute("documents", documentService.findAll());
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
        if (document == null) {
            return "redirect:/document?documentNotFound";
        }
        model.addAttribute("document", document);
        return "document/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Document document = documentService.findById(id);
        model.addAttribute("document", document);
        return "document/edit";
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> download(@PathVariable("id") Long id) {
        Document document = documentService.findById(id);
        String fileName = document.getFileName();
        try {
            ByteArrayResource resource = fileService.getFileForDownloading(fileName);
            String encodedName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + encodedName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("document") Document document) {
        if (documentService.update(id, document)) {
            return "redirect:/document/" + id + "?success";
        }
        return "redirect:/document/" + id + "?error";
    }

    @GetMapping("/{id}/delete")
    public String deleteViaLink(@PathVariable("id") Long id) {
        return delete(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        Document document = documentService.findById(id);
        try {
            fileService.deleteFromSourcePath(document.getFileName());
            documentService.delete(id);
            return "redirect:/document?deletionSuccess";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/document?deletionFailed";
        }
    }

}
