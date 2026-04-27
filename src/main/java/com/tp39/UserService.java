package com.tp39;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private com.tp39.userRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable avec l'id : " + id));
    }

    public User updateUser(int id, User updatedUser) {
        User user = getUserById(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        user.setPassword(updatedUser.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public List<User> findUsersByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public List<User> findUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Utilisateur introuvable avec l'email : " + email);
        }
        return user;
    }

    public User createUserWithProfile(User user) {
        if (user.getProfile() != null) {
            user.getProfile().setUser(user); // maintenir la relation bidirectionnelle
        }
        return userRepository.save(user);
    }

    public Page<User> getUsersWithPagination(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}