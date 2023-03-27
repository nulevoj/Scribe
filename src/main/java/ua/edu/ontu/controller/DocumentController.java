package ua.edu.ontu.controller;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.edu.ontu.dto.MapWrapperDto;
import ua.edu.ontu.dto.NewDocumentDto;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Document;
import ua.edu.ontu.service.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private SourceDirectoryFileService fileService;

    @Autowired
    private ScribeService scribeService;

    @GetMapping
    public String allPage(Model model) {
        model.addAttribute("documents", documentService.findAll());
        return "document/all";
    }

    @PostMapping
    public String add(@ModelAttribute("document") NewDocumentDto documentDto,
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
            fileService.save(documentDto.getFile());
            Document document = documentService.saveDto(documentDto);
            return "redirect:/document/" + document.getDocumentId() + "?success";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("document", documentDto);
            return "redirect:/document/new?error";
        }
    }

    @GetMapping("/new")
    public String addPage(@ModelAttribute("document") NewDocumentDto documentDto, Model model) {
        model.addAttribute("document", documentDto);
        return "document/new";
    }

    @GetMapping("/{id}")
    public String viewPage(@PathVariable("id") Long id, Model model) {
        Document document = documentService.findById(id);
        if (document == null) {
            return "redirect:/document?documentNotFound";
        }
        model.addAttribute("document", document);
        return "document/view";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Document document = documentService.findById(id);
        model.addAttribute("document", document);
        return "document/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("document") Document document) {
        if (documentService.update(id, document)) {
            return "redirect:/document/" + id + "?success";
        }
        return "redirect:/document/" + id + "?error";
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> downloadTemplate(@PathVariable("id") Long id) {
        Document document = documentService.findById(id);
        String fileName = document.getFileName();
        return fileService.download(fileName);
    }

    @GetMapping("{id}/replace")
    public String replacePage(@PathVariable("id") Long id, Model model) {
        Document document = documentService.findById(id);
        Account account = accountService.findByEmail(userDetailsService.getEmailFromPrincipal());
        Map<String, String> map = scribeService.getPreliminaryReplacementMap(document.getFileName(), account);

        MapWrapperDto dto = new MapWrapperDto();
        dto.setMap(map);
        model.addAttribute("dto", dto);
        return "document/replace";
    }

    @GetMapping("{id}/replace/download")
    public ResponseEntity<ByteArrayResource> downloadXWPFDocument(
            @PathVariable("id") Long id, @ModelAttribute("dto") MapWrapperDto dto) throws IOException {
        Map<String, String> map = dto.getMap();
        Document document = documentService.findById(id);
        String fileName = document.getFileName();
        XWPFDocument xwpfDocument = scribeService.replaceAll(fileName, map);
        ResponseEntity<ByteArrayResource> response = fileService.download(xwpfDocument, fileName);
        xwpfDocument.close();
        return response;
    }

    @GetMapping("/{id}/delete")
    public String deleteViaLink(@PathVariable("id") Long id) {
        return delete(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        Document document = documentService.findById(id);
        try {
            documentService.delete(id);
            fileService.delete(document.getFileName());
            return "redirect:/document?deletionSuccess";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/document?deletionFailed";
        }
    }

}
