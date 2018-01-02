package jp.aibax.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.aibax.data.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByLoginId(String loginId);
}
