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
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap-table-zh-CN.js"
	type="text/javascript"></script>
<script src="js/plugin/messenger/js/messenger.min.js"
	type="text/javascript"></script>
<script src="js/plugin/bootstrapvalidator/bootstrapValidator.js"
	type="text/javascript"></script>
<script src="js/plugin/bootstrapvalidator/language/zh_CN.min.js"
	type="text/javascript"></script>
<style type="text/css">
body td {
	height: 47px;
	font-size: 14px;
}
</style>
<script type="text/javascript">
	// var fun = function(a){
	// 	alert(1);
	// 	alert(a);
	// }
	// $.getJSON("http://192.168.30.147:9832/flycar/bussiness/order/testJsonpone?callback=?", fun);
	// $.getJSON("http://192.168.30.147:9832/flycar/bussiness/order/testJsonp?callback=?", fun);
	
	
	$.getJSON("http://192.168.30.147:9832/flycar/bussiness/order/testJsonpone?callback=?", function(result) {
		alert(2);
		alert(result);
	});
	$.getJSON("http://192.168.30.147:9832/flycar/bussiness/order/testJsonp?callback=?", function(result) {
		alert(1);
		alert(result);
	});
</script>
</head>
<body>


	<div id="toolbar" class="btn-group">
		<button id="btn_add" type="button" class="btn btn-default">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
		</button>
	</div>

</body>
</html>