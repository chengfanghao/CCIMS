package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.pojo.VMInfo;

public interface IVMInfoDao extends IBaseDao<VMInfo> {
	
	/**
	 * 根据ip查询对应虚拟机
	 * @param fullIp
	 * @return 虚拟机对象
	 */
	public VMInfo queryVmByFullIp(String fullIp);
	
	/**
	 * 根据查询输入信息模糊查询虚拟机列表
	 * @param VMInfo
	 * @return 虚拟机列表
	 */
	public List<VMInfo> queryVmList(VMInfo vmInfo , PageConfig page);
	
	/**
	 * 根据ip查询对应虚拟机个数
	 * @param fullIp
	 * @return 虚拟机个数
	 */
	public Integer getVmCountByFullIp(String fullIp);
	
	/**
	 * 根据虚拟机单号查询对应虚拟机
	 * @param orderVMId
	 * @return 虚拟机对象
	 */
	public VMInfo queryVMInfoByOrderVMId(String orderVMId);
	
	/**
	 * 根据物理机ID查询虚拟机列表
	 * @param pmId，page
	 * @return 虚拟机列表
	 */
	public List<VMInfo> queryVmListByPMId(String pmId, PageConfig page); 
	
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
