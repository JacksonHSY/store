<script type="text/javascript" src="${ctx}/static/js/date/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/utilTools.js"></script>
<script type="text/javascript" src="${ctx}/static/js/utils/ajaxfileupload.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/js/utils/autoclear.js"></script> --%>
<script type="text/javascript" >
function formatterdate(val, row) {
	if (val != null && val != "") {
		var date = new Date(val);
		var dateStr = date.format("yyyy-MM-dd hh:mm:ss");
		return dateStr
	} else {
		return "";
	}
}

function formatdate(val, row) {
	if (val != null && val != "") {
		var date = new Date(val);
		var dateStr = date.format("yyyy-MM-dd");
		return dateStr
	} else {
		return "";
	}
}

/**
 * 单位转换：btyes to Mb
 */
function bytesToSize(bytes) {
	if (bytes != null && bytes != "") {
	   if (bytes === 0) return '0 B';
	   var k = 1000, // or 1024
	   sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
	   i = Math.floor(Math.log(bytes) / Math.log(k));
	   return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
	}else{
		return "--";
	}
}

</script>

  
   

	