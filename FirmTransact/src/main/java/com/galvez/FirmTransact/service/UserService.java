package com.galvez.FirmTransact.service;

import com.galvez.FirmTransact.dto.UserDTO;
import com.galvez.FirmTransact.model.User;

public interface UserService {
    UserDTO createUser(User user);
}
