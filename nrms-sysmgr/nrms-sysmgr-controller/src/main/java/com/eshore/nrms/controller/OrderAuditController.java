package com.eshore.nrms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.nrms.controller.utils.SendMailComponent;
import com.eshore.nrms.sysmgr.pojo.AuditInfo;
import com.eshore.nrms.sysmgr.pojo.AuditProcess;
import com.eshore.nrms.sysmgr.pojo.OrderInfo;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;
import com.eshore.nrms.sysmgr.pojo.PMInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.pojo.VMInfo;
import com.eshore.nrms.sysmgr.service.IAuditInfoServive;
import com.eshore.nrms.sysmgr.service.IAuditProcessService;
import com.eshore.nrms.sysmgr.service.IOrderInfoService;
import com.eshore.nrms.sysmgr.service.IOrderVMInfoSercvice;
import com.eshore.nrms.sysmgr.service.IPMInfoService;
import com.eshore.nrms.sysmgr.service.IParamService;
import com.eshore.nrms.sysmgr.service.IUserInfoService;
import com.eshore.nrms.sysmgr.service.IVMInfoService;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;
import com.eshore.nrms.vo.InitData;
import com.eshore.nrms.vo.PageVo;

@Controller
@RequestMapping("/vmAudit")
public class OrderAuditController {

	@Autowired
	private SendMailComponent send;
	
	@Autowired
	private IOrderInfoService orderInfoService;

	@Autowired
	private IOrderVMInfoSercvice orderVMInfoService;

	@Autowired
	private IAuditInfoServive auditInfoService;

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IVMInfoService vmInfoService;

	@Autowired
	private IPMInfoService pmInfoService;

	@Autowired
	private IParamService paramService;

	@Autowired
	private IAuditProcessService auditProcessService;

	@RequestMapping("/orderList")
	public ModelAndView orderInfoList(HttpServletRequest request, OrderInfo orderInfo, PageConfig page) {
		ModelAndView view = new ModelAndView("vmAudit/orderList");
		UserInfo user = (UserInfo) request.getSession().getAttribute(Conts.USER_SESSION_KEY);
		PageVo<OrderInfo> orderInfoList = null;
		if (user.getUserRole().equals(Conts.ROLE_EMPLOYEE)) {
			int myState = this.auditProcessService.getStateValueByUserId(user.getId());
			orderInfoList = this.orderInfoService.queryOrderInfoByPage(orderInfo, user, page, myState);

			// 自身审批的操作列表
			List<AuditInfo> orderListByMyself = this.auditInfoService.queyAuditInfiByOperUserId(user.getId());
			for (AuditInfo auditInfo : orderListByMyself) {
				String orderId = auditInfo.getOrderId();
				OrderInfo temp = this.orderInfoService.get(orderId);
				orderInfoList.getDataList().add(temp);
			}
		} else {
			orderInfoList = this.orderInfoService.queryOrderInfoByPage(orderInfo, user, page);
		}
		List<AuditProcess> processList = this.auditProcessService.queryAuditProcessList();
		if (user.getIsAudit() == 1) {
			int myStateValue = this.auditProcessService.getStateValueByUserId(user.getId()); // 用户自身的审批状态码
			view.addObject("myStateValue", myStateValue);
		}
		view.addObject("page", orderInfoList);
		view.addObject("searchParam", orderInfo);
		view.addObject("processList", processList);
		return view;
	}

	/**
	 * 申请单审批
	 * 
	 * @param id：工单号
	 * @return modelview
	 */
	@RequestMapping("/audit")
	public ModelAndView auditOrder(String id) {
		ModelAndView view = new ModelAndView("vmAudit/audit");
		OrderInfo orderInfo = this.orderInfoService.get(id);
		List<OrderVMInfo> vmList = this.orderVMInfoService.queryVMListByOrderId(orderInfo.getId());
		view.addObject("orderInfo", orderInfo);
		view.addObject("ipList", InitData.paramListTable.get("ip_require_msg"));
		view.addObject("vmList", vmList);
		return view;
	}

	/**
	 * 查看分配虚拟机列表
	 * 
	 * @param id:工单号
	 * @return
	 */
	@RequestMapping("/deployVM")
	public ModelAndView deployVM(String id) {
		ModelAndView view = new ModelAndView("vmAudit/deployVM");
		OrderInfo orderInfo = this.orderInfoService.get(id);
		List<OrderVMInfo> vmList = this.orderVMInfoService.queryVMListByOrderId(orderInfo.getId());
		view.addObject("orderInfo", orderInfo);
		view.addObject("ipList", InitData.paramListTable.get("ip_require_msg"));
		view.addObject("vmList", vmList);
		return view;
	}

