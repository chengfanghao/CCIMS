package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.UserInfo;

public interface IUserInfoDao extends IBaseDao<UserInfo> {

	public UserInfo queryUserByLoginName(String loginName);
	
	public UserInfo queryUserByUserName(String userName);
	
	public List<UserInfo> queryUserList(UserInfo userInfo ,  PageConfig page);
	
	public Integer getUserCountByLoginName(String loginName, String userId);
	
	public Integer getUserCountByFullIp(String fullIp , String userId);
	
	public Integer getUserCountByUserName(String userName,String userId); 
	
	public Integer getCountByIpSegmentId(String ipSegmentId);

	public Integer getCountByIpSegmentIdAndSE(String ipSegmentId , String beginIp , String endIp);
	
	public Integer getCountBySite(String site);
	
	/**
	 * 获取某个座位号的人数
	 * @param SeatNum
	 * @return
	 */
	public Integer getCountBySeatNum(String SeatNum,String userId,String site);
	/**
	 * 获取某个工号的人数
	 * @param 
	 * @return
	 */
	public Integer getCountByUserCode(String userCode,String userId);
}
