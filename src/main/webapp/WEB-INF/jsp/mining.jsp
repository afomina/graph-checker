<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <!--<meta charset="UTF-8">-->
  <title>Дата майнинг</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
        integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
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

    /* === Form Elements === */
    .contact_form label {
      width:150px;
      margin-top: 3px;
      display:inline-block;
    }
    .contact_form input {
      height:20px;
      width:220px;
      padding:5px 8px;
    }
    .contact_form textarea {padding:8px; width:300px;}
    .contact_form button {margin-left:156px;}

    /* form element visual styles */
    .contact_form input, .contact_form textarea, .contact_form select {
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

    </style>
</head>
<body>
<form method="get" action="mine" class="contact_form">
  <div class="form-group">
    <label for="sql">Условие для графов:</label>
    <input type="text" id="sql" name="sql"/>
    <span class="form_hint">
      Введите запрос в формате: <b>инвариант1</b> =(<,>) <b>значение1</b> and(or) <b>инвариант2</b> =(<,>) <b>значение2</b> и т.д.<br/>
            Возможные инварианты:<br/>
            <ul>
              <li>code - матричный код</li>
              <li>vertex - количество вершин</li>
              <li>edge - количество ребер</li>
              <li>edgeCon - реберная связность</li>
              <li>vertCon - вершинная связность</li>
              <li>isConnected - связность (значения 0 или 1)</li>
              <li>radius - радиус</li>
              <li>diameter - диаметр</li>
              <li>components - число компонент связности</li>
              <li>girth - обхват</li>
              <li>isPrimitive - примитивность (значения 0 или 1)</li>
              <li>exp - экспонент</li>
              <li>isBipartite - двудольность (значения 0 или 1)</li>
              <li>chromaticNum - хроматическое число</li>
              <li>independenceNum - число независимости</li>
            </ul>
    </span>
    </div>

  <div class="form-group">
    <label for="main">Основной инвариант</label>
    <select name="main" id="main">
      <option></option>
      <option value="0">Хроматическое число</option>
      <option value="1">Число компонент связности</option>
      <option value="2">Диаметр</option>
      <option value="3">Реберная связность</option>
      <option value="4">Экспонент</option>
      <option value="5">Обхват</option>
      <option value="6">Радиус</option>
      <option value="7">Вершинная связность</option>
      <option value="8">Количество вершин</option>
      <option value="9">Количество ребер</option>
      <option value="10">Число независимости</option>
    </select>
    </div>

  <div class="form-group">
    <label for="a">Вспомогательный инвариант1</label>
    <select name="a" id="a">
      <option></option>
      <option value="0">Хроматическое число</option>
      <option value="1">Число компонент связности</option>
      <option value="2">Диаметр</option>
      <option value="3">Реберная связность</option>
      <option value="4">Экспонент</option>
      <option value="5">Обхват</option>
      <option value="6">Радиус</option>
      <option value="7">Вершинная связность</option>
      <option value="8">Количество вершин</option>
      <option value="9">Количество ребер</option>
      <option value="10">Число независимости</option>
    </select>
    </div>

  <div class="form-group">
    <label for="b">Вспомогательный инвариант2</label>
    <select name="b" id="b">
      <option></option>
      <option value="0">Хроматическое число</option>
      <option value="1">Число компонент связности</option>
      <option value="2">Диаметр</option>
      <option value="3">Реберная связность</option>
      <option value="4">Экспонент</option>
      <option value="5">Обхват</option>
      <option value="6">Радиус</option>
      <option value="7">Вершинная связность</option>
      <option value="8">Количество вершин</option>
      <option value="9">Количество ребер</option>
      <option value="10">Число независимости</option>
    </select>
  </div>
  <button type="submit" class="btn btn-primary">Найти</button>
  <br/>
</form>
</body>
</html>