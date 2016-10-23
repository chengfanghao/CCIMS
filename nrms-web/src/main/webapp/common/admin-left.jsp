<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<div class="sidebar">
	<nav class="sidenav">
		<ul class="nav_ul" id="accordion">
			<li><a class="no_link sidebtn" href="javascript:void(0);"> <span
					class="icon-menu"></span>
			</a></li> 
			<li><a class="bor-left-nav-btm" href="${basePath }"
				data-toggle="tooltip" data-container="#tooltip_box"
				data-placement="right" title="首页">
					<div class="up-pull-left">
						<span class="icon-shouye"></span> 首页
					</div>
			</a></li> 
			<li><a class="bor-left-nav-btm" href="#collapse_form"
				data-toggle="collapse" aria-expanded="false">
					<div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right" title="区域分布"></span>
						区域分布
					</div>
					
			</a>
			<li><a class="bor-left-nav-btm" href="#collapse_table"
				data-toggle="collapse" aria-expanded="false">
					<div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right"
							title="就业与在读情况"></span> 就业与在读情况
					</div>
					
				</a>
			</li>
			<li><a class="bor-left-nav-btm" href="#collapse_table"
				data-toggle="collapse" aria-expanded="false">
					<div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right"
							title="薪资状况"></span> 薪资状况
					</div>
					
				</a>
			</li>
			<li><a class="bor-left-nav-btm" href="#collapse_table" onclick="window.location.href = '${basePath}/analyseModel/scoreStuClassList'"
				data-toggle="collapse" aria-expanded="false">
					<div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right"
							title="学习成绩概况"></span> 学习成绩概况
					</div>
					
				</a>
			</li>
			<li><a class="bor-left-nav-btm" href="#collapse_table"
				data-toggle="collapse" aria-expanded="false">
					<div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right"
							title="学生获奖情况"></span> 学生获奖情况
					</div>
					
				</a>
			</li>
			<li><a class="bor-left-nav-btm" href="#collapse_table"
				data-toggle="collapse" aria-expanded="false">
					<div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right"
							title="论文与专利概况"></span> 论文与专利概况
					</div>
					
				</a>
			</li>
		</ul>
	</nav>
</div>