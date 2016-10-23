package com.eshore.nrms.sysmgr.service;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.vo.PageVo;

public interface IUserInfoService extends IBaseService<UserInfo> {

	public UserInfo userLogin(String loginName);
	
	public UserInfo getUserByUserName(String userName);
	
	public PageVo<UserInfo> queryUserInfoByPage(UserInfo userInfo , PageConfig page);
	
	public Integer getUserCountByLoginName(String loginName , String userId);
	
	public Integer getUserCountByFullIp(String fullIp , String userId);
	
	public Integer getUserCountByUserName(String userName,String userId);
	
	public Integer queryCountByIpSegmentId(String ipSegmentId);
	
	public Integer queryCountBySeatNum(String seatNum,String userId,String site);
	
	public Integer queryCountByUserCode(String userCode,String userId);
	
	public Integer queryCountByIpSegmentIdANDSE(String ipSegmentId , String beginIp, String endIp ); 
	
	public Integer queryCountBySite(String site);
}
