package com.example.taskapi.repository;

import com.example.taskapi.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    // Find by title (case-insensitive)
    List<Note> findByTitleContainingIgnoreCase(String title);

    // Find by archived status
    List<Note> findByArchived(boolean archived);

    // Find by tag
    List<Note> findByTagsContaining(String tag);

    // Custom MongoDB query
    @Query("{ 'archived': false, 'tags': ?0 }")
    List<Note> findActiveNotesByTag(String tag);
}