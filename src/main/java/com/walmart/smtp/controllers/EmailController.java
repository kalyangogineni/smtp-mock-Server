package com.walmart.smtp.controllers;

import com.walmart.smtp.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class EmailController {


    @Autowired
    Repository repository;

    @GetMapping("/email/{emailId}")
    @ResponseBody
    public String getEmail(@PathVariable String emailId) {
        return repository.getEmailData(emailId);
    }

    @GetMapping("/email")
    @ResponseBody
    public List<Map<String,Object>> getAllEmail(){
        return repository.getAllEmailData();
    }

    @DeleteMapping("/email")
    public void deleteAllEmail() {
        repository.deleteAllEmail();
    }

    @DeleteMapping("/email/{emailId}")
    public void deleteEmail(@PathVariable String emailId){
        repository.deleteEmailData(emailId);
    }

    @PostMapping("/email/{emailId}")
    @ResponseBody
    public String store(@PathVariable String emailId){
        repository.saveEmailData(emailId, "test");

        return "OK";
    }
}
