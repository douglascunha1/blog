package org.douglas.blog.dto;

import jakarta.validation.constraints.NotEmpty;

public record TagRequestDTO(
        @NotEmpty(message = "Name is required") String name,
        @NotEmpty(message = "Slug is required") String slug
) {
}
