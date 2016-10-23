package com.eshore.nrms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.nrms.sysmgr.pojo.AuditInfo;
import com.eshore.nrms.sysmgr.pojo.AuditProcess;
import com.eshore.nrms.sysmgr.pojo.OrderInfo;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.pojo.VMInfo;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.sysmgr.service.IAuditInfoServive;
import com.eshore.nrms.sysmgr.service.IAuditProcessService;
import com.eshore.nrms.sysmgr.service.IOrderInfoService;
import com.eshore.nrms.sysmgr.service.IOrderVMInfoSercvice;
import com.eshore.nrms.sysmgr.service.IParamService;
import com.eshore.nrms.sysmgr.service.IUserInfoService;
import com.eshore.nrms.sysmgr.service.IVMInfoService;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;
import com.eshore.nrms.vo.InitData;
import com.eshore.nrms.vo.PageVo;

@Controller
@RequestMapping("/vmApply")
public class OrderApplyController {

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
	private IAuditProcessService auditProcessService;
	
	@Autowired
	private IParamService  paramService;

	/**
	 * 查询所有工单
	 * 
	 * @return modelview
	 * */
	@RequestMapping("/orderList")
	public ModelAndView orderInfoList(HttpServletRequest request,
			OrderInfo orderInfo, PageConfig page) {
		ModelAndView view = new ModelAndView("vmApply/orderList");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		PageVo<OrderInfo> orderInfoList = this.orderInfoService
				.queryOrderInfoByPage(orderInfo, user, page);

		List<AuditProcess> processList = this.auditProcessService
				.queryAuditProcessList();
		// if(user.getIsAudit() == 1){
		// //用户具有审批权限
		// int myStateValue =
		// this.auditProcessService.getStateValueByUserId(user.getId());
		// //用户自身的审批状态码
		// view.addObject("myStateValue", myStateValue);
		// }
		view.addObject("page", orderInfoList);
		view.addObject("searchParam", orderInfo);
		view.addObject("processList", processList);
		return view;

	}

	/**
	 * 删除工单
	 * 
	 * @param orderid
	 *            :工单号
	 * @return result
	 */
	@RequestMapping("/deleteOrder")
	@ResponseBody
	public ExecResult deleteOrder(String id) {
		OrderInfo order = this.orderInfoService.get(id);
		ExecResult result = new ExecResult();
		if (order.getState() == 1) {
			order.setState(Conts.STATE_DELETE);
			this.orderInfoService.update(order);
			// this.orderInfoService.delete(id);
//			this.orderVMInfoService.delete(id);
			List<OrderVMInfo> vmList = this.orderVMInfoService.queryVMListByOrderId(id);
			for(OrderVMInfo temp : vmList){
				this.orderVMInfoService.delete(temp.getId());
			}
		} else {
			order.setState(Conts.STATE_DELETE);
			this.orderInfoService.update(order);
		}
		result.setMsg("删除成功");
		result.setSuccess(true);
		return result;
	}

	/**
	 * 提交工单
	 * 
	 * @param orderid
	 *            :工单号
	 * @return result
	 */
	@RequestMapping("/applyOrder")
	@ResponseBody
	public ExecResult applyOrder(HttpSession session, String id) {
		OrderInfo order = this.orderInfoService.get(id);
		UserInfo userInfo = (UserInfo) session.getAttribute(Conts.USER_SESSION_KEY);

		int nextState = this.auditProcessService.getNextState(order.getState());	//工单在提交后的下一个状态
		int myStateValue = -1;
		if(userInfo.getIsAudit()==1){//如果该用户具有审批权限
			AuditProcess temp = this.auditProcessService.getProcessByUserId(userInfo.getId());
			myStateValue = temp.getValue();
		}
		if(nextState == myStateValue){//具有审批权限的用户提交的工单不能给自己审批
			nextState = this.auditProcessService.getNextState(myStateValue);//往后挪一个状态
		}
		order.setState(nextState);
		this.orderInfoService.update(order);
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setOrderId(id);
		auditInfo.setOperUserId(userInfo.getId());
		auditInfo.setOperUserName(userInfo.getUserName());
		auditInfo.setOperDate(new Date(System.currentTimeMillis()));
		auditInfo.setOperType(1);
		auditInfo.setOperResult(0);
		auditInfo.setOperFeedBack("提交成功");
		this.auditInfoService.save(auditInfo);

		ExecResult result = new ExecResult();
		result.setMsg("提交成功");
		result.setSuccess(true);
		return result;
	}

