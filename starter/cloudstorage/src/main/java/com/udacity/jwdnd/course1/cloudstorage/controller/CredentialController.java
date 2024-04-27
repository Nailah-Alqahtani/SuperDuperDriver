package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    @PostMapping("/add-credential")
    public String addCredential(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        try{
        User user=userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());
        if(credential.getCredentialId()==null){
            credentialService.addCredential(credential);
            redirectAttributes.addFlashAttribute("operationSuccess","New credential is created successfully!");
        }
        else {
            credentialService.editCredential(credential);
            redirectAttributes.addFlashAttribute("operationSuccess","Credential is updated successfully!");
        }} catch (Exception e)
        {
            redirectAttributes.addFlashAttribute("operationFailed","Something went wrong!");
        }
        return "redirect:/home";
    }
    @GetMapping("/delete-credential/{credentialId}")
    public String noteCredential(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes) {
        try {
            credentialService.deleteCredential(credentialId);
            redirectAttributes.addFlashAttribute("operationSuccess", "Credential is deleted successfully!");
        } catch (Exception e)
        {
            redirectAttributes.addFlashAttribute("operationFailed","Something went wrong!");
        }
        return "redirect:/home";

    }
}
