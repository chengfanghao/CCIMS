<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<div class="sidebar">
	<nav class="sidenav">
		<ul class="nav_ul" id="accordion">
			<li>
				<a class="no_link sidebtn" href="javascript:void(0);"> 
					<span class="icon-menu"></span>
				</a>
			</li>
			 
			<li>
				<a class="bor-left-nav-btm" href="${basePath }"
				   data-toggle="tooltip" data-container="#tooltip_box"
				   data-placement="right" title="首页">
				   
				   <div class="up-pull-left">
				   		<span class="icon-shouye"></span> 首页
			       </div>
				</a>
			</li> 
			
			<li>
				<a class="bor-left-nav-btm" href="#collapse_form"
				   data-toggle="collapse" aria-expanded="false" 
				   onclick="window.location.href = '${basePath}/userInfo/userInfoList'">
				   
				   <div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							  data-container="#tooltip_box" data-placement="right" title="系统管理">
						</span>
						用户管理
					</div>
				</a>
			</li> 
			
			<li>
				<a class="bor-left-nav-btm" href="#collapse_table"
				   data-toggle="collapse" aria-expanded="false"
				   onclick="window.location.href = '${basePath}/awardInfo/awardInfoList'">
				   <div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							  data-container="#tooltip_box" data-placement="right"
							  title="获奖情况">
						</span> 获奖情况
					</div>
				</a>
			</li>
		</ul>
	</nav>
</div>