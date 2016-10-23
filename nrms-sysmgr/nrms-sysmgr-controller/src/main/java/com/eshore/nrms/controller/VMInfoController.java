package com.eshore.nrms.controller;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.nrms.controller.utils.SendMailComponent;
import com.eshore.nrms.sysmgr.pojo.AuditInfo;
import com.eshore.nrms.sysmgr.pojo.OrderInfo;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;
import com.eshore.nrms.sysmgr.pojo.PMInfo;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.pojo.VMInfo;
import com.eshore.nrms.sysmgr.service.IAuditInfoServive;
import com.eshore.nrms.sysmgr.service.IOrderInfoService;
import com.eshore.nrms.sysmgr.service.IOrderVMInfoSercvice;
import com.eshore.nrms.sysmgr.service.IPMInfoService;
import com.eshore.nrms.sysmgr.service.IUserInfoService;
import com.eshore.nrms.sysmgr.service.IVMInfoService;
import com.eshore.nrms.util.SecurityUtil;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;
import com.eshore.nrms.vo.InitData;
import com.eshore.nrms.vo.PageVo;


@Controller
@RequestMapping("/vmManagement")
public class VMInfoController {
	
	@Autowired
	private SendMailComponent send;
	
	@Autowired
	private IVMInfoService vmInfoService;
	
	@Autowired
	private IPMInfoService pmInfoService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IOrderInfoService oderInfoService;

	@Autowired
	private IOrderVMInfoSercvice orderVMInfoService;
	
	@Autowired
	private IOrderInfoService orderInfoService ;
	
