package com.eshore.nrms.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IOrderInfoDao;
import com.eshore.nrms.sysmgr.pojo.OrderInfo;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.sysmgr.service.IOrderInfoService;
import com.eshore.nrms.util.PageUtil;
import com.eshore.nrms.vo.PageVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo> implements IOrderInfoService{

	@Autowired
	private IOrderInfoDao orderInfoDao;
	
	@Override
	public IBaseDao<OrderInfo> getDao() {
		return orderInfoDao;
	}

	@Override
//	@Transactional(readOnly = true)
	public PageVo<OrderInfo> queryOrderInfoByPage(OrderInfo orderInfo,UserInfo user, PageConfig page) {
		List<OrderInfo> orderInfoList = this.orderInfoDao.queryOrderListByPage(orderInfo, user,page);
		return PageUtil.getPageList(page, orderInfoList);
	}

	@Override
	public Integer getOrderMaxId() {		
		return this.orderInfoDao.getOrderMaxId();
	}


	@Override
	public Integer idOrder(String orderId) {
		return this.orderInfoDao.isOrder(orderId);
	}

	@Override
	public List<OrderInfo> queryOrderList() {
		return this.orderInfoDao.queryOrderList();
	}


	@Override
	public PageVo<OrderInfo> queryOrderInfoByPage(OrderInfo orderInfo, UserInfo user, PageConfig page, int myState) {
		List<OrderInfo> orderInfoList = this.orderInfoDao.queryOrderListByPage(orderInfo, user,page,myState);
		return PageUtil.getPageList(page, orderInfoList);
	}

}
