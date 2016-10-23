package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.OrderInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;

public interface IOrderInfoDao extends IBaseDao<OrderInfo> {
	/**
	 * 分页分角色查询所有工单信息
	 * @param orderInfo
	 * @param user：用户
	 * @param page
	 * @return 工单列表:List<OrderInfo>
	 */
	public List<OrderInfo> queryOrderListByPage(OrderInfo orderInfo , UserInfo user, PageConfig page);
	/**
	 * 分页分角色查询所有工单信息
	 * @param orderInfo
	 * @param user：用户
	 * @param page
	 * @return 工单列表:List<OrderInfo>
	 */
	public List<OrderInfo> queryOrderListByPage(OrderInfo orderInfo , UserInfo user, PageConfig page,int myState);
	
	/**
	 * 获取工单总数
	 * @return 工单总数 Integer
	 */
	public Integer getOrderMaxId();
	

	public Integer isOrder(String orderId);

	/**
	 * 查询所有工单
	 * @return
	 */
	public List<OrderInfo> queryOrderList();
}
