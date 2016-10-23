package com.eshore.nrms.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.nrms.sysmgr.pojo.Award;
import com.eshore.nrms.sysmgr.pojo.AwardGradeInfo;
import com.eshore.nrms.sysmgr.pojo.RankInfo;
import com.eshore.nrms.sysmgr.pojo.StuBasicInfo;
import com.eshore.nrms.sysmgr.service.IAwardService;
import com.eshore.nrms.sysmgr.service.IStuBasicInfoService;
import com.eshore.nrms.util.SecurityUtil;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.ExecResult;
import com.eshore.nrms.vo.PageVo;

@Controller
@RequestMapping("/awardInfo")
public class AwardInfoController {

	@Autowired
	private IStuBasicInfoService stuBasicInfoService;

	@Autowired
	private IAwardService awardService;

	@RequestMapping("/awardInfoList")
	public ModelAndView userInfoList(Award awardInfo, PageConfig page, HttpSession session) {

		// 获取当前用户的学号
		StuBasicInfo user = (StuBasicInfo) session.getAttribute(Conts.USER_SESSION_KEY);
		awardInfo.setSno(user.getSno());

		ModelAndView view = new ModelAndView("awardInfo/awardList");
		PageVo<Award> awardInfoList = this.awardService.queryAwardByPage(awardInfo, page);

		// 初始化获奖级别
		List<AwardGradeInfo> gradeList = new ArrayList<AwardGradeInfo>();
		AwardGradeInfo gradeInfo1 = new AwardGradeInfo();
		gradeInfo1.setGradeId("1");
		gradeInfo1.setGradeName("国家级");
		gradeList.add(gradeInfo1);
		AwardGradeInfo gradeInfo2 = new AwardGradeInfo();
		gradeInfo2.setGradeId("2");
		gradeInfo2.setGradeName("省级");
		gradeList.add(gradeInfo2);
		AwardGradeInfo gradeInfo3 = new AwardGradeInfo();
		gradeInfo3.setGradeId("3");
		gradeInfo3.setGradeName("校级");
		gradeList.add(gradeInfo3);
		view.addObject("gradeList", gradeList);

		// 初始化获奖等次
		List<RankInfo> rankList = new ArrayList<RankInfo>();
		RankInfo rankInfo1 = new RankInfo();
		rankInfo1.setRankId("1");
		rankInfo1.setRankName("一等奖");
		rankList.add(rankInfo1);
		RankInfo rankInfo2 = new RankInfo();
		rankInfo2.setRankId("2");
		rankInfo2.setRankName("二等奖");
		rankList.add(rankInfo2);
		RankInfo rankInfo3 = new RankInfo();
		rankInfo3.setRankId("3");
		rankInfo3.setRankName("三等奖");
		rankList.add(rankInfo3);
		view.addObject("rankList", rankList);

		view.addObject("page", awardInfoList);
		view.addObject("searchParam", awardInfo);

		return view;
	}

	@RequestMapping("/toAddOrEditAward")
	public ModelAndView toAddOrEditUser(String awardId) {

		ModelAndView view = new ModelAndView("awardInfo/addOrEditAward");

		// 初始化获奖级别
		List<AwardGradeInfo> gradeList = new ArrayList<AwardGradeInfo>();
		AwardGradeInfo gradeInfo1 = new AwardGradeInfo();
		gradeInfo1.setGradeId("1");
		gradeInfo1.setGradeName("国家级");
		gradeList.add(gradeInfo1);
		AwardGradeInfo gradeInfo2 = new AwardGradeInfo();
		gradeInfo2.setGradeId("2");
		gradeInfo2.setGradeName("省级");
		gradeList.add(gradeInfo2);
		AwardGradeInfo gradeInfo3 = new AwardGradeInfo();
		gradeInfo3.setGradeId("3");
		gradeInfo3.setGradeName("校级");
		gradeList.add(gradeInfo3);
		view.addObject("gradeList", gradeList);

		// 初始化获奖等次
		List<RankInfo> rankList = new ArrayList<RankInfo>();
		RankInfo rankInfo1 = new RankInfo();
		rankInfo1.setRankId("1");
		rankInfo1.setRankName("一等奖");
		rankList.add(rankInfo1);
		RankInfo rankInfo2 = new RankInfo();
		rankInfo2.setRankId("2");
		rankInfo2.setRankName("二等奖");
		rankList.add(rankInfo2);
		RankInfo rankInfo3 = new RankInfo();
		rankInfo3.setRankId("3");
		rankInfo3.setRankName("三等奖");
		rankList.add(rankInfo3);
		view.addObject("rankList", rankList);

		if (StringUtils.isNotBlank(awardId)) {
			Award awardInfo = this.awardService.get(awardId);
			view.addObject("awardInfo", awardInfo);
		}
		return view;
	}

