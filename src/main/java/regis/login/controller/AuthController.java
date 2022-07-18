package regis.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import regis.login.domain.User;
import regis.login.services.CustomUserDetailsService;

@Controller
public class AuthController {

    @Autowired
    private CustomUserDetailsService userService;

    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

//    @RequestMapping(value = "/googleLogin",method = RequestMethod.GET)
//    public ModelAndView googleLogin(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("google");
//        return modelAndView;
//    }

    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public ModelAndView createNewUser(User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserBYEmail(user.getEmail());
        if(userExists!=null){
            bindingResult.rejectValue("email","error.user","There is already a user registered with the username provided");
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("signup");
        }else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage","User has been registered Successfully");
            modelAndView.addObject("user",new User());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
}
