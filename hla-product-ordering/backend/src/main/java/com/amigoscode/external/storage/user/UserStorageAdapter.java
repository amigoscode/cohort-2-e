package com.amigoscode.external.storage.user;

import com.amigoscode.domain.user.PageUser;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserAlreadyExistsException;
import com.amigoscode.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public
class UserStorageAdapter implements UserRepository {

    private final JpaUserRepository userRepository;
    private final UserEntityMapper mapper;

    @Override
    public User save(final User user) {
        try {
            UserEntity saved = userRepository.save(mapper.toEntity(user));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            log.warning("User " + user.getEmail() + " already exits in db");
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public void update(final User user) {
        userRepository.findById(user.getId()).ifPresent(userEntity -> userRepository.save(mapper.toEntity(user)));
    }

    @Override
    public void remove(final Integer id) {
        userRepository.findById(id).ifPresent(userEntity -> userRepository.deleteById(id));
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return userRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(final Integer id) {
        return userRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageUser findAllByName(String userName, Pageable pageable) {
        Page<UserEntity> pageOfUsersEntity = userRepository.findAllByNameContainingIgnoreCase(userName, pageable);
        List<User> usersOnCurrentPage = pageOfUsersEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageUser(
                usersOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfUsersEntity.getTotalPages(),
                pageOfUsersEntity.getTotalElements()
        );
    }
}
