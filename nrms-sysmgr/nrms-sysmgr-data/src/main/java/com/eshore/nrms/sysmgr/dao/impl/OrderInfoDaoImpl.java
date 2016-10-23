package com.eshore.nrms.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IOrderInfoDao;
import com.eshore.nrms.sysmgr.pojo.OrderInfo;
import com.eshore.nrms.sysmgr.pojo.UserInfo;
import com.eshore.nrms.vo.Conts;

@Repository
public class OrderInfoDaoImpl extends JpaDaoImpl<OrderInfo> implements IOrderInfoDao {

	@Override
	public List<OrderInfo> queryOrderListByPage(OrderInfo orderInfo, UserInfo user, PageConfig page) {
		StringBuilder hql = new StringBuilder("from OrderInfo o where 1=1 ");
		List params = new ArrayList();
		// 针对根据项目名查询的功能
		if (StringUtils.isNotBlank(orderInfo.getProjectName())) {
			hql.append(" and o.projectName like ? ");
			params.add("%" + orderInfo.getProjectName() + "%");
		}

		// 针对根据状态名查询的功能
		int state = orderInfo.getState();
		if (Conts.ROLE_ADMIN.equals(user.getUserRole()) || Conts.ROLE_MANAGER.equals(user.getUserRole())) {// 管理员角色查询
			if (state != 0) {
				hql.append(" and o.state = ?");
				params.add(state);
			}
			hql.append(" and o.state != 1");
		} else {
			int state1 = orderInfo.getState();// 初始的登陆的时候的值为0
			if (state1 != 0) {
				hql.append(" and o.state = ? ");
				params.add(state1);
			}
			hql.append(" and o.state != 0 and o.createUserName = ?");
			params.add(user.getUserName());	
		}
		
		hql.append("and o.state != 0");
		hql.append(" order by createDate ");
		return this.queryPage(hql.toString(), page, params.toArray());
	}

	@Override
	public Integer getOrderMaxId() {
		String hql = "from OrderInfo o WHERE o.id >= all (SELECT o1.id FROM OrderInfo o1 )";
	    OrderInfo orderInfo= this.getPojo(hql, null);
		return new Integer(orderInfo.getId());
		
	}

	@Override
	public Integer isOrder(String orderId) {
		StringBuilder hql = new StringBuilder("from OrderInfo o where o.id  = ? ");
		ArrayList params = new ArrayList();
		params.add(orderId);
		hql.append("and o.state != 0");
		return this.queryCount(hql.toString(), params.toArray());
	}

	@Override
	public List<OrderInfo> queryOrderList() {
		String hql = "from OrderInfo o where o.state != 0 ";
		return this.query(hql, null);
	}

	@Override
	public List<OrderInfo> queryOrderListByPage(OrderInfo orderInfo, UserInfo user, PageConfig page, int myState) {
		StringBuilder hql = new StringBuilder("from OrderInfo o where 1=1 ");
		List params = new ArrayList();
		// 针对根据项目名查询的功能
		if (StringUtils.isNotBlank(orderInfo.getProjectName())) {
			hql.append(" and o.projectName like ? ");
			params.add("%" + orderInfo.getProjectName() + "%");
		}
		hql.append(" and o.state != 0 and o.state = ?");
		params.add(myState);
		return this.queryPage(hql.toString(), page, params.toArray());
	}
}
