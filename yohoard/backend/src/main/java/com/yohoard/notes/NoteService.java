package com.yohoard.notes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  private static final Logger LOG = LoggerFactory.getLogger(NoteService.class);

  @Autowired
  private NoteRepository noteRepository;

  public List<Note> getAllNotes() {
    List<Note> notesList = noteRepository.findAll();
    LOG.info("Sent notesList of size: {}", notesList.size());
    return notesList;
  }

  public Note createNote(Note note) {
    Note newNote = noteRepository.save(note);
    LOG.info("Successfully created a note.");
    return newNote;
  }

  public void deleteNote(String id) {
    Optional<Note> existingNote = noteRepository.findById(id);

    if (existingNote.isPresent()) {
      String noteId = existingNote.get().getId();
      noteRepository.deleteById(noteId);
      LOG.info("Successfully deleted note with id: {}", id);
    } else {
      LOG.warn("Can't delete note of given id: {}", id);
    }
  }

  public Note updateNote(String noteId, Note editedNote) {
    Optional<Note> originalNote = noteRepository.findById(noteId);

    if (originalNote.isPresent()) {
      Note note = originalNote.get();
      note.setName(editedNote.getName());
      note.setText(editedNote.getText());
      note.setUpdatedAt(LocalDateTime.now());
      note.setCategories(editedNote.getCategories());
      note.setTags(editedNote.getTags());
      note.setHighlights(editedNote.getHighlights());
      note.setPriority(editedNote.getPriority());

      noteRepository.save(note);
      LOG.info("Successfully updated note with id: {}", noteId);
    } else {
      LOG.warn("Can't update note of given id: {}", noteId);
    }

    return editedNote;
  }
}
