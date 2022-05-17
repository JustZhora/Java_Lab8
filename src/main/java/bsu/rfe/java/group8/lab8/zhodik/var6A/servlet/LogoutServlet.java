package bsu.rfe.java.group8.lab8.zhodik.var6A.servlet;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bsu.rfe.java.group8.lab8.zhodik.var6A.entity.ChatMessage;
import bsu.rfe.java.group8.lab8.zhodik.var6A.entity.ChatUser;

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("name");
        if (name!=null) {
            ChatUser aUser = activeUsers.get(name);
            if (aUser!=null && aUser.getSessionId().equals((String)request.getSession().getId())) {
                synchronized (activeUsers) {
                    activeUsers.remove(name);
                }
                synchronized(messages){
                    messages.add(new ChatMessage("UserRemoveFromChat", activeUsers,
                            Calendar.getInstance().getTimeInMillis()));
                }
                request.getSession().setAttribute("name", null);
                response.addCookie(new Cookie("sessionId", null));
                response.sendRedirect(response.encodeRedirectURL("/lab_8_war_exploded/"));
            } else {
                response.addCookie(new Cookie("sessionId", null));
                response.sendRedirect(response.encodeRedirectURL("/lab_8_war_exploded/view.html"));
            }
        } else {
            response.sendRedirect(response.encodeRedirectURL("/lab_8_war_exploded/view.html"));
        }
    }
}
