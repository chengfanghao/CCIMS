<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function save() {

		if (!checkBlank($("#projectName").val())) {
			$("#msgBoxInfo").html("请输入项目名");
			$("#msgBox").modal('show');
			return;
		}

		if (!checkBlank($("#beginDate").val())) {
			$("#msgBoxInfo").html("请输入开始时间");
			$("#msgBox").modal('show');
			return;
		}

		if (!checkBlank($("#endDate").val())) {
			$("#msgBoxInfo").html("请输入结束时间");
			$("#msgBox").modal('show');
			return;
		}

		function checkEndToSatrtTime() {
			var beginDate = $("#beginDate").val();
			var start = new Date(beginDate.replace("-", "/").replace("-", "/"));
			var endDate = $("#endDate").val();
			var end = new Date(endDate.replace("-", "/").replace("-", "/"));
			if (end < start) {
				return false;
			}
			return true;
		}
		if (!checkEndToSatrtTime()) {
			$("#msgBoxInfo").html("请输入正确的结束时间<\br>结束时间必须大于开始时间");
			$("#msgBox").modal('show');
			return;
		}

		$.ajax({
			type : 'POST',
			url : '${basePath}/vmApply/writeOrder',
			data : {
				'projectName' : $("#projectName").val(),
				'beginDate' : $("#beginDate").val(),
				'endDate' : $("#endDate").val(),
				'id' : $("#id").val(),
				'orderId' : $("#orderId").val()
			},
			dataType : 'json',

			success : function(data) {
				if (data.success) {
					$("#msgBoxInfo").html("保存成功");
					$("#msgBox").modal('show');
					$("#orderId").val(data.msg);
					$("#xinzheng").attr("disabled", false);
				} else {
					$("#msgBoxInfo").html("保存失败");
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

	function add() {
		var url = "${basePath}/vmApply/toAddVM?orderId=" + $("#orderId").val();

		showDialog('虚拟机申请', url, '360px')
	}

	function deleteVMApply(orderId, id) {

		$("#msgBoxConfirmInfo").html("确定要删除该申请吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click',
			function() {
				$("#msgBoxConfirm").modal('hide');
				$.ajax({
					type : 'POST',
					url : '${basePath}/vmApply/deleteVMApply',
					data : {
						'orderId' : orderId,
						'vmId' : id
					},
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$("#msgBoxInfo").html(data.msg);
							$("#msgBox").modal('show');
							$("#msgBoxOKButton").on('click',function() {
								var orderId = $('#orderId').val();
								var url = "${basePath}/vmApply/showOrderApply?orderId="+ orderId;
								window.location.href = url;
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

	function applyOrder(orderId) {

		$("#msgBoxConfirmInfo").html("确定提交申请单吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click',
			function() {
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
							$("#msgBoxOKButton").on('click',function() {$("#xinzheng").attr("disabled",true);
							$("#baocun").attr("disabled",true);
							setTimeout(
								function() {
									window.location.href = "${basePath}/vmApply/orderList";
								},
							1500);});
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
</script>


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
						<span class="icon-right-dir"></span> 工单申请
					</h4>
				</div>
				<p class="up-cq-title up-cq-left-mar1">基本信息</p>
				<div class="up-cq-table-outer up-cq-left-mar1 up-cq-wid1">
					<div class="up-cq-table-inner">



						<form class="up-form-horizontal" id="projectDataForm">


							<input type="hidden" id="id" name="id"
								value="${sessionScope.LOGIN_USER.id}" /> <input type="hidden"
								id="orderId" name="orderId"
								<c:if test="${not empty orderInfo}" >value="${orderInfo.id }"</c:if> />
							<input type="hidden" id="orderVMId" name="orderVMId"> <input
								type="hidden" id="vmList" name="vmList">



							<div class="up-form-group">
								<label for="" class="up-col-sm-2 up-control-label"><span
									class="up-cq-red-star">*</span>项目名</label>
								<div class="up-col-sm-7">
									<input type="text" class="up-form-control" id="projectName"
										name="projectName" placeholder="请输入项目名"
										<c:if test="${not empty orderInfo }">readOnly="true"</c:if>
										value="${orderInfo.projectName }">
								</div>
							</div>
							<div class="up-form-group">
								<label for="" class="up-col-sm-2 up-control-label"><span
									class="up-cq-red-star">*</span>使用时间</label>
								<div class="up-col-sm-10 input_spell">
									<div class="up-col-sm-6">
										<div class="input_icon">
											<input type="text" class="up-form-control plug_date_start"
												id="beginDate" name="beginDate" placeholder="开始时间"
												<c:if test="${not empty orderInfo }">readOnly="true" value="${orderInfo.beginTime.toString().substring(0,10) }"</c:if>>
											<i class="icon-calendar"></i>
										</div>
										<span class="text_middle">至</span>
									</div>
									<div class="up-col-sm-6">
										<div class="input_icon">
											<input type="text" class="up-form-control plug_date_end"
												id="endDate" name="endDate" placeholder="结束时间"
												<c:if test="${not empty orderInfo }">readOnly="true"</c:if>
												value="${orderInfo.endTime.toString().substring(0,10) }">
											<i class="icon-calendar"></i>
										</div>
									</div>
								</div>
							</div>

							<div class="up-form-group up-pad-left-cq1">
								<button type="button" class="up-btn up-btn-primary"
									onclick="save()" id="baocun" name="baocun">保存</button>
							</div>

							<!-- <c:if test="${ empty orderInfo }">
								<div class="up-form-group up-pad-left-cq1">
									<button type="button" class="up-btn up-btn-primary"
										onclick="save()">保存</button>
								</div>
							</c:if> -->
						</form>



					</div>
				</div>
				<p class="up-cq-title up-cq-left-mar1">配置信息</p>
				<div class="up_page_con">

					<div class="ex_tab2" role="tabpanel"
						data-example-id="togglable-tabs">
						<div id="" class="up-tab-content">
							<div role="tabpanel" class="up-tab-pane up-active" id="mytab11"
								aria-labelledby="mytab11-tab">
								<div class="up-table-responsive">
									<div class="up-cq-table">
										<div class="up-cq-table-outer up-cq-left-mar1 up-cq-wid2">
											<div class="up-cq-table-inner">
												<div class="up-form-group">

													<button type="button" class="up-btn up-btn-primary"
														id="xinzheng" data-toggle="modal" data-target="#myModal2"
														onClick="add()"
														<c:if
												test="${empty orderInfo}">
												disabled="disabled"   </c:if>>新增</button>
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
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="vm" items="${orderVMList}">
															<tr>
																<c:forEach var="ipReq" items="${ipList }">
																	<c:if test="${ipReq.id eq vm.ipRequire }">
																		<c:set value="${ipReq.paramValue }" var="paraValue"></c:set>
																	</c:if>
																</c:forEach>
																<td><i:getValue dataType="ip_require_msg"
																		dataValue="${paraValue}"></i:getValue></td>
																<td>${vm.os }</td>
																<td>${vm.cpu }个</td>
																<td>${vm.memory }(G)</td>
																<td>${vm.disk }(G)</td>
																<td><a href="javascript:void(0)"
																	onClick="deleteVMApply('${vm.orderId}','${vm.id}')">删除</a>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												<%-- <div class="up-modal-footer up-modal-footer1">
													<button type="button" class="up-btn up-btn-reset mar-rig30"
														onClick="window.location.href='${basePath}/vmApply/orderList'">返回</button>
												</div> --%>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<p></p>
				<!-- class="up-btn up-btn-reset -->
				<div>
					<button type="button" class="up-btn up-btn-primary"
						mar-rig30"
						style="margin-left: 35%"
						onClick="applyOrder('${orderInfo.id}')"
						<c:if test="${empty orderVMList}">disabled="disabled" </c:if>>提交</button>
					<button type="button" class="up-btn up-btn-primary"
						mar-rig30"
						style="margin-left: 20px"
						onClick="window.location.href='${basePath}/vmApply/orderList'">返回</button>
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
