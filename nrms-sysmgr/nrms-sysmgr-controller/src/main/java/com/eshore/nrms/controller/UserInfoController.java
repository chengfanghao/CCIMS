package com.eshore.nrms.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.sysmgr.pojo.StuBasicInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.service.IStuBasicInfoService;
import com.eshore.nrms.sysmgr.service.IUserInfoService;
import com.eshore.nrms.util.SecurityUtil;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;
import com.eshore.nrms.vo.InitData;
import com.eshore.nrms.vo.PageVo;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IStuBasicInfoService stuBasicInfoService;

	@RequestMapping("/userInfoList")
	public ModelAndView userInfoList(StuBasicInfo userInfo, PageConfig page) {
		ModelAndView view = new ModelAndView("userInfo/userList");
		PageVo<StuBasicInfo> userInfoList = this.stuBasicInfoService.queryUserInfoByPage(userInfo, page);
		view.addObject("page", userInfoList);
		view.addObject("searchParam", userInfo);
		return view;
	}

	@RequestMapping("/toAddOrEditUser")
	public ModelAndView toAddOrEditUser(String id) {
		ModelAndView view = new ModelAndView("userInfo/addOrEditUser");

		if (StringUtils.isNotBlank(id)) {
			StuBasicInfo stuUser = this.stuBasicInfoService.get(id);
			view.addObject("stuUser", stuUser);
		}
		return view;
	}

	@RequestMapping("/saveOrUpdateUser")
	@ResponseBody
	public ExecResult saveOrUpdateUser(StuBasicInfo userInfo) {
		ExecResult result = new ExecResult();

		if (!StringUtils.isBlank(userInfo.getId())) {
			StuBasicInfo user = this.stuBasicInfoService.get(userInfo.getId());
			user.setGradeName(userInfo.getGradeName());
			user.setClassName(userInfo.getClassName());
			user.setSex(userInfo.getSex());
			this.stuBasicInfoService.update(user);
		} else {

			int count = this.stuBasicInfoService.getUserCountBySno(userInfo.getSno());
			if (count > 0) {
				result.setMsg("帐号已经存在");
				result.setSuccess(false);
				return result;
			}

			userInfo.setId(null);
			String pwd = SecurityUtil.md5("1234");
			userInfo.setPassWord(pwd);
			userInfo.setRole("学生");
			this.stuBasicInfoService.save(userInfo);
		}

		result.setMsg("保存成功");
		result.setSuccess(true);
		return result;
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public ExecResult deleteUser(String id) {

		this.stuBasicInfoService.delete(id);

		ExecResult result = new ExecResult();
		result.setMsg("删除成功");
		result.setSuccess(true);
		return result;
	}

	@RequestMapping("/resetUserPwd")
	@ResponseBody
	public ExecResult resetUserPwd(String id) {
		StuBasicInfo user = this.stuBasicInfoService.get(id);
		user.setPassWord(SecurityUtil.md5("123456"));
		this.stuBasicInfoService.update(user);

		ExecResult result = new ExecResult();
		result.setMsg("重置密码成功");
		result.setSuccess(true);
		return result;
	}

	@RequestMapping("/viewUserInfo")
	public ModelAndView viewUserInfo(String id) {

		ModelAndView view = new ModelAndView("userInfo/viewUser");
		UserInfo user = this.userInfoService.get(id);

		view.addObject("user", user);
		return view;
	}

}
