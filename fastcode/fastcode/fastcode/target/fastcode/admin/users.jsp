<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Usuários</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

<h1>Lista de Usuários Cadastrados</h1>

<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>

<table>
    <thead>
    <tr>
        <th>Nome</th>
        <th>Email</th>
        <th>Data de Nascimento</th>
        <th>CPF</th>
        <th>Endereço</th>
        <th>CEP</th>
        <th>Papel</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.birthdate}</td>
            <td>${user.cpf}</td>
            <td>${user.address}</td>
            <td>${user.cep}</td>
            <td>${user.role}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
