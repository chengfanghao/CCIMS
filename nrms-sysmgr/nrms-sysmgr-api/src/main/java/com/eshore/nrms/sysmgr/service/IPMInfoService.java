package com.eshore.nrms.sysmgr.service;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.PMInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.vo.PageVo;

/**
 * 
 * @author lile
 * @date 2016年8月1日
 *
 */
public interface IPMInfoService extends IBaseService<PMInfo>{
	//查询所有的物理机，返回物理机List
	//public List<PMInfo> queryAllPMInfo();
	
	public Integer queryPMInfoByFullIpAndId(String fullIp,String id);
	
	public PageVo<PMInfo> queryPMInfoByPage(PMInfo pmInfo , PageConfig page);
	
	public Integer getPMCountByFullIp(String fullIp);
	
	public List<PMInfo> queryAllPMInfo(PageConfig page);
	
	public Integer queryCountByIpSegmentId(String ipSegmentId);
	/*
	 * 
	 * 参考IUserInfoService
		public UserInfo userLogin(String loginName);
	
		public PageVo<UserInfo> queryUserInfoByPage(UserInfo userInfo , PageConfig page);
	
		public Integer getUserCountByLoginName(String loginName , String userId);
	
		public Integer getUserCountByFullIp(String fullIp , String userId);
	 * */
	
	public Integer queryCountByIpSegmentIdANDSE(String ipSegmentId , String beginIp, String endIp ); 
	
	//public Integer queryParamCountByParamName(String paramName);
}
