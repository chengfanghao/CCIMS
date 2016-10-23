<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">
	$(function() {
		$('#state').change(function() {
			var stateVal  = $("#state").val();
			window.location.href = '${basePath }/vmApply/orderList?pageNum=1&projectName=&state='+stateVal;
		})
	})
	function deleteOrder(orderId) {

		$("#msgBoxConfirmInfo").html("确定要删除该申请单吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click', function() {
			$("#msgBoxConfirm").modal('hide');
			$.ajax({
				type : 'POST',
				url : '${basePath}/vmApply/deleteOrder',
				data : {
					'id' : orderId
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#msgBoxInfo").html(data.msg);
						$("#msgBox").modal('show');
						$("#msgBoxOKButton").on('click', function() {
							window.location.href = '${basePath }/vmApply/orderList?pageNum=1';
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

	function viewOrder(orderId) {

		$.ajax({
			type : 'POST',
			url : '${basePath}/vmApply/viewOrder',
			data : {
				'id' : orderId
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
			}
		})
	}

	function applyOrder(orderId) {

		$("#msgBoxConfirmInfo").html("确定提交申请单吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click', function() {
			$("#msgBoxConfirm").modal('hide');
			$.ajax({
				type : 'POST',
				url : '${basePath}/vmApply/applyOrder',
				data : {
					'id' : orderId
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
						<span class="icon-right-dir"></span> 虚拟服务器申请
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
														<form class="up-form-inline" id="searchForm"
															action="${basePath }/vmApply/orderList">
															<input type="hidden" id="pageNum" name="pageNum"
																value="1">
															<div class="up-form-group">
																<label for="" class="up-control-label">项目名:</label> <input
																	type="text" class="up-form-control" id="projectName"
																	name="projectName" value="${searchParam.projectName }">
															</div>
															<div class="up-form-group">
																<label for="" class="up-control-label">审批状态:</label> <select
																	name="state" id="state" class="up-form-control"
																	value="${searchParam.state }">
																	<option value="0">--全部--</option>
																	<c:forEach var="orderState" items="${processList }">
																		<option value="${orderState.value }"
																			<c:if test="${orderState.value eq searchParam.state }">selected</c:if>>${orderState.auditState }</option>
																	</c:forEach>
																</select>
															</div>
															<div class="up-form-group">
																<button type="submit" class="up-btn up-btn-primary">搜索</button>
															</div>
														</form>
													</div>
												</div>

												<!-- 新增的按钮 ，只有普通用户才能新增工单-->
												<c:if test="${sessionScope.LOGIN_USER.userRole eq '3' }">
													<div class="up-clearfix table_head">
														<div class="reference_search">
															<div class="up-form-group">
																<button type="submit"
																	class="up-btn up-btn-primary up-btn-primary-red"
																	data-toggle="modal"
																	onClick="location='${basePath}/vmApply/addOrderApply'">新增</button>
																<!-- 
																	"location='${basePath}/vmApply/addOrderApply'"
																	-->
															</div>
														</div>
													</div>
												</c:if>

												<table
													class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
													<thead>
														<tr class="up-active">
															<th>工单号</th>
															<th>项目</th>
															<th>开始日期</th>
															<th>结束日期</th>
															<th>审核状态</th>
															<th>申请数/分配数</th>
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="order" items="${page.dataList }">
															<tr>
																<td>${order.id}</td>
																<td>${order.projectName}</td>
																<td>${order.beginTime.toString().substring(0,10)}</td>
																<td>${order.endTime.toString().substring(0,10)}</td>
																<td><c:forEach var="process"
																		items="${processList }">
																		<c:if test="${process.value eq order.state }">
																				${process.auditState }
																			</c:if>
																	</c:forEach></td>
																<td>${order.vmNum}/${order.assignedNum}</td>
																<td><a
																	href="${basePath }/vmApply/viewOrderDetail?id=${order.id}">查看</a>
																	<c:if
																		test="${(order.state eq '1' || order.state eq '-1') && order.vmNum>0}">
																		<a href="javascript:void(0)"
																			onClick="applyOrder('${order.id}')">提交</a>
																	</c:if> <c:if
																		test="${order.state eq '1' || order.state eq '-1' }">
																		<a
																			href="${basePath}/vmApply/showOrderApply?orderId=${order.id }">编辑</a>
																	</c:if> <c:if
																		test="${order.state eq '1' || order.state eq '-1' }">
																		<a href="javascript:void(0)"
																			onClick="deleteOrder('${order.id}')">删除</a>
																	</c:if> <c:if test="${sessionScope.LOGIN_USER.isAudit == 1}">
																		<!-- 具有审批权限 -->
																		<c:if test="${order.state == myStateValue }">
																			<!-- 审批单状态码正好等于自身状态码 -->
																			<a href="${basePath }/vmAudit/audit?id=${order.id}">审批</a>
																		</c:if>
																	</c:if>
																	<c:if test="${order.state != 1 }">
																	<a href="javascript:void(0)"
																	onClick="showDialog('工单审批明细' , '${basePath}/vmAudit/viewOrderOption?orderId=${order.id}' , '350px')">审批详情</a>
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

		<!-- “新增”按钮的添加 -->
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
						<h4 class="up-modal-title" id="modalDialogTitle">新增申请</h4>
					</div>
					<iframe id="modalDialogFrame" width="100%" height="420px"
						frameborder="0"></iframe>
				</div>
			</div>
		</div>

	</div>


</body>

</html>
