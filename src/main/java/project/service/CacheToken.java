package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;
import project.errors.UnauthorizedException;
import project.model.User;
import project.repository.UserRepository;

@Component
@RequiredArgsConstructor
@RedisHash(timeToLive = 200)
public class CacheToken implements ICacheToken {

    private final UserRepository userRepository;

    @Cacheable(value = "user", key = "#documentNumber")
    public User getOne(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new UnauthorizedException("Usuário incorreto"));

    }

    @CacheEvict(value = "user", key = "#user.documentNumber")
    public User save(User user) {
        return userRepository.save(user);

    }
}
