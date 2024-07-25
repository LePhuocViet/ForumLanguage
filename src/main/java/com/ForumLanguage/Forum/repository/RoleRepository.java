package com.ForumLanguage.Forum.repository;

import com.ForumLanguage.Forum.enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

   Role findByIdAccount(int idAccount);
}
