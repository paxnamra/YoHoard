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
    Optional<Note> searchedNote = noteRepository.findById(id);

    if (searchedNote.isPresent()) {
      String noteId = searchedNote.get().getId();
      noteRepository.deleteById(noteId);
      LOG.info("Successfully deleted note with id: {}", id);
    } else {
      LOG.warn("Can't delete note of given id: {}", id);
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
      LOG.info("Successfully updated note with id: {}", noteId);
    } else {
      LOG.warn("Can't update note of given id: {}", noteId);
    }

    return dataToEdit;
  }
}
