package com.edigest.journalApp.controller;


import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.JournalEntryService;
import com.edigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
//    private Map<String, JournalEntry> journalEntries=new HashMap<>();

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        System.out.print("get journal function called");

        List<JournalEntry> journalEntries=userService.findByUsername(username).getJournalEntries();

        if(journalEntries!=null && !journalEntries.isEmpty()){
            return new ResponseEntity<List<JournalEntry>>( journalEntries, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
         Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        System.out.print("get journal function called");
        try{
            User user=userService.findByUsername(userName);
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user= userService.findByUsername(userName);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry=journalEntryService.findById(myId);
            return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        System.out.print("delete journal entry functions is called\n");
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        journalEntryService.deleteById(myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{Id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId Id, @RequestBody JournalEntry newEntry){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.findByUsername(username);
        if(user==null){
            return new ResponseEntity<>("User not found with the username: "+username,HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x->x.getId().equals(Id)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry=journalEntryService.findById(Id);
            System.out.print("The value of the old journal entry is; "+ journalEntry+"\n");
            if(journalEntry.isPresent()){
                JournalEntry old=journalEntry.get();
                old.setTitle(!newEntry.getTitle().isEmpty() ?newEntry.getTitle(): old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent():old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<JournalEntry>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
