package ua.edu.ontu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.edu.ontu.dto.MapWrapperDto;
import ua.edu.ontu.dto.NewTemplateDto;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Template;
import ua.edu.ontu.scribe.Scribe;
import ua.edu.ontu.service.ScribeService;
import ua.edu.ontu.service.SourceDirectoryFileService;
import ua.edu.ontu.service.UserDetailsServiceImpl;
import ua.edu.ontu.model.service.AccountService;
import ua.edu.ontu.model.service.TemplateService;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/templates")
public class TemplateController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private SourceDirectoryFileService fileService;

    @Autowired
    private ScribeService scribeService;

    @GetMapping
    public String allTemplatesPage(@RequestParam(value = "search", required = false) String search,
                                   Model model) {
        if (search == null || search.isBlank()) {
            model.addAttribute("templates", templateService.findAll());
        } else {
            model.addAttribute("templates", templateService.findAll(search));
        }
        return "template/all";
    }

    @GetMapping("/new")
    public String newPage(@ModelAttribute("template") NewTemplateDto templateDto, Model model) {
        model.addAttribute("template", templateDto);
        return "template/new";
    }

    @GetMapping("/{id}")
    public String viewPage(@PathVariable("id") Long id, Model model) {
        Template template = templateService.findById(id);
        if (template == null) {
            return "redirect:/templates?templateNotFound";
        }
        model.addAttribute("template", template);
        return "template/view";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Template template = templateService.findById(id);
        model.addAttribute("template", template);
        return "template/edit";
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> downloadTemplate(@PathVariable("id") Long id) {
        Template template = templateService.findById(id);
        String filename = template.getFilename();
        return fileService.download(filename);
    }

    @GetMapping("/{id}/delete")
    public String deleteViaLink(@PathVariable("id") Long id) {
        return delete(id);
    }

    @GetMapping("{id}/replace")
    public String replacePage(@PathVariable("id") Long id, Model model) {
        Template template = templateService.findById(id);
        Account account = accountService.findByEmail(userDetailsService.getEmailFromPrincipal());
        Map<String, String> map = scribeService.getPreliminaryReplacementMap(template.getFilename(), account);

        MapWrapperDto dto = new MapWrapperDto();
        dto.setMap(map);
        model.addAttribute("dto", dto);
        return "template/replace";
    }

    @GetMapping("{id}/replace/download")
    public ResponseEntity<ByteArrayResource> downloadDocument(
            @PathVariable("id") Long id,
            @RequestParam(name = "docx", required = false) String docxButton,
            @RequestParam(name = "pdf", required = false) String pdfButton,
            @ModelAttribute("dto") MapWrapperDto dto) {
        Template template = templateService.findById(id);
        String filename = template.getFilename();

        Scribe scribe = new Scribe(fileService.getFile(filename));
        scribe.replaceAll(dto.getMap());

        ResponseEntity<ByteArrayResource> response = null;
        if (docxButton != null) {
            response = scribeService.downloadDocx(scribe);
        }
        if (pdfButton != null) {
            response = scribeService.downloadPdf(scribe);
        }
        scribe.close();
        return response;
    }

    @PostMapping
    public String add(@ModelAttribute("template") NewTemplateDto templateDto,
                      RedirectAttributes redirectAttributes) {
        if (templateDto.getFile().isEmpty()) {
            redirectAttributes.addFlashAttribute("template", templateDto);
            return "redirect:/templates/new?emptyFile";
        }
        if (templateService.isExist(templateDto.getFile().getOriginalFilename())) {
            redirectAttributes.addFlashAttribute("template", templateDto);
            return "redirect:/templates/new?fileExist";
        }
        try {
            fileService.save(templateDto.getFile());
            Template template = templateService.saveDto(templateDto);
            return "redirect:/templates/" + template.getTemplateId() + "?success";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("template", templateDto);
            return "redirect:/templates/new?error";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("template") Template template) {
        if (templateService.update(id, template)) {
            return "redirect:/templates/" + id + "?success";
        }
        return "redirect:/templates/" + id + "?error";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        Template template = templateService.findById(id);
        try {
            templateService.delete(id);
            fileService.delete(template.getFilename());
            return "redirect:/templates?deletionSuccess";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/templates?deletionFailed";
        }
    }

}
