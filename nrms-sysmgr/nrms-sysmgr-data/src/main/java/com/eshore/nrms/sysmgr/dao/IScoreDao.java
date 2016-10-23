package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.Score;
import com.eshore.nrms.sysmgr.pojo.UserInfo;

public interface IScoreDao extends IBaseDao<Score> {	
	public List<Score> queryScoreList(Score score ,  PageConfig page);
	
	public Integer getScoreCountByStuId(String sno);
	
	public Score queryScoreByScoreId(Integer scoreId) ;
}
