<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>工单首页</title>
	<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="css/bootstrap-table.css" />
	<link type="text/css" rel="stylesheet" href="css/processInfo/processIndex.css" />
	<script src="js/jquery-1.12.2.min.js" type="text/javascript"></script>
	<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-table.js" type="text/javascript"></script>
	<script src="js/bootstrap-table-zh-CN.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="container">
			<div id="toolbar" class="btn-group">
				<button id="btn_add" type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				</button>
				<button id="btn_edit" type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
				</button>
				<button id="btn_delete" type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				</button>
			</div>
			<table id="processTable">
				<thead>
					<tr>
						<th data-field="id">id</th>
						<th data-field="userName">登录账户</th>
						<th data-field="realName">用户名</th>
						<th data-field="department">部门</th>
						<th data-field="level">账号级别</th>
						<th data-field="perms">权限</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
	</html>