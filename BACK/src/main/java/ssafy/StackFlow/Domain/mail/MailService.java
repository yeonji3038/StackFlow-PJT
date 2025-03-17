package ssafy.StackFlow.Domain.mail;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMail(String to, String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("메일 내용은 null이거나 비어 있을 수 없습니다.");
        }

        MimeMessage message = mailSender.createMimeMessage();


        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("매장 코드 안내"); // 고정된 제목
            helper.setText(content, true); // HTML 지원

            mailSender.send(message);
            System.out.println("메일 발송 성공!");
        } catch (MessagingException e) {
            System.err.println("메일 발송 실패: " + e.getMessage());
        }
    }

}
