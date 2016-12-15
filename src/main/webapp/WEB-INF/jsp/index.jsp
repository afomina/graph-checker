<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ru">
<head >
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
            width:250px;
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
            .contact_form button {margin-left:256px;}

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
<h2>Поиск графов с заданными параметрами</h2>
<form method="get" action="graphs" class="contact_form" >
    <div class="form-group">
        <label for="code">Код</label>
        <input id="code" name="code"/>
        <span class="form_hint">Матричный код в формате graph8</span>
    </div>
    <div class="form-group">
        <label for="order">n</label>
        <input id="order" name="order"/>
        <span class="form_hint">Количество вершин</span>
        </div>
    <div class="form-group">
        <label>m</label>
        <input id="e" name="edgeAmount"/>
        <span class="form_hint">Количество ребер</span>
        </div>
    <div class="form-group">
        <label for="c">Связный</label>
        <select id="c" name="connected">
            <option></option>
            <option value="1">Да</option>
            <option value="0">Нет</option>
        </select>
        </div>
    <div class="form-group">
        <label for="v">Вершинная связность</label>
        <input id="v" name="vertexConnectivity"/>
        <span class="form_hint">Наименьшее число вершин, удаление которых приводит к несвязному графу</span>
    </div>
    <div class="form-group">
        <label>Реберная связность</label>
        <input name="edgeConnectivity"/>
        <span class="form_hint">Наименьшее число ребер, удаление которых приводит к несвязному графу</span>
    </div>
    <div class="form-group">
        <label for="r">Радиус</label>
        <input id="r" name="radius"/>
        <span class="form_hint">Минимальный эксцентриситет</span>
    </div>
    <div class="form-group">
        <label>Диаметр</label>
        <input name="diametr"/>
        <span class="form_hint">Максимальный эксцентриситет</span>
    </div>
    <div class="form-group">
        <label>Число компонент связности</label>
        <input name="components"/>
    </div>
    <div class="form-group">
        <label for="gir">Обхват</label>
        <input id="gir" name="girth"/>
        <span class="form_hint">Длина наименьшего цикла</span>
    </div>
    <div class="form-group">
        <label>Примитивный</label>
        <select name="primitive">
            <option></option>
            <option value="1">Да</option>
            <option value="0">Нет</option>
        </select>
        <span class="form_hint">Существует ли целое число r ≥ 1 такое, что каждая вершина графа достижима из любой вершины за r шагов</span>
    </div>
    <div class="form-group">
        <label>Экспонент</label>
        <input name="exp"/>
        <!--<span class="form_hint"></span>-->
    </div>
    <div class="form-group">
        <label>Двудольный</label>
        <select name="twoPartial">
            <option></option>
            <option value="1">Да</option>
            <option value="0">Нет</option>
        </select>
        <span class="form_hint">Можно ли разделить его вершины на две доли так, чтобы не было ребер, соединяющих две вершины одной доли</span>
    </div>
    <div class="form-group">
        <label>Хроматическое число</label>
        <input name="chromeNumber"/>
        <!--<span class="form_hint"></span>-->
    </div>
     <div class="form-group">
            <label>Число независимости</label>
            <input name="independenceNumber"/>
            <!--<span class="form_hint"></span>-->
     </div>
    <button type="submit" class="btn btn-primary">Найти</button>
</form>
<a href="more" style="font-size: 20px;">Расширенный запрос</a>
<script src="@{/webjars/jquery/2.0.3/jquery.min.js}"></script>
<script src="@{/webjars/bootstrap/3.0.3/js/bootstrap.min.js}"></script>
</body>
</html>