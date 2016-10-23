package com.eshore.nrms.sysmgr.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.khala.core.data.jpa.dao.impl.JpaDaoImpl;
import com.eshore.nrms.sysmgr.dao.IUserInfoDao;
import com.eshore.nrms.sysmgr.pojo.UserInfo;

@Repository
public class UserInfoDaoImpl extends JpaDaoImpl<UserInfo> implements
		IUserInfoDao {


	@Override
	public UserInfo queryUserByLoginName(String loginName) {
		
		String hql = "from UserInfo u where u.loginName = ?";
		
		return this.getPojo(hql, new Object[]{loginName});
	}
	
	@Override
	public List<UserInfo> queryUserList(UserInfo userInfo, PageConfig page) {
		
		StringBuilder hql = new StringBuilder("from UserInfo u where 1=1 ");
		List params = new ArrayList();
		if(StringUtils.isNotBlank(userInfo.getLoginName())){
			hql.append(" and u.loginNmae like ? ");
			params.add("%" + userInfo.getLoginName() + "%");
		}
		
		if(StringUtils.isNotBlank(userInfo.getUserName())){
			hql.append(" and u.userName like ? ");
			params.add("%" + userInfo.getUserName() + "%");
		}
		
		if(StringUtils.isNotBlank(userInfo.getFullIp())){
			hql.append(" and u.fullIp like ? ");
			params.add("%" + userInfo.getFullIp() + "%");
		}
		
		hql.append(" and u.state = 1");
		hql.append(" order by loginName ");
		
		return this.queryPage(hql.toString(), page, params.toArray());
		
	}

	@Override
	public Integer getUserCountByLoginName(String loginName, String userId) {
		StringBuilder hql = new StringBuilder("select count(*) from UserInfo u where u.state = 1 and u.loginName = ? ");
		ArrayList params = new ArrayList();
		params.add(loginName);
		
		if(StringUtils.isNotBlank(userId)){
			hql.append(" and u.id != ? ");
			params.add(userId);
		}
		
		return this.queryCount(hql.toString(), params.toArray());
	}

	@Override
	public Integer getUserCountByFullIp(String fullIp, String userId) {
		StringBuilder hql = new StringBuilder("select count(*) from UserInfo u where u.state = 1 and u.fullIp = ? ");
		ArrayList params = new ArrayList();
		params.add(fullIp);
		
		if(StringUtils.isNotBlank(userId)){
			hql.append(" and u.id != ? ");
			params.add(userId);
		}
		
		return this.queryCount(hql.toString(), params.toArray());
	}

	@Override
	public Integer getUserCountByUserName(String userName, String userId) {
		StringBuilder hql = new StringBuilder("select count(*) from UserInfo u where u.state = 1 and u.userName = ? ");
		ArrayList params = new ArrayList();
		params.add(userName);
		
		if(StringUtils.isNotBlank(userId)){
			hql.append(" and u.id != ? ");
			params.add(userId);
		}
		
		return this.queryCount(hql.toString(), params.toArray());
	}

	@Override
	public UserInfo queryUserByUserName(String userName) {
		String hql = "from UserInfo u where u.userName = ?";
		
		return this.getPojo(hql, new Object[]{userName});
	}
	
	@Override
	public Integer getCountByIpSegmentId(String ipSegmentId) {
		StringBuilder hql = new StringBuilder("select count(*) from UserInfo u where 1= 1 and u.state = 1 and u.ipSegmentId = ? ");
		
		return this.queryCount(hql.toString(),new Object[]{ipSegmentId});
	}
	
	@Override
	public Integer getCountByIpSegmentIdAndSE(String ipSegmentId , String beginIp , String endIp){
		StringBuilder hql = new StringBuilder("select count(*) from UserInfo u where 1= 1 and u.state = 1 "
					+"and u.ipSegmentId = ? "
					+"and (u.ipNum < ? or u.ipNum > ?)");
		
		return this.queryCount(hql.toString(),new Object[]{ipSegmentId,Integer.valueOf(beginIp),Integer.valueOf(endIp)});
	}

	@Override
	public Integer getCountBySite(String site) {
		StringBuilder hql = new StringBuilder("select count(*) from UserInfo u where 1= 1 and u.state = 1 and u.site = ? ");
		
		return this.queryCount(hql.toString(),new Object[]{site});
	}
	
		@Override
	public Integer getCountBySeatNum(String SeatNum,String userId,String site) {
			if(SeatNum != null && !("".equals(SeatNum))){
				StringBuilder hql = new StringBuilder("select count(*) from UserInfo u where u.state = 1 and u.seatNum = ? and u.site = ?");
				ArrayList params = new ArrayList();
				params.add(SeatNum);
				params.add(site);
				
				if(StringUtils.isNotBlank(userId)){
					hql.append(" and u.id != ? ");
					params.add(userId);
				}
				
				return this.queryCount(hql.toString(), params.toArray());
			}else
				return 0;
	}

	@Override
	public Integer getCountByUserCode(String userCode,String userId) {
		if(userCode != null && !("".equals(userCode))){
			StringBuilder hql = new StringBuilder("select count(*) from UserInfo u where u.state = 1 and u.userCode = ? ");
			ArrayList params = new ArrayList();
			params.add(userCode);
			
			if(StringUtils.isNotBlank(userId)){
				hql.append(" and u.id != ? ");
				params.add(userId);
			}
			
			return this.queryCount(hql.toString(), params.toArray());
		}else
			return 0;
	}
	

}
