package com.etc.personhome.entity;

import java.util.Date;

public class Tuser {
    private int id ;
    private String loginname ;
    private String password ;
    private int status ;
    private String username ;
    private String faceurl ;
    private String facepath ;
    private Date createdate ;

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFaceurl() {
        return faceurl;
    }

    public void setFaceurl(String faceurl) {
        this.faceurl = faceurl;
    }

    public String getFacepath() {
        return facepath;
    }

    public void setFacepath(String facepath) {
        this.facepath = facepath;
    }
}
