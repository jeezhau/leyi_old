package me.jeekhan.leyi.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import me.jeekhan.leyi.common.FileFilter;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
/**
 * ͨ�ô���
 * @author Jee Khan
 *
 */
@Controller
@RequestMapping("/common")
@SessionAttributes({"operator"})
public class CommonAction {
	
	
	/**
	 * �ϴ�ͼƬ
	 * ��Ȩ�ޡ�
	 * 	1����¼�û���
	 * ������˵����
	 * 	1.ȡ������Ϣ��������²������򷵻�Ӧ����ҳ��
	 * 	2.ȡ����������Ϣ��
	 * 	3.������ʾģʽ���������ˣ���
	 * @param articleId	����ID
	 * @param map
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/uploadImg")
	@ResponseBody
	public void uploadImg(@RequestParam(value="upload")MultipartFile upload,@ModelAttribute("operator")Operator operator,PrintWriter out,HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(!upload.isEmpty()){
			String imgType = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf('.')+1);
			String path = SysPropUtil.getParam("DIR_USER_UPLOAD") + operator.getUsername() + "/";  
			
			response.setCharacterEncoding("utf-8");  
		    // CKEditor�ص�����
			String callback = request.getParameter("CKEditorFuncNum");  
//			if (imgType.equals("jpeg")|| imgType.equals("jpg")||imgType.equals("gif") || imgType.equals("image/png")) {  
//		           
//		    }  
	        if (upload.getSize() > 3*1024*1024) {  
	            //out.println("<script type=\"text/javascript\">");  
	            out.println("function(){window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'�ļ���С���ô���3M')}();");  
	            //out.println("</script>");  
	            return;
	        }  
	        File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			//ͼƬ�ϴ�·��  
	        String fileName = java.util.UUID.randomUUID().toString(); // ����ʱ��+UUID�ķ�ʽ�漴����
	        fileName += "." + imgType;
			FileOutputStream outFile = new FileOutputStream(path + fileName);
			InputStream in = upload.getInputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while((n=in.read(buf))>0){
				outFile.write(buf, 0, n);
			}
			outFile.close();

		  
	        // ������ʾͼƬ·��   
			String showPath = request.getContextPath() + "/common/showPic/" + operator.getUsername() + "/" + fileName;
	        out.println("<script type=\"text/javascript\">");  
	        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + showPath + "','')");  
	        out.println("</script>");  
	        return ;  
		}
	}
	/**
	 * ��ʾͼƬ
	 * @param username
	 * @param picName
	 * @param out
	 * @throws IOException 
	 */
	@RequestMapping(value="/showPic/{username}/{picName}")
	public void getPersonPicture(@PathVariable("username")String username,@PathVariable("picName")String picName,OutputStream out,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String path = SysPropUtil.getParam("DIR_USER_UPLOAD") + username + "/"; 
		File dir = new File(path);
		File[] files = dir.listFiles(new FileFilter(picName));
		if(files != null && files.length>0){
			BufferedImage image = ImageIO.read(files[0]);
			response.setContentType("image/*");  
			OutputStream os = response.getOutputStream();  
			String type = files[0].getName().substring(picName.length()+1);
			ImageIO.write(image, type, os);  
		}
	}
}
