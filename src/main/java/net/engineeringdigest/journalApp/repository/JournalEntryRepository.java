package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.model.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry , ObjectId> {
    public Optional<JournalEntry> findByIdInAndTitle(List<ObjectId> ids, String title);
    public void deleteByTitle(String title);
}
