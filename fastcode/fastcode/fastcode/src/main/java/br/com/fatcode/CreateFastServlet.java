package br.com.fatcode;

import br.com.fatcode.dao.UserDAO;
import br.com.fatcode.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/create-user")
public class CreateFastServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CreateFastServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperar parâmetros do formulário
        String username = request.getParameter("name");
        String email = request.getParameter("email");
        String birthdate = request.getParameter("birthdate");
        String cpf = request.getParameter("cpf");
        String address = request.getParameter("address");
        String cep = request.getParameter("cep");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "As senhas não coincidem.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Validar a data de nascimento
        LocalDate parsedBirthdate;
        try {
            parsedBirthdate = LocalDate.parse(birthdate);
        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Data de nascimento inválida.\"}");
            return;
        }

        // Validação de e-mail
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Formato de e-mail inválido.\"}");
            return;
        }

        // Definir a função do usuário
        String role = "CLIENT"; // Papel padrão
        if (email.equals("admin@example.com")) {
            role = "ADMIN";
        }

        // Criar o objeto User com todos os campos
        User user = new User(username, password, email, parsedBirthdate, cpf, address, cep, role);

        // Salvar no banco de dados
        try {
            new UserDAO().createUser(user);
            response.sendRedirect("index.html");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar o usuário", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro ao criar o usuário. Tente novamente mais tarde.\"}");
        }
    }
}
