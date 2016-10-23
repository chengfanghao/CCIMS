package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.Award;

public interface IAwardDao extends IBaseDao<Award> {

	public Award queryAwardByAwardId(String awardId);

	public List<Award> queryAwardList(Award award, PageConfig page);

	public Integer getAwardCountBySno(String sno);
}
