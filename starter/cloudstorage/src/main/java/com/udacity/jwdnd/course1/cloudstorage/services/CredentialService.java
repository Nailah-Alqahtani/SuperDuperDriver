package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public List<Credential> getAllCredential(Integer userId) {
        return credentialMapper.getCredential(userId);
    }

    public int addCredential(Credential credential) {
        String encodedKey = generateKey();
        credential.setKey(encodedKey);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(),encodedKey));
        return credentialMapper.insertCredential(new Credential(null , credential.getUrl(), credential.getUserName(), credential.getKey(), credential.getPassword() , credential.getUserId()));
    }
    public void editCredential(Credential credential) {
        String encodedKey = generateKey();
        credential.setKey(encodedKey);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(),encodedKey));
        credentialMapper.updateCredential(new Credential(credential.getCredentialId() , credential.getUrl(), credential.getUserName(), credential.getKey(), credential.getPassword() , credential.getUserId()));
    }
    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
    private String generateKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }


}
