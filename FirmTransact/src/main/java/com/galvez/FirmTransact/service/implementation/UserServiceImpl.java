package com.galvez.FirmTransact.service.implementation;

import com.galvez.FirmTransact.dto.UserDTO;
import com.galvez.FirmTransact.dto.UserDTOMapper;
import com.galvez.FirmTransact.model.User;
import com.galvez.FirmTransact.repository.UserRepository;
import com.galvez.FirmTransact.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
