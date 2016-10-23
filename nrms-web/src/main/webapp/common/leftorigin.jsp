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
			 
			<li>
			<a class="bor-left-nav-btm" href="#collapse_form"
				data-toggle="collapse" aria-expanded="false">
					<div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right" title="系统管理"></span>
						系统管理
					</div>
					<div class="up-pull-right">
						<span class="icon-down-open"></span>
					</div>
			</a>
 
				<ul class="nav_ul2 up-collapse" id="collapse_form" aria-expanded="false">

					<c:if test="${sessionScope.LOGIN_USER.userRole eq '3' }">
						<li><a class="no_link"
							href="${basePath }/userInfo/userInfoList" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right" title="用户查询">
								<div class="up-pull-left">
									<span class="icon-right-dir"></span> 用户查询
								</div>
						</a></li>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.userRole eq '1' or sessionScope.LOGIN_USER.userRole eq '2'}">
						<li><a class="no_link"
							href="${basePath }/userInfo/userInfoList" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right" title="用户管理">
								<div class="up-pull-left">
									<span class="icon-right-dir"></span> 用户管理
								</div>
						</a></li>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.userRole eq '1'}">
						<li><a class="no_link"
							href="${basePath }/paramManage/showList" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right" title="参数管理">
								<div class="up-pull-left">
									<span class="icon-right-dir"></span> 参数管理
								</div>
						</a></li>
					</c:if>
				</ul>
			</li>
				
			<c:if
				test="${sessionScope.LOGIN_USER.userRole eq '1' or sessionScope.LOGIN_USER.userRole eq '2'}">
				<li><a class="bor-left-nav-btm" href="#collapse_form1"
					data-toggle="collapse" aria-expanded="false">
						<div class="up-pull-left">
							<span class="icon-th-large" data-toggle="tooltip"
								data-container="#tooltip_box" data-placement="right"
								title="服务器管理"></span> 服务器管理
						</div>
						<div class="up-pull-right">
							<span class="icon-down-open"></span>
						</div>
				</a>
					<ul class="nav_ul2 up-collapse" id="collapse_form1"
						aria-expanded="false">
						<c:if
							test="${sessionScope.LOGIN_USER.userRole eq '1'  || sessionScope.LOGIN_USER.userRole eq '2'  }">
							<li><a class="no_link" href="${basePath }/pmInfo/pmList"
								data-toggle="tooltip" data-container="#tooltip_box"
								data-placement="right" title="物理服务器管理">
									<div class="up-pull-left">
										<span class="icon-right-dir"></span> 物理服务器管理
									</div>
							</a></li>


							<li><a class="no_link"
								href="${basePath }/vmManagement/vmList" data-toggle="tooltip"
								data-container="#tooltip_box" data-placement="right"
								title="虚拟服务器管理">
									<div class="up-pull-left">
										<span class="icon-right-dir"></span> 虚拟服务器管理
									</div>
							</a></li>
						</c:if>
					</ul></li>
			</c:if>
			
			<li><a class="bor-left-nav-btm" href="#collapse_table"
				data-toggle="collapse" aria-expanded="false">
					<div class="up-pull-left">
						<span class="icon-th-large" data-toggle="tooltip"
							data-container="#tooltip_box" data-placement="right"
							title="虚拟机申请"></span> 虚拟机申请
					</div>
					<div class="up-pull-right">
						<span class="icon-down-open"></span>
					</div>
			</a>
			
				<ul class="nav_ul2 up-collapse" id="collapse_table"
					aria-expanded="false">
					<c:if test="${sessionScope.LOGIN_USER.userRole eq '3'   }">
						<li><a class="no_link" href="${basePath }/vmApply/orderList"
							data-toggle="tooltip" data-container="#tooltip_box"
							data-placement="right" title="虚拟服务器申请">
								<div class="up-pull-left">
									<span class="icon-right-dir"></span> 虚拟服务器申请
								</div>
						</a></li>
					</c:if>
					<c:if
						test="${sessionScope.LOGIN_USER.isAudit == 1 or sessionScope.LOGIN_USER.userRole eq '2' or sessionScope.LOGIN_USER.userRole eq '1'}">
						<li><a class="no_link" href="${basePath }/vmAudit/orderList"
							data-toggle="tooltip" data-container="#tooltip_box"
							data-placement="right" title="虚拟服务器审批">
								<div class="up-pull-left">
									<span class="icon-right-dir"></span> 虚拟服务器审批
								</div>
						</a></li>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.userRole eq '2' or sessionScope.LOGIN_USER.userRole eq '1'}">
						<li><a class="no_link"
							href="${basePath }/auditProcessManage/auditProcessList"
							data-toggle="tooltip" data-container="#tooltip_box"
							data-placement="right" title="审批流程配置">
								<div class="up-pull-left">
									<span class="icon-right-dir"></span> 审批流程配置
								</div>
						</a></li>
					</c:if>
				</ul> 
				
				</li>
		</ul>
	</nav>
</div>