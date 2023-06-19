package com.yohoard.notes;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HighlightRepository extends MongoRepository<Highlight, String> {
}
