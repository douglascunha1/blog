package org.douglas.blog.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.douglas.blog.dto.TagRequestDTO;
import org.douglas.blog.dto.TagResponseDTO;
import org.douglas.blog.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/api/v1")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findAll());
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<TagResponseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findById(id));
    }

    @GetMapping("/tags/{name}")
    public ResponseEntity<TagResponseDTO> findByName(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findByName(name));
    }

    @GetMapping("/tags/{slug}")
    public ResponseEntity<TagResponseDTO> findBySlug(@PathVariable("slug") String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findBySlug(slug));
    }

    @PostMapping("/tags")
    public ResponseEntity<TagResponseDTO> create(@RequestBody @Valid TagRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(dto));
    }

    @PutMapping("/tags/{id}")
    public ResponseEntity<TagResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid TagRequestDTO dto) {
        return ResponseEntity.ok(tagService.update(id, dto));
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        tagService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
