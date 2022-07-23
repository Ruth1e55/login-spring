package regis.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import regis.login.domain.Project;
import regis.login.domain.User;
import regis.login.repository.ProjectRepository;
import regis.login.services.CustomUserDetailsService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private ProjectRepository projectRepository;

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

//    @RequestMapping(value = "/dashboard",method = RequestMethod.POST)
//    public ModelAndView project(@RequestParam String projectTitle, @RequestParam String projectDesc, @RequestParam String addedBy, @RequestParam Date startDate, @RequestParam Date endDate, @RequestParam String projectCode){
//        ModelAndView modelAndView = new ModelAndView("dashboard");
//        Project project = new Project();
//        project.setProjectTitle(projectTitle);
//        project.setProjectDesc(projectDesc);
//        project.setProjectCode(projectCode);
//        project.setAddedBy(addedBy);
//        project.setStartDate(startDate);
//        project.setEndDate(endDate);
//
//        projectRepository.save(project);
//
////        modelAndView.addObject("project",project);
////
//        return modelAndView;
//    }

    @PostMapping(value = "/dashboard")
    public String project(@RequestParam String projectTitle, @RequestParam String projectDesc, @RequestParam String addedBy, @RequestParam Date startDate, @RequestParam Date endDate){
        Project project = new Project();
        project.setProjectTitle(projectTitle);
        project.setProjectDesc(projectDesc);
        project.setProjectCode();
        project.setAddedBy(addedBy);
        project.setStartDate(startDate);
        project.setEndDate(endDate);

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("project",projectRepository.findById(projectTitle));
        projectRepository.save(project);
//        return modelAndView;
        return "redirect:/dashboard";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/dashboard",method = RequestMethod.GET)
    public ModelAndView getDashboard(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projects",projectRepository.findAll());
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

//    @RequestMapping(value = "/dashboard", method = RequestMethod.DELETE)
//    public ModelAndView deleteProject(@RequestParam String projectTitle){
//        ModelAndView modelAndView = new ModelAndView();
//        Optional<Project> project = projectRepository.findById(projectTitle);
//        projectRepository.delete(project.get());
//        modelAndView.setViewName("dashboard");
//        return modelAndView;
//    }

//    @RequestMapping(value = "/dashboard/update",method = RequestMethod.POST)
//    public ModelAndView updateProject(@RequestParam String projectTitle, @RequestParam String projectDesc, @RequestParam String addedBy, @RequestParam Date startDate, @RequestParam Date endDate, @RequestParam String projectCode){
//        ModelAndView modelAndView = new ModelAndView();
//
//        Optional<Project> project = projectRepository.findById(projectTitle);
//        project.get().setProjectDesc(projectDesc);
//        project.get().setProjectCode(projectCode);
//        project.get().setStartDate(startDate);
//        project.get().setAddedBy(addedBy);
//        project.get().setEndDate(endDate);
//
//        projectRepository.save(project.get());
//
//        modelAndView.addObject("project",project);
//        modelAndView.setViewName("dashboard");
//
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