	@RequestMapping("/saveOrUpdateAward")
	@ResponseBody
	public ExecResult saveOrUpdateUser(Award awardInfo, HttpSession session) {
		// 初始化获奖级别
		List<AwardGradeInfo> gradeList = new ArrayList<AwardGradeInfo>();
		AwardGradeInfo gradeInfo1 = new AwardGradeInfo();
		gradeInfo1.setGradeId("1");
		gradeInfo1.setGradeName("国家级");
		gradeList.add(gradeInfo1);
		AwardGradeInfo gradeInfo2 = new AwardGradeInfo();
		gradeInfo2.setGradeId("2");
		gradeInfo2.setGradeName("省级");
		gradeList.add(gradeInfo2);
		AwardGradeInfo gradeInfo3 = new AwardGradeInfo();
		gradeInfo3.setGradeId("3");
		gradeInfo3.setGradeName("校级");
		gradeList.add(gradeInfo3);

		// 初始化获奖等次
		List<RankInfo> rankList = new ArrayList<RankInfo>();
		RankInfo rankInfo1 = new RankInfo();
		rankInfo1.setRankId("1");
		rankInfo1.setRankName("一等奖");
		rankList.add(rankInfo1);
		RankInfo rankInfo2 = new RankInfo();
		rankInfo2.setRankId("2");
		rankInfo2.setRankName("二等奖");
		rankList.add(rankInfo2);
		RankInfo rankInfo3 = new RankInfo();
		rankInfo3.setRankId("3");
		rankInfo3.setRankName("三等奖");
		rankList.add(rankInfo3);

		ExecResult result = new ExecResult();

		if (!StringUtils.isBlank(awardInfo.getAwardId())) {
			Award awardTemp = this.awardService.get(awardInfo.getAwardId());
			// 更新获奖名称
			awardTemp.setAwardName(awardInfo.getAwardName());

			// 更新获奖级别ID和名称
			awardTemp.setGradeId(awardInfo.getGradeId());
			for (AwardGradeInfo awardGradeItem : gradeList) {
				if (StringUtils.equals(awardGradeItem.getGradeId(), awardInfo.getAwardId())) {
					awardTemp.setGradeName(awardGradeItem.getGradeName());
					break;
				}
			}

			// 更新获奖等次ID和名称
			awardTemp.setRankId(awardInfo.getRankId());
			for (RankInfo awardRankItem : rankList) {
				if (StringUtils.equals(awardRankItem.getRankId(), awardInfo.getRankId())) {
					awardTemp.setRankName(awardRankItem.getRankName());
					break;
				}
			}

			awardTemp.setAwardTime(awardInfo.getAwardTime());
			awardTemp.setUnit(awardInfo.getUnit());
			this.awardService.update(awardTemp);
		} else {

			awardInfo.setAwardId(null);

			// 添加获奖级别名称
			for (AwardGradeInfo awardGradeItem : gradeList) {
				if (StringUtils.equals(awardGradeItem.getGradeId(), awardInfo.getAwardId())) {
					awardInfo.setGradeName(awardGradeItem.getGradeName());
					break;
				}
			}

			// 添加获奖等次名称
			for (RankInfo awardRankInfo : rankList) {
				if (StringUtils.equals(awardRankInfo.getRankId(), awardInfo.getRankId())) {
					awardInfo.setRankName(awardRankInfo.getRankName());
					break;
				}
			}

			// 获取当前用户的学号
			StuBasicInfo user = (StuBasicInfo) session.getAttribute(Conts.USER_SESSION_KEY);
			awardInfo.setSno(user.getSno());

			this.awardService.save(awardInfo);
		}

		result.setMsg("保存成功");
		result.setSuccess(true);
		return result;
	}

	@RequestMapping("/deleteAward")
	@ResponseBody
	public ExecResult deleteUser(String awardId) {

		this.awardService.delete(awardId);

		ExecResult result = new ExecResult();
		result.setMsg("删除成功");
		result.setSuccess(true);
		return result;
	}

}
