package bsu.rfe.java.group8.lab8.zhodik.var6A.servlet;

import bsu.rfe.java.group8.lab8.zhodik.var6A.entity.ChatMessage;
import bsu.rfe.java.group8.lab8.zhodik.var6A.entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "ChatServlet")
public class ChatServlet extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = 1L;
    protected HashMap<String, ChatUser> activeUsers;
    protected ArrayList<ChatMessage> messages;

    @SuppressWarnings("unchecked")
    public void init() throws ServletException {//
        super.init();
        activeUsers = (HashMap<String, ChatUser>)
                getServletContext().getAttribute("activeUsers");
        messages = (ArrayList<ChatMessage>)
                getServletContext().getAttribute("messages");
        if (activeUsers==null) {
            activeUsers = new HashMap<String, ChatUser>();
            getServletContext().setAttribute("activeUsers",
                    activeUsers);
        }
        // Если список сообщений ещѐ не определѐн
        if (messages==null) {
            // Создать новый список
            messages = new ArrayList<ChatMessage>(100);
            // Поместить его в контекст сервлета,
// чтобы другие сервлеты могли до него добраться
            getServletContext().setAttribute("messages", messages);
        }
    }


}
