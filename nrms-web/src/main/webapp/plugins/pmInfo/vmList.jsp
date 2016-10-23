<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">

	function recoveryVM(vmid) {
		
		$("#msgBoxConfirmInfo").html("确定要回收该虚拟机吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click' , function(){
			$("#msgBoxConfirm").modal('hide');
			$.ajax({
				type : 'POST',
				url : '${basePath}/vmManagement/recoveryVM',
				data : {
					'id' : vmid
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#msgBoxInfo").html(data.msg);
						$("#msgBox").modal('show');
						$("#msgBoxOKButton").on('click' , function(){
							window.location.reload();
						});
					} else {
						$("#msgBoxInfo").html(data.msg);
						$("#msgBox").modal('show');
					}
				},
				error : function(data) {
					$("#msgBoxInfo").html("程序执行出错");
					$("#msgBox").modal('show');
				}
			});
		});
		
	}
	
	function showDialog(title , url , height){
		$("#modalDialogTitle").html(title);
		$("#modalDialogFrame").attr("height" , height);
		$("#modalDialogFrame").attr("src" , url);
		$("#modalDialog").modal('show');
	}
	
	function hideDialog(){
		$("#modalDialog").modal('hide');
	}
	function backToPMView(){
		 window.location.href="${basePath}/pmInfo/pmList";
	}
</script>

<head>
</head>

<body>
	<div id="wrap" class="">
		<!--    头部 和  菜单 start -->
		<%@include file="/common/headAndLeft.jsp"%>
		<!--    头部 和  菜单 end -->
		<!-- 内容start -->
		<main class="main up-container-fluid">
		<div class="up-tab-content">
			<div class="up-tab-pane up-active" id="tabs1">
				<div class="border_btm first_title">
					<h4 class="up-top-cq-color">
						<span class="icon-right-dir"></span> 虚拟机管理
					</h4>
				</div>
				<div class="up_page_con">
					<div class="ex_tab2" role="tabpanel"
						data-example-id="togglable-tabs">
						<div id="" class="up-tab-content">
							<div role="tabpanel" class="up-tab-pane up-active" id="mytab11"
								aria-labelledby="mytab11-tab">
								<div class="up-table-responsive">
									<div class="up-cq-table">
										<div class="up-cq-table-outer">
											<div class="up-cq-table-inner">
												<table
													class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
													<thead>
														<tr class="up-active">
															<th>项目</th>
															<th>虚拟机ip</th>
															<th>系统</th>
															<th>CPU(个)</th>
															<th>内存(GB)</th>
															<th>硬盘(GB)</th>
															<th>开始日期</th>
															<th>结束日期</th>
															<th>用户账号</th>
															<th>用户密码</th>
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="vm" items="${page.dataList }">
															<tr>
																<td>${vm.projectName}</td>
																<td>${vm.fullIp}</td>
																<td>${vm.os}</td>
																<td>${vm.cpu}</td>
																<td>${vm.memory}</td>
																<td>${vm.disk}</td>
																<td>${vm.beginDate.toString().substring(0,10)}</td>
																<td>${vm.endDate.toString().substring(0,10)}</td>
																<td>${vm.userLogin}</td>
																<td>${vm.userPwd}</td>
																<td><a href="javascript:void(0)"
																	onClick="showDialog('查看虚拟机' , '${basePath}/vmManagement/viewVmInfo?vmId=${vm.id }' , '470px')"">查看</a>
																	<c:if
																		test="${vm.status eq '1' and sessionScope.LOGIN_USER.userRole eq '1'}">
																		<a href="javascript:void(0)"
																			onClick="recoveryVM('${vm.id}')">回收</a>
																	</c:if></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>

												<div class="up-clearfix">
													<button
														class="up-pull-left up-btn up-btn-primary up-btn-sm" id="back"
														onClick="backToPMView()">返回</button>
													<div class="up-pull-right">
														<%@include file="/common/page.jsp"%>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</main>

		<!-- 侧栏提示工具容器 -->
		<div id="tooltip_box"></div>
		<!-- 侧栏提示工具容器 -->

		<!--    提示框 start -->
		<%@include file="/common/msgBox.jsp"%>
		<!--    提示框 -->

		<div class="up-modal up-fade" id="modalDialog" data-drag="true"
			data-backdrop="static">
			<div class="up-modal-dialog up-modal-lg">
				<div class="up-modal-content">
					<div class="up-modal-header">
						<button type="button" class="up-close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
						<h4 class="up-modal-title" id="modalDialogTitle">新增用户</h4>
					</div>
					<iframe id="modalDialogFrame" width="100%" height="420px"
						frameborder="0"></iframe>
				</div>
			</div>
		</div>

	</div>
</body>

</html>
