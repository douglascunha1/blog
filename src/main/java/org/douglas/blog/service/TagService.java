package org.douglas.blog.service;

import jakarta.transaction.Transactional;
import org.douglas.blog.dto.TagRequestDTO;
import org.douglas.blog.dto.TagResponseDTO;
import org.douglas.blog.exception.ResourceNotFoundException;
import org.douglas.blog.exception.TagAlreadyExistsException;
import org.douglas.blog.mapper.TagMapper;
import org.douglas.blog.model.Tag;
import org.douglas.blog.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public  TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Transactional
    public TagResponseDTO create(TagRequestDTO dto) {
        if (
                tagRepository.findByName(dto.name()).isPresent() ||
                tagRepository.findBySlug(dto.slug()).isPresent()
        ) {
            throw new TagAlreadyExistsException("Name or Slug already exists");
        }

        Tag newTag = tagMapper.toTag(dto);

        Tag savedTag = tagRepository.save(newTag);

        return tagMapper.toTagResponseDTO(savedTag);
    }

    public List<TagResponseDTO> findAll() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toTagResponseDTO)
                .collect(Collectors.toList());
    }

    public TagResponseDTO findById(Long id) {
        return tagRepository.findById(id)
                .map(tagMapper::toTagResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id : " + id + " not found"));
    }

    public TagResponseDTO findByName(String name) {
        return tagRepository.findByName(name)
                .map(tagMapper::toTagResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with name : " + name + " not found"));
    }

    public TagResponseDTO findBySlug(String slug) {
        return tagRepository.findBySlug(slug)
                .map(tagMapper::toTagResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with Slug : " + slug + " not found"));
    }

    @Transactional
    public TagResponseDTO update(Long id, TagRequestDTO dto) {
        Tag existingTag = tagRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Tag with id : " + id + " not found"));

        tagRepository.findByName(dto.name())
                .filter(tag -> !tag.getId().equals(id))
                .ifPresent(tag -> {
                    throw new TagAlreadyExistsException("Name already exists");
                });

        tagRepository.findBySlug(dto.slug())
                .filter(tag -> !tag.getId().equals(id))
                .ifPresent(tag -> {
                    throw new TagAlreadyExistsException("Slug already exists");
                });

        existingTag.setName(dto.name());
        existingTag.setSlug(dto.slug());

        Tag savedTag = tagRepository.save((existingTag));

        return tagMapper.toTagResponseDTO(savedTag);
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
