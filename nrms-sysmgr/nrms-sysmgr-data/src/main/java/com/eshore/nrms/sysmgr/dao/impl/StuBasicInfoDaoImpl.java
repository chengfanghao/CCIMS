package com.eshore.nrms.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IStuBasicInfoDao;
import com.eshore.nrms.sysmgr.pojo.StuBasicInfo;

@Repository
public class StuBasicInfoDaoImpl extends JpaDaoImpl<StuBasicInfo> implements IStuBasicInfoDao {

	@Override
	public StuBasicInfo queryStuUserBySno(String sno) {

		String hql = "from StuBasicInfo u where u.sno = ?";

		return this.getPojo(hql, new Object[] { sno });
	}

	@Override
	public List<StuBasicInfo> queryUserList(StuBasicInfo userInfo, PageConfig page) {

		StringBuilder hql = new StringBuilder("from StuBasicInfo u where 1=1 ");
		List params = new ArrayList();
		if (StringUtils.isNotBlank(userInfo.getSno())) {
			hql.append(" and u.sno like ? ");
			params.add("%" + userInfo.getSno() + "%");
		}

		if (StringUtils.isNotBlank(userInfo.getName())) {
			hql.append(" and u.name like ? ");
			params.add("%" + userInfo.getName() + "%");
		}

		hql.append(" and u.role = '学生' ");
		hql.append(" order by sno ");

		return this.queryPage(hql.toString(), page, params.toArray());
	}

	@Override
	public Integer getUserCountBySno(String sno) {
		StringBuilder hql = new StringBuilder("select count(*) from StuBasicInfo u where u.sno = ? ");
		ArrayList params = new ArrayList();

		if (StringUtils.isNotBlank(sno)) {
			params.add(sno);
		}

		return this.queryCount(hql.toString(), params.toArray());
	}

}
