package org.douglas.blog.mapper;

import org.douglas.blog.dto.TagRequestDTO;
import org.douglas.blog.dto.TagResponseDTO;
import org.douglas.blog.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagResponseDTO toTagResponseDTO(Tag tag)
    {
        if (tag == null) {
            return null;
        }

        return new TagResponseDTO(
                tag.getId(),
                tag.getName(),
                tag.getSlug()
        );
    }

    public Tag toTag(TagRequestDTO tagRequestDTO)
    {
        if (tagRequestDTO == null) {
            return null;
        }

        Tag tag = new Tag();
        tag.setName(tagRequestDTO.name());
        tag.setSlug(tagRequestDTO.slug());

        return tag;
    }
}