	/**
	 * 查看虚拟机详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewVM")
	public ModelAndView viewVM(String vmOrderId) {
		ModelAndView view = new ModelAndView("vmManagement/viewVm");
		VMInfo vmInfo = this.vmInfoService.queryVMInfoByOrderVMId(vmOrderId);
		String serverId = vmInfo.getServerId();
		PMInfo pminfo = this.pmInfoService.get(serverId);
		view.addObject("pmInfo", pminfo);
		view.addObject("vmInfo", vmInfo);
		return view;
	}

	/**
	 * 按照申请单部署虚拟机
	 * 
	 * @param vmId
	 * @return
	 */
	@RequestMapping("/doDeployVM")
	public ModelAndView doDeployVM(String vmId, PageConfig page) {
		ModelAndView view = new ModelAndView("vmAudit/doDeployVM");
		OrderVMInfo vmInfo = this.orderVMInfoService.get(vmId);
		List<PMInfo> pmList = this.pmInfoService.queryAllPMInfo(page);
		view.addObject("pmList", pmList);
		view.addObject("vmInfo", vmInfo);

		view.addObject("osList", InitData.paramListTable.get("os"));
		view.addObject("ipRequireList", InitData.paramListTable.get("ip_require_msg"));
		view.addObject("ipList", InitData.paramListTable.get("vm_ip"));
		view.addObject("serverIpList", InitData.paramListTable.get("server_ip"));
		return view;
	}

	/**
	 * 保存审批操作
	 * 
	 * @param orderId：工单号
	 * @param operValue：操作类型
	 *            0-通过 1-驳回
	 * @param userId:
	 *            操作人Id
	 * @param operFeedBack:
	 *            操作反馈
	 * @return
	 */
	@RequestMapping("/saveAuditOption")
	@ResponseBody
	public ExecResult saveAuditOption(String orderId, Integer operValue, String userId, String feedBack) {
		ExecResult result = new ExecResult();
		UserInfo userInfo = this.userInfoService.get(userId);// 获取操作人
		OrderInfo orderInfo = this.orderInfoService.get(orderId);
		AuditInfo auditInfo = new AuditInfo();
		UserInfo creatUser = this.userInfoService.get(orderInfo.getCreateUserId());// 获取订单创建人
		auditInfo.setOrderId(orderId);
		auditInfo.setOperUserId(userInfo.getId());
		auditInfo.setOperUserName(userInfo.getUserName());
		auditInfo.setOperDate(new Date(System.currentTimeMillis()));
		auditInfo.setOperResult(operValue);
		auditInfo.setOperFeedBack(feedBack);

		int oldAuditState = orderInfo.getState();// 当前审批状态
		auditInfo.setOperType(oldAuditState);// 操作类型即状态编码

		int nextAuditState = Conts.TURN_DOWN; // 下一个状态
		String resulte;
		if (operValue == 0) {// 审批通过
			resulte ="通过申请";
			nextAuditState = this.auditProcessService.getNextState(oldAuditState);
			// 如果订单创建人具有审批权限的话，且下一个状态是订单创建人审批，则跳过
			if (creatUser.getIsAudit() == 1) {
				int creatUserValue = this.auditProcessService.getStateValueByUserId(creatUser.getId());
				if (creatUserValue == nextAuditState) {
					nextAuditState = this.auditProcessService.getNextState(creatUserValue);
				}
			}
		}else{
			resulte ="驳回申请";
		}
		orderInfo.setState(nextAuditState);
		this.auditInfoService.save(auditInfo);
		this.orderInfoService.update(orderInfo);

		String contex = creatUser.getUserName() + "，您的工单：" + orderInfo.getProjectName()
				+"（工单号："+orderInfo.getId()
				+"）,已由" + userInfo.getUserName()+"审批完成！"+"审批结果："+resulte
				+ "，请进入网络资" + "源管理系统查看详情！亿迅科技！";
		this.send.send("网络资源管理系统——工单审批通知", contex, creatUser.getEmail());
		result.setMsg("保存成功");
		result.setSuccess(true);
		return result;
	}

	/**
	 * 查询对应工单下所有操作
	 * 
	 * @param orderId：工单Id
	 * @author hecheng
	 * @return
	 */
	@RequestMapping("/viewOrderOption")
	public ModelAndView viewOrderOption(String orderId) {
		ModelAndView view = new ModelAndView("vmAudit/viewOrderOption");
		List<AuditInfo> orderOptList = this.auditInfoService.queryAuditInfoByOrderId(orderId);
		view.addObject("orderOpList", orderOptList);
		return view;
	}
}
