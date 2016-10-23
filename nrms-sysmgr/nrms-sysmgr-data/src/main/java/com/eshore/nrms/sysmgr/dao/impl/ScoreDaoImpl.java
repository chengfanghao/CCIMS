package com.eshore.nrms.sysmgr.dao.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IScoreDao;
import com.eshore.nrms.sysmgr.pojo.Score;
import com.eshore.nrms.sysmgr.pojo.UserInfo;


@Repository
public class ScoreDaoImpl extends JpaDaoImpl<Score> implements
		IScoreDao {

	@Override
	public List<Score> queryScoreList(Score score, PageConfig page) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("from Score s where 1=1 ");
		List params = new ArrayList();
		if(StringUtils.isNotBlank(score.getSno())){
			hql.append(" and s.sno = ? ");
			params.add(score.getSno() );
		}
		
		if(StringUtils.isNotBlank(score.getTermName())){
			hql.append(" and s.termName = ? ");
			params.add(score.getTermName());
		}
		
		if(StringUtils.isNotBlank(score.getYearId().toString())){
			hql.append(" and s.yearId = ? ");
			params.add(score.getYearId().toString());
		}

		hql.append(" order by scoreId ");
		
		return this.queryPage(hql.toString(), page, params.toArray());
	}

	@Override
	public Integer getScoreCountByStuId(String sno) {
		StringBuilder hql = new StringBuilder("select count(*) from Score s where 1 = 1 and s.sno = ? ");
		ArrayList params = new ArrayList();
		params.add(sno);

		
		return this.queryCount(hql.toString(), params.toArray());
	}

	@Override
	public Score queryScoreByScoreId(Integer scoreId) {
		String hql = "from Score s where s.scoreId = ?";
		
		return this.getPojo(hql, new Object[]{scoreId});
	}


	

}
