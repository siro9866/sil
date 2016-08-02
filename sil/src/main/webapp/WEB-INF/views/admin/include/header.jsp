<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/sample/include/taglibs.jsp" %>

<!-- S:FILE:header.jsp -->
<spring:eval expression="@config['TABLESC']" var="tablesc" />
<spring:eval expression="@message['SUCCESS']" var="success" />
${tablesc }/${success }
<!-- E:FILE:header.jsp -->