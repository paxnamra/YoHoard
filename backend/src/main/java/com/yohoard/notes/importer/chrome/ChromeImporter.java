package com.yohoard.notes.importer.chrome;

import com.yohoard.notes.importer.Importer;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ChromeImporter implements Importer {
    @Override
    public void run(InputStream inputStream) {

//        LocalDateTime.ofInstant(Instant.ofEpochSecond());
    }
}
