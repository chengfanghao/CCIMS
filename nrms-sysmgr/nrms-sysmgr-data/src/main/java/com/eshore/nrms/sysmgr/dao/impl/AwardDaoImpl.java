package com.eshore.nrms.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IAwardDao;
import com.eshore.nrms.sysmgr.pojo.Award;

@Repository
public class AwardDaoImpl extends JpaDaoImpl<Award> implements IAwardDao {

	@Override
	public Award queryAwardByAwardId(String awardId) {

		String hql = "from Award u where u.awardId = ?";

		return this.getPojo(hql, new Object[] { awardId });
	}

	@Override
	public List<Award> queryAwardList(Award award, PageConfig page) {
		StringBuilder hql = new StringBuilder("from Award u where 1=1 ");
		List params = new ArrayList();

		if (StringUtils.isNotBlank(award.getSno())) {
			hql.append(" and u.sno = ? ");
			params.add(award.getSno());
		}

		if (StringUtils.isNotBlank(award.getAwardName())) {
			hql.append(" and u.awardName like ? ");
			params.add("%" + award.getAwardName() + "%");
		}

		if (StringUtils.isNotBlank(award.getAwardTime())) {
			hql.append(" and u.awardTime = ? ");
			params.add(award.getAwardTime());
		}

		if (StringUtils.isNotBlank(award.getGradeId())) {
			hql.append(" and u.gradeId = ? ");
			params.add(award.getGradeId());
		}

		if (StringUtils.isNotBlank(award.getRankId())) {
			hql.append(" and u.rankId = ? ");
			params.add(award.getRankId());
		}

		if (StringUtils.isNotBlank(award.getUnit())) {
			hql.append(" and u.unit like ? ");
			params.add("%" + award.getUnit() + "%");
		}

		hql.append(" order by awardTime ");

		return this.queryPage(hql.toString(), page, params.toArray());
	}

	@Override
	public Integer getAwardCountBySno(String sno) {
		StringBuilder hql = new StringBuilder("select count(*) from Award u where u.sno = ? ");
		ArrayList params = new ArrayList();

		if (StringUtils.isNotBlank(sno)) {
			params.add(sno);
		}

		return this.queryCount(hql.toString(), params.toArray());
	}

}
