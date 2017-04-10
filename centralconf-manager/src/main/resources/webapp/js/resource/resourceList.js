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

	/***************************************************************************
	 * 表单验证开始
	 */

	$('#resourceForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			rscName : {
				message : '资源名验证失败',
				validators : {
					regexp : {/* 只需加此键值对，包含正则表达式，和提示 */
						regexp : /^[a-zA-Z0-9_\.-]+$/,
						message : '只能是数字和字母及部特殊符号（-_.）'
					},
					notEmpty : {
						message : '资源名不能为空'
					}
				}
			}
		}
	});

	$("#resourceClosed").click(function() {
		$('#resourceForm').data('bootstrapValidator').resetForm(true);
	})

	/***************************************************************************
	 * 表单验证结束
	 */

	$("#btn_add").click(function() {
		$("#appModal").modal('show');
		$("#clicktype").val("1");
		$("#myModalLabel").text("添加资源");
		// $("#userName").removeAttr("readonly");
		reset();
	})

	$("#suresubmit").click(function() {
		$.ajax({
			type : "post",
			url : "delRes",
			data : {
				id : $("#resId").val()
			},
			success : function(data) {
				$('#permListTable').bootstrapTable('refresh');
				$('#sure').modal('hide');
				message(data);
			}
		});
	})

	window.operateEvents = {
		'click .btn_config' : function(e, value, row, index) {
			window.location.href = "index#toResourceEnv?resId=" + row.id;
			// var $a = $("<a>"); toResourceEnv
			// $a.attr('href', "index#toEnv?app=" + row.id);
			// $a[0].click();
		},
		'click .btn_delete' : function(e, value, row, index) {
			$("#mySmallModalLabel").text("资源删除(" + row.resName + ")")
			$('#sure').modal('show');
			$("#resId").val(row.id);
		},
		'click .btn_common' : function(e, value, row, index) {
			window.location.href = "index#toConfItem?env=_common&app=" + row.id;
		}

	};

	$("#permListTable").bootstrapTable({
		url : "getAllRes",
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
		// queryParams: getParams,//携带参数
		pageSize : 1000,// 每页行数
		pageIndex : 0,// 其实页
		method : "get",// 请求格式
		columns : [ {
			title : '资源名',
			field : 'resName',
			width : '30%',
			align : 'center',
		},  {
			title : '描述',
			field : 'resDesc',
			width : '30%',
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
		return [ '<button type="button" class="btn_config btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>环境配置</button>', '<button type="button" class="btn_delete btn btn-default  btn-sm" style="margin-right:10px;"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>' ].join('');
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

	$('#resourceForm').on('submit', function(e) {
		e.preventDefault();
		if ($(this).data('bootstrapValidator').isValid()) {
			var url = "addRes";
			if ($("#clicktype").val() != 1) {
				url = "updateRes";
			}
			$.ajax({
				type : "post",
				url : url,
				data : $("#resourceForm").serializeObject(),
				success : function(data) {
					$('#permListTable').bootstrapTable('refresh');
					$('#resourceForm').data('bootstrapValidator').resetForm(true);
					$('#appModal').modal('hide');
					message(data);
					reset();
				}
			});
		}

	})

	$("#resourceSubmit").click(function() {
		$("#resourceForm").submit();
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