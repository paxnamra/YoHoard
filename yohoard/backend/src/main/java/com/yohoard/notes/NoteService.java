package com.yohoard.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  @Autowired
  private NoteRepository noteRepository;

  public Note createNote(Note note) {
    return noteRepository.save(note);
  }
}
