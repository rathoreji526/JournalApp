package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.DTO.JournalEntryDTO;
import net.engineeringdigest.journalApp.DTO.UpdateJournalDTO;
import net.engineeringdigest.journalApp.model.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER')")
    public String saveJournalEntry(@RequestBody JournalEntryDTO journalEntryDTO,
                                   @AuthenticationPrincipal UserDetails userDetails){
            String response = journalEntryService.saveJournalEntry(userDetails, journalEntryDTO );
            return response;

    }

    @GetMapping("/findByTitle")
    @PreAuthorize("hasRole('USER')")
    public JournalEntry findByTitle(@RequestParam String title,
                                    @AuthenticationPrincipal UserDetails userDetails){
        return journalEntryService.findEntryByTitle(userDetails , title);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('USER')")
    public List<JournalEntry> findAll(@AuthenticationPrincipal UserDetails userDetails){
        return journalEntryService.findAll(userDetails);
    }

    @PatchMapping("/updateContent")
    @PreAuthorize("hasRole('USER')")
    public String updateContentByTitle(
                                      @RequestBody UpdateJournalDTO dto,
                                      @AuthenticationPrincipal UserDetails userDetails){
            journalEntryService.updateContentByTitle(userDetails , dto);

        return ("content updated successfully.");
    }

    @DeleteMapping("/deleteByTitle")
    @PreAuthorize("hasRole('USER')")
    public String deleteByTitle(@RequestParam String title,
                                @AuthenticationPrincipal UserDetails userDetails){
        String response = journalEntryService.deleteEntryByTitle( userDetails , title);
        return response;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/test")
    public String testPreAuth(){
        return "Success";
    }
}
