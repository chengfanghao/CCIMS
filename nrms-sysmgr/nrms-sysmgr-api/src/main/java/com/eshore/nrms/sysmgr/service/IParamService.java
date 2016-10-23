package com.eshore.nrms.sysmgr.service;

import java.util.List;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.api.IBaseService;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.vo.PageVo;

public interface IParamService extends IBaseService<Param> {

	/**
	 * 根据参数类型搜索
	 * @param paramType
	 * @return
	 */
	public List<Param> queryParamByParamType(String paramType);
	
	/**
	 * 查询所有参数列表
	 * @param param
	 * @param page
	 * @return
	 */
	public PageVo<Param> queryParamList(Param param,PageConfig page);
	
	/**
	 * 查询参数表总数
	 * @return
	 */
	public Integer queryParamCount();
	
	/**
	 * 查询在冲突的ip段个数
	 * @return
	 */
	public Integer queryParamCountByIpSegmentAndSE(String id , String paramValue , String beginIp , String endIp);
	
	/**
	 * 查询名为paramName的记录个数
	 * @return
	 */
	public Integer queryParamCountByParamName(String paramName);
	
	/**
	 * 查询名为paramName或值为paramValue但id不等于此id的记录个数
	 * @return
	 */
	public Integer queryParamCountByParamNameAndParamValue(String id , String paramName, String paramValue);
	
	/**
	 * 得到名为paramName的实例
	 * @return
	 */
	public Param getParamByParamName(String paramName);
}
