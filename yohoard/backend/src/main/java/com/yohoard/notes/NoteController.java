package com.yohoard.notes;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController {

  @Autowired
  private NoteService noteService;

  @GetMapping("/list")
  public ResponseEntity<List<Note>> listAll() {
    var allNotes = noteService.getAllNotes();
    return new ResponseEntity<>(allNotes, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<Note> create(@RequestBody Note note) {
    var newNote = noteService.createNote(note);
    return new ResponseEntity<>(newNote, HttpStatus.CREATED);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") String id) {
    noteService.deleteNote(id);
    return new ResponseEntity<>("Successfully deleted a note.", HttpStatus.NO_CONTENT);
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<Note> update(@PathVariable("id") String id, @RequestBody Note note) {
    var updatedNote = noteService.updateNote(id, note);
    return new ResponseEntity<>(updatedNote, HttpStatus.OK);
  }
}
