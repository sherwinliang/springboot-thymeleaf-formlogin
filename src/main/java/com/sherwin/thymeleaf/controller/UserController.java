package com.sherwin.thymeleaf.controller;

import com.sherwin.thymeleaf.model.User;
import com.sherwin.thymeleaf.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    UserDao inMemoryUserDao;
    /* @description: Home page
     * @author: Sherwin Liang
     * @timestamp: 2021/2/27 16:35
     * @param:
     * @return:
    */
    @RequestMapping("/")
    private ModelAndView home(){
        Iterable<User> users = inMemoryUserDao.findAll();
        return new ModelAndView("/user/list","users", users);
    }
    /* @description: All users page
     * @author: Sherwin Liang
     * @timestamp: 2021/2/27 16:35
     * @param:
     * @return:
    */
    @RequestMapping("/user/list")
    private ModelAndView list(){
        Iterable<User> users = inMemoryUserDao.findAll();
        return new ModelAndView("/user/list","users", users);
    }
    /* @description: The create-user page
     * @author: Sherwin Liang
     * @timestamp: 2021/2/27 16:35
     * @param:
     * @return:
    */
    @RequestMapping("/user/form")
    private ModelAndView form(){
        User user = new User();
        return new ModelAndView("/user/form","user", user);
    }
    /* @description: Add user endpoint
     * @author: Sherwin Liang
     * @timestamp: 2021/2/27 16:36
     * @param:
     * @return:
    */
    @RequestMapping("/add/user")
    private ModelAndView add(User user, BindingResult result, RedirectAttributes redirect){
        if (result.hasErrors()) {
            return new ModelAndView("user/form", "formErrors", result.getAllErrors());
        }
        if(this.inMemoryUserDao.save(user)){
            return new ModelAndView("user/form", "formErrors", result.getAllErrors().add(new ObjectError("formErrors","Create user failed.")));
        }else{
            Iterable<User> users = inMemoryUserDao.findAll();
            return new ModelAndView("/user/list","users", users);
        }
    }
    /* @description: Build the mvc mapping login <=> login.html
     * @author: Sherwin Liang
     * @timestamp: 2021/2/27 16:36
     * @param:
     * @return:
    */
    @RequestMapping("/login")
    private String login(){
        return "login";
    }
}
