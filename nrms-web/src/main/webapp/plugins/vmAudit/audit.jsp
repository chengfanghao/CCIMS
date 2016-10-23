<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">
	function audit(orderId) {
		//保存审批操作审批
		var operValue = $("#optionAudit").val();
		var userId = "${sessionScope.LOGIN_USER.id}";
		var feedBack = $("#txtBack").val();
		if(operValue == 1){
			//驳回操作，检测反馈
			if(!checkBlank(feedBack)){
				$("#msgBoxInfo").html("请填写驳回反馈");
				$("#msgBox").modal('show');
				return;
			}
		}			
		$.ajax({
			url : '${basePath}/vmAudit/saveAuditOption',
			dataType : 'json' ,
			data : {
				'orderId' : orderId,
				'operValue' : operValue,
				'userId' : userId,
				'feedBack' : feedBack
			},
			success : function(data) {
				if (data.success) {
					$("#msgBoxInfo").html(data.msg);
					$("#msgBox").modal('show');
					setTimeout(function() {
						window.location.href = "${basePath}/vmAudit/orderList";
					}, 2000);
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
						<span class="icon-right-dir"></span> 虚拟服务器审批
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
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="up-form-group ">
						<label for=""
							class="up-col-sm-1 up-control-label up-control-label1 up-cq-left-mar1">审批</label>
						<div class="up-col-sm-4">
							<select name="optionAudit" id="optionAudit"
								class="up-form-control">
								<option value="0">通过</option>
								<option value="1">驳回</option>
							</select>
						</div>
					</div>
					<div class="up-form-group ">
						<label for=""
							class="up-col-sm-1 up-control-label up-control-label1 up-cq-left-mar1">反馈</label>
						<div class="up-col-sm-5">
							<textarea id="txtBack"></textarea>
						</div>
					</div>
					<div class="up-text-center up-cq-wid3">
						<button id="btnAudit" type="button" class="up-btn up-btn-primary"
							data-dismiss="modal" onclick="audit('${orderInfo.id}')">保存</button>
						<button type="button" class="up-btn up-btn-default"
							data-dismiss="modal" onclick="window.location.href='${basePath}/vmAudit/orderList'">取消</button>
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
