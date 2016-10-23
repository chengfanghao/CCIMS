package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;

public interface IOrderVMInfoDao extends IBaseDao<OrderVMInfo> {
	/**
	 * 查询工单下的所有虚拟机
	 * @param orderId:工单id
	 * @return:工单下虚拟机列表
	 */
	public List<OrderVMInfo> queryVMListByOrderId(String orderId);

	public Integer getVMCount();
	
	public Integer getVMCountByOrderId(String orderId);
	
	public Integer isOrderVmInfo(String id);
}
