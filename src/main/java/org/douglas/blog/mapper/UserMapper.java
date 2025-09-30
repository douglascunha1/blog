package org.douglas.blog.mapper;

import org.douglas.blog.dto.UserRequestDTO;
import org.douglas.blog.dto.UserResponseDTO;
import org.douglas.blog.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toUserResponseDTO(final User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public User toUser(final UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userRequestDTO.username());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        return user;
    }
}
