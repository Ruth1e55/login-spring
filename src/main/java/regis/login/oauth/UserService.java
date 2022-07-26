package regis.login.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import regis.login.domain.Role;
import regis.login.domain.User;
import regis.login.repository.UserRepository;
import regis.login.repository.RoleRepository;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void processOAuthPostLogin(String username, String email, String picture){
        User existUser = userRepository.findByFullname(username);

        if(existUser == null){
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setEnabled(true);
            newUser.setFullname(username);
            newUser.setPassword(picture);

            Role userRole = roleRepository.findByRole("USER");
            newUser.setRoles(new HashSet<>(Arrays.asList(userRole)));

            userRepository.save(newUser);
        }
    }

}
