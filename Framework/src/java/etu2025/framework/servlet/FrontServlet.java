/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu2025.framework.servlet;

import etu2025.framework.Mapping;
import etu2025.framework.ModelView;
import etu2025.framework.annotation.url;
import etu2025.framework.util.Utils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tiavi
 */
public class FrontServlet extends HttpServlet {
    private HashMap<String, Mapping> MappingUrls;
    private ArrayList<Class<?>> list_class;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        String package_model = config.getInitParameter("model-package");
        setListClass(new ArrayList<>());
        setMappingUrls(package_model);
    }
    
     
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet Frontservlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>URL at " + getURL(request) + "</h1>");
            String url = getURL(request);
            Object model_view = executeController(url);
            dispatch(request, response, model_view);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void dispatch(HttpServletRequest request, HttpServletResponse response, Object model_view) throws Exception {
        if (model_view instanceof ModelView modelView) {
            try {
                dispatch(request, response, modelView);
            } catch (ServletException | IOException e) {
                throw e;
            }
        }
        throw new Exception("The controller's method must return a ModelView");
    }
    
    private void dispatch(HttpServletRequest request, HttpServletResponse response, ModelView model_view) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(model_view.getView());
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    private Object executeController(String url) throws Exception {
        Object model_view = null;
        Class<?> controller = findController(url);
        System.out.println(controller.getName());
        Method controller_method = findMethodController(controller, url);
        Object[] controller_parameter = new Object[0];
        model_view = controller_method.invoke(controller.newInstance(), controller_parameter);
        return model_view;
    }
    
    private Method findMethodController(Class<?> c, String url) throws Exception {
        for (Method m : c.getDeclaredMethods()) {
            if (m.getName().equals(getMappingUrls().get(url).getMethod())){
                return m;
            }
        }
        throw new Exception("Method not found");
    }
        
    private Class findController(String url) throws Exception {
        List<Class<?>> lc = getListClass();
        for (Class<?> c : lc) {
            if (c.getSimpleName().equals(getMappingUrls().get(url).getClassName())) {
                for (Method m : c.getDeclaredMethods()) {
                    if (m.getName().equals(getMappingUrls().get(url).getMethod())){
                        return c;
                    }
                }
            }
        }
        throw new Exception("Controller not found");
    }
    
    private String getURL(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        return requestURI.split(contextPath)[1];
    }

    public HashMap<String, Mapping> getMappingUrls() {
        return MappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> MappingUrls) {
        this.MappingUrls = MappingUrls;
    }
    
    public void setMappingUrls(String path) {
        try {
            List<Class> lc = Utils.getClassFrom(path);
            setMappingUrls(new HashMap<>());
            for (Class c : lc) {
                for (Method m : c.getDeclaredMethods()) {
                    url u = m.getAnnotation(url.class);
                    if (u  != null) {
                       getMappingUrls().put(u.value() , new Mapping(c.getSimpleName(), m.getName()));
                    }
                }
                getListClass().add(c);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Class<?>> getListClass() {
        return this.list_class;
    }
    public void setListClass(ArrayList<Class<?>> list_class) {
        this.list_class = list_class;
    }
    
}
