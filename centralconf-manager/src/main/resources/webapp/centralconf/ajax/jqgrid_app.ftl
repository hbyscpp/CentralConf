<!-- Bread crumb is created dynamically -->
<!-- row -->
<html>
<div class="row">
	
	<!-- col -->
	<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
		<h1 class="page-title txt-color-blueDark">
			
			<!-- PAGE HEADER -->
			<i class="fa-fw fa fa-table"></i> 
				APPS
			<span>>  
				应用
			</span>
		</h1>
	</div>
	
</div>
<!-- end row -->

<!-- widget grid -->
<section id="widget-grid" class="">

	<!-- row -->
	<div class="row">
		
		<!-- NEW WIDGET START -->
		<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<table id="jqgrid"></table>
				<div id="pjqgrid"></div>
				
		</article>
		<!-- WIDGET END -->
		
	</div>

	<!-- end row -->

</section>
<!-- end widget grid -->


<script type="text/javascript">
	 
	/* DO NOT REMOVE : GLOBAL FUNCTIONS!
	 *
	 * pageSetUp(); WILL CALL THE FOLLOWING FUNCTIONS
	 *
	 * // activate tooltips
	 * $("[rel=tooltip]").tooltip();
	 *
	 * // activate popovers
	 * $("[rel=popover]").popover();
	 *
	 * // activate popovers with hover states
	 * $("[rel=popover-hover]").popover({ trigger: "hover" });
	 *
	 * // activate inline charts
	 * runAllCharts();
	 *
	 * // setup widgets
	 * setup_widgets_desktop();
	 *
	 * // run form elements
	 * runAllForms();
	 *
	 ********************************
	 *
	 * pageSetUp() is needed whenever you load a page.
	 * It initializes and checks for all basic elements of the page
	 * and makes rendering easier.
	 *
	 */

	pageSetUp();
	
	/*
	 * ALL PAGE RELATED SCRIPTS CAN GO BELOW HERE
	 * eg alert("my home function");
	 * 
	 * var pagefunction = function() {
	 *   ...
	 * }
	 * loadScript("js/plugin/_PLUGIN_NAME_.js", pagefunction);
	 * 
	 */
	
	var pagefunction = function() {
		loadScript("js/plugin/jqgrid/jquery.jqGrid.min.js", run_jqgrid_function);

		function run_jqgrid_function() {

			jQuery("#jqgrid").jqGrid({
				url:"getApp",//TODO
				//data : jqgrid_data,
				datatype : "json",
				mtype:"POST",
				height : 'auto',
				colNames : [ '应用名', '操作'],
				colModel : [
					{ name : 'name', index : 'name' , editable : true}, 
					{ name : 'act', index:'act', sortable:false }
					//{ name : 'amount', index : 'amount', align : "right", editable : true }, 
					],
				rowNum : 'all',
				//rowList : [10, 20, 30],
				pager : '#pjqgrid',
				pgbuttons:false,
				pginput :false,
				sortname : 'id',
				toolbarfilter: true,
				viewrecords : false,
				sortorder : "asc",
				gridComplete: function(){
					var ids = jQuery("#jqgrid").jqGrid('getDataIDs');
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];
						var rowData = jQuery("#jqgrid").jqGrid('getRowData',ids[i]);
						ca = "<a class='btn btn-info' aria-describedby='jqgrid_operator' style='text-decoration: none; color: #fff;' data-original-title='Cancel' href=\"#toEnv?app="+rowData.name+"\"><i class='fa fa-exclamation-sign'></i>环境</a>";
						jQuery("#jqgrid").jqGrid('setRowData',ids[i],{act:ca});
					}	
				},
				editurl : "updApp",
				caption : "应用列表",
			//	multiselect : true,
				autowidth : true
				});
				jQuery("#jqgrid").jqGrid('navGrid', "#pjqgrid", {
					search:false,
					edit : false,
					add : true,
					del : true
				});
				//jQuery("#jqgrid").jqGrid('inlineNav', "#pjqgrid");
				/* Add tooltips */
				$('.navtable .ui-pg-button').tooltip({
					container : 'body'
				});

				jQuery("#m1").click(function() {
					var s;
					s = jQuery("#jqgrid").jqGrid('getGridParam', 'selarrrow');
					alert(s);
				});
				jQuery("#m1s").click(function() {
					jQuery("#jqgrid").jqGrid('setSelection', "13");
				});
				
				// remove classes
				$(".ui-jqgrid").removeClass("ui-widget ui-widget-content");
			    $(".ui-jqgrid-view").children().removeClass("ui-widget-header ui-state-default");
			    $(".ui-jqgrid-labels, .ui-search-toolbar").children().removeClass("ui-state-default ui-th-column ui-th-ltr");
			    $(".ui-jqgrid-pager").removeClass("ui-state-default");
			    $(".ui-jqgrid").removeClass("ui-widget-content");
			    
			    // add classes
			    $(".ui-jqgrid-htable").addClass("table table-bordered table-hover");
			    $(".ui-jqgrid-btable").addClass("table table-bordered table-striped");
			   
			   
			    $(".ui-pg-div").removeClass().addClass("btn btn-sm btn-primary");
			    $(".ui-icon.ui-icon-plus").removeClass().addClass("fa fa-plus");
			    $(".ui-icon.ui-icon-pencil").removeClass().addClass("fa fa-pencil");
			    $(".ui-icon.ui-icon-trash").removeClass().addClass("fa fa-trash-o");
			    $(".ui-icon.ui-icon-search").removeClass().addClass("fa fa-search");
			    $(".ui-icon.ui-icon-refresh").removeClass().addClass("fa fa-refresh");
			    $(".ui-icon.ui-icon-disk").removeClass().addClass("fa fa-save").parent(".btn-primary").removeClass("btn-primary").addClass("btn-success");
			    $(".ui-icon.ui-icon-cancel").removeClass().addClass("fa fa-times").parent(".btn-primary").removeClass("btn-primary").addClass("btn-danger");
			  
				$( ".ui-icon.ui-icon-seek-prev" ).wrap( "<div class='btn btn-sm btn-default'></div>" );
				$(".ui-icon.ui-icon-seek-prev").removeClass().addClass("fa fa-backward");
				
				$( ".ui-icon.ui-icon-seek-first" ).wrap( "<div class='btn btn-sm btn-default'></div>" );
			  	$(".ui-icon.ui-icon-seek-first").removeClass().addClass("fa fa-fast-backward");		  	
	
			  	$( ".ui-icon.ui-icon-seek-next" ).wrap( "<div class='btn btn-sm btn-default'></div>" );
			  	$(".ui-icon.ui-icon-seek-next").removeClass().addClass("fa fa-forward");
			  	
			  	$( ".ui-icon.ui-icon-seek-end" ).wrap( "<div class='btn btn-sm btn-default'></div>" );
			  	$(".ui-icon.ui-icon-seek-end").removeClass().addClass("fa fa-fast-forward");
			  	
			  	
			  
			  
			    // update buttons
			    
			    $(window).on('resize.jqGrid', function () {
					jQuery("#jqgrid").jqGrid( 'setGridWidth', $("#content").width() );
				})


			} // end function


	}
	
	loadScript("js/plugin/jqgrid/grid.locale-en.min.js", pagefunction);
	
	var modal_pagefunction = function() {
		$("#dialog-message").dialog({
			autoOpen : false,
			modal : true,
			title : "环境",
			buttons : [{
				html : "取消",
				"class" : "btn btn-default",
				click : function() {
					$(this).dialog("close");
				}
			}, {
				html : "<i class='fa fa-check'></i>&nbsp; OK",
				"class" : "btn btn-primary",
				click : function() {
					$(this).dialog("close");
				}
			}]
	
		});
	};
	modal_pagefunction();
</script>


	
</html>