package me.jeekhan.leyi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.UserFullInfo;
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
	public String showArticle(@PathVariable("mode")String mode,@PathVariable("userId")Integer userId,Map<String,Object> map){
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
}
