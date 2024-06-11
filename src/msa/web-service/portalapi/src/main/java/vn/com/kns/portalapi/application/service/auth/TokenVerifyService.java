package vn.com.kns.portalapi.application.service.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.kns.portalapi.core.entity.app.TokenVerify;
import vn.com.kns.portalapi.core.entity.app.User;
import vn.com.kns.portalapi.data.repository.app.UserRepository;
import vn.com.kns.portalapi.data.repository.app.TokenVerifyRepository;
import vn.com.kns.portalapi.web.exception.BaseException;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class TokenVerifyService {

    @Autowired
    private TokenVerifyRepository tokenVerifyRepository;

    @Autowired
    private UserRepository userRepository;

    public String generateVerifyToken(Long userId) {
        String token = UUID.randomUUID().toString();
        TokenVerify tokenVerify = new TokenVerify();
        tokenVerify.setToken(token);
        tokenVerify.setUserId(userId);
        tokenVerify.setIsVerify(false);
        tokenVerifyRepository.save(tokenVerify);
        return token;
    }

    public void verifyAccount(String token) throws Exception {
        Optional<TokenVerify> tokenVerify = tokenVerifyRepository.findByToken(token);
        fetchUserAndEnable(tokenVerify.orElseThrow(() -> new BaseException("Invalid token verify")));
    }

    private void fetchUserAndEnable(TokenVerify tokenVerify) throws Exception {
        if (tokenVerify.getIsVerify() == true) {
            throw new BaseException("Account has been activated");
        }
        String username = tokenVerify.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BaseException("User not found with name - " + username));
        user.setEnabled(true);
        user.setEnabled(true);
        userRepository.save(user);

        tokenVerify.setIsVerify(true);
        tokenVerifyRepository.save(tokenVerify);
    }
}
