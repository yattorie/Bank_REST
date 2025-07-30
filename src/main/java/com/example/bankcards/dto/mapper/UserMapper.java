package com.example.bankcards.dto.mapper;

import com.example.bankcards.dto.user.UserCreateRequest;
import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.dto.user.UserUpdateRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role", qualifiedByName = "roleToString")
    UserResponse toDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "cards", ignore = true)
    User toEntity(UserCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "role", source = "role", qualifiedByName = "stringToRole")
    User toEntity(UserUpdateRequest dto);

    UserUpdateRequest toUpdateDto(User entity);

    UserCreateRequest toCreateDto(User entity);

    @Named("roleToString")
    default String roleToString(Role role) {
        return role == null ? null : role.name();
    }

    @Named("stringToRole")
    default Role stringToRole(String role) {
        return role == null ? null : Role.valueOf(role);
    }
}
