<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>

			<!-- 左侧菜单的实现思路:
				要求: 根据当前登录用户的权限, 显示对应的菜单
				步骤
				用户->角色->模块
				1.获取当前用户
				2.遍历当前用户对应的角色
				3.遍历每个角色对应的模块
			 -->
			 <!-- 定义一个变量接收已经显示的菜单 -->
			 <c:set var="aaa" value="" scope="page"/>
			 <!-- 1.获取当前用户对应的角色 -->
			 <c:forEach items="${_CURRENT_USER.roles }" var="role">
			 	<!-- 遍历角色对应的模块Scope -->
			 	<c:forEach items="${role.modules }" var="module">
			 		<!-- 由于角色对应的模块有两种菜单: 0:顶部菜单 1:左侧菜单,2:顶部下方菜单 此处只需要显示顶部下方菜单 -->
			 		
			 		
			 		<c:if test="${(moduleName eq module.remark) and module.ctype==2  }">
			 			<!-- 由于一些角色对应的模块是重复的, 只需要显示一次 -->
			 			 <c:if test="${fn:contains(aaa, module.cpermission) eq false }">
			 				<!--  此时当前的模块名字还没有显示, 需要显示 -->
			 				<c:set var="aaa" value="${aaa }, ${module.cpermission }" />
			 				<!--  显示模块的名字 -->
			 				<c:if test="${fn:contains(module.cpermission, '查看') }">
				 				<li id="view"><a href="#" onclick="${module.curl}" target="main" id="aa_1">${module.cpermission }</a></li>
			 				</c:if>
			 				<c:if test="${fn:contains(module.cpermission, '新增') }">
				 				<li id="new"><a href="#" onclick="${module.curl}" target="main" id="aa_1">${module.cpermission }</a></li>
			 				</c:if>
			 				<c:if test="${fn:contains(module.cpermission, '修改') }">
				 				<li id="update"><a href="#" onclick="${module.curl}" target="main" id="aa_1">${module.cpermission }</a></li>
			 				</c:if>
			 				<c:if test="${fn:contains(module.cpermission, '删除') }">
				 				<li id="delete"><a href="#" onclick="${module.curl}" target="main" id="aa_1">${module.cpermission }</a></li>
			 				</c:if>
			 				<%-- <li><a href="#" onclick="${module.curl}" target="main" id="aa_1">${module.cpermission }</a></li> --%>
			 			</c:if>
			 		</c:if>
			 		
			 		
			 		
			 		
			 	</c:forEach>
			 </c:forEach>
</ul>
