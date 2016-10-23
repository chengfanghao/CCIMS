package com.eshore.nrms.sysmgr.service;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.Award;
import com.eshore.nrms.vo.PageVo;

public interface IAwardService extends IBaseService<Award> {

	public PageVo<Award> queryAwardByPage(Award award, PageConfig page);

	public Integer getAwardCountBySno(String sno);
}
