<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">
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
		<div class='up-tab-content'>
			<div class="up-tab-pane up-active" id="tabs1">
				<div class="border_btm first_title">
					<h4 class="up-top-cq-color">
						<span class="icon-right-dir"></span> 虚拟服务器分配
					</h4>
				</div>
				<p class="up-cq-title up-cq-left-mar1">基本信息</p>

				<form class="up-form-horizontal" id="dateForm">
					<div
						class="up-cq-table-outer up-cq-left-mar1 up-cq-wid1 up-form_mar1">
						<div class="up-cq-table-inner">
							<div class="up-form-group">
								<label for="" class="up-col-sm-2 up-control-label"><span
									class="up-cq-red-star"></span>项目名</label>
								<div class="up-col-sm-7">
									<input type="text" readonly="readonly" class="up-form-control"
										id="" value="${orderInfo.projectName }">
								</div>
							</div>

							<div class="up-form-group">
								<label for="" class="up-col-sm-2 up-control-label"><span
									class="up-cq-red-star"></span>使用时间</label>
								<div class="up-col-sm-10 input_spell">
									<div class="up-col-sm-6">
										<div class="input_icon">
											<input type="text" readonly="readonly"
												class="up-form-control" id=""
												value="${orderInfo.beginTime.toString().substring(0,10) }">
										</div>
										<span class="text_middle">至</span>
									</div>
									<div class="up-col-sm-6">
										<div class="input_icon">
											<input type="text" readonly="readonly"
												class="up-form-control" id="" value="${orderInfo.endTime.toString().substring(0,10) }">
										</div>
									</div>
								</div>
							</div>
							
							<table
								class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
								<thead>
									<tr class="up-active">
										<th>IP要求</th>
										<th>操作系统</th>
										<th>CPU</th>
										<th>内存</th>
										<th>硬盘</th>
										<th>是否已分配</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="vm" items="${vmList }">
										<tr>
											<!-- <td><i:getValue dataType="ip_require_msg" dataValue="${vm.ipRequire}"></i:getValue></td> -->
											<c:forEach var = "ipReq" items="${ipList }">
												<c:if test="${ipReq.id eq vm.ipRequire }">
													<c:set value = "${ipReq.paramValue }" var="paraValue"></c:set>
												</c:if>
											</c:forEach>
											<td><i:getValue dataType="ip_require_msg" dataValue="${paraValue}"></i:getValue></td>
											<td>${vm.os }</td>
											<td>${vm.cpu }个</td>
											<td>${vm.memory }(G)</td>
											<td>${vm.disk }(G)</td>
											<c:if test="${vm.isAssigned == 0}">
												<td>未分配</td>
											</c:if>
											<c:if test="${vm.isAssigned == 1}">
												<td>已分配</td>
											</c:if>
											<td>
											<!--  
												<c:if test="${vm.isAssigned == 1 }">
													<a href="javascript:void(0)"
													onClick="showDialog('查看虚拟机' , '${basePath}/vmAudit/viewVM?vmOrderId=${vm.id}' , '340px')">查看</a>
												</c:if>
												
											-->
											
											<c:if test="${vm.isAssigned == 1 }">
													<a href="javascript:void(0)"
													onClick="showDialog('查看虚拟机' , '${basePath }/vmApply/viewVM?vmOrderId=${vm.id}' , '380px')">查看</a>
												</c:if>
												<c:if test="${vm.isAssigned == 0 }">
													无操作
												</c:if>
												</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="up-modal-footer up-modal-footer1">
								<button type="button" class="up-btn up-btn-reset mar-rig30"
									onClick="window.location.href='${basePath}/vmApply/orderList'">返回</button>
							</div>
						</div>
					</div>
				</form>
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
