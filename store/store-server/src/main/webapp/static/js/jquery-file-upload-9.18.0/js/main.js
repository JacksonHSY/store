/*
 * jQuery File Upload Plugin JS Example
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * https://opensource.org/licenses/MIT
 */

/* global $, window */

$(function () {
    'use strict';
    function getRootPath() {//获得根目录
    	var strFullPath = window.document.location.href;
    	var strPath = window.document.location.pathname;
    	var pos = strFullPath.indexOf(strPath);
    	var prePath = strFullPath.substring(0, pos);
    	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    	return (prePath + postPath);
    };
    function getFileExec(name) {//获得根目录
    	var filename=name.replace(/.*(\/|\\)/, "");
    	var fileExt=(/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : '';
    	return fileExt;
    }
    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: getRootPath()+'/upload/doUpload'
    });

    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    if (window.location.hostname === 'blueimp.github.io') {
        // Demo settings:
        $('#fileupload').fileupload('option', {
            url: '//jquery-file-upload.appspot.com/',
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            maxFileSize: 999000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: '//jquery-file-upload.appspot.com/',
                type: 'HEAD'
            }).fail(function () {
                $('<div class="alert alert-danger"/>')
                    .text('Upload server currently unavailable - ' +
                            new Date())
                    .appendTo('#fileupload');
            });
        }
    } else {
        // Load existing files:
       /* $('#fileupload').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('#fileupload').fileupload('option', 'url'),
            dataType: 'json',
            context: $('#fileupload')[0],
            acceptFileTypes: /(\.|\/)(mp3|mp4)$/i
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
        }).bind('fileuploaddone', function (e, data) {
          	 if(data.result.code == '0000'){
        		 $.messager.alert('提示','上传成功!','info');
        		 $('#upload_win').window('close');
        		 showFileRecords(uploadType);
        	 }else{
        		 $.messager.alert('提示',data.result.msg,'alert');
        		 $('#upload_win').window('close');
        	 }
        });*/
     $('#fileupload').addClass('fileupload-processing');
     var curCount = 0;//已上传文件数
   	 $('#fileupload').fileupload('option', {
            url: $('#fileupload').fileupload('option', 'url'),
            context: $('#fileupload')[0],
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
           /* disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),*/
            maxFileSize: 419430400,
            acceptFileTypes: /(\.|\/)(mp3|mp4|flv)$/i
        }).bind('fileuploadchange', function (e, data) {

            /**判断上传文件是否重名*/
            videoNames.splice(0,videoNames.length);
            videoNames = videoNames.concat(videoUploadNames);
            $("#files_table tr td p.name").each(function(){
                videoNames.push($(this).text())
            });
        	if('1' == uploadType){//视频
        		//视频文件只能上传3条限制
        		isVedio = true;
                var videoTotalNum =videoUploadNum+$("#files_table tr").length+data.files.length;

        		if(videoTotalNum > 3 || $("#files_table tr").length>2){
                    videoTotalNum = videoTotalNum - data.files.length;
        			isVedio = false;
        			$.messager.show({
                        title: "操作提示",
                        msg: '视频文件上传个数不得超过三个!',
                        showType: 'slide',
                        timeout: 1000
                    });
        		}
        		//判断文件格式是否允许
        		isRightType = true;
        		isOverSize = true;
                isSameName = true;
                for(var i=0;i<data.files.length;i++){
                    var file = data.files[i];
                    if(file.size > 419430400){
                        isOverSize = false;
                        $.messager.show({
                            title: "操作提示",
                            msg: file.name+"文件大小超过400M!",
                            showType: 'slide',
                            timeout: 1000
                        });
                    }
                    if("mp4" != getFileExec(file.name) && ("flv" != getFileExec(file.name))){
                        isRightType = false;
                        $.messager.show({
                            title: "操作提示",
                            msg: file.name+'视频文件格式不是mp4或者flv!',
                            showType: 'slide',
                            timeout: 1000
                        });
                    }
                    if(contains(videoNames,file.name) != -1){
                        isSameName = false;
                        $.messager.show({
                            title: "操作提示",
                            msg: file.name+'视频文件已选择,请重新添加!',
                            showType: 'slide',
                            timeout: 1000
                        });
                    }
                    if(isVedio && isRightType && isOverSize && isSameName){
                        videoNames.push(file.name);
                    }
                }

        	}else{
        		//音频
        		isOverSize = true;
        		isVedio = true;
        		isRightType = true;
        		//判断文件格式是否允许
        		for(var i=0;i<data.files.length;i++){
        			var file = data.files[i];
        			if(file.size > 52428800){
        				isOverSize = false;
        				$.messager.show({
                            title: "操作提示",
                            msg: "文件大小超过50M!",
                            showType: 'slide',
                            timeout: 1000
                        });
            		}
        			if('mp3' != getFileExec(file.name)){
        				isRightType = false;
        			}
        		}
        		if(!isRightType){
        			$.messager.show({
                        title: "操作提示",
                        msg: '音频文件格式不是mp3!',
                        showType: 'slide',
                        timeout: 1000
                    });
        		}
        	}
        }).bind('fileuploaddone', function (e, data) {
             curCount ++;
        	 if(data.result.code == '0000'){
        		 $.messager.show({
                     title: "操作提示",
                     msg: data.files[0].name+"上传成功",
                     showType: 'slide',
                     timeout: 1000
                 });
           		 /*$.messager.alert('提示','上传成功!','info');*/

           	 }else{
           		 $.messager.alert({
                     title: "操作提示",
                     msg: data.files[0].name+data.result.msg,
                     showType: 'slide',
                     timeout: 1000
                 });
           		 /*$('#upload_win').window('close');*/
           	 }
           	 if(curCount == $("#files_table tr").length){
                 curCount = 0;
                 showFileRecords(uploadType);
                 $('#upload_win').window('close');
             }

        });

    }
    function contains(arr, obj) {
        //while
        var i = arr.length;
        while(i--) {
            if(arr[i] === obj) {
                return i;
            }
        }
        return -1;
    }

});
