package com.yohoard.notes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  @Autowired
  private NoteRepository noteRepository;

  public List<Note> getAllNotes() {
    return noteRepository.findAll();
  }

  public Note createNote(Note note) {
    return noteRepository.save(note);
  }

  public void deleteNote(String id) {
    Optional<Note> searchedNote = noteRepository.findById(id);

    if (searchedNote.isPresent()) {
      String noteId = searchedNote.get().getId();
      noteRepository.deleteById(noteId);
    } else {
      // LOG that note of some id dont exist
    }
  }

  public Note updateNote(String noteId, Note dataToEdit) {
    Optional<Note> originalNote = noteRepository.findById(noteId);

    if (originalNote.isPresent()) {
      Note newNote = originalNote.get();
      newNote.setName(dataToEdit.getName());
      newNote.setText(dataToEdit.getText());
      newNote.setUpdatedAt(LocalDateTime.now());
      newNote.setCategories(dataToEdit.getCategories());
      newNote.setTags(dataToEdit.getTags());
      newNote.setHighlights(dataToEdit.getHighlights());
      newNote.setPriority(dataToEdit.getPriority());

      noteRepository.save(newNote);
    } else {
      // LOG that note of some id don't exist
    }

    return dataToEdit;
  }
}
