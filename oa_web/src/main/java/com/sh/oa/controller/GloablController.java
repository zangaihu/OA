package com.sh.oa.controller;

import com.sh.oa.biz.GlobalBiz;
import com.sh.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
@Controller("globalController")
public class GloablController {
    @Autowired
    private GlobalBiz globalBiz;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }



    //  登录页面

    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam String sn, @RequestParam String password, Model model) {

        if(sn==""){

            model.addAttribute("msg1","请输入工号");
            return "login";

        }
        if (password==""){
            model.addAttribute("msg2","请输入密码");
            return "login";

        }


            Employee employee = globalBiz.login(sn,password);

            if (employee == null) {
                model.addAttribute("msg3","用户名或密码错误");
                return "login";
            }

            session.setAttribute("employee",employee);
            return "redirect:self";




//        if("员工".equals(employee.getPost()))
//        {
//            session.setAttribute("employee",employee);
//            return "redirect:employeelogin";
//
//        }


    }
    @RequestMapping("/self")
    public String self(){
        return  "self";
    }
    @RequestMapping("/employeelogin")
    public String emloyeelogin(){
        return  "employee/employeelogin";
    }

    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }

    @RequestMapping("/to_change_password")
    public String toChangePassword(){
        return "change_password";
    }

    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1 ,@RequestParam String new2){
        Employee employee = (Employee)session.getAttribute("employee");
        if(employee.getPassword().equals(old)){
            if(new1.equals(new2)){
                employee.setPassword(new1);
                globalBiz.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";
    }

}
