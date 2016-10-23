package com.eshore.nrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.nrms.sysmgr.pojo.PMInfo;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.pojo.VMInfo;
import com.eshore.nrms.sysmgr.service.IPMInfoService;
import com.eshore.nrms.sysmgr.service.IParamService;
import com.eshore.nrms.sysmgr.service.IVMInfoService;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;
import com.eshore.nrms.vo.InitData;
import com.eshore.nrms.vo.PageVo;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2016年8月1日
 *  
 */
@Controller
@RequestMapping("/pmInfo")
public class PMInfoController {
	@Autowired
	private IPMInfoService pmInfoService;
	@Autowired
	private IVMInfoService vmInfoService;
	@Autowired
	private IParamService paramService;

	@RequestMapping("/pmList")
	public ModelAndView  pmInfoList(PMInfo pmInfo , PageConfig page){
		ModelAndView view = new ModelAndView("pmInfo/pmList");
		PageVo<PMInfo> pmInfoList = this.pmInfoService.queryPMInfoByPage(pmInfo, page);
		view.addObject("page" , pmInfoList);
		view.addObject("searchParam" , pmInfo);
		return view;
	}
	/**
	 * 查看指定id的物理机下的所有虚拟机
	 * @param pmInfo
	 * @param page
	 * @return
	 */
	@RequestMapping("/vmList")
	public ModelAndView  vmList(String id){
		ModelAndView view = new ModelAndView("pmInfo/vmList");
		PageConfig page = new PageConfig();
		PageVo<VMInfo> vmInfoList = vmInfoService.queryVMInfoByServerId(id, page);
		view.addObject("page", vmInfoList);
		return view;
	}

	@RequestMapping("/viewPM")
	public ModelAndView  viewPmInfo(String id){
		ModelAndView view = new ModelAndView("pmInfo/viewPM");
		PMInfo pmInfo = this.pmInfoService.get(id);
		view.addObject("pm" , pmInfo);
		return view;
	}
	@RequestMapping("/addPM") 
	public ModelAndView  addPM(){
		ModelAndView view = new ModelAndView("pmInfo/addPM");
		List<Param> serverIpList = this.paramService.queryParamByParamType("server_ip");
		view.addObject("ipList" , serverIpList);
		return view;
	}

