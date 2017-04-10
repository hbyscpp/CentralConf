$(function() {
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	$('#appform').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				message : '应用名验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '应用名不能为空'
					}
				}
			}
		}
	});

	$("#btn_add").click(function() {
		$("#appModal").modal('show');
		$("#clicktype").val("1");
		$("#myModalLabel").text("添加权限");
		reset();
	})

	$("#appClose").click(function() {
		$('#appform').data('bootstrapValidator').resetForm(true);
	})

	$("#suresubmit").click(function() {
		$.ajax({
			type : "post",
			url : "delApp",
			data : {
				id : $("#appId").val()
			},
			success : function(data) {
				$('#permListTable').bootstrapTable('refresh');
				$('#sure').modal('hide');
				message(data);
			}
		});
	})

	window.operateEvents = {
		'click .btn_edit' : function(e, value, row, index) {
			window.location.href = "index#toEnv?app=" + row.id;
			// var $a = $("<a>");
			// $a.attr('href', "index#toEnv?app=" + row.id);
			// $a[0].click();
		},
		'click .btn_delete' : function(e, value, row, index) {
			$("#mySmallModalLabel").text("应用删除(" + row.appName + ")")
			$('#sure').modal('show');
			$("#appId").val(row.id);
		},
		'click .btn_common' : function(e, value, row, index) {
			// $a = $("<a>");
			// $a.attr('href', "index#toConfItem?env=_common&app=" + row.id);
			// $a[0].click();
			window.location.href = "index#toCommonItemDetail?appId=" + row.id;
		}

	};

	$("#permListTable").bootstrapTable({
		url : "getApp",
		toolbar : "#toolbar",
		sidePagination : "server",// 服务器分页
		// sortName: "rkey",//排序列
		striped : true,// 是否显示行间隔色
		search : true,// 搜索功能
		responseHandler : function(res) {// 这里我查看源码的，在ajax请求成功后，发放数据之前可以对返回的数据进行处理，返回什么部分的数据，比如我的就需要进行整改的！
			if (res.code != 0) {
				return res;
			} else {
				return res.data;
			}
		},
		contentType : 'application/x-www-form-urlencoded',
		// minimumCountColumns: 2, //最少允许的列数
		showRefresh : true,// 刷新功能
		clickToSelect : true,// 选择行即选择checkbox
		singleSelect : true,// 仅允许单选
		// searchOnEnterKey: true,//ENTER键搜索
		pagination : true,// 启用分页
		escape : false,// 过滤危险字符
		showColumns : true,// 是否显示所有的列
		showToggle : true, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		queryParams : function(params) {
			return params;
		},// 携带参数
		pageNumber : 1,
		pageSize : 3,// 每页行数
		method : "post",// 请求格式
		columns : [ {
			title : '应用名',
			field : 'appName',
			width : '10%',
			align : 'center',
		}, {
			title : '描述',
			field : 'appDesc',
			width : '10%',
			align : 'center',
		}, {
			title : '负责人',
			field : 'liableUserId',
			width : '10%',
			align : 'center',
		}, {
			title : '创建人',
			field : 'createUserId',
			width : '10%',
			align : 'center',
		}, {
			title : '创建时间',
			field : 'createTime',
			width : '10%',
			align : 'center',
		}, {
			title : '修改时间',
			field : 'updateTime',
			width : '10%',
			align : 'center',
		}, {
			field : '',
			title : '操作',
			width : '30%',
			align : 'center',
			events : operateEvents,
			formatter : operateFormatter
		} ],
		onPageChange : function(number, size) {
			// currPageIndex = number;
			// currLimit = size
		},
		onLoadSuccess : function() {
			// $("#searchBtn").button('reset');
		}
	});

	function operateFormatter(value, row, index) {
		return [ '<button type="button" class="btn_edit btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>环境配置</button>',
				'<button type="button" class="btn_common btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>公共环境配置</button>',
				'<button type="button" class="btn_delete btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>' ].join('');
	}

	function centerModals() {
		$('#appModal').each(function(i) {
			var $clone = $(this).clone().css('display', 'block').appendTo('body');
			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
			top = top > 0 ? top : 0;
			$clone.remove();
			$(this).find('.modal-content').css("margin-top", top);
		});
		$('#sure').each(function(i) {
			var $clone = $(this).clone().css('display', 'block').appendTo('body');
			var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
			top = top > 0 ? top : 0;
			$clone.remove();
			$(this).find('.modal-content').css("margin-top", top);
		});
	}
	// $('#permModal').on('show.bs.modal', centerModals);
	centerModals();

	$('#appform').on('submit', function(e) {
		e.preventDefault();
		if ($(this).data('bootstrapValidator').isValid()) {
			var url = "addApp";
			if ($("#clicktype").val() != 1) {
				url = "updatePerm";
			}
			$.ajax({
				type : "post",
				url : url,
				data : $("#appform").serializeObject(),
				success : function(data) {
					$('#permListTable').bootstrapTable('refresh');
					$('#appform').data('bootstrapValidator').resetForm(true);
					$('#appModal').modal('hide');
					message(data);
					reset();
				}
			});

		}
	})

	$("#appsubmit").click(function() {
		$("#appform").submit();
	})

	var reset = function() {
		$("#appNameForm").val("");
	}
	Messenger.options = {
		extraClasses : 'messenger-fixed messenger-on-top messenger-on-right',
		theme : 'future'
	}
	var message = function(data) {
		var type = "info";
		if (data.code != 0) {
			tyep = "error";
		}
		Messenger().post({
			message : data.msg,
			type : type,
			showCloseButton : true
		// extraClasses : 'messenger-fixed messenger-on-top messenger-on-right',
		// theme : 'Block'
		});
	}

})