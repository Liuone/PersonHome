package com.etc.personhome.service;

import com.etc.personhome.entity.Tuser;
import com.etc.personhome.entity.Facekey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonService {
    //实现登录功能
    public Tuser login(String  uname , String pwd) ;
    //实现用户查询功能
    public List<Tuser> getTuser(String username , int status) ;
    //查询某个用户的信息
    public Tuser getOne(int id) ;
    //修改用户信息
    public void updateUser(String username , String loginname , int statis , int id) ;
     //删除用户信息
    public void deleUser(String ids) ;
    //添加用户信息
    public void addUser(Tuser tuser) ;
     //人脸识别登录
    void upDataFaceUrlByName(String loginname, String urlPath, String path);
       //查看用户
    List<Tuser> selectUser();

    Facekey selectFaceKye();
}
