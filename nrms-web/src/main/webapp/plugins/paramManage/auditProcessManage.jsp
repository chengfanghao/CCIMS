<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">
	function deleteProcess(processId) {
		$("#msgBoxConfirmInfo").html("确定要删除该流程状态吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click', function() {
			$("#msgBoxConfirm").modal('hide');
			$.ajax({
				type : 'POST',
				url : '${basePath}/auditProcessManage/deleteProcess',
				data : {
					'processId' : processId,
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#msgBoxInfo").html(data.msg);
						$("#msgBox").modal('show');
						$("#msgBoxOKButton").on('click', function() {
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
	function recory(){
		$.ajax({
			type : 'POST',
			url : '${basePath}/auditProcessManage/recoryMyself',
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$("#msgBoxInfo").html(data.msg);
					$("#msgBox").modal('show');
					$("#msgBoxOKButton").on('click', function() {
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
	}
	function showDialog(title, url, height) {
		$("#modalDialogTitle").html(title);
		$("#modalDialogFrame").attr("height", height);
		$("#modalDialogFrame").attr("src", url);
		$("#modalDialog").modal('show');
	}

	function hideDialog() {
		$("#modalDialog").modal('hide');
	}
</script>

<head>
</head>

<body>
	<div id="wrap" class="">
		<!--    头部 和  菜单 start -->
		<%@include file="/common/headAndLeft.jsp"%>
		<!--    头部 和  菜单 end -->
		<main class="main up-container-fluid">
		<div class="up-tab-content">
			<div class="up-tab-pane up-active" id="tabs1">
				<div class="border_btm first_title">
					<h4 class="up-top-cq-color">
						<span class="icon-right-dir"></span> 审批流程管理
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
												<div class="up-clearfix table_head margin_bottom10">
													<div class="reference_search">
														<div class="up-form-group">
															<button type="button"
																class="up-btn up-btn-primary up-btn-primary-red"
																style="margin-left: 20px" data-toggle="modal"
																onclick="showDialog('配置审批流程' , '${basePath}/auditProcessManage/toAddProcess' , '200px')">配置审批流程</button>
															<c:if test="${!containManager or !containAdmin}">
																<button type="button"
																	class="up-btn up-btn-primary up-btn-primary-red"
																	style="margin-left: 20px" data-toggle="modal"
																	onclick="recory()">恢复自身审批权限</button>
															</c:if>
														</div>
													</div>
												</div>
												<table
													class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
													<thead>
														<tr class="up-active">
															<th>流程</th>
															<th>状态码</th>
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="process" items="${processList }">
															<tr>
																<td>${process.auditState }</td>
																<td>${process.value }</td>
																<td><c:if
																		test="${sessionScope.LOGIN_USER.userRole eq '2'}">
																		<c:if
																			test="${process.value le 90 and process.value gt 1}">
																			<a href="javascript:void(0)"
																				onClick="deleteProcess('${process.id}')">删除</a>
																		</c:if>
																	</c:if> <c:if
																		test="${sessionScope.LOGIN_USER.userRole eq '1'}">
																		<c:if
																			test="${process.value le 194 and process.value gt 90}">
																			<a href="javascript:void(0)"
																				onClick="deleteProcess('${process.id}')">删除</a>
																		</c:if>
																	</c:if></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
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
						<h4 class="up-modal-title" id="modalDialogTitle">新增物理机</h4>
					</div>
					<iframe id="modalDialogFrame" width="100%" height="420px"
						frameborder="0"></iframe>
				</div>
			</div>
		</div>

	</div>
</body>

</html>
