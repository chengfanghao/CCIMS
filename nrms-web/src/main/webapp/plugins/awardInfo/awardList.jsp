<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>
<%@include file="/common/common-ui.jsp"%>
<html>

<script type="text/javascript">
	
	function deleteUser(awardId) {
		
		$("#msgBoxConfirmInfo").html("确定要删除该奖项吗");
		$("#msgBoxConfirm").modal('show');
		$("#msgBoxConfirmButton").on('click' , function(){
			$("#msgBoxConfirm").modal('hide');
			$.ajax({
				type : 'POST',
				url : '${basePath}/awardInfo/deleteUser',
				data : {
					'id' : awardId
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#msgBoxInfo").html(data.msg);
						$("#msgBox").modal('show');
						$("#msgBoxOKButton").on('click' , function(){
							window.location.href = '${basePath }/awardInfo/awardInfoList?pageNum=1';
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
						<span class="icon-right-dir"></span> 获奖情况
					</h4>
				</div>
				<div class="up_page_con">
					<div class="ex_tab2" role="tabpanel" data-example-id="togglable-tabs">
						<div id="" class="up-tab-content">
							<div role="tabpanel" class="up-tab-pane up-active" id="mytab11" aria-labelledby="mytab11-tab">
								<div class="up-table-responsive">
									<div class="up-cq-table">
										<div class="up-cq-table-outer">
											<div class="up-cq-table-inner">
												<div class="up-clearfix table_head margin_bottom10">
													<div class="reference_search">
														<form class="up-form-inline" id="searchForm" action="${basePath }/awardInfo/awardInfoList">
															<input type="hidden" id="pageNum" name="pageNum" value="1">
															
															<div class="up-form-group">
																<label for="" class="up-control-label">获奖名称:</label> 
																<input type="text" class="up-form-control" id="awardName" name="awardName" value="${searchParam.awardName }">
															</div>
															
															<div class="up-form-group" style="margin-left: 20px">
																<label for="" class="up-control-label">获奖级别:</label> 
																<select class="up-form-control" name="gradeId" id="gradeId" style="width:260px">
																	<option value="">请选择</option>
																	<c:forEach var="gradeItem" items="${gradeList }">
																		<option value="${gradeItem.gradeId}" <c:if test="${gradeItem.gradeId eq searchParam.gradeId }">selected</c:if>>${gradeItem.gradeName }</option>
																	</c:forEach>
																</select>
															</div>
															
															<div class="up-form-group" style="margin-left: 20px">
																<label for="" class="up-control-label">获奖等次:</label> 
																<select class="up-form-control" name="rankId" id="rankId" style="width:260px">
																	<option value="">请选择</option>
																	<c:forEach var="rankItem" items="${rankList }">																		
																		<option value="${rankItem.rankId}" <c:if test="${rankItem.rankId eq searchParam.rankId }">selected</c:if>>${rankItem.rankName }</option>
																	</c:forEach>
																</select>
															</div>
															
															<div class="up-form-group" style="margin-top: 10px">
																<label for="" class="up-control-label">发奖单位:</label> 
																<input type="text" class="up-form-control" id="unit" name="unit" value="${searchParam.unit }">
															</div>
															
															<div class="up-form-group" style="margin-left: 20px;margin-top: 10px;">
																<label for="" class="up-control-label">获奖时间:</label> 
																<input type="text" style="width:180px;height:30px" class="Wdate up-form-control" id="adwardTime" name="awardTime" 
																value="${searchParam.awardTime}" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy年MM月',isShowClear:false,readOnly:true})">
															</div>
															
															<div class="up-form-group" style="margin-top: 10px">
																<button type="submit"  class="up-btn up-btn-primary">搜索</button>
															</div>
														</form>
													</div>
												</div>
												
												<div class="up-clearfix table_head">
													<div class="reference_search">
														<div class="up-form-group">
															<button type="submit" class="up-btn up-btn-primary up-btn-primary-red" data-toggle="modal"
																 onClick="showDialog('新增奖项' , '${basePath}/awardInfo/toAddOrEditAward' , '470px')">新增</button>
														</div>
													</div>
												</div>
												
												<table
													class="up-table up-table-bordered up-table-hover margin_bottom10 up-text-center">
													<thead>
														<tr class="up-active">
															<th>获奖名称</th>
															<th>获奖级别</th>
															<th>获奖等次</th>
															<th>发奖单位</th>
															<th>获奖时间</th>
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="stuAward" items="${page.dataList }">
															<tr>
																<td>${stuAward.awardName }</td>
																<td>${stuAward.gradeName}</td>
																<td>${stuAward.rankName}</td>
																<td>${stuAward.unit}</td>
																<td>${stuAward.awardTime}</td>
																<td>														
																	<a href="javascript:void(0)" onClick="showDialog('编辑用户' , '${basePath}/awardInfo/toAddOrEditAward?awardId=${stuAward.awardId } ' , '470px')">编辑</a>
																	<a href="javascript:void(0)" onClick="deleteUser('${stuAward.awardId}')">删除</a>
																</td>
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
		
		 <div class="up-modal up-fade" id="modalDialog"  data-drag="true" data-backdrop="static">
          <div class="up-modal-dialog up-modal-lg">
            <div class="up-modal-content">
              <div class="up-modal-header">
                <button type="button" class="up-close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="up-modal-title" id="modalDialogTitle">新增用户</h4>
              </div>
              <iframe id="modalDialogFrame"  width="100%" height="420px" frameborder="0"></iframe>
            </div>
          </div>
        </div>

	</div>
</body>

</html>
