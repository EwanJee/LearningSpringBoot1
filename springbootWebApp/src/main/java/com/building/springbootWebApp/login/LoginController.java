package com.building.springbootWebApp.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Slf4j //이거를 써서도 가능 log.~~
@SessionAttributes("name")
public class LoginController {
    /*private AuthenticationService authenticationService;
    @Autowired
    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }*/

//    private final Logger logger = LoggerFactory.getLogger(getClass());

    //localhost:8080/login@name=Ranga
    /*@GetMapping("/login")
    public String toLogin(ModelMap modelMap) {
        //logger.debug("Request Param is {}",name);
        //logger.info("I want this printed at info level");
        //logger.warn("I want this printed at warn level");
        modelMap.put("name","user");

        return "welcome";
    }
     */
    @GetMapping("/")
    public String toIndex(ModelMap modelMap){
//        modelMap.put("name",getLoggedInUsername());
        modelMap.put("name","user");
        return "welcome";
    }

//    private String getLoggedInUsername(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();
//
//    }

    /*@PostMapping("/login")
    public String toWelcome(@RequestParam String name, @RequestParam String password, ModelMap modelMap) {

        if(authenticationService.authenticate(name,password)){
            modelMap.put("name",name);
            modelMap.put("password",password);

            //Authentication
            //name - user
            //password - pw
            return "welcome";
        }
        modelMap.put("errorMessage","Invalid Credentials! Please try again");
        return "welcome";
    }*/
}
