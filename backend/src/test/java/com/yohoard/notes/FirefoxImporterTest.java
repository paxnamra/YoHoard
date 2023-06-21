package com.yohoard.notes;

import com.yohoard.notes.importer.firefox.FirefoxImporter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Example;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
class FirefoxImporterTest {

    @Autowired
    FirefoxImporter firefoxImporter;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    NoteRepository noteRepository;

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6-jammy");

    @Test
    void testSaveItem() throws IOException {
        firefoxImporter.run(getClass().getClassLoader().getResourceAsStream("test/importer/firefox.json"));

        assertEquals(4, categoryRepository.count());

        assertArrayEquals(
                new String[]{"Mozilla Firefox", "Ubuntu and Free Software links", "New folder", "Subfolder"},
                categoryRepository.findAll().stream().map(Category::getName).toArray()
        );

        assertEquals(12, noteRepository.count());

        Note exampleNote = new Note();
        exampleNote.setName("Getting Started");

        Optional<Note> gettingStarted = noteRepository.findOne(Example.of(exampleNote));

        assertArrayEquals(
                new String[]{"default", "firefox"},
                gettingStarted.get().getTags().stream().map(Tag::getName).toArray()
        );
    }
}
