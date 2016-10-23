package com.eshore.nrms.sysmgr.service;

import java.util.List;

import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;

public interface IOrderVMInfoSercvice extends IBaseService<OrderVMInfo>{
	public List<OrderVMInfo> queryVMListByOrderId(String orderId);
	
	public Integer getVMCount();
	
	public Integer getVMCountByOrderId(String orderId);
	
	public Integer isOrderVmInfo(String vmId);
	
}
