package com.dylandewit.skeleton.api.role;

import com.dylandewit.skeleton.api.base.BaseService;
import com.dylandewit.skeleton.api.role.dto.CreateRoleDto;
import com.dylandewit.skeleton.api.role.dto.ViewRoleDto;
import com.dylandewit.skeleton.api.role.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, ViewRoleDto, CreateRoleDto> {

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        super(roleRepository);
    }

    @Override
    protected ViewRoleDto mapToDto(Role role) {
        return new ViewRoleDto(role);
    }

    @Override
    protected Role mapForUpdate(Role role, CreateRoleDto createRoleDto) {
        return role;
    }
}
