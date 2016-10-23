package com.eshore.nrms.sysmgr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IAuditInfoDao;
import com.eshore.nrms.sysmgr.pojo.AuditInfo;

@Repository
public class AuditInfoDaoImpl extends JpaDaoImpl<AuditInfo> implements IAuditInfoDao{

	@Override
	public List<AuditInfo> queryAuditInfoByOrderId(String orderId) {
		String hql = new String("from AuditInfo o where o.orderId = ? order by o.operDate");
		Object[] param = {orderId};
		return this.query(hql, param);
	}

	@Override
	public List<AuditInfo> queryAuditInfoByOperUserId(String operUserId) {
		String hql = new String("from AuditInfo o where o.operUserId = ? and o.operType != 1 order by o.operDate");
		Object[] param = {operUserId};
		return this.query(hql, param);
	}
}
