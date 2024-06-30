# /*
#     Author: Matthew Galvez
#     Company: FirmTransact
#  */
#
#
# CREATE SCHEMA IF NOT EXISTS FirmTransact;
#
# SET NAMES 'UTF8MB4';
# SET TIME_ZONE = '-5:00';
#
# USE FirmTransact;
#
#
# /*
#     Creates table for Users
# */
#
# DROP TABLE IF EXISTS Users;
#
# CREATE TABLE Users
# (
#     user_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     first_name VARCHAR(100) NOT NULL,
#     last_name VARCHAR(100) NOT NULL,
#     email VARCHAR(200) NOT NULL,
#     password VARCHAR(100) DEFAULT NULL,
#     address VARCHAR(200) DEFAULT NULL,
#     phone VARCHAR(25) DEFAULT NULL,
#     title VARCHAR(50) DEFAULT NULL,
#     bio VARCHAR(255) DEFAULT NULL,
#     enabled BOOLEAN DEFAULT FALSE,
#     locked BOOLEAN DEFAULT FALSE,
#     using_mfa BOOLEAN DEFAULT FALSE,
#     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
#     image_url VARCHAR(255) DEFAULT 'default_profile_icon.png',
#     CONSTRAINT UQ_Users_Email UNIQUE (email)
# );
#
#
# /*
#     Creates table for Roles
# */
#
# DROP TABLE IF EXISTS Roles;
#
# CREATE TABLE Roles
# (
#     roles_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     name VARCHAR(200) NOT NULL,
#     permission VARCHAR(255) DEFAULT NULL,
#     CONSTRAINT UQ_Roles_Name UNIQUE (name)
# );
#
#
# /*
#     Creates table for User Roles
# */
# DROP TABLE IF EXISTS UserRoles;
#
# CREATE TABLE UserRoles
# (
#     user_roles_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     user_id BIGINT UNSIGNED NOT NULL,
#     roles_id BIGINT UNSIGNED NOT NULL,
#     FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
#     FOREIGN KEY (roles_id) REFERENCES Roles (roles_id) ON DELETE RESTRICT ON UPDATE CASCADE,
#     CONSTRAINT UQ_User_Roles_user_id UNIQUE (user_id),
#     CONSTRAINT UQ_User_Roles_roles_id UNIQUE (roles_id)
# );
#
#
# /*
#     Creates table for Events
# */
#
# DROP TABLE IF EXISTS Events;
#
# CREATE TABLE Events
# (
#     event_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     type VARCHAR(50) NOT NULL CHECK ( type IN ('LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE', 'LOGIN_ATTEMPT_SUCCESS', 'PROFILE_UPDATE', 'PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE', 'ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE', 'MFA_UPDATE')),
#     description VARCHAR(255) DEFAULT NULL,
#     CONSTRAINT UQ_Events_Type UNIQUE (type)
# );
#
#
# /*
#     Creates table for User Events
# */
#
# DROP TABLE IF EXISTS UserEvents;
#
# CREATE TABLE UserEvents
# (
#     user_events_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     user_id BIGINT UNSIGNED NOT NULL,
#     event_id BIGINT UNSIGNED NOT NULL,
#     device VARCHAR(100) DEFAULT NULL,
#     ip_address VARCHAR(100) DEFAULT NUll,
#     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
#     FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
#     FOREIGN KEY (event_id) REFERENCES Events (event_id) ON DELETE RESTRICT ON UPDATE CASCADE
# );
#
# /*
#     Creates table for Account Verification
#     Use the date comment in future to check if url is
#     valid after a certain time period.
# */
# DROP TABLE IF EXISTS AccountVerifications;
#
# CREATE TABLE AccountVerifications
# (
#     account_verifications_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     user_id BIGINT UNSIGNED NOT NULL,
#     url VARCHAR(255) NOT NUll,
#     -- date DATETIME NOT NULL,
#     FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
#     CONSTRAINT UQ_AccountVerifications_user_id UNIQUE (user_id),
#     CONSTRAINT UQ_AccountVerifications_url UNIQUE (url)
# );
#
#
# /*
#     Creates table for Reset Password Verifications
# */
#
# DROP TABLE IF EXISTS ResetPasswordVerifications;
#
# CREATE TABLE ResetPasswordVerifications
# (
#     reset_password_verifications_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     user_id BIGINT UNSIGNED NOT NULL,
#     url VARCHAR(255) NOT NUll,
#     expiration_date DATETIME NOT NULL,
#     FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
#     CONSTRAINT UQ_ResetPasswordVerifications_user_id UNIQUE (user_id),
#     CONSTRAINT UQ_ResetPasswordVerifications_url UNIQUE (url)
# );
#
#
# /*
#     Creates table for Two Factor Verification
# */
#
# DROP TABLE IF EXISTS TwoFactorVerifications;
#
# CREATE TABLE TwoFactorVerifications
# (
#     two_factor_verifications_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
#     user_id BIGINT UNSIGNED NOT NULL,
#     code VARCHAR(10) NOT NULL,
#     expiration_date DATETIME NOT NULL,
#     FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
#     CONSTRAINT UQ_TwoFactorVerifications_user_id UNIQUE (user_id),
#     CONSTRAINT UQ_TwoFactorVerifications_code UNIQUE (code)
# );
