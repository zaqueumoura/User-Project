package project.service;

import org.springframework.cache.annotation.CacheEvict;
import project.model.User;

public interface ICacheToken {


    User getOne(String documentNumber);

    @CacheEvict(value = "user", key = "#documentNumber")
    User save(User user);
}
