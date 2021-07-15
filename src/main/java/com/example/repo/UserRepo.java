package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User getUserByEmail(String email);
}
