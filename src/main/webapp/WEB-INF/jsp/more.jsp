<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8" />
    <title>База данных графов</title>
    <!--<link th:href="@{/webjars/bootstrap/3.0.3/css/bootstrap.min.css}" rel="stylesheet" media="all"/>-->
    <!--<link th:href="@{/styles.css}" rel="stylesheet" media="all"/>-->
<head >
    <style>

        /* === List Styles === */
        .contact_form ul {
            width:750px;
            list-style-type:none;
            list-style-position:outside;
            margin:0px;
            padding:0px;
        }
        .contact_form li{
            padding:12px;
            border-bottom:1px solid #eee;
            position:relative;
        }
        .contact_form li:first-child, .contact_form li:last-child {
            border-bottom:1px solid #777;
        }

        /* === Form Header === */
        .contact_form h2 {
            margin:0;
            display: inline;
        }
        .required_notification {
            color:#d45252;
            margin:5px 0 0 0;
            display:inline;
            float:right;
        }

        /* === Form Elements === */
        .contact_form label {
            width:150px;
            margin-top: 3px;
            display:inline-block;
            float:left;
            padding:3px;
        }
        .contact_form input {
            height:20px;
            width:220px;
            padding:5px 8px;
        }
        .contact_form textarea {padding:8px; width:300px;}
        .contact_form button {margin-left:156px;}

        /* form element visual styles */
        .contact_form input, .contact_form textarea {
            border:1px solid #aaa;
            box-shadow: 0px 0px 3px #ccc, 0 10px 15px #eee inset;
            border-radius:2px;
            padding-right:30px;
            -moz-transition: padding .25s;
            -webkit-transition: padding .25s;
            -o-transition: padding .25s;
            transition: padding .25s;
        }
        .contact_form input:focus, .contact_form textarea:focus {
            background: #fff;
            border:1px solid #555;
            box-shadow: 0 0 3px #aaa;
        }

        /* === HTML5 validation styles === */
        .contact_form input:required, .contact_form textarea:required {
            background: #fff url(images/red_asterisk.png) no-repeat 98% center;
        }
        .contact_form input:required:valid, .contact_form textarea:required:valid {
            background: #fff url(images/valid.png) no-repeat 98% center;
            box-shadow: 0 0 5px #5cd053;
            border-color: #28921f;
        }
        .contact_form input:focus:invalid, .contact_form textarea:focus:invalid {
            background: #fff url(images/invalid.png) no-repeat 98% center;
            box-shadow: 0 0 5px #d45252;
            border-color: #b03535
        }

        /* === Form hints === */
        .form_hint {
            background: #d45252;
            border-radius: 3px 3px 3px 3px;
            color: white;
            margin-left:8px;
            padding: 1px 6px;
            z-index: 999; /* hints stay above all other elements */
            position: absolute; /* allows proper formatting if hint is two lines */
            display: none;
        }
        .form_hint::before {
            content: "\25C0";
            color:#d45252;
            position: absolute;
            top:1px;
            left:-6px;
        }
        .contact_form input:focus + .form_hint {display: inline;}
        .contact_form input:required:valid + .form_hint {background: #28921f;}
        .contact_form input:required:valid + .form_hint::before {color:#28921f;}

        button.submit {
            background-color: #68b12f;
            background: -webkit-gradient(linear, left top, left bottom, from(#68b12f), to(#50911e));
            background: -webkit-linear-gradient(top, #68b12f, #50911e);
            background: -moz-linear-gradient(top, #68b12f, #50911e);
            background: -ms-linear-gradient(top, #68b12f, #50911e);
            background: -o-linear-gradient(top, #68b12f, #50911e);
            background: linear-gradient(top, #68b12f, #50911e);
            border: 1px solid #509111;
            border-bottom: 1px solid #5b992b;
            border-radius: 3px;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            -ms-border-radius: 3px;
            -o-border-radius: 3px;
            box-shadow: inset 0 1px 0 0 #9fd574;
            -webkit-box-shadow: 0 1px 0 0 #9fd574 inset ;
            -moz-box-shadow: 0 1px 0 0 #9fd574 inset;
            -ms-box-shadow: 0 1px 0 0 #9fd574 inset;
            -o-box-shadow: 0 1px 0 0 #9fd574 inset;
            color: white;
            font-weight: bold;
            padding: 6px 20px;
            text-align: center;
            text-shadow: 0 -1px 0 #396715;
        }
        button.submit:hover {
            opacity:.85;
            cursor: pointer;
        }
        button.submit:active {
            border: 1px solid #20911e;
            box-shadow: 0 0 10px 5px #356b0b inset;
            -webkit-box-shadow:0 0 10px 5px #356b0b inset ;
            -moz-box-shadow: 0 0 10px 5px #356b0b inset;
            -ms-box-shadow: 0 0 10px 5px #356b0b inset;
            -o-box-shadow: 0 0 10px 5px #356b0b inset;

        }

        .btn-primary {
            color: #fff;
            background-color: #337ab7;
            border-color: #2e6da4;
        }
        .btn {
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: normal;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            -ms-touch-action: manipulation;
            touch-action: manipulation;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            background-image: none;
            border: 1px solid transparent;
            border-radius: 4px;
        }

        .form-group {
            margin-bottom: 15px;
        }
    </style>
</head>
</head>
<body>
<form method="get" action="graphsMore" class="contact_form" >
    <div class="form-group">
        <label for="sql">Запрос:</label>
        <textarea id="sql" name="sql"></textarea>
    </div>
    <button type="submit" class="btn btn-primary">Найти</button>
    <br/>

</form><span>
            Введите запрос в формате: <b>инвариант1</b> =(<,>) <b>значение1</b> and(or) <b>инвариант2</b> =(<,>) <b>значение2</b> и т.д.<br/>
            Возможные инварианты:<br/>
            <ul>
                <li>code - матричный код</li>
                <li>vertex - количество вершин</li>
                <li>edge - количество ребер</li>
                <li>edgeCon - реберная связность</li>
                <%--<li>vertcon - вершинная связность</li>--%>
                <li>isConnected - связность (значения 0 или 1)</li>
                <li>radius - радиус</li>
                <li>diameter - диаметр</li>
                <li>components - число компонент связности</li>
                <li>girth - обхват</li>
                <li>isPrimitive - примитивность (значения 0 или 1)</li>
                <li>exp - экспонент</li>
                <li>isBipartite - двудольность (значения 0 или 1)</li>
            </ul>
        </span>
<script src="@{/webjars/jquery/2.0.3/jquery.min.js}"></script>
<script src="@{/webjars/bootstrap/3.0.3/js/bootstrap.min.js}"></script>
</body>
</html>