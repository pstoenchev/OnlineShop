package org.softuni.productshop.repository;

import org.softuni.productshop.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByAuthority(String authority);

}
