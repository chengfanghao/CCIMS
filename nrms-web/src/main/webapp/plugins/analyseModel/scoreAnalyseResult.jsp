<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/common.jsp"%>

<html>
<%@include file="/common/admin-common-ui.jsp"%>
<script type="text/javascript">	

</script>
<head>
<meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="viewport" content="width=device-width,initial-scale=1.0">
</head>

<body>
	<div id="wrap" class="">
		<!--    头部 和  菜单 start -->
		<%@include file="/common/admin-headAndLeft.jsp"%>
		<!--    头部 和  菜单 end -->

		<!-- 内容start -->
		<main class="main up-container-fluid">
		<div class="up-tab-content">
			<div class="up-tab-pane up-active" id="tabs1">
				<div class="border_btm first_title">
					<h4 class="up-top-cq-color">
						<span class="icon-right-dir"></span> 成绩分析
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
														<form class="up-form-inline" id="searchForm" action="${basePath }">
															<input type="hidden" id="pageNum" name="pageNum" value="1">
															<div class="up-form-group">
																<label for="" class="up-control-label">年份:</label> 
																<select name="yearId" id="yearId" class="up-form-control" value="">
																	<option value="">--全部--</option>
																	<option value="2013" >2013</option>
																	<option value="2014" >2014</option>
																	<option value="2015" >2015</option>
																	<option value="2016" >2016</option>
																</select>
															</div>
															<div class="up-form-group">
																<button type="submit"  class="up-btn up-btn-primary">分析</button>
															</div>
														</form>
													</div>
												</div>
												<div class="up-box1-rc"
													style="width: 100%; text-align: center; padding-top: 180px;">
												 <div id="charts" style="width: 600px;height:400px;"></div>
			
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
	</div>
</body>
 			<script type="text/javascript">
				var myChart = echarts.init(document.getElementById('charts'));

				// 指定图表的配置项和数据
				var option = {
				    title: {
				        text: '13楚才理科班全视图'
				    },
				    tooltip: {},
				    legend: {
				        data:['平均成绩分布']
				    },
				    xAxis: {
				        data: ["0~60","60~70","70~80","80~90","90~95","95~100"]
				    },
				    yAxis: {},
				    series: [{
				        name: '人数',
				        type: 'bar',
				        data: [5, 20, 36, 10, 10, 20]
				    }]
				};

				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			 </script>
</html>