	/**
	 * 查看工单下已分配的虚拟机详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewOrderDetail")
	public ModelAndView viewVM(String id) {
		ModelAndView view = new ModelAndView("vmApply/viewOrderDetail");
		OrderInfo orderInfo = this.orderInfoService.get(id);
		List<OrderVMInfo> vmList = this.orderVMInfoService
				.queryVMListByOrderId(orderInfo.getId());
		view.addObject("ipList", InitData.paramListTable.get("ip_require_msg"));
		view.addObject("orderInfo", orderInfo);
		view.addObject("vmList", vmList);
		return view;
	}

	/**
	 * 进去某张申请单后，查看已分配的虚拟机详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewVM")
	public ModelAndView viewApplyVM(String vmOrderId) {
		ModelAndView view = new ModelAndView("vmManagement/viewVm");
		VMInfo vmInfo = this.vmInfoService.queryVMInfoByOrderVMId(vmOrderId);
		view.addObject("vmInfo", vmInfo);
		return view;
	}

	/**
	 * 跳转新增工单
	 * 
	 * @return modelview
	 * 
	 */
	@RequestMapping("/addOrderApply")
	public ModelAndView saveOrUpdateOrder() {
		// String projectName,Date beginTime,Date endTime
		ModelAndView view = new ModelAndView("vmApply/addOrder");
		return view;
	}

	/**
	 * 编辑页面也跳转这个 跳转显示 在已经填写工单的基础上， 显示刚新增的虚拟机
	 * 
	 * @return modelview
	 * 
	 */
	@RequestMapping("/showOrderApply")
	public ModelAndView showOrder(String orderId) {
		ModelAndView view = new ModelAndView("vmApply/addOrder");
		OrderInfo orderInfo = this.orderInfoService.get(orderId);
		List<OrderVMInfo> orderVMList = this.orderVMInfoService
				.queryVMListByOrderId(orderId);
		view.addObject("ipList", InitData.paramListTable.get("ip_require_msg"));
		view.addObject("orderInfo", orderInfo);
		view.addObject("orderVMList", orderVMList);
		return view;
	}

