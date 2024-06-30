package com.galvez.FirmTransact.repository.implementation;

import com.galvez.FirmTransact.exception.ApiException;
import com.galvez.FirmTransact.model.Role;
import com.galvez.FirmTransact.model.User;
import com.galvez.FirmTransact.repository.RoleRepository;
import com.galvez.FirmTransact.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.UUID;

import static com.galvez.FirmTransact.enumeration.RoleType.ROLE_USER;
import static com.galvez.FirmTransact.enumeration.VerificationType.ACCOUNT;
import static com.galvez.FirmTransact.query.UserQuery.*;
import static java.util.Map.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;

    private final RoleRepository<Role> roleRepository;

    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        // check unique email
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) {
            throw new ApiException("Email already in use.");
        }

        // if unique save user
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());

            // add role to user
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());

            // send verification url
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());

            // save url in verification table
            jdbc.update(INSERT_VERIFICATION_QUERY, of("userId", user.getId(), "url", verificationUrl));

            // send email to user with verification url
            /*emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT.getType());*/
            user.setEnabled(false);
            user.setLocked(false);
            return user;
        }
        /*catch (EmptyResultDataAccessException e) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        }*/
        catch(Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. :(");

        }

    }



    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }


    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verify/" + type + "/" + key).toUriString();
    }
}
