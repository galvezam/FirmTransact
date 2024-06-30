package com.galvez.FirmTransact.query;

public class RoleQuery {

    public static final String INSERT_ROLE_TO_USER_QUERY =
            "INSERT INTO UserRoles (user_id, roles_id) VALUES (:userId, :rolesId)";
    public static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * FROM Roles WHERE name = :name";
}
