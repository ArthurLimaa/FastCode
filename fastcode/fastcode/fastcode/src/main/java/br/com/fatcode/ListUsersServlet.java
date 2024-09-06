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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/list-users")
public class ListUsersServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ListUsersServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html"); // Redireciona para a página de login se não estiver autenticado
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Você não tem permissão para acessar esta página.");
            return;
        }

        UserDAO userDAO = new UserDAO();
        try {
            // Recuperar todos os usuários do banco de dados
            List<User> users = userDAO.findAllUsers(); // Use findAllUsers para recuperar todos os usuários

            // Definir os usuários como um atributo da requisição
            request.setAttribute("users", users);

            // Encaminhar para o JSP que exibe a tabela
            request.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(request, response);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao buscar usuários", e);
            request.setAttribute("error", "Erro ao buscar usuários: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
