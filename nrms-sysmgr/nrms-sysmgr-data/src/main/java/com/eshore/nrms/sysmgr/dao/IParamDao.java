package com.eshore.nrms.sysmgr.dao;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.api.dao.IBaseDao;
import com.eshore.nrms.sysmgr.pojo.Param;

public interface IParamDao extends IBaseDao<Param> {

	public List<Param> queryParamByParamType(String paramType);
	
	/**
	 * 分页查询参数列表
	 * @param param
	 * @param page
	 * @return
	 * @author hecheng
	 */
	public List<Param> queryParamList(Param param,PageConfig page);
	
	/**
	 * 查询参数列表总数
	 * @return
	 */
	public Integer queryParamCount();
	
	/**
	 * 查询某一ip段与之冲突的Ip段个数
	 * @return
	 */
	public Integer queryParamCountByIpSegmentAndSE(String id , String paramValue , String beginIp , String endIp);
	
	/**
	 * 查询名为paramName
	 * @return
	 */
	public Integer queryParamCountByParamName(String paramName);
	
	/**
	 * 查询名为paramName且值为paramValue但id不为id的记录个数
	 * @return
	 */
	public Integer queryParamCountByParamNameAndParamValue(String id , String paramName , String paramValue);
	
	/**
	 * 得到名为paramName的实例
	 * @return
	 */
	public Param getParamByParamName(String paramName);
}
