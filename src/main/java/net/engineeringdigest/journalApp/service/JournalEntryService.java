package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.DTO.JournalEntryDTO;
import net.engineeringdigest.journalApp.DTO.UpdateJournalDTO;
import net.engineeringdigest.journalApp.exceptions.*;
import net.engineeringdigest.journalApp.model.JournalEntry;
import net.engineeringdigest.journalApp.model.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    @Transactional
    public String saveJournalEntry( UserDetails userDetails,
                                    JournalEntryDTO journalEntryDTO){
        String username = userDetails.getUsername();


        User user = userService.findByUsername(username);
        String title = journalEntryDTO.getTitle();
        for(JournalEntry entry : user.getJournalEntries()){
            if(entry.getTitle().equals(title)){
                throw new JournalEntryWithSameTitleException("Can not save journal entry with same title.");
            }
        }
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(journalEntryDTO.getTitle());
        journalEntry.setContent(journalEntryDTO.getContent());
        journalEntry.setCreatedAt(LocalDateTime.now());
        journalEntry.setUpdatedAt(LocalDateTime.now());
        JournalEntry saved =  journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
        return "Entry saved successfully.";
    }

    public JournalEntry findEntryByTitle(UserDetails userDetails,
                                         String title){
        String username = userDetails.getUsername();

        if(!username.equals(userDetails.getUsername())){
            throw new AccessDeniedException("You are not allowed to perform this action!");
        }

        List<ObjectId> ids = userService.findByUsername(username)
                .getJournalEntries()
                .stream()
                .map(JournalEntry::getId)
                .toList();

        return journalEntryRepository.findByIdInAndTitle(ids , title)
                .orElseThrow(()->
                        new NotFoundException("Journal entry not found with title: "+title)
                );
    }

    public List<JournalEntry> findAll(UserDetails userDetails){
        String username = userDetails.getUsername();

        return userService.findByUsername(username).getJournalEntries();
    }

    public void updateContentByTitle(UserDetails userDetails,
                                     UpdateJournalDTO dto){

        String username = userDetails.getUsername();

        List<ObjectId> ids = userService.findByUsername(username)
                .getJournalEntries()
                .stream()
                .map(JournalEntry::getId)
                .toList();

        JournalEntry journalEntry = journalEntryRepository.findByIdInAndTitle(ids, dto.getTitle())
                .orElseThrow(() ->
                        new NotFoundException("Entry not found with title: "+ dto.getTitle()));

        if(dto.getContent().isBlank()){
            throw new NullContentException("Please enter a valid content");
        }

        if(Objects.equals(dto.getContent() , journalEntry.getContent())){
            throw new NoChangeException("Content already up to date.");
        }
        journalEntry.setContent(dto.getContent());
        journalEntry.setUpdatedAt(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);

    }

    public String deleteEntryByTitle(UserDetails userDetails, String title){
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        List<ObjectId> ids = userService.findByUsername(username).getJournalEntries()
                .stream()
                .map(JournalEntry::getId)
                .toList();

        JournalEntry journalEntry = journalEntryRepository.findByIdInAndTitle(ids , title)
                .orElseThrow(()->new NotFoundException("Entry not found with title: "+title));


        journalEntryRepository.deleteByTitle(title);
        userService.userRepository.pullJournalEntryFromUser(user.getEmail() , journalEntry.getId());
        return "Entry deleted successfully.";
    }

}
