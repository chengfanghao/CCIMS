package com.eshore.nrms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.sysmgr.service.IPMInfoService;
import com.eshore.nrms.sysmgr.service.IParamService;
import com.eshore.nrms.sysmgr.service.IUserInfoService;
import com.eshore.nrms.sysmgr.service.IVMInfoService;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;
import com.eshore.nrms.vo.InitData;
import com.eshore.nrms.vo.PageVo;

@Controller
@RequestMapping("/paramManage")
public class ParamManageController {

	@Autowired
	private IParamService paramService;
	
	@Autowired
	private IUserInfoService userService;
	
	@Autowired
	private IPMInfoService pmService;
	
	@Autowired
	private IVMInfoService vmService;

	@RequestMapping("/showList")
	public ModelAndView showIndex(Param param, PageConfig page) {
		ModelAndView view = new ModelAndView("paramManage/paramList");

		PageVo<Param> paramList = this.paramService.queryParamList(param, page);
		view.addObject("paramTypeList", Conts.PARAM_TYPE_LIST);
		view.addObject("page", paramList);
		view.addObject("searchParam", param);
		return view;
	}
	
	@RequestMapping("/deleteParam")
	@ResponseBody
	public ExecResult deleteParam(String paramId){
		ExecResult result = new ExecResult();
		Param param = this.paramService.get(paramId);
		if(param.getParamType().contains("_ip")){
			Integer count1 = this.pmService.queryCountByIpSegmentId(paramId);
			Integer count2 = this.userService.queryCountByIpSegmentId(paramId);
			Integer count3 = this.vmService.queryCountByIpSegmentId(paramId);
			if(count1 > 0 || count2 > 0 || count3 > 0 ){
				result.setSuccess(false);
				result.setMsg("该ip池下还有正在使用的ip，不能删除该ip池");
				return result;
			}
		}
		if("site".equals(param.getParamType())){
			Integer count = this.userService.queryCountBySite(param.getParamValue());
			if(count > 0){
				result.setSuccess(false);
				result.setMsg("该座位区域下还有在职员工，不能删除该座位区域");
				return result;
			}
		}
		this.paramService.delete(paramId);
		this.rePropertiesSet();
		result.setSuccess(true);
		result.setMsg("删除成功！");
		return result;
	}
	
	@RequestMapping("/saveParam")
	@ResponseBody
	public ExecResult savaParam(Param param){
		ExecResult result = new ExecResult();
		param.setState(1);
		if(param.getParamType().contains("_ip")){
			//ip项
			String paramValue = param.getParamValue();
			String ipBegin = param.getIpBegin().intValue()+"";
			String ipEdn = param.getIpEnd().intValue()+"";
			String paramName = paramValue+"("+ipBegin+"-"+ipEdn+")";
			param.setParamName(paramName);
			if(paramService.queryParamCountByIpSegmentAndSE("-1", paramValue, ipBegin, ipEdn) > 0){
				result.setSuccess(false);
				result.setMsg("与已经存在的ip段冲突");
				return result;
			}
		}
		Integer count = this.paramService.queryParamCountByParamNameAndParamValue("-1", param.getParamName(), param.getParamValue());
		if(count > 0){
			result.setSuccess(false);
			result.setMsg("参数名或者参数值已存在！");
			return result;
		}
		count = this.paramService.queryParamCount();
		param.setId(count.intValue()+1+"");
		this.paramService.save(param);
		this.rePropertiesSet();
		result.setSuccess(true);
		result.setMsg("保存成功！");
		return result;
	}
	
