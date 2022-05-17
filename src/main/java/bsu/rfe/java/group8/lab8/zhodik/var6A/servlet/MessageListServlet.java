package bsu.rfe.java.group8.lab8.zhodik.var6A.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bsu.rfe.java.group8.lab8.zhodik.var6A.entity.ChatMessage;

@WebServlet(name = "MessageListServlet")
public class MessageListServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");
        PrintWriter pw = response.getWriter();

        pw.println("<html>" +
                "<head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><meta http-equiv='refresh' content='10'>" +
                "</head>");
        pw.println("<body>");

        pw.println("<div><strong>" + (String) request.getSession().getAttribute("name") + ": Online" + "</strong></div>");

        for (int i = messages.size() - 1; i >= 0; i--) {
            ChatMessage aMessage = messages.get(i);
            String priv = messages.get(i).getPrivatem();
            System.out.println(priv);
            String auth = messages.get(i).getAuthor().getName();
            String uname = (String) request.getSession().getAttribute("name");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            Date date = new Date(aMessage.getTimestamp());


            if (priv != null) {
                if (priv.equals(uname) || auth.equals(uname)) {

                        pw.println("<div><strong>" + aMessage.getAuthor().getName() + "[Privat]"
                                + "</strong>: "  + aMessage.getMessage()+ "</div> ");
                }
                else if(aMessage.getMessage().equals("UserRemoveFromChat")){
                    pw.println("<div>Пользователь <strong>" + auth +"</strong> вышел из чата" + "</div>");
                }
                } else {
                pw.println("<div><strong>" + aMessage.getAuthor().getName() + "(" + sdf.format(date) + ")"
                        + "</strong>: " + aMessage.getMessage() + "</div>");
                 }
            pw.println("</body></html>");
        }
    }
}
