package org.douglas.blog.service;

import jakarta.transaction.Transactional;
import org.douglas.blog.dto.UserRequestDTO;
import org.douglas.blog.dto.UserResponseDTO;
import org.douglas.blog.exception.ResourceNotFoundException;
import org.douglas.blog.exception.UserAlreadyExistsException;
import org.douglas.blog.mapper.UserMapper;
import org.douglas.blog.model.User;
import org.douglas.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        if (
                userRepository.findByEmail(dto.email()).isPresent() ||
                userRepository.findByUsername(dto.username()).isPresent()
        ){
            throw new UserAlreadyExistsException("Username or Email already exists");
        }

        User newUser = userMapper.toUser(dto);

        User savedUser = userRepository.save(newUser);

        return userMapper.toUserResponseDTO(savedUser);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
    }

    public UserResponseDTO findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User with name: " + username + " not found"));
    }

    public UserResponseDTO findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User with email: " + email + " not found"));
    }

    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));

        userRepository.findByUsername(dto.username())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Username already exists");
                });

        userRepository.findByEmail(dto.email())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Email already exists");
                });

        existingUser.setUsername(dto.username());
        existingUser.setEmail(dto.email());
        existingUser.setPassword(dto.password());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toUserResponseDTO(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
