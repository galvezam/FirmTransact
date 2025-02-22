package com.galvez.FirmTransact.repository.implementation;

import com.galvez.FirmTransact.exception.ApiException;
import com.galvez.FirmTransact.rowmapper.RoleRowMapper;
import com.galvez.FirmTransact.model.Role;
import com.galvez.FirmTransact.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static com.galvez.FirmTransact.enumeration.RoleType.ROLE_USER;
import static com.galvez.FirmTransact.query.RoleQuery.INSERT_ROLE_TO_USER_QUERY;
import static com.galvez.FirmTransact.query.RoleQuery.SELECT_ROLE_BY_NAME_QUERY;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {


    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);
        try {
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleName), new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, of("userId", userId, "rolesId", requireNonNull(role).getId()));
        }
        catch (EmptyResultDataAccessException e) {
            throw new ApiException("No role found by name; " + ROLE_USER.name());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. :(");
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
