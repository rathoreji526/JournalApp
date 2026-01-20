package net.engineeringdigest.journalApp.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entry")
@Data
public class JournalEntry {
    @Id
    private ObjectId id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String content;
}
