package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.AuditProcess;

public interface IAuditProcessDao extends IBaseDao<AuditProcess> {
	/**
	 * 查询所有的审批流程
	 * @return
	 */
	public List<AuditProcess> queryAuditProcess();
	
	/**
	 * 获取下一个工单审批状态值
	 * @param oldState：工单现审批状态值
	 * @return
	 */
	public int getNextStateValue(int oldState);
	
	/**
	 * 根据用户id获取用户在审批流程中的状态码
	 * @param userId:用户id
	 * @return
	 */
	public int getStateValueByUserId(String userId);
	
	/**
	 * 根据用户Id获取流程状态
	 * @param userId
	 * @return
	 */
	public AuditProcess getProcessByUserId(String userId);
}
