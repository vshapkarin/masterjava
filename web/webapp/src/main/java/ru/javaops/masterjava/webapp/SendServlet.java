package ru.javaops.masterjava.webapp;

import lombok.extern.slf4j.Slf4j;
import ru.javaops.masterjava.service.mail.GroupResult;
import ru.javaops.masterjava.service.mail.MailWSClient;
import ru.javaops.masterjava.webapp.data.InputStreamDataSource;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/send")
@MultipartConfig
@Slf4j
public class SendServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result;
        try {
            log.info("Start sending");
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            String users = req.getParameter("users");
            String subject = req.getParameter("subject");
            String body = req.getParameter("body");

            List<DataHandler> attachments = new ArrayList<>();
            List<String> attachmentNames = new ArrayList<>();
            List<Part> fileParts = req.getParts().stream()
                    .filter(p -> "attach".equals(p.getName()) && p.getSize() > 0)
                    .collect(Collectors.toList());
            for (Part filePart : fileParts) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                attachmentNames.add(fileName);
                DataSource dataSource = new InputStreamDataSource(filePart.getInputStream());
                attachments.add(new DataHandler(dataSource));
            }

            GroupResult groupResult = MailWSClient.sendBulk(MailWSClient.split(users), subject, body, attachments, attachmentNames);
            result = groupResult.toString();
            log.info("Processing finished with result: {}", result);
        } catch (Exception e) {
            log.error("Processing failed", e);
            result = e.toString();
        }
        resp.getWriter().write(result);
    }
}