	@RequestMapping("/editPM") 
	public ModelAndView  editPM(String id){
		ModelAndView view = new ModelAndView("pmInfo/editPM");
		List<Param> serverIpList = this.paramService.queryParamByParamType("server_ip");
		PMInfo pmInfo = this.pmInfoService.get(id);
		String ipSegment = pmInfo.getIpSegment();
		ipSegment = ipSegment.substring(0, ipSegment.length()-1);
		pmInfo.setIpSegment(ipSegment);
		view.addObject("ipList" , serverIpList);
		view.addObject("pm" , pmInfo);
		return view;
	}
	@RequestMapping("/deletePM") 
	@ResponseBody
	public ExecResult  deletePM(String id){
		ExecResult result = new ExecResult();
		//先核查该物理机下有无在运行的虚拟机
		PageConfig page = new PageConfig();
		VMInfo vmInfo = new VMInfo();
		vmInfo.setServerId(id);
		vmInfo.setStatus(Conts.STATE_OK);
		PageVo<VMInfo> vmInfoList = vmInfoService.queryVMInfoByPage(vmInfo, page);
		int count = vmInfoList.getDataList().size();
		if( count > 0){
			result.setMsg("该物理机上还有虚拟机在运行，无法删除");
			result.setSuccess(false);
			return result;
		}
		PMInfo pmInfo = this.pmInfoService.get(id);
		pmInfo.setStatus(Conts.STATE_DELETE);
		this.pmInfoService.update(pmInfo);
		result.setMsg("删除成功");
		result.setSuccess(true);
		return result;
	}
	@RequestMapping("/savePM") 
	@ResponseBody
	public ExecResult savePM(PMInfo pmInfo){
		ExecResult result = new ExecResult();
		Param param = InitData.paramMapTable.get("server_ip").get(pmInfo.getIpSegmentId());
		String ipSegment = param.getParamValue();
		String ipSegmentAndPort = param.getParamName(); 
		if(!ipSegment.endsWith(".")){
			ipSegment = ipSegment+".";
		}
		pmInfo.setIpSegment(ipSegment);
		pmInfo.setFullIp(ipSegment + pmInfo.getIpNum());

		//判断IP是否被占用
		int count = this.pmInfoService.getPMCountByFullIp(pmInfo.getFullIp());
		if(count > 0){
			result.setMsg("IP已被使用");
			result.setSuccess(false);
			return result;
		}
		//获取参数表的server_ip类型的参数集合
		List<Param> serverIp = this.paramService.queryParamByParamType("server_ip");
		int ipBegin = 0;
		int ipEnd = 0;
		for(Param item : serverIp){
			if(item.getParamName().equals(ipSegmentAndPort)){
				ipBegin = item.getIpBegin();
				ipEnd = item.getIpEnd();
				System.out.println(ipBegin);
				System.out.println(ipEnd);
			}
		}
		//判断IP是否超过段区间
		int pmIpNum = Integer.valueOf(pmInfo.getIpNum());
		System.out.println(pmIpNum);
		if(  pmIpNum < ipBegin || pmIpNum > ipEnd ){
			result.setMsg("末位IP号设置不合理，请更换");
			result.setSuccess(false);
			return result;
		}
		pmInfo.setStatus(Conts.STATE_OK);
		this.pmInfoService.save(pmInfo);
		result.setMsg("保存成功");
		result.setSuccess(true);
		return result;
	}
	@RequestMapping("/updatePM") 
	@ResponseBody
	public ExecResult updatePM(PMInfo pmInfo){
		ExecResult result = new ExecResult();
		Param param = InitData.paramMapTable.get("server_ip").get(pmInfo.getIpSegmentId());
		String ipSegment = param.getParamValue();
		String ipSegmentAndPort = param.getParamName(); 
		if(!ipSegment.endsWith(".")){
			ipSegment = ipSegment+".";
		}
		pmInfo.setIpSegment(ipSegment);
		pmInfo.setFullIp(ipSegment + pmInfo.getIpNum());
		//获取参数表的server_ip类型的参数集合
		List<Param> serverIp = this.paramService.queryParamByParamType("server_ip");
		int ipBegin = 0;
		int ipEnd = 0;
		for(Param item : serverIp){
			if(item.getParamName().equals(ipSegmentAndPort)){
				ipBegin = item.getIpBegin();
				ipEnd = item.getIpEnd();
			}
		}
		//判断IP是否超过段区间
		int pmIpNum = Integer.valueOf(pmInfo.getIpNum());
		if(  pmIpNum < ipBegin || pmIpNum > ipEnd ){
			result.setMsg("末位IP号设置不合理，请更换");
			result.setSuccess(false);
			return result;
		}
		
		int count = 0;
		count = this.pmInfoService.queryPMInfoByFullIpAndId(pmInfo.getFullIp(),pmInfo.getId());
		//将id和fullIp联合起来查询，看修改结果是否改变了fullIp,未修改，则直接修改非ipNum和ipSegment的属性，并更新;否则,先核查修改后的fullIp是否冲突
		if( count != 1){
			//修改结果改变了fullIp
			//判断IP是否被占用
			int count2 = this.pmInfoService.getPMCountByFullIp(pmInfo.getFullIp());
			if(count2 > 0){
				result.setMsg("IP已被使用");
				result.setSuccess(false);
				return result;
			}
		}
		PMInfo pm = this.pmInfoService.get(pmInfo.getId());
		if( pmInfo.getCpu() < pm.getCpuUsed()){
			result.setMsg("物理机cpu数目不合理，小于已使用数目");
			result.setSuccess(false);
			return result;
		}
		if( pmInfo.getDisk() < pm.getDiskUsed() ){
			result.setMsg("物理机硬盘大小不合理，小于已使用数目");
			result.setSuccess(false);
			return result;
		}
		if( pmInfo.getMemory() < pm.getMemoryUsed()){
			result.setMsg("物理机内存大小不合理，小于已使用数目");
			result.setSuccess(false);
			return result;
		}
		
		pm.setIpSegmentId(pmInfo.getIpSegmentId());
		pm.setIpSegment(pmInfo.getIpSegment());
		pm.setIpNum(pmInfo.getIpNum());
		pm.setFullIp(pmInfo.getFullIp());
		pm.setCpu(pmInfo.getCpu());
		pm.setMemory(pmInfo.getMemory());
		pm.setDisk(pmInfo.getDisk());
		pm.setConsoleLogin(pmInfo.getConsoleLogin());
		pm.setConsolePwd(pmInfo.getConsolePwd());
		pm.setServerLogin(pmInfo.getServerLogin());
		pm.setServerPwd(pmInfo.getServerPwd());

		this.pmInfoService.update(pm);
		result.setMsg("更新成功");
		result.setSuccess(true);
		return result;
	}


}
