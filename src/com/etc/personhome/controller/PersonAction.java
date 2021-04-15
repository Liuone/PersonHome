package com.etc.personhome.controller;

import com.etc.personhome.entity.Tuser;
import com.etc.personhome.service.PersonService;
import com.etc.personhome.util.HrmConstants;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PersonAction {

    //页面跳转
    @RequestMapping("/go")
    public String go(String go){
        return go ;
    }

    @Autowired
    PersonService pservice ;
    @RequestMapping(value = "/addUser" , method = RequestMethod.POST)
    public String  addUser(Tuser tuser){
        System.out.println(tuser.getLoginname() + " " + tuser.getUsername());
        pservice.addUser(tuser);
        return "user/showAddUser.jsp" ;
    }

    /*
    删除用户信息
     */
    @RequestMapping("/deleUser")
    public String deleUser(String ids){
        System.out.println(ids);
        pservice.deleUser(ids);
        return "user/user.jsp" ;
    }
    @RequestMapping("/updateUser")
    public String updateTuser(String username , String loginname  ,   int id  , int status){
        System.out.println(status);
     pservice.updateUser(username , loginname , 1, id);
     return "user/user.jsp" ;
    }
    @RequestMapping("/returnInfo")
    public String getOne(int id , Model model){
       Tuser tuser =  pservice.getOne(id) ;
        System.out.println(tuser.getLoginname() + " " + tuser.getUsername());
       model.addAttribute("tuser" , tuser) ;
       return "user/showUpdateUser.jsp" ;
    }

    /**
     * 查询所有用户信息
     * @param username
     * @param status
     * @param model
     * @return
     */
      @RequestMapping(value = "/getUser" , method = RequestMethod.POST)
   public String getUser(String username , int status , Model model){
      List<Tuser> tuserList = pservice.getTuser(username , status) ;
      model.addAttribute("users" , tuserList) ;
      return  "user/user.jsp" ;
   }

    /**
     * 登录功能
     * @param loginname
     * @param password
     * @return
     */
    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public String login(String loginname , String password , HttpServletRequest request){
        Tuser tuser = pservice.login(loginname , password) ;
        String result = "" ;
        if(tuser == null){
            result = "login.jsp" ;
        }else{
            //因为在做人脸的识别的时候需要找到当前登录的用户信息，所以将登录对象存储到session中
            request.getSession().setAttribute(HrmConstants.USER_SESSION , tuser);
            result = "main.jsp" ;
        }
        return  result ;
    }
}
