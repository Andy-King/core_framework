<%@ page language="java" contentType="text/html; utf-8"	pageEncoding="utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="/css/tablecloth.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="/css/button.css" rel="stylesheet" type="text/css" media="screen" />
	<title>数据收集任务列表</title>
	<style>
		body{
			margin:0;
			padding:0;
			background:#f1f1f1;
			font:70% Arial, Helvetica, sans-serif; 
			color:#555;
			line-height:150%;
			text-align:left;
		}
		a{
			text-decoration:none;
			color:#057fac;
		}
		a:hover{
			text-decoration:none;
			color:#999;
		}
		h1{
			font-size:140%;
			margin:0 20px;
			line-height:80px;	
		}
		h2{
			font-size:120%;
		}
		#container{
			margin:0 auto;
			width:800px;
			background:#fff;
			padding-bottom:20px;
		}
		#content{margin:0 20px;}
	</style>
</head>
<body>



	<div id="container">
		<div>
		${users.userName} - ${users.email} 
		</div>
	</div>
</body>
</html>