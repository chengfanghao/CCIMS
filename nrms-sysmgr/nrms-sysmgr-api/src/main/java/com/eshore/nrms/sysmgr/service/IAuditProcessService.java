package com.eshore.nrms.sysmgr.service;

import java.util.List;

import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.AuditProcess;

public interface IAuditProcessService extends IBaseService<AuditProcess>{
	/**
	 * 获取审批流程列表
	 * @return
	 */
	public List<AuditProcess> queryAuditProcessList();
	
	/**
	 * 获取下一个审批状态参数值
	 * @param oldState：现状态参数值
	 * @return
	 */
	public int getNextState(int oldState);
	
	/**
	 * 获取上一个审批状态参数值
	 * @param oldState：现状态参数值
	 * @return
	 */
	public int getLastState(int oldState);
	
	/**
	 * 根据用户id获取用户在审批流程中的状态码
	 * @param userId:用户id
	 * @return
	 */
	public int getStateValueByUserId(String userId);
	
	/**
	 * 根据用户id获取用户在审批流程
	 * @param userId:用户id
	 * @return
	 */
	public AuditProcess getProcessByUserId(String userId);
}
