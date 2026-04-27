package com.tp39;   // ← AJOUTER CETTE LIGNE EN PREMIER

import com.tp39.User;
import com.tp39.UserService;
import com.tp39.userDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/users")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    private userDTO convertToDTO(User user) {
        return modelMapper.map(user, userDTO.class);
    }

    @GetMapping
    public List<userDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<userDTO> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(convertToDTO(userService.getUserById(id)));
    }

    @PostMapping
    public ResponseEntity<userDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);
        if (user.getProfile() != null) {
            user.getProfile().setUser(user);
        }
        if (userCreateDTO.getRoles() != null) {
            List<Role> persistedRoles = userCreateDTO.getRoles().stream()
                    .map(r -> roleRepository.findByName(r.getName())
                            .orElseThrow(() -> new RuntimeException("Role not found: " + r.getName())))
                    .collect(Collectors.toList());
            user.setRoles(persistedRoles);
        }
        User created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<userDTO> updateUser(@PathVariable int id,
                                              @Valid @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/with-profile")
    public ResponseEntity<userDTO> createUserWithProfile(@RequestBody User user) {
        User created = userService.createUserWithProfile(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(created));
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<userDTO>> getUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.getUsersWithPagination(pageable);
        List<userDTO> users = userPage.getContent().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}