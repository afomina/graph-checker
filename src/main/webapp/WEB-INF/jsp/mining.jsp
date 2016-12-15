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
</head>
<body>
<form method="get" action="mine" class="contact_form" >
  <div class="form-group">
    <label for="sql">Условие для графов:</label>
    <textarea id="sql" name="sql"></textarea>

    <label>Основной инвариант</label>
    <select name="main" >
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
    <=
    <label>Вспомогательный инвариант1</label>
    <select name="a" >
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
    <label>Вспомогательный инвариант2</label>
    <select name="b" >
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