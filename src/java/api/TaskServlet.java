package api;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import model.Task;
import org.json.JSONObject;
import org.json.JSONArray;


@WebServlet(urlPatterns = {"/tasks"})
public class TaskServlet extends HttpServlet {
    
private JSONObject getJSONBody(BufferedReader reader) throws Exception{
    StringBuilder buffer = new StringBuilder();
    String line = null;
    while ((line = reader.readLine())!=null) {
        buffer.append(line);
     
    }
    return new JSONObject(buffer.toString());    
}

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        response.setContentType("application/json;charset=UTF-8");
        JSONObject file = new JSONObject();
        try {
          file.put("list", new JSONArray(Task.list));
            
        }catch(Exception ex){
            response.setStatus(500);
            file.put("error", ex.getLocalizedMessage());
            
           
        }
           response.getWriter().print(file.toString());
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject file = new JSONObject();
        try {
            
            JSONObject body = getJSONBody (request.getReader());
            String title = body.getString("title");
            if(title !=null) {
                Task t = new Task(title);
                Task.list.add(t);
            }
            file.put("list", new JSONArray(Task.list));
            
        }catch(Exception ex){
            response.setStatus(500);
            file.put("error", ex.getLocalizedMessage());             
        }
        response.getWriter().print(file.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject file = new JSONObject();
        try {
            String title = request.getParameter("title");
            int i = -1;
            for(Task t :Task.list) {
                if(t.getTitle().equals(title)){
                    i = Task.list.indexOf(t);
                    break;
                }
                
            }
            Task.list.remove(i);
            file.put("list", new JSONArray(Task.list));
            
        }catch(Exception ex){
            response.setStatus(500);
            file.put("error", ex.getLocalizedMessage());
            
           
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
