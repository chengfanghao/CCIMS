package com.eshore.nrms.sysmgr.service;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.OrderInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.vo.PageVo;

public interface IOrderInfoService extends IBaseService<OrderInfo> {
	/**
	 * 分页查询所有工单列表
	 * @param orderInfo
	 * @param user
	 * @param page
	 * @return
	 */
	public PageVo<OrderInfo> queryOrderInfoByPage(OrderInfo orderInfo , UserInfo user,PageConfig page);
	
	/**
	 * 分页查询所有工单列表
	 * @param orderInfo
	 * @param user
	 * @param page
	 * @param myState:普通用户审批流程中的值
	 * @return
	 */
	public PageVo<OrderInfo> queryOrderInfoByPage(OrderInfo orderInfo , UserInfo user,PageConfig page,int myState);
	
	/**
	 * 查询工单总数
	 * @return
	 */
	public Integer getOrderMaxId();
	
	public Integer idOrder(String orderId);
	
	/**
	 * 查询所有工单
	 * @return
	 */
	public List<OrderInfo> queryOrderList();
	
}
