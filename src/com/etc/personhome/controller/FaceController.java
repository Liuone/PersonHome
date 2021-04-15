package com.etc.personhome.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etc.personhome.entity.Tuser;
import com.etc.personhome.service.PersonService;
import com.etc.personhome.entity.Facekey;
import com.etc.personhome.util.FaceClient;
import com.etc.personhome.util.Hrm;
import com.etc.personhome.util.HrmConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@Controller
public class FaceController extends BasicController{
	


	@Autowired
	PersonService pservice ;
	
	@RequestMapping("/user/faceRegister")
	public void faceRegister(HttpServletResponse response,HttpServletRequest request,@RequestParam("base") String base){
		HttpSession session=request.getSession();
		JSONObject json=new JSONObject();
		json.put("message", "");
		//从session中读取tuser用户的名称，所以在用户登录之后必然要将登录的用户信息存储到session,并且存储名称为HrmConstants.USER_SESSION
		Tuser user=(Tuser) session.getAttribute(HrmConstants.USER_SESSION);
		//HrmConstants.JOBTABLE:当使用类的时候，属性就为常量的表示方式，如果使用接口，那么只需要直接定义即可
		//System.out.println(Hrm.USERNAME); ;
		 
		String path=request.getServletContext().getRealPath("/")+"headPhoto\\" ;
	       
        String urlPath=request.getContextPath() + "/headPhoto/"+user.getLoginname()+".jpg";
        File uploadDir = new File(path);
        if (!uploadDir.exists() && !uploadDir.isDirectory()) {// 检查目录
			uploadDir.mkdirs();
		}
        path+=user.getLoginname()+".jpg";
		OutputStream out = null;
        InputStream is = null;
        System.out.println("aaaaa=="+path);
		try {
			byte[] imgByte  = new BASE64Decoder().decodeBuffer(base); 
			for (int i = 0; i < imgByte.length; ++i) {
				if (imgByte[i] < 0) {// 调整异常数据
				imgByte[i] += 256;
				}
			}
			out = new FileOutputStream(path);
			is = new ByteArrayInputStream(imgByte);
			byte[] buff = new byte[1024];
	        int len = 0;
	        while((len=is.read(buff))!=-1){
	            out.write(buff, 0, len);
	        }
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			json.put("message", "注册失败");
			e.printStackTrace();
			this.writeJson(json.toString(), response);
		}finally{
			if(is!=null){
				try {
					is.close();
			       
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		pservice.upDataFaceUrlByName(user.getLoginname(), urlPath,path);
		

		json.put("message", "注册成功");
		this.writeJson(json.toString(), response);
	}
	
	
	@RequestMapping("/user/faceLogin")
	public void faceLogin(HttpServletResponse response,HttpServletRequest request,@RequestParam("base") String base){
		//System.out.println(base);
		HttpSession session=request.getSession();
		JSONObject json=new JSONObject();
	
		json.put("message", "");
		List<Tuser> list=pservice.selectUser();

		Facekey Facekey=pservice.selectFaceKye();
		for(int i=0;i<list.size();i++){
			Tuser user=list.get(i);
			if(user.getFacepath()!=null && (!user.getFacepath().trim().equals(""))){
				String base1=this.getImageStr(user.getFacepath());
				//将登录拍下的图片与数据库中已经存储的注册的图片进行比对
				FaceClient faceClient=FaceClient.getInstance(Facekey.getAppID(),Facekey.getApiKey(), Facekey.getSecretKey());
				//登录图片与注册图片的比对
				boolean loginBool=faceClient.faceContrast(base, base1);
				if(loginBool){
					json.put("message", "登录成功");
					session.setAttribute(HrmConstants.USER_SESSION, user);
					break;
				}
			}
			
		}

		this.writeJson(json.toString(), response);
	}
	
	

	@RequestMapping("/user/gotoFaceRegister")
	public String gotoFaceRegister( ) {

		return "user/face.jsp";
	}
}