	/**
	 * 删除 新增工单页面下，已经申请的虚拟机信息
	 * 
	 * @param orderId
	 *            :工单号 vmId:虚拟机号
	 * @return result
	 */
	@RequestMapping("/deleteVMApply")
	@ResponseBody
	public ExecResult deleteVMApply(String orderId, String vmId) {
		ExecResult result = new ExecResult();
		OrderInfo order = this.orderInfoService.get(orderId);
		this.orderVMInfoService.delete(vmId);
		Integer count = order.getVmNum() - 1;
		order.setVmNum(count.intValue());
		this.orderInfoService.update(order);
		if (order.getVmNum() == this.orderVMInfoService
				.getVMCountByOrderId(orderId)) {
			result.setMsg("删除成功");
			result.setSuccess(true);
		} else {
			result.setMsg("删除失败");
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 填写新增工单
	 * 
	 * @return modelview
	 * @throws ParseException
	 * 
	 */
	@RequestMapping("/writeOrder")
	@ResponseBody
	public ExecResult writeOrderInfo(String projectName, String beginDate,
			String endDate, String id, String orderId) throws ParseException {

		if (StringUtils.isNotBlank(orderId)) {
			// 针对一开始orderId不为空的时候，即工单已经提交成功了，但是还可以再次修改那张提交成功的，继续点保存的操作
			OrderInfo orderInfo = this.orderInfoService.get(orderId);
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			Date beginTime = simple.parse(beginDate);
			Date endTime = simple.parse(endDate);
			orderInfo.setProjectName(projectName);
			orderInfo.setBeginTime(beginTime);
			orderInfo.setEndTime(endTime);
			orderInfo.setCreateDate(new Date(System.currentTimeMillis()));
			this.orderInfoService.update(orderInfo);

			ExecResult result = new ExecResult();

			// 验证是否已经写入
			if (this.orderInfoService.idOrder(orderId) > 0) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
			result.setMsg(orderId);
			return result;

		} else {

			// 针对一开始orderId为0的时候，相当于第一次填写工单信息
			OrderInfo orderInfo = new OrderInfo();
			UserInfo userInfo = this.userInfoService.get(id);
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			String key = null;
			Integer maxId = this.orderInfoService.getOrderMaxId();
			String tempId = maxId.toString().substring(0, 8);
			//System.out.println(tempId);
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat simple2 = new SimpleDateFormat("yyyyMMdd");
			String currentDate = simple2.format(date);

			if (tempId.equals(currentDate)) {

				maxId += 1;
				key = maxId.toString();
			} else {
				key = currentDate + "01";
			}
			Date beginTime = simple.parse(beginDate);
			Date endTime = simple.parse(endDate);
			orderInfo.setId(key);// 修改了主键生成策略
			orderInfo.setProjectName(projectName);
			orderInfo.setBeginTime(beginTime);
			orderInfo.setEndTime(endTime);
			orderInfo.setState(Conts.WAITING_APPLY);// 设置工单状态为未提交
			orderInfo.setCreateDate(new Date(System.currentTimeMillis()));
			orderInfo.setCreateUserName(userInfo.getUserName());
			orderInfo.setCreateUserId(userInfo.getId());
			this.orderInfoService.save(orderInfo);

			ExecResult result = new ExecResult();
			result.setSuccess(true);
			result.setMsg(orderInfo.getId());
			return result;
		}
	}

	/**
	 * 虚拟机新增弹框
	 * 
	 * @return modelview
	 * @throws ParseException
	 *             传一个参数给弹框 <input type="hidden" id="orderId" name="orderId"
	 *             value="${orderInfo.id}" />
	 */
	@RequestMapping("/toAddVM")
	public ModelAndView toAddVM(String orderId) {
		ModelAndView view = new ModelAndView("vmApply/addVM");
		//System.out.println(orderId);
		OrderInfo orderInfo = this.orderInfoService.get(orderId);
		view.addObject("ipList", InitData.paramListTable.get("ip_require_msg"));
		view.addObject("osList", InitData.paramListTable.get("os"));
		view.addObject("orderInfo", orderInfo);
		return view;
	}

	/**
	 * 保存虚拟机信息
	 * 
	 * @return modelview
	 * @throws ParseException
	 */
	@RequestMapping("/saveVM")
	@ResponseBody
	public ExecResult saveVM(String ip, String cpu, String memory, String disk,
			String os, String orderId, String userId) {

		ExecResult result = new ExecResult();

		OrderVMInfo orderVMInfo = new OrderVMInfo();
		
		Param param = this.paramService.getParamByParamName(ip);
		

		// 类型转换

		int Cpu = Integer.parseInt(cpu);
		int Memory = Integer.parseInt(memory);
		int Disk = Integer.parseInt(disk);
		int IP = Integer.parseInt(param.getId());

		UserInfo userInfo = this.userInfoService.get(userId);
		OrderInfo orderInfo = this.orderInfoService.get(orderId);
		Integer count = this.orderVMInfoService.getVMCount();
		count = count + 1;
		//System.out.println(count);
		// OrderVMInfo的主键
		String key = count.toString()
				+ new Date(System.currentTimeMillis()).toString();

		orderVMInfo.setId(key);
		orderVMInfo.setOrderId(orderId);
		orderVMInfo.setProjectName(orderInfo.getProjectName());
		orderVMInfo.setCpu(Cpu);
		orderVMInfo.setMemory(Memory);
		orderVMInfo.setDisk(Disk);
		orderVMInfo.setOs(os);
		orderVMInfo.setBeginDate(orderInfo.getBeginTime());
		orderVMInfo.setEndDate(orderInfo.getEndTime());
		orderVMInfo.setIsAssigned(0);
		orderVMInfo.setUserId(userId);
		orderVMInfo.setUserName(userInfo.getUserName());
		orderVMInfo.setIpRequire(IP);
		this.orderVMInfoService.save(orderVMInfo);

		/*System.out.println("这是在虚拟机申请明细表根据工单号查的，保存之后的"
				+ this.orderVMInfoService.getVMCountByOrderId(orderId));
		System.out.println("这是在工单表根据工单查的，未更新的" + orderInfo.getVmNum());*/

		if (this.orderVMInfoService.getVMCountByOrderId(orderId).equals(
				(orderInfo.getVmNum() + 1))
				&& (this.orderVMInfoService.isOrderVmInfo(key) > 0)) {
			int vmCount = orderInfo.getVmNum();
			vmCount = vmCount + 1;
			orderInfo.setVmNum(vmCount);// 修改该工单里申请虚拟机的数目
			this.orderInfoService.update(orderInfo);

			result.setSuccess(true);
			result.setMsg(orderInfo.getId());
			return result;
		} else {
			this.orderVMInfoService.delete(orderVMInfo);

			result.setSuccess(true);
			result.setMsg(orderInfo.getId());
			return result;
		}
	}

}
