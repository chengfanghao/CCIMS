package com.eshore.nrms.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IParamDao;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.vo.Conts;

@Repository
public class ParamDaoImpl extends JpaDaoImpl<Param> implements IParamDao {


	@Override
	public List<Param> queryParamByParamType(String paramType) {
		String hql = "from Param where paramType = ? and state = 1 order by paramName";
		return this.query(hql, new Object[]{paramType});
	}

	@Override
	public List<Param> queryParamList(Param param, PageConfig page) {
		StringBuilder hql = new StringBuilder("from Param p where state = 1 ");
		List<String> params = new ArrayList<String>();
		
		if(StringUtils.isNotBlank(param.getParamType()) && !"0".equals(param.getParamType())){
			int index = new Integer(param.getParamType()).intValue();
			String type = Conts.PARAM_TYPE[index-1];
			hql.append(" and p.paramType like ? ");
			params.add(type);
		}
		hql.append(" order by paramType ");
		return this.queryPage(hql.toString(), page, params.toArray());
	}

	@Override
	public Integer queryParamCount() {
		String sql = "from Param s where "+
				"id = (select max(id) from Param s1 where LENGTH(s1.id) >= all ("+
				"select LENGTH(s3.id) from Param s3))";
		Param param = this.getPojo(sql, null);
		return new Integer(param.getId());
	}
	
	@Override
	public Integer queryParamCountByIpSegmentAndSE(String id , String paramValue , String beginIp , String endIp){
		StringBuilder hql = new StringBuilder("select count(*) from Param p where 1= 1 and p.state = 1 "
				+ " and p.paramValue = ? "
				+ " and ("
				+ " (p.ipBegin <= ? and p.ipBegin >= ? ) or "
				+ " (p.ipEnd <= ? and p.ipEnd >= ? ) or "
				+ " (p.ipBegin <= ? and p.ipEnd >= ? ) "
				+ " ) and p.id <> ? ");
		return this.queryCount(hql.toString(), new Object[]{paramValue , Integer.valueOf(endIp)  , Integer.valueOf(beginIp) , Integer.valueOf(endIp) , Integer.valueOf(beginIp) , Integer.valueOf(beginIp) , Integer.valueOf(endIp) , id });
	}

	@Override
	public Integer queryParamCountByParamName(String paramName){
		StringBuilder hql = new StringBuilder("select count(*) from Param p where p.paramName = ?");
		return this.queryCount(hql.toString(),new Object[]{paramName});
	}

	@Override
	public Integer queryParamCountByParamNameAndParamValue(String id , String paramName, String paramValue) {
		StringBuilder hql = new StringBuilder("select count(*) from Param p where p.id <> ? and ( p.paramName = ? or p.paramValue = ? )");
		return this.queryCount(hql.toString(),new Object[]{ id , paramName , paramValue });
	}

	@Override
	public Param getParamByParamName(String paramName) {
		StringBuilder  hql = new StringBuilder(" from Param p where p.paramName = ?");
		return this.getPojo(hql.toString(), new Object[]{paramName});
	}
	

	
}
