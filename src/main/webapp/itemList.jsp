<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="telephone=no" name="format-detection" />
		<meta name="browsermode" content="application">
		<meta name="renderer" content="webkit">
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript" >
    var ctx = "${ctx}";	
</script>
<title>项目</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/casher/css/global.css"/>
		<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
		<script src="${ctx}/casher/js/global.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/comUtil.js"></script>  
<script type="text/javascript" src="${ctx}/js/itemList.js"></script>
</head>
<body>
	<body>

		<!-- 项目状态 -->
		<div class="i-itemStyle">
			<div class="i-itemStyle-list">

			</div>
		</div>
		<!-- 搜索 -->
		<div class="i-itemSeek">
			<input placeholder="请输入项目名称"id ="proName" type="text" />
			<div id="indexSeek"></div>
		</div>

		<!-- 项目详情 -->
		<div class="i-itemDetail">

			<div class="i-itemDetail-area">
				<div class="i-itemDetail-area-title">
					<div>必胜客</div>
					<p>详情</p>
				</div>
				<div class="i-itemDetail-area-content">
					<div class="content-row">
						<p>铺位号</p>
						<p>101</p>
						<!-- i标签 on1无需安装   on2已安装   on3安装失败  on4未安装   on5未开业  on6已拆除 -->
						<p><i class="on3"></i>无需安装</p>
					</div>
					<div class="content-row">
						<p>铺位号</p>
						<p>101</p>
					</div>
					<div class="content-row">
						<p>铺位号</p>
						<p>win7/64</p>
					</div>
				</div>
			</div>
		</div>
		<!-- 新建抄表 -->
<!--  		<div class="i-addTable">
			<div class="icon"></div>
			<div class="text" href="javascript:void(0)" onClick="create()" >新建抄表</div>
		</div> 
		 -->
		<script type="text/javascript">

			$(function(){
				/*
				 *	弹窗的使用方法  代码在global.js  app.alert
				*/
				//临时的方法 测试弹窗用
				//function ls(){
				//	alert("点了弹窗按钮");
				//}
				//app.alert("确定要退出吗？")			//只有确定按钮  		点击后关闭弹窗不执行任何操作		
				//app.alert("确定要退出吗？",2)			//有确定取消按钮  	点击2个按钮关闭弹窗都不执行任何操作
				//app.alert("确定要退出吗？",1,ls)		//只有确定按钮		点击后关闭弹窗并执行方法
				//app.alert("确定要退出吗？",2,ls)		//有确定取消按钮		点击确定关闭弹窗执行方法	点击取消弹窗关闭不执行方法
			})
		</script>
	</body>
</body>
</html>