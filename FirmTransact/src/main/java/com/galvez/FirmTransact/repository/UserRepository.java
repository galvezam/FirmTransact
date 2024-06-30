package com.galvez.FirmTransact.repository;

import com.galvez.FirmTransact.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface UserRepository <T extends User> {

    /* CRUD Operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);

    T get(Long id);
    T update(T data);
    Boolean delete(Long id);
}
