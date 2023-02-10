package com.youtube.random_code.user_dashboard_crud.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	UserEntity getUserEntityById(Integer id);
}
