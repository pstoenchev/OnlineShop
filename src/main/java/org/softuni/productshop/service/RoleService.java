package org.softuni.productshop.service;

import java.util.Set;

public interface RoleService {

    void seedRolesInDb();

    // void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers);

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);

}
