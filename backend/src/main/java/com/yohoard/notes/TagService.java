package com.yohoard.notes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getOrCreate(Set<String> names) {
        List<Tag> tags = tagRepository.findByNameIn(names);

        Set<String> existingNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        Set<Tag> newTags = names.stream()
                .filter(name -> !existingNames.contains(name))
                .map(name -> new Tag(name, "", 0))
                .collect(Collectors.toSet());

        tagRepository.saveAll(newTags);

        tags.addAll(newTags);

        return tags;
    }
}
