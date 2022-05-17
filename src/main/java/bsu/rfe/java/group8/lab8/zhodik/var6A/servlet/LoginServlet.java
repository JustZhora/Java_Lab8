package bsu.rfe.java.group8.lab8.zhodik.var6A.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bsu.rfe.java.group8.lab8.zhodik.var6A.entity.ChatUser;

@WebServlet(name="LoginServlet")
public class LoginServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;
    private int sessionTimeout = 10*60;
    public void init() throws ServletException {
        super.init();
        String value = getServletConfig().getInitParameter("SESSION_TIMEOUT");
        if (value!=null) {
            sessionTimeout = Integer.parseInt(value);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String)request.getSession().getAttribute("name");
        String errorMessage = (String)request.getSession().getAttribute("error");
        String previousSessionId = null;
        if (name==null) {
            try {
                for (int i=0; i< request.getCookies().length; i++)
                   {
                    if (request.getCookies()[i].getName().equals("sessionId")) {
                        previousSessionId = request.getCookies()[i].getValue();
                        break;
                    }
                }
            }catch (NullPointerException e){
                name = null;
            }
            if (previousSessionId!=null) {
                for (ChatUser aUser: activeUsers.values()) {
                    if
                    (aUser.getSessionId().equals(previousSessionId)) {
                        name = aUser.getName();
                        aUser.setSessionId(request.getSession().getId());
                    }
                }
            }
        }

        if (name!=null && !"".equals(name)) {
            errorMessage = processLogonAttempt(name, request, response);
        }

        response.setCharacterEncoding("utf8");
        PrintWriter pw = response.getWriter();
        pw.println("<html><head><title>Mega chat!</title><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>");
        if (errorMessage!=null) {
            pw.println("<p><font color='red'>" + errorMessage + "</font></p>");
        }

        pw.println("<form action='/lab_8_war_exploded/' method='post'> Enter the name: <input type='text' name='name' value=''><input type='submit' value='Connect to the chat'>");
        pw.println("</form>  </body></html>");
        request.getSession().setAttribute("error", null);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = (String)request.getParameter("name");
        String errorMessage = null;
        if (name==null || "".equals(name)) {
            errorMessage = "Name cannot be empty!";
        } else {
            errorMessage = processLogonAttempt(name, request, response);
        }
        if (errorMessage!=null) {
            request.getSession().setAttribute("name", null);
            request.getSession().setAttribute("error", errorMessage);
            response.sendRedirect(response.encodeRedirectURL("/lab_8_war_exploded/"));
        }
    }
    String processLogonAttempt(String name, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = request.getSession().getId();
        ChatUser aUser = activeUsers.get(name);
        if (aUser==null) {
            aUser = new ChatUser(name, Calendar.getInstance().getTimeInMillis(), sessionId);
            synchronized (activeUsers) {
                activeUsers.put(aUser.getName(), aUser);
            }
        }
        if (aUser.getSessionId().equals(sessionId) || aUser.getLastInteractionTime()<(Calendar.getInstance().getTimeInMillis()- sessionTimeout*1000)) {
            request.getSession().setAttribute("name", name);
            aUser.setLastInteractionTime(Calendar.getInstance().getTimeInMillis());
            Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
            sessionIdCookie.setMaxAge(60);
            response.addCookie(sessionIdCookie);
            request.getSession().setAttribute("privatem", null);
            response.sendRedirect(response.encodeRedirectURL("/lab_8_war_exploded/view.html"));
            return null;
        } else {
            return "Sorry, <strong>" + name + "</strong> is engaged. Please try another one!";
        }
    }
}
