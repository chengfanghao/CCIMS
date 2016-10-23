package com.eshore.nrms.web.controller;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.nrms.sysmgr.pojo.StuBasicInfo;
import com.eshore.nrms.sysmgr.service.IStuBasicInfoService;
import com.eshore.nrms.sysmgr.service.IUserInfoService;
import com.eshore.nrms.util.RandomValidateCodeGenerator;
import com.eshore.nrms.util.SecurityUtil;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;

@Controller
public class MainController {

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IStuBasicInfoService stuBasicInfoService;

	@RequestMapping({ "", "/" })
	public String toIndex(HttpSession session) {

		StuBasicInfo user = (StuBasicInfo) session.getAttribute(Conts.USER_SESSION_KEY);
		if (user == null) {
			return "login";
		} else {
			if (user.getRole().equals("管理员"))
				return "adminMainPage";
			return "mainPage";
		}
	}

	@RequestMapping({ "/doLogin" })
	@ResponseBody
	public ExecResult doLogin(String loginName, String pwd, String validateCode, HttpSession session,
			HttpServletRequest request) {

		ExecResult result = new ExecResult();

		String sessionValidateCode = (String) session.getAttribute(Conts.VALIDATE_CODE_KEY);
		if (!sessionValidateCode.equalsIgnoreCase(validateCode)) {
			result.setSuccess(false);
			result.setMsg("验证码错误");
			return result;
		}

		StuBasicInfo stuUser = this.stuBasicInfoService.userLogin(loginName);
		if (stuUser == null) {
			result.setSuccess(false);
			result.setMsg("帐号名或密码错误");
			return result;
		}

		String pwdMD5 = SecurityUtil.md5(pwd);
		if (!stuUser.getPassWord().equalsIgnoreCase(pwdMD5)) {
			result.setSuccess(false);
			result.setMsg("账户名或密码错误");
			return result;
		}

		String localIp = this.getHostIp(request);
		session.setAttribute("localIp", localIp);
		session.setAttribute(Conts.USER_SESSION_KEY, stuUser);
		result.setSuccess(true);
		result.setMsg("登录成功");
		return result;
	}

	@RequestMapping("/validate")
	public void validate(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String key = RandomValidateCodeGenerator.randKey(4);

		try {
			Map.Entry<String, BufferedImage> randCode = new RandomValidateCodeGenerator().getRandCode(key);
			session.setAttribute(Conts.VALIDATE_CODE_KEY, randCode.getKey());

			ImageIO.write(randCode.getValue(), "JPEG", response.getOutputStream());

		} catch (Exception e) {
			System.out.println("生成验证码图片失败了!");
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/logOut" })
	public String logOut(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping("/changePwd")
	@ResponseBody
	public ExecResult changePassword(String userId, String password, String newPassword) {
		ExecResult result = new ExecResult();
		StuBasicInfo user = this.stuBasicInfoService.get(userId);
		if (null == user) {
			result.setSuccess(false);
			result.setMsg("用户不存在！");
			return result;
		}
		String pwdMD5 = SecurityUtil.md5(password);
		if (!user.getPassWord().equalsIgnoreCase(pwdMD5)) {
			result.setSuccess(false);
			result.setMsg("原密码错误！");
			return result;
		}
		String newPwdMD5 = SecurityUtil.md5(newPassword);
		user.setPassWord(newPwdMD5);
		this.stuBasicInfoService.update(user);
		result.setSuccess(true);
		result.setMsg("密码修改成功");
		return result;
	}

	@RequestMapping("/passwordChange")
	public ModelAndView changePwd() {
		ModelAndView view = new ModelAndView("/changePwd");
		return view;
	}

	/**
	 * 获取本机ip
	 * 
	 * @return
	 */
	private static String getHostIp(HttpServletRequest request) {
		InetAddress addr;
		String ip = request.getRemoteAddr();
		return ip;
	}
}
