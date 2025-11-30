package com.example.taskapi.controller;

import com.example.taskapi.model.Note;
import com.example.taskapi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(
            @RequestParam(required = false) Boolean archived) {
        if (archived != null && !archived) {
            return ResponseEntity.ok(noteService.getActiveNotes());
        }
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable String id) {
        return noteService.getNoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Note>> searchNotes(@RequestParam String title) {
        return ResponseEntity.ok(noteService.searchByTitle(title));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<Note>> getNotesByTag(@PathVariable String tag) {
        return ResponseEntity.ok(noteService.getNotesByTag(tag));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note created = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable String id,
            @RequestBody Note note) {
        try {
            return ResponseEntity.ok(noteService.updateNote(id, note));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Note> archiveNote(@PathVariable String id) {
        try {
            return ResponseEntity.ok(noteService.archiveNote(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}