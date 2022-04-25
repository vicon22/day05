package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import java.util.Optional;

public interface UsersRepository {




    Optional<User> findById(Long id);
}
