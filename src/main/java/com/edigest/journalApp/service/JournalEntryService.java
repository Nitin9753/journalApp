package com.edigest.journalApp.service;

import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService  {
    @Autowired
    private JournalEntryRepository  journalEntryRepository;
    @Autowired
    private UserService userService;
    @Transactional

    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user=userService.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
//            user.setUsername(null);
            userService.saveUser(user);
        }catch(Exception e){
            System.out.print(e);
            throw new RuntimeException("An error occured while saving the entry: ", e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){
//        User user=userService.findByUsername(userName);
        journalEntryRepository.save(journalEntry);
//        user.getJournalEntries().add(saved);
//        userService.saveEntry(user);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public void deleteById(ObjectId id, String username){
        try {
            User user=userService.findByUsername(username);
            System.out.print("The username from the Journal Entry Service is: "+username);
            boolean removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.print("An error occurred while deleting the entry in journal entry services."+e+"\n");
            throw new RuntimeException("An error occurred while deleting the entry in journal entry services."+e);
        }
    }


//    public List<JournalEntry> findByUserName(String username){
//
//    }

    


}

//controller -->service ---> repository