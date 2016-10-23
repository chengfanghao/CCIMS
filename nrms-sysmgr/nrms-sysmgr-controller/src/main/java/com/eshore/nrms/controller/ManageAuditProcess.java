package com.eshore.nrms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.nrms.controller.utils.SendMailComponent;
import com.eshore.nrms.sysmgr.pojo.AuditProcess;
import com.eshore.nrms.sysmgr.pojo.OrderInfo;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.service.IAuditProcessService;
import com.eshore.nrms.sysmgr.service.IOrderInfoService;
import com.eshore.nrms.sysmgr.service.IUserInfoService;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;
import com.eshore.nrms.vo.InitData;

@Controller
@RequestMapping("/auditProcessManage")
public class ManageAuditProcess {

	@Autowired
	private IAuditProcessService auditProcessService;

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IOrderInfoService orderInfoService;

	@Autowired
	private SendMailComponent send;
	/**
	 * 显示审批流程
	 * 
	 * @return
	 */
	@RequestMapping("/auditProcessList")
	public ModelAndView showAuditProcessList() {
		ModelAndView view = new ModelAndView("paramManage/auditProcessManage");
		List<AuditProcess> processList = this.auditProcessService.queryAuditProcessList();
		boolean containManager = false;
		boolean containAdmin = false;
		for (AuditProcess temp : processList) {
			if (temp.getValue() == 90) {
				containManager = true;
			}
			if (temp.getValue() == 194) {
				containAdmin = true;
			}
		}
		view.addObject("processList", processList);
		view.addObject("containManager", containManager);
		view.addObject("containAdmin", containAdmin);
		return view;
	}

	/**
	 * 删除审批流程中 某个状态
	 * 
	 * @param processId：流程状态id
	 * @param userId:
	 *            状态所属人员id
	 * @return
	 */
	@RequestMapping("/deleteProcess")
	@ResponseBody
	public ExecResult deleteProcess(HttpServletRequest request, HttpSession session, String processId) {
		ExecResult result = new ExecResult();
		AuditProcess process = this.auditProcessService.get(processId);
		String userId = process.getUserId();
		UserInfo userInfo = this.userInfoService.get(userId);
		userInfo.setIsAudit(0); // 设置不具有审批权限
		this.userInfoService.update(userInfo);
		UserInfo myself = (UserInfo) session.getAttribute(Conts.USER_SESSION_KEY);
		if (userId.equals(myself.getId())) {
			session.setAttribute(Conts.USER_SESSION_KEY, userInfo); // 更新session中的user值
		} else {
			// 邮件通知该用户
			String contex = userInfo.getUserName() + "，您已被" + myself.getUserName()
					+ "取消审批权限，您将无法进行工单的审批操作，"
					+ "您未审批的工单已进入下一审批流程，请进入网络资" 
					+ "源管理系统查看详情！亿迅科技！";
			this.send.send("网络资源管理系统——审批权限通知", contex, userInfo.getEmail());
		}
		// 将该状态下的工单状态全部后移
		int delState = process.getValue(); // 待删除的状态码
		int nextState = this.auditProcessService.getNextState(delState);// 下一个状态码
		List<OrderInfo> orderList = this.orderInfoService.queryOrderList();
		for (OrderInfo order : orderList) {
			if (order.getState() == delState) {
				order.setState(nextState);
				this.orderInfoService.update(order);
			}
		}
		if (!process.getUserRole().equals(Conts.ROLE_EMPLOYEE)) {
			process.setState(0);// 设置状态为不可用
			this.auditProcessService.update(process);
		} else {
			this.auditProcessService.delete(processId);
		}
		result.setSuccess(true);
		result.setMsg("删除成功！");
		return result;
	}

	/**
	 * 显示新增审批流程界面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddProcess")
	public ModelAndView toAddProcess(HttpSession session) {
		ModelAndView view = new ModelAndView("paramManage/addProcess");
		List<AuditProcess> processList = this.auditProcessService.queryAuditProcessList();
		UserInfo userInfo = (UserInfo) session.getAttribute(Conts.USER_SESSION_KEY);
		int myState;
		if (userInfo.getUserRole().equals(Conts.ROLE_MANAGER)) {
			myState = 90;
		} else {
			myState = 194;
		}
		List<AuditProcess> processList2 = new ArrayList<AuditProcess>();
		for (AuditProcess temp : processList) {
			if (temp.getValue() <= myState) {
				processList2.add(temp);
			}
		}
		view.addObject("processList", processList2);
		return view;
	}

	@RequestMapping("/saveProcess")
	@ResponseBody
	public ExecResult saveProcess(HttpServletRequest request, String addName, String nextProcessValue) {
		int nextValue = new Integer(nextProcessValue).intValue();
		ExecResult result = new ExecResult();
		// 检测用户是否存在
		UserInfo userInfo = this.userInfoService.getUserByUserName(addName);
		if (null == userInfo) {
			result.setSuccess(false);
			result.setMsg("保存失败！<\br>用户不存在！");
			return result;
		} else if (userInfo.getIsAudit() == 1) {
			result.setSuccess(false);
			result.setMsg("保存失败！<\br>用户已具有审批权限！");
			return result;
		}
		// 检测状态码是否合格
		int lastValue = this.auditProcessService.getLastState(nextValue);
		int thisValue = (lastValue + nextValue) / 2; // 新增的状态值每次位于两者之间
		if (thisValue == lastValue || thisValue == nextValue) {
			result.setSuccess(false);
			result.setMsg("保存失败！<\br>已经没有足够的值空间添加该用户！");
			return result;
		}

		userInfo.setIsAudit(1);
		this.userInfoService.update(userInfo);

		AuditProcess auditProcess = new AuditProcess();
		auditProcess.setAuditState("待" + addName + "审批");
		auditProcess.setUserId(userInfo.getId());
		auditProcess.setUserRole(userInfo.getUserRole());
		auditProcess.setState(1);
		auditProcess.setValue(thisValue);
		this.auditProcessService.save(auditProcess);

		// 邮件通知该用户
		UserInfo createUser = (UserInfo) request.getSession().getAttribute(Conts.USER_SESSION_KEY);
		String contex = userInfo.getUserName() + "，您已被" + createUser.getUserName() + "指定为工单审批人，请进入网络资"
				+ "源管理系统查看详情！亿迅科技！";
		this.send.send("网络资源管理系统——审批权限通知", contex, userInfo.getEmail());
		result.setSuccess(true);
		result.setMsg("保存成功！");
		return result;
	}

	/**
	 * 恢复自身审批权限
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/recoryMyself")
	@ResponseBody
	public ExecResult recoryMyself(HttpSession session) {
		ExecResult result = new ExecResult();
		UserInfo userInfo = (UserInfo) session.getAttribute(Conts.USER_SESSION_KEY);
		userInfo.setIsAudit(1);
		this.userInfoService.update(userInfo);
		AuditProcess process = this.auditProcessService.getProcessByUserId(userInfo.getId());
		process.setState(1);
		this.auditProcessService.update(process);
		result.setSuccess(true);
		result.setMsg("恢复成功！");
		return result;
	}
}
