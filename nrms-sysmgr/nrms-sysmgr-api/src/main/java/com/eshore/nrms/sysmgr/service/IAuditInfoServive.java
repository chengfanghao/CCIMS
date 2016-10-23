package com.eshore.nrms.sysmgr.service;

import java.util.List;

import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.AuditInfo;

public interface IAuditInfoServive extends IBaseService<AuditInfo>{
	/**
	 * 查询工单下所有的操作集合
	 * @param orderId：工单id
	 * @return
	 */
	public List<AuditInfo> queryAuditInfoByOrderId(String orderId);
	
	/**
	 * 根据用户id查询自身审批过的工单
	 * @param operUserId
	 * @return
	 */
	public List<AuditInfo> queyAuditInfiByOperUserId(String operUserId);
}
