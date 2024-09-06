package br.com.fatcode;

import br.com.fatcode.dao.UserDAO;
import br.com.fatcode.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.findUserByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                // Criar uma sessão para o usuário autenticado
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Redirecionar com base no papel do usuário
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect("admin/list-users"); // Redireciona para a página com a tabela de usuários para ADMIN
                } else {
                    response.sendRedirect("products.html"); // Página para o cliente
                }
            } else {
                // Usuário não encontrado ou senha incorreta
                response.sendRedirect("login.html?error=Usuário ou senha incorretos.");
            }

        } catch (SQLException e) {
            response.sendRedirect("login.html?error=Erro ao autenticar o usuário. Tente novamente.");
        }
    }
}
