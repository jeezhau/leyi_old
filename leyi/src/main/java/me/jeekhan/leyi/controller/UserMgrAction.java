package me.jeekhan.leyi.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import me.jeekhan.leyi.common.FileFilter;
import me.jeekhan.leyi.common.SunSHAUtils;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.InviteInfo;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.InviteInfoService;
import me.jeekhan.leyi.service.UserService;

/**
 * 个人信息管理控制类
 * @author Jee Khan
 *
 */
@RequestMapping("/{username}/user_mgr")
@Controller
@SessionAttributes("operator")
public class UserMgrAction {
	@Autowired
	private UserService userService;
	@Autowired
	private InviteInfoService inviteInfoService;
	/**
	 * 显示用户详细信息
	 * 【权限】
	 * 	1、详情显示-用户自己；
	 *  2、审核-管理员；如果为非管理员则变更模式为详情显示；
	 * 【功能说明】
	 * 完成用户信息内容显示及审核显示：detail-详情显示；review-审核显示
	 * 	1.取用户信息，如果用户不存在则返回应用主页；
	 * 	2.保存显示模式（详情或审核）；
	 * @param userId	用户ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{mode}/{userId}",method=RequestMethod.GET)
	public String showUser(@PathVariable("mode")String mode,@PathVariable("userId")Integer userId,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(userId);
		if(("detail".equals(mode) || "review".equals(mode)) && userInfo != null){
			map.put("mode", mode);
			map.put("userInfo", userInfo);
			if("review".equals(mode)){
				Operator operator = (Operator) map.get("operator");
				if(operator == null || operator.getLevel() < 9){
					mode = "detail";
				}
			}
			return "userShow";
		}else{
			return "redirect:/";
		}
	}
	

	/**
	 * 用户审核：通过
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的用户是否存在；
	 *  2、执行审核通过
	 * @param userId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public String accept(Integer userId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername() + "/user_mgr/review/" + userId;
		if(operator == null || operator.getLevel() < 9){ //无权限
			return redirectUrl + "?error=您无权限执行该操作！";
		}
		if(userId == null){ //用户为空
			return redirectUrl;
		}
		if(remark !=null && remark.length()>600){
			return redirectUrl + "?error=" + "审核说明：不可超过600个字符！";
		}
		UserFullInfo user = userService.getUserFullInfo(userId);
		if(user == null){ //无该用户
			return redirectUrl + "?error=系统中无该用户信息！";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		userService.reviewUser(userId, "0",reviewInfo);
		return "redirect:/"+operator.getUsername()+ "/review/";
	}
	
	/**
	 * 用户审核：拒绝
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的用户是否存在；
	 *  2、执行审核拒绝
	 * @param userId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public String refuse(Integer userId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/user_mgr/review/" + userId;
		if(operator == null || operator.getLevel() < 9){ //无权限
			return redirectUrl + "?error=您无权限执行该操作！";
		}
		
		if(userId == null || remark == null || remark.trim().length()<1){ //用户或审核说明为空
			return redirectUrl + "?error=" + ((userId == null)? "用户ID：不可为空！" : "审核说明：不可为空！");
		}
		if(remark.length()>600){
			return redirectUrl + "?error=" + "审核说明不可超过600个字符！";
		}
		UserFullInfo user = userService.getUserFullInfo(userId);
		if(user == null){ //无该用户
			return redirectUrl + "?error=系统中无该用户信息！";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		userService.reviewUser(userId, "R",reviewInfo);
		
		return "redirect:/"+operator.getUsername()+ "/review/";
	}
	/**
	 * 展示用户编辑页面
	 * @param operator
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String edit(@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		int userId = operator.getUserId();
		UserFullInfo userInfo = userService.getUserFullInfo(userId);
		InviteInfo info = inviteInfoService.get(userId);
		map.put("userInfo", userInfo);
		map.put("inviteInfo", info);
		return "userEdit";
	}
	
	/**
	 * 保存用户基本信息编辑
	 * @param userInfo
	 * @param operator
	 * @param result
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/editBasic",method=RequestMethod.POST)
	public String editUser(@Valid UserFullInfo userInfo,BindingResult result,@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		int userId = operator.getUserId();
		UserFullInfo oldInfo = userService.getUserFullInfo(userId);
		InviteInfo info = inviteInfoService.get(userId);
		map.put("userInfo", userInfo);
		map.put("inviteInfo", info);
		if(operator == null || operator.getUserId() != userInfo.getId() || oldInfo == null){ //无权限
			map.put("mode","editBasic");
			map.put("error","您无权限执行该操作！");
			return "userEdit";
		}
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put(filed, e.getDefaultMessage());
			}
			userInfo.setPicture(oldInfo.getPicture());
			map.put("mode","editBasic");
			return "userEdit";
		}
		//数据保存
		userInfo.setPasswd(oldInfo.getPasswd());
		userInfo.setInviteCode(oldInfo.getInviteCode());
		userInfo.setPicture(oldInfo.getPicture());
		int id = userService.saveUser(userInfo);
		if(id<=0){
			if(id == -1){
				map.put("username", "该用户名已被使用");
			}
			if(id == -2){
				map.put("email", "该邮箱已被使用");
			}
			map.put("mode","editBasic");
			return "userEdit";
		}
		return "redirect:/"+operator.getUsername()+ "/detail";
	}
	
	/**
	 * 修改密码
	 * @param userId
	 * @param old_passwd
	 * @param new_passwd
	 * @param operator
	 * @param map
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/editPwd",method=RequestMethod.POST)
	public String editPwd(int userId,String old_passwd,String new_passwd,@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		UserFullInfo oldInfo = userService.getUserFullInfo(userId);
		InviteInfo info = inviteInfoService.get(userId);
		map.put("userInfo", oldInfo);
		map.put("inviteInfo", info);
		if(operator == null || operator.getUserId() != userId || oldInfo == null){ //无权限
			map.put("mode","editPwd");
			map.put("error","您无权限执行该操作！");
			return "userEdit";
		}
		//数据保存
		String oldPwd = SunSHAUtils.encodeSHA512Hex(old_passwd);
		String newPwd = SunSHAUtils.encodeSHA512Hex(new_passwd);
		if(!oldPwd.equals(oldInfo.getPasswd())){
			map.put("mode","editPwd");
			map.put("error","原密码不正确！");
			return "userEdit";
		}
		oldInfo.setPasswd(newPwd);
		userService.saveUser(oldInfo);
		return "redirect:/"+operator.getUsername()+ "/detail";
	}
	/**
	 * 保存用户图片编辑
	 * @param file
	 * @param operator
	 * @param map
	 * @return
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="/editPic",method=RequestMethod.POST)
	public String editUser(int userId, MultipartFile picFile,@ModelAttribute("operator")Operator operator,Map<String,Object> map) throws IOException, NoSuchAlgorithmException{
		UserFullInfo oldInfo = userService.getUserFullInfo(userId);
		InviteInfo info = inviteInfoService.get(userId);
		map.put("userInfo", oldInfo);
		map.put("inviteInfo", info);
		if(operator == null || operator.getUserId() != userId || oldInfo == null){ //无权限
			map.put("error","您无权限执行该操作！");
			map.put("mode","editPic");
			return "userEdit";
		}
		//文件保存
		String path = SysPropUtil.getParam("DIR_USER_UPLOAD") + oldInfo.getUsername() + "/";  
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String picName = oldInfo.getPicture();
		if(picName == null || picName.length()<1){
			picName = java.util.UUID.randomUUID().toString();
		}
		//删除旧的图像
		File[] files = dir.listFiles(new FileFilter(picName));
		if(files != null && files.length>0){
			for(File file:files){
				file.delete();
			}
		}
		String fileName = picName + picFile.getOriginalFilename().substring(picFile.getOriginalFilename().lastIndexOf('.'));
		if(!picFile.isEmpty()){
			oldInfo.setPicture(fileName);
			FileOutputStream out = new FileOutputStream(path + fileName);
			InputStream in = picFile.getInputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while((n=in.read(buf))>0){
				out.write(buf, 0, n);
			}
			out.close();
			oldInfo.setPicture(fileName);
			userService.saveUser(oldInfo);
		}
		return "redirect:/"+operator.getUsername()+ "/detail";
	}
	
	
}
