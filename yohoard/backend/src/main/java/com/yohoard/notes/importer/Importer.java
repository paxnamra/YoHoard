package com.yohoard.notes.importer;

import java.io.IOException;
import java.io.InputStream;

public interface Importer {
    void run(InputStream inputStream) throws IOException;
}
