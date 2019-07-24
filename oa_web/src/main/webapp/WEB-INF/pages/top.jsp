<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.sh.oa.global.Contant" %>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <title> </title>
    <link rel="stylesheet" type="text/css" href="/assets/skin/default_skin/css/theme.css">
    <link rel="stylesheet" type="text/css" href="/assets/admin-tools/admin-forms/css/admin-forms.css">
</head>
<body class="admin-validation-page" data-spy="scroll" data-target="#nav-spy" data-offset="200">
<div id="main">
    <header class="navbar navbar-fixed-top navbar-shadow">
        <div class="navbar-branding">
            <a class="navbar-brand" href="#">
            </a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown menu-merge">
                    <li class="dropdown-footer">
                        <a href="/quit" class="">
                            <span class="fa fa-power-off pr5"></span> 退出 </a>
                    </li>
                </ul>
            </li>
        </ul>
    </header>
    <aside id="sidebar_left" class="nano nano-light affix">
        <div class="sidebar-left-content nano-content">
            <header class="sidebar-header">
                <div class="sidebar-widget author-widget">
                    <div class="media">
                        <a class="media-left" href="#">

                        </a>
                        <div class="media-body">
                            <div class="media-author" style="font-size: 16px">姓名：${employee.name}</div>
                            <div class="media-author" style="font-size: 16px">职务：${employee.post}</div>
                            <div class="media-links">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="sidebar-widget search-widget hidden">
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="fa fa-search"></i>
                        </span>
                        <input type="text" id="sidebar-search" class="form-control" placeholder="Search...">
                    </div>
                </div>
            </header>
            <br>
            <ul class="nav sidebar-menu">


                <li>
                    <a class="accordion-toggle" href="#">
                        <span >&nbsp;&nbsp;</span>
                        <span class="sidebar-title" style="font-size: 16px">个人信息管理</span>
                        <span class="caret"></span>
                    </a>

                    <ul class="nav sub-nav">
                        <li>
                            <a href="/self">
                                <span class="sidebar-title" style="font-size: 16px"></span> 个人信息 </a>
                        </li>
                        <li >
                            <a href="/to_change_password">
                                <span class="sidebar-title" style="font-size: 16px"></span> 修改密码</a>
                        </li>
                    </ul>
                </li>
                <hr>
               <li>
                <a class="accordion-toggle" href="#">
                    <span >&nbsp;&nbsp;</span>
                    <span class="sidebar-title" style="font-size: 16px">报销单处理</span>
                    <span class="caret"></span>
                </a>
                <ul class="nav sub-nav">
                    <li>
                        <a href="/claim_voucher/deal">
                            <span ></span>
                            <span class="sidebar-title" > 需处理报销单</span>
                        </a>
                    </li>
                    <li >
                        <a href="/claim_voucher/self">
                            <span ></span>
                            <span class="sidebar-title" >个人报销单</span>
                        </a>
                    </li>
                    <li>
                        <a href="/claim_voucher/to_add">
                            <span></span>
                            <span class="sidebar-title" >申请报销</span>
                        </a>
                </ul>
               </li>
                <c:if test="${employee.post==Contant.POST_GM ||employee.post==Contant.POST_FM}">
                    <hr>
                <li>
                    <a class="accordion-toggle" href="#">
                        <span >&nbsp;&nbsp;</span>
                        <span class="sidebar-title" style="font-size: 16px">员工管理</span>
                        <span class="caret"></span>
                    </a>
                    <ul class="nav sub-nav" style="list-style: circle">
                        <li>
                            <a href="/employee/list">
                                <span style="font-size: 16px"></span> 员工列表 </a>
                        </li>
                        <li >
                            <a href="/employee/to_add">
                                <span style="font-size: 16px"></span> 添加员工 </a>
                        </li>
                    </ul>
                </li>
                </c:if>
                <c:if test="${employee.post==Contant.POST_GM}"    >
                    <hr>
                <li>
                    <a class="accordion-toggle" href="#">
                        <span >&nbsp;&nbsp;</span>
                        <span class="sidebar-title" style="font-size: 16px">部门管理</span>
                        <span class="caret"></span>
                    </a>
                    <ul class="nav sub-nav">
                        <li>
                            <a href="/department/list">
                                <span style="font-size: 16px"></span> 部门列表 </a>
                        </li>
                        <li >
                            <a href="/department/to_add">
                                <span style="font-size: 16px"></span> 添加部门 </a>
                        </li>
                    </ul>
                </li></c:if>
            </ul>
        </div>
    </aside>
