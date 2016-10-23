package com.eshore.nrms.sysmgr.service;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.Score;
import com.eshore.nrms.vo.PageVo;

public interface IScoreService extends IBaseService<Score>{
	
	public Score queryScoreByScoreId(Integer scoreId) ;
	
	public PageVo<Score> queryScoreListByPage(Score score, PageConfig page);
	
	public Integer getScoreCountByStuId(String sno) ;
	

}
