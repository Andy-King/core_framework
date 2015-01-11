<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8" language="java"
	import="com.lefeng.dcframe.task.*,com.lefeng.dcframe.bussniess.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="refresh" content="5">
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

	<%
		ScheduleManager scheduleManager = com.lefeng.dcframe.common.ApplicationConstants
				.getScheduleManager();
	%>

	<div id="container">
		<div id="content">
			<h1>数据收集任务列表</h1>
			<%
				Map<String, TaskGroup> mapGroup = scheduleManager.getMapGroup();
				for (Map.Entry<String, TaskGroup> m : mapGroup.entrySet()) {
			%>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<th  rowspan="3" colspan="2" >任务组:<%=m.getKey()%>&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="/task/start.action?groupName=<%=m.getKey()%>" class="a_demo_four">启动</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="/task/shutdown.action?groupName=<%=m.getKey()%>"  class="a_demo_five">关闭</a>
					</th>
					<th></th>
				</tr>
				<tr>
					<th></th>
				</tr>
				<tr>
					<th></th>
				</tr>
				<tr>
					<th>任务名称</th>
					<th>任务状态</th>
					<th>操作</th>
				</tr>
				<%
					TaskGroup taskGroup = m.getValue();
						List<AbstractTask> taskList = m.getValue().getWorkThread();
						for (int i = 0; i < taskList.size(); i++) {
				%>
				<tr>
					<td><%=taskList.get(i).getName()%></td>
					<%
						if (taskList.get(i).getStatus() == 0) {
					%>
					<td>未初始化</td><td></td>
					<%
						} else if (taskList.get(i).getStatus() == 1) {
					%>
					<td>准备</td><td></td>
					<%
						} else if (taskList.get(i).getStatus() == 4) {
					%>
					<td>休息</td><td><a
						href="/task/suspend.action?groupName=<%=m.getKey()%>&index=<%=i%>">暂停</a></td>
					<%
						} else if (taskList.get(i).getStatus() == 2) {
					%>
					<td>运行</td><td><a
						href="/task/suspend.action?groupName=<%=m.getKey()%>&index=<%=i%>">暂停</a></td>
					<%
						} else if (taskList.get(i).getStatus() == 5) {
					%>
					<td>中断</td><td><a
						href="/task/redo.action?groupName=<%=m.getKey()%>&index=<%=i%>">恢复</a></td>
					<%
						} else if (taskList.get(i).getStatus() == 3) {
					%>
					<td>结束</td><td></td>
					<%
						}
					%>
				</tr>
				<%
					}
				%>
			</table>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>