<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>权限</title>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap-table.css" />
<link type="text/css" rel="stylesheet" href="js/plugin/messenger/css/messenger-theme-future.css" />
<link type="text/css" rel="stylesheet" href="js/plugin/messenger/css/messenger.css" />
<link type="text/css" rel="stylesheet" href="css/awesome-bootstrap-checkbox.css" />
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table-zh-CN.js" type="text/javascript"></script>
<script src="js/plugin/messenger/js/messenger.min.js" type="text/javascript"></script>
<script src="js/plugin/bootstrapvalidator/bootstrapValidator.js" type="text/javascript"></script>
<script src="js/app/commonItemDetail.js" type="text/javascript"></script>
<style type="text/css">
body td {
	height: 47px;
	font-size: 14px;
}

body #sourceListTable td {
	height: 37px;
	font-size: 14px;
}
</style>
</head>
<body>

	<input value="${env.appId}" type="hidden" id="appId">
	<input value="${env.envId}" type="hidden" id="envId">
	<div class="row">

		<!-- col -->
		<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
			<h1 class="page-title txt-color-blueDark">

				<!-- PAGE HEADER -->
				<i class="fa-fw fa fa-table"></i> APPS <span id="app_span">> <a href="#toApp">应用（${env.appId}）</a>
				> <a href="#toEnv?app=${env.appId}">环境（common）</a></span><span>> 公共配置项 </span>
			</h1>
		</div>

	</div>
	<div style="border: 1px solid #9CB4C5; margin-top: 50px;">
		<div class="alert  alert-info" role="alert">
			<strong>公共配置项列表</strong>
		</div>
		<div id="comtoolbar" class="btn-group">
			<button id="btn_comadd" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
		</div>
		<table id="comitemListTable">
		</table>

		<div class="modal fade" id="comItemModal" tabindex="-1" role="dialog" aria-labelledby="comItemLabel" aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="comItemLabel">公共配置项添加</h4>
					</div>
					<div class="modal-body">
						<form user="form" id="comitemform">
							<div>
								<input type="hidden" class="form-control" id="comclicktype"> <input type="hidden" class="form-control" id="comitemIdForm" name="comitemId" />
							</div>
							<div class="form-group">
								<label>公共配置项名</label> <input type="text" class="form-control" id="comitemNameForm" name="key" />
							</div>
							<div class="form-group">
								<label>值</label> <input type="text" class="form-control" id="comitemValueForm" name="value" />
							</div>
							<div class="form-group">
								<label>描述</label> <input type="text" class="form-control" id="comitemDescForm" name="desc" />
							</div>

						</form>
					</div>
					<div class="modal-footer">
						<button type="button" id="comItemSubmit" class="btn btn-primary">保存</button>
						<button type="button" id="comitemClosed" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<div id="comSure" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="comSmallModalLabel" aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="comSmallModalLabel">公共项删除</h4>
						<input type="hidden" class="form-control" id="comitemId">
					</div>
					<div class="modal-footer">
						<button type="button" id="comsuresubmit" class="btn btn-primary">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>