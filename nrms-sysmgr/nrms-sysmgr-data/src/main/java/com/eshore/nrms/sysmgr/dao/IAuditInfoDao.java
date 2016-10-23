package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.AuditInfo;

public interface IAuditInfoDao extends IBaseDao<AuditInfo> {
	/**
	 * 根据工单查询工单所有操作
	 * @param orderId
	 * @return 操作集合
	 */
	public List<AuditInfo> queryAuditInfoByOrderId(String orderId);
	
	/**
	 * 根据用户id查询出其审批过的所有工单id列表
	 * @param userId：用户id
	 * @return
	 */
	public List<AuditInfo> queryAuditInfoByOperUserId(String operUserId);
}
