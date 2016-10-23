package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.PMInfo;

/**
*@author lile
*@date 2016年8月1日
**/
public interface IPMInfoDao extends IBaseDao<PMInfo>{
	/**
	 * 根据输入信息，模糊查询所有符合条件的物理列表，无输入信息时将获取所有物理机列表
	 * @param pmInfo
	 * @param page 页面参数
	 * @returnList<PMInfo>物理机信息List集合
	 */
	public List<PMInfo> queryPMInfoList(PMInfo pmInfo,PageConfig page);
	/***************同上queryPMInfoList可以进行整合*******************/
	/**
	 * 查询所有的物理机信息
	 * @return List<PMInfo>物理机信息List集合
	 */
	public List<PMInfo> queryPMInfoListAll(PageConfig page);
	/***************同上queryPMInfoList可以进行整合*******************/
	
	/**
	 * 根据pmInfo对象查询某个物理机
	 * @param fullIp
	 * @return 物理机信息
	 */
	public Integer queryPMInfoByFullIpAndId(String fullIp, String id);
	
	/**
	 * 根据fullIp查询对应物理机个数
	 * @param fullIp 
	 * @return
	 */
	public Integer getPMCountByFullIp(String fullIp);
	
	/**
	 * 根据IP段ID来查询记录条数
	 * @param ipSegmentId
	 * @return count
	 * 2016年8月4日
	 */
	public Integer getCountByIpSegmentId(String ipSegmentId);
	
	/**
	 * 查询不在对应ip段起始ip内的记录条数
	 * @param ipSegmentId,beginIp,endIp
	 * @return count
	 * 2016年8月4日
	 */
	public Integer getCountByIpSegmentIdAndSE(String ipSegmentId , String beginIp , String endIp);
	
	
	
}
