package com.yohoard.notes.importer.firefox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"guid", "index", "id", "type", "keyword", "postData"})
public record BookmarkItem(
    String title,
    long dateAdded,
    long lastModified,
    int id,
    int typeCode,
    String uri,
    String iconUri,
    String root,
    String tags,
    BookmarkItem[] children
) {}