package com.etc.personhome.serviceImpl;

import com.etc.personhome.entity.Tuser;
import com.etc.personhome.mapper.PersonMapper;
import com.etc.personhome.service.PersonService;
import com.etc.personhome.entity.Facekey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("pservice")
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonMapper personMapper ;
    @Override
    public Tuser login(String uname, String pwd) {
        return personMapper.login(uname , pwd);
    }

    /**
     * 实现用户查询功能
     * @param username
     * @param status
     * @return
     */
    @Override
    public List<Tuser> getTuser(String username, int status) {
        return personMapper.getTuser(username , status);
    }

    /**
     * 回显某个用户的信息
     * @param id
     * @return
     */
    @Override
    public Tuser getOne(int id) {
        return personMapper.getOne(id);
    }

    /**
     * 修改用户信息
     * @param username
     * @param loginname
     * @param statis
     * @param id
     */
    //因为此功能为修改功能，所以需要将只读改为非只读
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateUser(String username, String loginname, int statis, int id) {
          personMapper.updagteUser(username , loginname , statis , id) ;
    }

    /**
     * 实现删除用户信息功能
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleUser(String ids) {
        //因为可能删除多条数据信息，所以传递的ids中有,我们需要将ids的值先进行分割，然后再实现删除功能
        String[] idss = ids.split(",") ;
        for (String id: idss
             ) {
            //将String类型的id转换为int类型的uid
            int uid = Integer.parseInt(id) ;
            personMapper.deleUser(uid) ;
        }
    }

    /**
     * 添加用户信息
     * @param tuser
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void addUser(Tuser tuser) {
        personMapper.addUser(tuser) ;
    }

    /**
     * 人脸识别
     * @param loginname
     * @param
     * @param
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void upDataFaceUrlByName(String loginname, String faceUrl,String facepath) {
      personMapper.upDataFaceUrlByName(loginname, faceUrl,facepath);
    }

    @Override
    public List<Tuser> selectUser() {
        return personMapper.selectUser();
    }

    @Override
    public Facekey selectFaceKye() {
        return personMapper.selectFaceKye();
    }
}
