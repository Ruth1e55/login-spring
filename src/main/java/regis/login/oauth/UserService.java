package regis.login.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import regis.login.domain.User;
import regis.login.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void processOAuthPostLogin(String username){
        User existUser = userRepository.findByFullname(username);

        if(existUser == null){
            User newUser = new User();
            newUser.setEnabled(true);
            newUser.setFullname(username);
            userRepository.save(newUser);
        }
    }

}