	@RequestMapping("/saveEditedParam")
	@ResponseBody
	public ExecResult savaEditedParam(Param param){
		ExecResult result = new ExecResult();
		if(param.getParamType().equals("mail_config")){
			//邮件通知配置
			param.setState(1);
			param.setParamName("是否需要邮件通知");
			this.paramService.update(param);
			this.rePropertiesSet();
			result.setSuccess(true);
			result.setMsg("保存成功！");
			return result;
		}
		
		param.setState(1);
		String id = param.getId();
		String paraType = param.getParamType();
		
		//获取新的param信息
		String newParamValue = param.getParamValue();
		String newIpBegin = param.getIpBegin().intValue()+"";
		String newIpEnd = param.getIpEnd().intValue()+"";
		
		//获取旧的param信息
		Param oldParam = this.paramService.get(id);
		String oldParamValue = oldParam.getParamValue();
		String oldIpBegin = oldParam.getIpBegin().intValue()+"";
		String oldIpEnd = oldParam.getIpEnd().intValue()+"";
		
		Integer count = 0 ;
		
		if(oldParamValue.equals(newParamValue)){
			count = this.paramService.queryParamCountByIpSegmentAndSE(id,newParamValue, newIpBegin, newIpEnd);
			if(count > 0){
				result.setSuccess(false);
				result.setMsg("与已经存在的ip段冲突");
				return result;
			}
			else{
				Integer count1 = this.pmService.queryCountByIpSegmentIdANDSE(id, newIpBegin, newIpEnd);
				Integer count2 = this.userService.queryCountByIpSegmentIdANDSE(id, newIpBegin, newIpEnd);
				Integer count3 = this.vmService.queryCountByIpSegmentIdANDSE(id, newIpBegin, newIpEnd);
				if(count1 > 0 || count2 > 0 || count3 > 0 ){
					result.setSuccess(false);
					result.setMsg("原来的ip池下还有正在使用的ip");
					return result;
				}
			}	
		}
		else{
			Integer count1 = this.pmService.queryCountByIpSegmentId(id);
			Integer count2 = this.userService.queryCountByIpSegmentId(id);
			Integer count3 = this.vmService.queryCountByIpSegmentId(id);
			if(count1 > 0 || count2 > 0 || count3 > 0 ){
				result.setSuccess(false);
				result.setMsg("原来的ip池下还有正在使用的ip");
				return result;
			}
			else{
				count = this.paramService.queryParamCountByIpSegmentAndSE(id,newParamValue, newIpBegin, newIpEnd);
				if(count > 0){
					result.setSuccess(false);
					result.setMsg("与已经存在的ip段冲突");
					return result;
				}
			}
		}
		String newParamName = newParamValue+"("+newIpBegin+"-"+newIpEnd+")";
		param.setParamName(newParamName);
		this.paramService.update(param);
		this.rePropertiesSet();
		result.setSuccess(true);
		result.setMsg("保存成功！");
		return result;
	}
	
	@RequestMapping("/addParam")
	public ModelAndView addParam(int paramTypeIndex){
		ModelAndView view = new ModelAndView("paramManage/addParam");
		String paramType = Conts.PARAM_TYPE[paramTypeIndex-1];
		view.addObject("paramType", paramType);
		view.addObject("paramTypeIndex", paramTypeIndex);
		return view;
	}
	
	@RequestMapping("/alterParam")
	public ModelAndView alterParam(String paramId){
		ModelAndView view = new ModelAndView("paramManage/alterParam");
		Param param = paramService.get(paramId);
		view.addObject("paramInfo", param);
		return view;
	}
	
	//重新设置静态参数hash表里面的字段
	public void rePropertiesSet(){
		InitData.paramListTable.clear();
		InitData.paramMapTable.clear();
		for(String paramType : Conts.PARAM_TYPE){
			List<Param> paramList = paramService.queryParamByParamType(paramType);
			InitData.paramListTable.put(paramType, paramList);
			InitData.paramMapTable.put(paramType, listToMap(paramType , paramList));
		}
	}
	
	private Map<String,Param> listToMap(String paramType , List<Param> paramList){
		HashMap<String , Param> paramMap = new HashMap<String , Param>();
		for(Param param : paramList){
			if(paramType.toLowerCase().contains("_ip")){
				paramMap.put(param.getId(), param);
			}else{
				paramMap.put(param.getParamValue(), param);
			}
		}
		return paramMap;
	}
}
