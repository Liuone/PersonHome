package com.etc.personhome.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;

import sun.misc.BASE64Encoder;

import java.util.regex.Pattern;


@Controller
public class BasicController {
	private Logger log = Logger.getLogger(BasicController.class); 

	public void writeJson(String json,HttpServletResponse response){
		PrintWriter out =null;
		try {
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.print(json);
			//out.write();
			out.flush();
			out.close();
		} catch (Exception e) {
			if(out!=null){
				out.close();
			}
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
		}
	}

	public boolean sessionTimeout(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("id")==null || ((String)session.getAttribute("id")).equals("")){
			return true;
		}else{
			return false;
		}
	}

	public String getUUID(){
		String uuid = UUID.randomUUID().toString();   
		uuid = uuid.replace("-", "");
		return uuid;  
	}

	public Date getDate() {
		SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date changedate=null;
		try {
			changedate = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("数据获取异常了", e);
		}
		return changedate;
	}
	
	public String removeHtmlTag(String inputString) {
		if (inputString == null)
		return null;
		String htmlStr = inputString;
		String textStr = "";
		Pattern p_script;
		java.util.regex.Matcher m_script;
		Pattern p_style;
		java.util.regex.Matcher m_style;
		Pattern p_html;
		java.util.regex.Matcher m_html;
		Pattern p_special;
		java.util.regex.Matcher m_special;
		try {

		String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

		String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

		String regEx_html = "<[^>]+>";

		String regEx_special = "\\&[a-zA-Z]{1,10};";

		p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll("");
		p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll("");
		p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll("");
		p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
		m_special = p_special.matcher(htmlStr);
		htmlStr = m_special.replaceAll("");
		
		p_special = Pattern.compile("\\s*|\t|\r|\n");
		m_special = p_special.matcher(htmlStr);
		htmlStr = m_special.replaceAll("");
		
		textStr = htmlStr;
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		return textStr;
		}

	
	public  String getStringDate(Date date) {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String dateString = formatter.format(date);
		  return dateString;
	} 

	public  String  md5(String str) {
		if(str==null){
			return "";
		}

		MessageDigest m = null;
		try {

			m = MessageDigest.getInstance("MD5");

			m.update(str.getBytes("UTF8"));

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		byte s[] = m.digest();

		StringBuilder result = new StringBuilder("");

		for (int i = 0; i < s.length; i++) {

			result.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
		}

		return result.toString();

	}
	public  String getImageStr(String imgFile)  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    } 
	
	public static void main(String arg[]){
		//System.out.println("aaa=="+md5("111111"));
	}
}
