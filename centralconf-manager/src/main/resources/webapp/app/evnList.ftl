<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>权限</title>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap-table.css" />
<link type="text/css" rel="stylesheet"
	href="js/plugin/messenger/css/messenger-theme-future.css" />
<link type="text/css" rel="stylesheet"
	href="js/plugin/messenger/css/messenger.css" />
<link type="text/css" rel="stylesheet"
	href="css/awesome-bootstrap-checkbox.css" />
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table-zh-CN.js"
	type="text/javascript"></script>
<script src="js/plugin/messenger/js/messenger.min.js"
	type="text/javascript"></script>
<script src="js/plugin/bootstrapvalidator/bootstrapValidator.js"
	type="text/javascript"></script>
<script src="js/app/evnList.js" type="text/javascript"></script>
<style type="text/css">
body td {
	height: 47px;
	font-size: 14px;
}

body #sourceListTable td {
	height: 37px;
	font-size: 14px;
}

.detail-view table {
	border: 1px solid #dddddd;
	text-align: center;
}

.detail-view table td {
	border: 1px solid #dddddd;
}
</style>
</head>
<body>

	<input value=${app.appId} type="hidden" id="appId">
	<div class="row">

		<!-- col -->
		<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
			<h1 class="page-title txt-color-blueDark">

				<!-- PAGE HEADER -->
				<i class="fa-fw fa fa-table"></i> APPS <span id="app_span">>
					<a href="#toApp">应用（${app.appName}）</a>
				</span> <span>> 环境 </span>
			</h1>
		</div>

	</div>
	<div id="toolbar" class="btn-group">
		<button id="btn_common" type="button" class="btn btn-default">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>公共环境配置
		</button>
		<button id="btn_add" type="button" class="btn btn-default">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
		</button>
	</div>
	<table id="evnListTable">
	</table>

	<!-- Button trigger modal -->
	<!-- <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
				  Launch demo modal
				</button> -->

	<!-- Modal -->
	<div class="modal fade" id="evnModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">权限环境</h4>
				</div>
				<div class="modal-body">
					<form user="form" id="evnform">
						<div>
							<input type="hidden" class="form-control" id="clicktype">
							<input type="hidden" class="form-control" id="appIdForm"
								name="appId" />
						</div>
						<div class="form-group">
							<label>环境名</label> <input type="text" class="form-control"
								id="evnNameForm" name="envName" /> <label>环境描述</label> <input
								type="text" class="form-control" id="envDescForm" name="envDesc" />
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="evnsubmit" class="btn btn-primary">保存</button>
					<button type="button" id="evnClosed" class="btn btn-default"
						data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div id="sure" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="mySmallModalLabel">应用删除</h4>
					<input type="hidden" class="form-control" id="envId">
				</div>
				<div class="modal-footer">
					<button type="button" id="suresubmit" class="btn btn-primary">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>

	<div id="clone" class="modal fade bs-example-modal-sm" tabindex="-1"
		role="dialog" aria-labelledby="cloneModalLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="cloneModalLabel">复制环境</h4>
				</div>
				<div class="modal-body">
					<form user="form" id="cloneForm">
						<input type="hidden" class="form-control" id="cloneEvnName">
						<div class="form-group">
							<label>是否覆盖</label>
							<div class="radio radio-info">
								<input type="radio" name="radio" id="radio_true" value="true"
									checked> <label for="radio3"> 是 </label>
							</div>
							<div class="radio radio-info">
								<input type="radio" name="radio" id="radio_false" value="false">
								<label for="radio4"> 否 </label>
							</div>
						</div>
						<div class="form-group">
							<label>目标环境名</label> <input type="text" class="form-control"
								id="dstEnv" name="tarEnvName"> <label>目标环境描述</label> <input
								type="text" class="form-control" id="envDescForm"
								name="tarEnvDesc" />
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="clonesubmit" class="btn btn-primary">确定</button>
					<button type="button" id="cloneClosed" class="btn btn-default"
						data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>


	<div class="modal fade bs-example-modal-lg" id="source" tabindex="-1" 
		role="dialog" aria-labelledby="sourceLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-dialog modal-lg" style="width:70%">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="sourceLabel">资源关联</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" class="form-control" id="envRelationId">
					<table id="sourceListTable">
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" id="sourceSubmit" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade bs-example-modal-lg" id="itemSource"
		tabindex="-1" role="dialog" aria-labelledby="itemSourceLabel"
		aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="itemSourceLabel">资源列表</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" class="form-control" id="itemEnvRelationId">
					<table id="itemSourceListTable">
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" id="itemSourceSubmit" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>