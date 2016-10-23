package com.eshore.nrms.sysmgr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IAuditProcessDao;
import com.eshore.nrms.sysmgr.pojo.AuditProcess;

@Repository
public class AuditProcessDaoImpl extends JpaDaoImpl<AuditProcess>
	implements IAuditProcessDao{

	@Override
	public List<AuditProcess> queryAuditProcess() {
		String sql = "from AuditProcess a where a.state = 1 order by a.value";
		return this.query(sql, null);
	}

	@Override
	public int getNextStateValue(int oldState) {
		List<AuditProcess> processList = this.queryAuditProcess();
		int nextState = 0;
		for(AuditProcess temp : processList){
			nextState = temp.getValue();
			if(temp.getValue() > oldState){
				return nextState;
			}
		}
		return nextState;
	}

	@Override
	public int getStateValueByUserId(String userId) {
		String hql = "from AuditProcess a where a.state = 1 and a.userId = ?";
		return this.getPojo(hql, new Object[]{userId}).getValue();
	}

	@Override
	public AuditProcess getProcessByUserId(String userId) {
		String hql = "from AuditProcess a where a.userId = ?";
		return this.getPojo(hql, new Object[]{userId});
	}
}
