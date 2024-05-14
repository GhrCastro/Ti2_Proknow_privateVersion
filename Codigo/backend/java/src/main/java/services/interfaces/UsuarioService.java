package services.interfaces;

import models.Usuario;
import dao.UsuarioDao;
import spark.Request;
import spark.Response;
import java.time.LocalDate;
import java.util.UUID;
import java.text.SimpleDateFormat;

public class UsuarioService {

    public void create(Request request, Response response){
        
        String name = request.queryParams("name");
        String cpf = request.queryParams("cpf");
        String email = request.queryParams("email");
        double salary = Double.parseDouble(request.queryParams("salary"));
        String cellNumber = request.queryParams("cellNumber");
        String password = request.queryParams("password");
        double expenses = Double.parseDouble(request.queryParams("expenses"));
        LocalDate regDate = LocalDate.parse(request.queryParams("regDate"));

        try {
            UUID id = UUID.randomUUID();
            UsuarioDao.insert(id,name,cpf,email,salary,cellNumber,password,expenses,regDate); 
            System.out.println("Usuário criado com sucesso!");
            response.status(200);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Erro ao cadastrar usuário" + e);
            response.status(402);
        }
    }
}