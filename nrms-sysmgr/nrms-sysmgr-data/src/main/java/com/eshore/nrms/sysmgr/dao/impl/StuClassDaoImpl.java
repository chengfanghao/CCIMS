package com.eshore.nrms.sysmgr.dao.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IScoreDao;
import com.eshore.nrms.sysmgr.dao.IStuClassDao;
import com.eshore.nrms.sysmgr.pojo.Score;
import com.eshore.nrms.sysmgr.pojo.StuClass;
import com.eshore.nrms.sysmgr.pojo.UserInfo;


@Repository
public class StuClassDaoImpl extends JpaDaoImpl<StuClass> implements
		IStuClassDao {

	@Override
	public List<StuClass> queryClassList(StuClass stuclass, PageConfig page) {
		// TODO Auto-generated method stub
				StringBuilder hql = new StringBuilder("from StuClass s where 1=1 ");
				List params = new ArrayList();
				if(stuclass.getClassId()!=null&&StringUtils.isNotBlank(stuclass.getClassId().toString())){
					hql.append(" and s.classId = ? ");
					params.add(stuclass.getClassId() );
				}
				
				if(StringUtils.isNotBlank(stuclass.getClassName())){
					hql.append(" and s.className = ? ");
					params.add(stuclass.getClassName());
				}
				
				if(stuclass.getGradeId()!=null&&StringUtils.isNotBlank(stuclass.getGradeId().toString())){
					hql.append(" and s.gradeId = ? ");
					params.add(stuclass.getGradeId());
				}
				
				if(StringUtils.isNotBlank(stuclass.getGradeName())){
					hql.append(" and s.gradeName = ? ");
					params.add(stuclass.getGradeName());
				}

				hql.append(" order by gradeName ");
				
				return this.queryPage(hql.toString(), page, params.toArray());
	}

	@Override
	public StuClass queryStuClassByClassId(Integer classId) {
		String hql = "from StuClass s where s.classId = ?";
		
		return this.getPojo(hql, new Object[]{classId});
	}

	

}
