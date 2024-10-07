package com.userServiceProject.repository;

import com.userServiceProject.model.User;
import com.userServiceProject.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String USER_TABLE_NAME = "user_table";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Inserts a new user into the database and returns the ID of the newly created user.
     * @param user The User object containing details to be inserted into the database.
     * @return The ID of the newly created user.
     */
    @Override
    public Long createUser(User user) {
        String sql = "INSERT INTO " + USER_TABLE_NAME + " (first_name, last_name, email, age, address) values (?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.getAddress()
        );
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
    }

    /**
     * Updates an existing user's details in the database.
     * @param user The User object containing the updated details.
     */
    @Override
    public void updateUser(User user) {
        String sql = "UPDATE " + USER_TABLE_NAME + " SET first_name=?, last_name=?, email=?, age=?, address=? WHERE user_id=?";
        jdbcTemplate.update(
                sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.getAddress(),
                user.getId()
        );
    }

    /**
     * Deletes a user from the database based on the given user ID.
     * @param id The ID of the user to be deleted.
     */
    @Override
    public void deleteUserById(Long id) {
        String sql = "DELETE FROM " + USER_TABLE_NAME + " WHERE user_id=?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * Retrieves a user from the database based on the given user ID.
     * @param id The ID of the user to be retrieved.
     * @return The User object if found, or null if not found.
     */
    @Override
    public User getUserById(Long id) {
        String sql = "SELECT * FROM " + USER_TABLE_NAME + " WHERE user_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserMapper());
    }

    /**
     * Registers an existing user by updating their joining date and status.
     * @param userId The ID of the user to be registered.
     * @return The updated User object with the new joining date and status.
     */
    @Override
    public User registerUser(Long userId) {
        User existingUser = getUserById(userId);
        if (existingUser != null) {
            String sql = "UPDATE " + USER_TABLE_NAME + " SET joining_date=?, status=? WHERE user_id=?";
            jdbcTemplate.update(sql, LocalDate.now(), true, userId);
        }
        return existingUser;
    }

    /**
     * Retrieves all users from the database.
     * @return A list of all User objects stored in the database.
     */
    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM " + USER_TABLE_NAME;
        return jdbcTemplate.query(sql, new UserMapper());
    }
}
