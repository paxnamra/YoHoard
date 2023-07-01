package com.yohoard.notes;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface TagRepository extends MongoRepository<Tag, String> {
    List<Tag> findByNameIn(Set<String> names);
}