	@Autowired
	private IAuditInfoServive auditInfoService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    } 
	
	@RequestMapping("/vmList")
	public ModelAndView  vmInfoList(VMInfo vmInfo , PageConfig page){
		ModelAndView view = new ModelAndView("vmManagement/vmList");
		List<PMInfo> pmInfoList = this.pmInfoService.queryAllPMInfo(page);
		PageVo<VMInfo> vmInfoList = this.vmInfoService.queryVMInfoByPage(vmInfo, page);
		view.addObject("page" , vmInfoList);
		view.addObject("pmList", pmInfoList);
		view.addObject("searchParam", vmInfo);
		//view.addObject("ipList" , InitData.paramListTable.get("server_ip"));
		return view;
	}
	
	@RequestMapping("/recoveryVM") 
	@ResponseBody
	public ExecResult recoveryVM(String id){
		
		//获取虚拟机信息，将状态置为回收
		VMInfo vm = this.vmInfoService.get(id);
		vm.setStatus(Conts.STATE_DELETE);
		this.vmInfoService.update(vm);

		//更新对应的主机信息
		PMInfo pm = this.pmInfoService.get(vm.getServerId());
		//CPU是虚拟机共享，所以使用量永远为0
		pm.setCpuUsed(0);
		pm.setDiskUsed(pm.getDiskUsed() - vm.getDisk());
		pm.setMemoryUsed(pm.getMemoryUsed() - vm.getMemory());
		this.pmInfoService.update(pm);
		
		//邮件通知
		UserInfo userInfo = this.userInfoService.get(vm.getUserId());
		String contex = vm.getUserName() + "，您所使用的虚拟机："+vm.getProjectName()
					+ "已被管理员回收，请进入网络资"
					+ "源管理系统查看详情！亿迅科技！";
		this.send.send("网络资源管理系统——虚拟机部署通知", contex, userInfo.getEmail());
		
		ExecResult result = new ExecResult();
		result.setMsg("回收成功");
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping("/addVm") 
	public ModelAndView  toAddVm(String id , PageConfig page){
		ModelAndView view = new ModelAndView("vmManagement/addVm");
		List<PMInfo> pmInfoList = this.pmInfoService.queryAllPMInfo(page);
		view.addObject("pmList", pmInfoList);
		view.addObject("osList", InitData.paramListTable.get("os"));
		//view.addObject("ipRequireList", InitData.paramListTable.get("ip_require_msg"));
		view.addObject("vmIpSegList", InitData.paramListTable.get("vm_ip"));
		//view.addObject("ipList" , InitData.paramListTable.get("server_ip"));
		return view;
	}
	
	@RequestMapping("/saveVm") 
	@ResponseBody
	public ExecResult saveVm(VMInfo vmInfo){
		ExecResult result = new ExecResult();
		
		
		//判断用户是否存在
		int count = this.userInfoService.getUserCountByUserName(vmInfo.getUserName(), null);
		if(count > 0){
			vmInfo.setUserId(this.userInfoService.getUserByUserName(vmInfo.getUserName()).getId());
		}
		else{
			result.setMsg("用户不存在");
			result.setSuccess(false);
			return result ;
		}
		
		//判断物理机资源是否可用
		PMInfo pm = this.pmInfoService.get(vmInfo.getServerId());
		if(pm.getCpu()  < vmInfo.getCpu()){
			result.setMsg("该台物理机的CPU不满足申请的虚拟机的CPU要求");
			result.setSuccess(false);
			return result ;
		}
		if(pm.getDisk() - pm.getDiskUsed() < vmInfo.getDisk()){
			result.setMsg("该台物理机可用磁盘空间不足，请重新选择！");
			result.setSuccess(false);
			return result ;
		}
		if(pm.getMemory() - pm.getMemoryUsed() < vmInfo.getMemory()){
			result.setMsg("该台物理机可用内存不足，请重新选择！");
			result.setSuccess(false);
			return result ;
		}
		
		//根据ipSegId找到ipSeg并添加fullIp
		String ipSeg = "" ;
		String fullIp = "" ;
		List<Param> paramList = InitData.paramListTable.get("vm_ip");
		for(Param param:paramList){
			if(param.getId().equals(vmInfo.getIpSegId())){
				ipSeg = param.getParamValue() ;
				fullIp = param.getParamValue() + "." + vmInfo.getIpNum() ;
				vmInfo.setIpSeg(ipSeg);
				vmInfo.setFullIp(fullIp);
				break ;
			}
		}
		//判断ip是否被占用
		count = this.vmInfoService.getVmCountByFullIp(fullIp);
		if(count > 0){
			result.setMsg("ip已经被占用");
			result.setSuccess(false);
			return result ;
		}
		
		
		
		vmInfo.setStatus(1);
		this.vmInfoService.save(vmInfo);
		
		//更新物理机资源
		pm.setCpuUsed(0);
		pm.setDiskUsed(pm.getDiskUsed() + vmInfo.getDisk());
		pm.setMemoryUsed(pm.getMemoryUsed() + vmInfo.getMemory());
		this.pmInfoService.update(pm);
		
		result.setMsg("保存成功");
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping("/deploySaveVm") 
	@ResponseBody
	public ExecResult saveVm(HttpSession session,String vmOrderInfoId,String serverId,
			String ipSegId,String ipNum,String managerLogin,
			String managerPwd,String userLogin,String userPwd){
		ExecResult result = new ExecResult();
		
		OrderVMInfo orderVMInfo = this.orderVMInfoService.get(vmOrderInfoId);
		VMInfo vmInfo = new VMInfo();
		vmInfo.setOrderVMId(vmOrderInfoId);
		vmInfo.setServerId(serverId);
		vmInfo.setProjectName(orderVMInfo.getProjectName());
		vmInfo.setIpSegId(ipSegId);
		String ipSeg = InitData.paramMapTable.get("vm_ip").get(ipSegId).getParamValue();
		vmInfo.setIpSeg(ipSeg);
		vmInfo.setIpNum(ipNum);
		vmInfo.setFullIp(ipSeg+"."+ipNum);
		vmInfo.setCpu(orderVMInfo.getCpu());
		vmInfo.setMemory(orderVMInfo.getMemory());
		vmInfo.setDisk(orderVMInfo.getDisk());
		vmInfo.setOs(orderVMInfo.getOs());
		vmInfo.setManagerLogin(managerLogin);
		vmInfo.setManagerPwd(managerPwd);
		vmInfo.setUserLogin(userLogin);
		vmInfo.setUserPwd(userPwd);
		vmInfo.setBeginDate(orderVMInfo.getBeginDate());
		vmInfo.setEndDate(orderVMInfo.getEndDate());
		vmInfo.setUserId(orderVMInfo.getUserId());
		vmInfo.setUserName(orderVMInfo.getUserName());
		
		//判断物理机资源是否可用
		PMInfo pm = this.pmInfoService.get(vmInfo.getServerId());
		if(pm.getCpu()  < vmInfo.getCpu()){
			result.setMsg("该台物理机的CPU不满足申请的虚拟机的CPU要求");
			result.setSuccess(false);
			return result ;
		}
		if(pm.getDisk() - pm.getDiskUsed() < vmInfo.getDisk()){
			result.setMsg("该台物理机可用磁盘空间不足，请重新选择！");
			result.setSuccess(false);
			return result ;
		}
		if(pm.getMemory() - pm.getMemoryUsed() < vmInfo.getMemory()){
			result.setMsg("该台物理机可用内存不足，请重新选择！");
			result.setSuccess(false);
			return result ;
		}
		
		
		result = this.saveVm(vmInfo);
		if(result.getSuccess()){
			//部署成功，保存部署信息
			UserInfo userInfo = (UserInfo) session.getAttribute(Conts.USER_SESSION_KEY);
			AuditInfo auditInfo = new AuditInfo();
			auditInfo.setOrderId(orderVMInfo.getOrderId());
			auditInfo.setOperUserId(userInfo.getId());
			auditInfo.setOperUserName(userInfo.getUserName());
			auditInfo.setOperDate(new Date(System.currentTimeMillis()));
			auditInfo.setOperResult(0);
			//如果部署成功，更新申请单
			orderVMInfo.setIsAssigned(1);
			this.orderVMInfoService.update(orderVMInfo);
			
			OrderInfo orderInfo = this.orderInfoService.get(orderVMInfo.getOrderId());
			UserInfo creatUser = this.userInfoService.get(orderInfo.getCreateUserId());// 获取订单创建人
			orderInfo.setAssignedNum(orderInfo.getAssignedNum()+1);
			if(orderInfo.getAssignedNum()==orderInfo.getVmNum()){
				orderInfo.setState(Conts.ISASSINED);//设置工单状态为：已完成虚拟机部署
				auditInfo.setOperFeedBack("完成部署");
				auditInfo.setOperType(Conts.ISASSINED);
				
				//邮件通知
				String contex = creatUser.getUserName() + "，您在工单："+orderInfo.getProjectName()
						+"工单号："+orderInfo.getId()
						+" 下所申请的虚拟机"
						+"已完成部署，请进入网络资"
						+"源管理系统查看详情！亿迅科技！";
				this.send.send("网络资源管理系统——虚拟机部署通知", contex, creatUser.getEmail());
				
			}else{
				auditInfo.setOperType(Conts.AUDIT_SUCCESS);
				auditInfo.setOperFeedBack("部署第"+orderInfo.getAssignedNum()+"台虚拟机");
			}
			this.auditInfoService.save(auditInfo);
			this.orderInfoService.update(orderInfo);
			
			//更新物理机资源
			pm.setCpuUsed(0);
			pm.setDiskUsed(pm.getDiskUsed() + vmInfo.getDisk());
			pm.setMemoryUsed(pm.getMemoryUsed() + vmInfo.getMemory());
			this.pmInfoService.update(pm);
		}
		return result;
	}

	
	@RequestMapping("/viewVmInfo") 
	public ModelAndView viewUserInfo(String vmId){
		
		ModelAndView view = new ModelAndView("vmManagement/viewVm");
		VMInfo vminfo = this.vmInfoService.get(vmId);
		String serverId = vminfo.getServerId();
		PMInfo pminfo = this.pmInfoService.get(serverId);
		view.addObject("pmInfo",pminfo);
		view.addObject("vmInfo" , vminfo);
		return view;
	}
}
