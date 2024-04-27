package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
@AllArgsConstructor
public class HomeController {
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final FileService fileService;

    @GetMapping("/home")
    public String homeView(Model model, Authentication authentication ) {
        User user=userService.getUser(authentication.getName());

        if(user!=null) {
            List<String> files = fileService.getAllFiles(user.getUserId());
            model.addAttribute("files", files);
            List<Note> notes = noteService.getAllNotes(user.getUserId());
            model.addAttribute("notes",notes);
            List<Credential> credentials = credentialService.getAllCredential(user.getUserId());
            model.addAttribute("credentials",credentials);
        }
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
