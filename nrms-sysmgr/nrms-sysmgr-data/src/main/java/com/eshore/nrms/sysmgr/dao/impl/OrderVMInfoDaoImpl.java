package com.eshore.nrms.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IOrderVMInfoDao;
import com.eshore.nrms.sysmgr.pojo.OrderVMInfo;

@Repository
public class OrderVMInfoDaoImpl extends JpaDaoImpl<OrderVMInfo> implements IOrderVMInfoDao{

	@Override
	public List<OrderVMInfo> queryVMListByOrderId(String orderId) {
		String hql = "from OrderVMInfo o where o.orderId = ?";
		Object[] param = {orderId};
		return this.query(hql, param);
	}

	@Override
	public Integer getVMCount() {
		String hql ="select count(*) from OrderVMInfo u";		
		return this.queryCount(hql);
	}

	@Override
	public Integer getVMCountByOrderId(String orderId) {
		StringBuilder hql = new StringBuilder("select count(*) from OrderVMInfo u where u.orderId  = ? ");
		ArrayList params = new ArrayList();
		params.add(orderId);		
		return this.queryCount(hql.toString(), params.toArray());
	}

	@Override
	public Integer isOrderVmInfo(String id) {
		StringBuilder hql = new StringBuilder("select count(*) from OrderVMInfo u where u.id  = ? ");
		ArrayList params = new ArrayList();
		params.add(id);		
		return this.queryCount(hql.toString(), params.toArray());
	}
}
