<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">
	function deletePM(id) {
		$("#msgBoxConfirmInfo").html("确定要删除该物理机吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click', function() {
			$("#msgBoxConfirm").modal('hide');
			$.ajax({
				type : 'POST',
				url : '${basePath}/pmInfo/deletePM',
				data : {
					'id' : id
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#msgBoxInfo").html(data.msg);
						$("#msgBox").modal('show');
						$("#msgBoxOKButton").on('click', function() {
							window.location.href = '${basePath }/pmInfo/pmList?pageNum=1';
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

		<!-- 内容start -->
		<main class="main up-container-fluid">
		<div class="up-tab-content">
			<div class="up-tab-pane up-active" id="tabs1">
				<div class="border_btm first_title">
					<h4 class="up-top-cq-color">
						<span class="icon-right-dir"></span> 物理机服务管理
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
													
														<form class="up-form-inline" id="searchForm" action="${basePath }/pmInfo/pmList">
															<input type="hidden" id="pageNum" name="pageNum"
																value="1">
															<div class="up-form-group">
																<label for="" class="up-control-label">服务器IP:</label> 
																<input
																	type="text" class="up-form-control" id="fullIp"
																	name="fullIp" value="${searchParam.fullIp }">
															</div>
															<div class="up-form-group">
																<button type="submit" class="up-btn up-btn-primary">查询</button>
															</div>
														</form>
														
													</div>
												</div>
												<c:if test="${sessionScope.LOGIN_USER.userRole eq '1' }">
													<div class="up-clearfix table_head">
														<div class="reference_search">
															<div class="up-form-group">
																<button type="submit"
																	class="up-btn up-btn-primary up-btn-primary-red"
																	data-toggle="modal"
																	onClick="showDialog('新增物理机' , '${basePath}/pmInfo/addPM' , '480px')">新增</button>
															</div>
														</div>
													</div>
												</c:if>
												<table
													class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
													<thead>
														<tr class="up-active">
															<th>服务器IP</th>
															<th>控制台IP</th>
															<th>控制台账户</th>
															<th>控制台密码</th>
															<th>总CPU/剩余</th>
															<th>总内存/剩余(GB)</th>
															<th>总硬盘/剩余(GB)</th>
															
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="pm" items="${page.dataList }">
															<tr>
																<td>${pm.fullIp}</td>
																<td>${pm.consoleUrl}</td>
																<td>${pm.consoleLogin}</td>
																<td>${pm.consolePwd}</td>
																<td>${pm.cpu}/ ${pm.cpu - pm.cpuUsed}</td>
																<td>${pm.memory}/${pm.memory - pm.memoryUsed}</td>
																<td>${pm.disk}/${pm.disk - pm.diskUsed} </td>

																<td><a href="javascript:void(0)" onClick="showDialog('查看' , '${basePath}/pmInfo/viewPM?id=${pm.id }' , '380px')"">查看</a>
																	
																	<!-- 查看虚拟机 --> 
																	<!-- 跳转到新的页面 -->
																	<a href="${basePath}/pmInfo/vmList?id=${pm.id }">查看虚拟机</a>

																	<!-- 管理员权限能查看 --> <c:if
																		test="${sessionScope.LOGIN_USER.userRole eq '1' }">
																		<a href="javascript:void(0)"
																			onClick="showDialog('编辑' , '${basePath}/pmInfo/editPM?id=${pm.id }' , '380px')">编辑</a>
																		<a href="javascript:void(0)"
																			onClick="deletePM('${pm.id}')">删除</a>
																	</c:if></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>

												<div class="up-clearfix">
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
