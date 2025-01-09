package ru.luttsev.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.luttsev.authservice.model.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select count(u) = 1 from User u where u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Modifying
    @Query("update User u set u.isBlocked = true where u.id = :id")
    void blockById(@Param("id") UUID id);

    @Modifying
    @Query("update User u set u.isBlocked = false where u.id = :id")
    void unblockById(UUID id);
}
