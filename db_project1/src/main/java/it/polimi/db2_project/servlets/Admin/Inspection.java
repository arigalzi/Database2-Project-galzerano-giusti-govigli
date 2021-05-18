package it.polimi.db2_project.servlets.Admin;

import com.google.gson.Gson;
import it.polimi.db2_project.auxiliary.jsonContent.InspectionPageUserContent;
import it.polimi.db2_project.entities.Answer;
import it.polimi.db2_project.entities.Product;
import it.polimi.db2_project.services.AnswerService;
import it.polimi.db2_project.services.ProductService;
import it.polimi.db2_project.services.UserService;
import org.apache.commons.lang.StringEscapeUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

@WebServlet("/Inspection")
@MultipartConfig
public class Inspection extends HttpServlet {
    @EJB(name = "it.polimi.db2_project.entities.services/ProductService")
    private ProductService productService;

    @EJB(name = "it.polimi.db2_project.entities.services/AnswerService")
    private AnswerService answerService;

    @EJB(name = "it.polimi.db2_project.entities.services/UserService")
    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    boolean checkDate (Date date) {
        return java.sql.Date.valueOf(LocalDate.now()).after(date)  || java.sql.Date.valueOf(LocalDate.now()).equals(date);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDate = request.getParameter("date");
        if(sDate.equals("")) {
            response.setStatus(403);
            return;
        }
        Date date= null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        if(checkDate(date)) {
            try {

                Product product;
                List<String> usersWhoSubmitted, usersWhoCanceled;
                List<InspectionPageUserContent> content = new ArrayList<>();
                product = productService.checkDateAvailability(date);

                if (product != null) {
                    usersWhoSubmitted = userService.getUsersWhoSubmits(product);
                    usersWhoCanceled = userService.getUsersWhoCanceled(product);

                    if (!(usersWhoSubmitted == null && usersWhoCanceled==null )) {
                        content = createContent(usersWhoSubmitted,usersWhoCanceled,product);
                    }
                    // As a last element we send the product
                    content.add(new InspectionPageUserContent(null,null,null,null,product));
                    response.setStatus(HttpServletResponse.SC_OK);

                }
                else{
                    response.setStatus(403);
                }
                String jsonResponse = new Gson().toJson(content);
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.write(jsonResponse);


            }catch (Exception e) {
                sendError(request, response, "Inspection Error", e.getCause().getMessage());
            }
        }
        else{
            response.setStatus(400);
        }
    }

    /**
     * Method to handle errors, redirects to an error page
     * @param request request
     * @param response response
     * @param errorType type of error
     * @param errorInfo information about the error
     * @throws IOException if there are problems redirecting
     */
    protected void sendError(HttpServletRequest request, HttpServletResponse response, String errorType, String errorInfo) throws IOException {
        request.getSession().setAttribute ("errorType", errorType);
        request.getSession().setAttribute ("errorInfo", errorInfo);
        try {
            getServletConfig().getServletContext().getRequestDispatcher("/error.html").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public List<InspectionPageUserContent> createContent(List<String> usersWhoSubmitted,List<String> usersWhoCanceled,Product product){
        List<InspectionPageUserContent> content = new ArrayList<>();
        List<String> questions;
        List<Answer> answers;
        boolean isCanceled = false;
        if (usersWhoSubmitted != null) {
            for (String username : usersWhoSubmitted) {
                if (usersWhoCanceled != null && usersWhoCanceled.contains(username))
                    isCanceled = true;
                questions = userService.getAnsweredQuestions(product, username);
                answers = answerService.getUserAnswers(product, username);
                InspectionPageUserContent userContent = new InspectionPageUserContent(username, isCanceled, answerService.getAnswerText(answers), questions,null);
                content.add(userContent);
                isCanceled = false;

            }
        }
        else if(usersWhoCanceled!=null) {
            for (String username : usersWhoCanceled) {
                InspectionPageUserContent userContent = new InspectionPageUserContent(username, true, null, null, null);
                content.add(userContent);
            }
        }
        return content;
    }
}