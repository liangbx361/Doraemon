package com.droaemon.common.util;

import org.springframework.data.repository.CrudRepository;

import java.rmi.NoSuchObjectException;
import java.util.Optional;

public class JpaUtil {

    public static <T> T findById(CrudRepository<T, Long> repository, Long id) throws NoSuchObjectException {
        Optional<T> optional = repository.findById(id);
        if(!optional.isPresent()) {
            throw new NoSuchObjectException( id + " is not exit");
        }

        return optional.get();
    }
}
