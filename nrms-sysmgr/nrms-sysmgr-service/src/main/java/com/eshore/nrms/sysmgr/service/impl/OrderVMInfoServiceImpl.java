package com.eshore.nrms.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IOrderVMInfoDao;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;
import com.eshore.nrms.sysmgr.service.IOrderVMInfoSercvice;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderVMInfoServiceImpl extends BaseServiceImpl<OrderVMInfo> 
	implements IOrderVMInfoSercvice{

	@Autowired
	private IOrderVMInfoDao orderVMInfoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<OrderVMInfo> queryVMListByOrderId(String orderId) {
		return orderVMInfoDao.queryVMListByOrderId(orderId);
	}

	@Override
	public IBaseDao<OrderVMInfo> getDao() {
		return orderVMInfoDao;
	}

	@Override
	public Integer getVMCount() {
		return this.orderVMInfoDao.getVMCount();
	}

	@Override
	public Integer getVMCountByOrderId(String orderId) {
		return this.orderVMInfoDao.getVMCountByOrderId(orderId);
	}

	@Override
	public Integer isOrderVmInfo(String vmId) {
		return this.orderVMInfoDao.isOrderVmInfo(vmId);
	}

}
