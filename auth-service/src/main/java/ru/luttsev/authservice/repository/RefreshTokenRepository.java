package ru.luttsev.authservice.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.luttsev.authservice.model.entity.RefreshToken;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    @Query("delete RefreshToken rt where rt.user.email = :email")
    @Modifying
    @Transactional
    void deleteByEmail(@Param("email") String email);
}
