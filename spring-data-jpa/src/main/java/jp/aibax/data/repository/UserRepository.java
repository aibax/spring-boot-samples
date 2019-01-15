package jp.aibax.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.aibax.data.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findByLoginId(String loginId);
}
