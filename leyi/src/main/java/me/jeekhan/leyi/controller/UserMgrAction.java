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
 * ������Ϣ���������
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
	 * ��ʾ�û���ϸ��Ϣ
	 * ��Ȩ�ޡ�
	 * 	1��������ʾ-�û��Լ���
	 *  2�����-����Ա�����Ϊ�ǹ���Ա����ģʽΪ������ʾ��
	 * ������˵����
	 * ����û���Ϣ������ʾ�������ʾ��detail-������ʾ��review-�����ʾ
	 * 	1.ȡ�û���Ϣ������û��������򷵻�Ӧ����ҳ��
	 * 	2.������ʾģʽ���������ˣ���
	 * @param userId	�û�ID
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
	 * �û���ˣ�ͨ��
	 * ��Ȩ�ޡ�
	 * 	1������¼�Ĺ���Ա��ִ�иò�����
	 * ������˵����
	 *  1���ж���˵��û��Ƿ���ڣ�
	 *  2��ִ�����ͨ��
	 * @param userId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public String accept(Integer userId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername() + "/user_mgr/review/" + userId;
		if(operator == null || operator.getLevel() < 9){ //��Ȩ��
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
		}
		if(userId == null){ //�û�Ϊ��
			return redirectUrl;
		}
		if(remark !=null && remark.length()>600){
			return redirectUrl + "?error=" + "���˵�������ɳ���600���ַ���";
		}
		UserFullInfo user = userService.getUserFullInfo(userId);
		if(user == null){ //�޸��û�
			return redirectUrl + "?error=ϵͳ���޸��û���Ϣ��";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		userService.reviewUser(userId, "0",reviewInfo);
		return "redirect:/"+operator.getUsername()+ "/review/";
	}
	
	/**
	 * �û���ˣ��ܾ�
	 * ��Ȩ�ޡ�
	 * 	1������¼�Ĺ���Ա��ִ�иò�����
	 * ������˵����
	 *  1���ж���˵��û��Ƿ���ڣ�
	 *  2��ִ����˾ܾ�
	 * @param userId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public String refuse(Integer userId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/user_mgr/review/" + userId;
		if(operator == null || operator.getLevel() < 9){ //��Ȩ��
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
		}
		
		if(userId == null || remark == null || remark.trim().length()<1){ //�û������˵��Ϊ��
			return redirectUrl + "?error=" + ((userId == null)? "�û�ID������Ϊ�գ�" : "���˵��������Ϊ�գ�");
		}
		if(remark.length()>600){
			return redirectUrl + "?error=" + "���˵�����ɳ���600���ַ���";
		}
		UserFullInfo user = userService.getUserFullInfo(userId);
		if(user == null){ //�޸��û�
			return redirectUrl + "?error=ϵͳ���޸��û���Ϣ��";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		userService.reviewUser(userId, "R",reviewInfo);
		
		return "redirect:/"+operator.getUsername()+ "/review/";
	}
}
