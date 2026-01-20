package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.DTO.JournalEntryDTO;
import net.engineeringdigest.journalApp.DTO.UpdateJournalDTO;
import net.engineeringdigest.journalApp.exceptions.NoChangeException;
import net.engineeringdigest.journalApp.exceptions.NullContentException;
import net.engineeringdigest.journalApp.exceptions.ResourceNotFoundException;
import net.engineeringdigest.journalApp.model.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;

    @PostMapping("/save")
    public ResponseEntity<String> saveJournalEntry(@RequestBody JournalEntryDTO journalEntryDTO,
                                                   @AuthenticationPrincipal UserDetails userDetails){
        try{
            String response = journalEntryService.saveJournalEntry(userDetails, journalEntryDTO );
            return new ResponseEntity<>(response , HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/findByTitle")
    public ResponseEntity<JournalEntry> findByTitle(@RequestParam String title,
                                                    @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(journalEntryService.findEntryByTitle(userDetails , title) , HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public List<JournalEntry> findAll(@AuthenticationPrincipal UserDetails userDetails){
        return journalEntryService.findAll(userDetails);
    }

    @PatchMapping("/updateContent")
    public ResponseEntity<String> updateContentByTitle(
                                      @RequestBody UpdateJournalDTO dto,
                                      @AuthenticationPrincipal UserDetails userDetails){
        try{
            journalEntryService.updateContentByTitle(userDetails , dto);
        } catch (NullContentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        catch (NoChangeException e){
            return ResponseEntity
                    .ok(e.getMessage());
        }catch (ResourceNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("content updated successfully.");
    }

    @DeleteMapping("/deleteByTitle")
    public String deleteByTitle(@RequestParam String title,
                                @AuthenticationPrincipal UserDetails userDetails){
        String response = journalEntryService.deleteEntryByTitle( userDetails , title);
        return response;
    }
}
