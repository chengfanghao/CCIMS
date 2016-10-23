package com.eshore.nrms.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.khala.core.service.impl.BaseServiceImpl;
import com.eshore.nrms.sysmgr.dao.IAuditProcessDao;
import com.eshore.nrms.sysmgr.pojo.AuditProcess;
import com.eshore.nrms.sysmgr.service.IAuditProcessService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AuditProcessServiceImpl extends BaseServiceImpl<AuditProcess>
	implements IAuditProcessService{

	@Autowired
	private IAuditProcessDao auditProcessDao;
	
	@Override
	public List<AuditProcess> queryAuditProcessList() {
		return this.auditProcessDao.queryAuditProcess();
	}

	@Override
	public IBaseDao<AuditProcess> getDao() {
		return auditProcessDao;
	}

	@Override
	public int getNextState(int oldState) {
		return this.auditProcessDao.getNextStateValue(oldState);
	}

	@Override
	public int getStateValueByUserId(String userId) {
		return this.auditProcessDao.getStateValueByUserId(userId);
	}

	@Override
	public int getLastState(int oldState) {
		List<AuditProcess> processList = this.auditProcessDao.queryAuditProcess();
		int lastState = 0;
		for(AuditProcess temp : processList){
			if(temp.getValue() >= oldState){
				return lastState;
			}
			lastState = temp.getValue();
		}
		return lastState;
	}

	@Override
	public AuditProcess getProcessByUserId(String userId) {
		return this.auditProcessDao.getProcessByUserId(userId);
	}
}
