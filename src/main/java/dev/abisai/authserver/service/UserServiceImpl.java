package dev.abisai.authserver.service;

import dev.abisai.authserver.model.User;
import dev.abisai.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }

        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }
}
