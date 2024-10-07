package com.userServiceProject.service;

import com.userServiceProject.feignClientPollService.PollService;
import com.userServiceProject.model.User;
import com.userServiceProject.repository.UserRepository;
import jdk.security.jarsigner.JarSignerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollService pollService;

    /**
     * Creates a new user by delegating the operation to the repository.
     * @param user The User object containing the details to be saved.
     * @return The ID of the newly created user.
     */
    @Override
    public Long createUser(User user) {
        return userRepository.createUser(user);
    }

    /**
     * Updates an existing user's details by calling the update method in the repository.
     * @param user The User object containing the updated details.
     */
    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    /**
     * Deletes a user by their ID and also removes all associated answers from the poll service.
     * @param id The ID of the user to be deleted.
     */
    @Override
    public void deleteUserById(Long id) {
        // Delete user by ID in the user repository
        userRepository.deleteUserById(id);
        // Delete all user-related answers using the PollService
        pollService.deleteAllUserAnswers(id);
    }

    /**
     * Retrieves a user by their ID by calling the corresponding method in the repository.
     * @param id The ID of the user to be retrieved.
     * @return The User object if found, otherwise null.
     * @throws JarSignerException if the user cannot be found or if an error occurs.
     */
    @Override
    public User getUserById(Long id) throws JarSignerException {
        return userRepository.getUserById(id);
    }

    /**
     * Registers a user by updating their registration details.
     * @param userId The ID of the user to be registered.
     * @return The updated User object with registration information.
     */
    @Override
    public User registerUser(Long userId) {
        return userRepository.registerUser(userId);
    }

    /**
     * Checks if a user is registered based on their status.
     * @param userId The ID of the user to check.
     * @return True if the user is registered, otherwise false.
     */
    @Override
    public Boolean isUserRegistered(Long userId) {
        User user = userRepository.getUserById(userId);
        return user != null && user.getStatus();
    }

    /**
     * Retrieves a list of all users.
     * @return A list of all User objects stored in the database.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
