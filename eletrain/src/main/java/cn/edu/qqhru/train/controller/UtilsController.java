package cn.edu.qqhru.train.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.AdminService;
import cn.edu.qqhru.train.service.TeacherService;

@Controller
@RequestMapping("/utils")
public class UtilsController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private TeacherService teacherService;
	
	@RequestMapping("/uploadImage")
	public Map<String,String> uploadImage(@RequestParam("upfile")  CommonsMultipartFile upfile,HttpServletRequest request,Model model) throws Exception{   
        
        //得到路径 C:/tomcat/webapps/testDemo/  
        String rootPath = request.getSession().getServletContext().getRealPath("/");  
        rootPath = rootPath.replaceAll("\\\\", "/");  
        String path = rootPath + "/ueditor/jsp/upload/image"; 
          
        File f = new File(path);  
        if (!f.exists()) {  
            f.mkdirs();  
        }  
      
        FileItem item = upfile.getFileItem();  
        //文件路径  
        String pathFileName = item.getName();  
        //字节数  
        long l = item.getSize();  
        String fileSize = Long.toString(l);  
        //文件名  
        int start = pathFileName.lastIndexOf("\\");  
        String fileName = pathFileName.substring(start + 1);  
        //后缀 .jpg  
        int indexName = fileName.lastIndexOf('.');  
        String subName = fileName.substring(indexName);  
        //新文件名  
        String nowName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) +"_"+ fileName;  
              
        item.write(new File(path, nowName));  
          
        String strBackUrl = "http://" + request.getServerName() //服务器地址  
        + ":"  
        + request.getServerPort()           //端口号    
        + request.getContextPath();      //项目名称  
          
        Map<String,String> map = new HashMap<String,String >();  
        //文件原名称   
        map.put("title", nowName);  
        //现在文件名称  
        map.put("original", fileName);  
        //文件大小（字节数）  
        map.put("size", fileSize);  
        //是否上传成功  
        map.put("state", "SUCCESS");  
        //文件类型 .+后缀名  
        map.put("type", subName);  
        //文件路径  
        map.put("url",  strBackUrl+"/ueditor/jsp/upload/image/"+nowName);  
          
        return map;  
          
    }  
	
	@RequestMapping("/verifyLoginnameIsExist")
	@ResponseBody
	public boolean verifyLoginnameIsExist(@RequestBody String loginname){
		boolean flag = true;
		loginname = loginname.replace("=","");
		List<String> loginnameList = new ArrayList<String>();
		List<Admin> adminList = adminService.getAdminList();
		List<Teacher> teacherList = teacherService.getAllTeacher();
		
		for(Admin admin : adminList){
			if(admin != null){
				loginnameList.add(admin.getLoginname());
			}
		}
		
		for(Teacher teacher : teacherList){
			if(teacher != null){
				loginnameList.add(teacher.getLoginname());
			}
		}
		
		for(String loginname1 : loginnameList){
			if(loginname.equals(loginname1)){
				flag = false;
				break;
			}
		}
		return flag;
	}
}
