package com.eshore.nrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eshore.khala.common.model.PageConfig;
import com.eshore.nrms.sysmgr.pojo.Param;
import com.eshore.nrms.sysmgr.pojo.StuClass;
import com.eshore.nrms.sysmgr.service.IStuClassService;
import com.eshore.nrms.vo.Conts;
import com.eshore.nrms.vo.PageVo;

@Controller
@RequestMapping("/analyseModel")
public class AnalyseModelController {
	
	@Autowired
	private IStuClassService stuClassService ;
	
	@RequestMapping("/scoreStuClassList")
	public ModelAndView scoreStuClassList(StuClass stuClass , PageConfig page) {
		ModelAndView view = new ModelAndView("analyseModel/scoreStuClassList");
		PageVo<StuClass> stuClassList = this.stuClassService.queryClassList(stuClass, page);
		//System.out.println("打开班级列表");
		view.addObject("page" , stuClassList);
		view.addObject("searchParam" , stuClass);
		return view;
	}
	
	@RequestMapping("/scoreAnalyseResult")
	public ModelAndView showScoreResult() {
		System.out.println("打开成绩分析页面");
		ModelAndView view = new ModelAndView("analyseModel/scoreAnalyseResult");
		return view;
	}
	
	
	
	
}
