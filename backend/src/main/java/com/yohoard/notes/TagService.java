package com.yohoard.notes;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getOrCreate(List<String> names) {
        List<Tag> existingTags = tagRepository.findByNameIn(names);

        Set<String> existingNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        List<Tag> newTags = names.stream()
                .filter(name -> !existingNames.contains(name))
                .map(name -> new Tag(name, "", 0))
                .toList();

        tagRepository.saveAll(newTags);

        List<Tag> allTags = new ArrayList<>(existingTags);

        allTags.addAll(newTags);

        return allTags;
    }
}
