package com.eshore.nrms.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IAuditInfoDao;
import com.eshore.nrms.sysmgr.pojo.AuditInfo;
import com.eshore.nrms.sysmgr.service.IAuditInfoServive;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AuditInfoServiceImpl extends BaseServiceImpl<AuditInfo> implements IAuditInfoServive{

	@Autowired
	private IAuditInfoDao auditInfoDao;
	
	@Override
	public List<AuditInfo> queryAuditInfoByOrderId(String orderId) {
		return this.auditInfoDao.queryAuditInfoByOrderId(orderId);
	}

	@Override
	public IBaseDao<AuditInfo> getDao() {
		return auditInfoDao;
	}

	@Override
	public List<AuditInfo> queyAuditInfiByOperUserId(String operUserId) {
		return this.auditInfoDao.queryAuditInfoByOperUserId(operUserId);
	}

}
