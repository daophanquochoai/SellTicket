package doctorhoai.learn.notificationservice.service.impl;

import com.cloudinary.Cloudinary;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import doctorhoai.learn.basedomain.Event.TicketEmail;
import doctorhoai.learn.notificationservice.service.inter.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailSender;
    private LocalDateTime time = LocalDateTime.now();
    private final Cloudinary cloudinary;

    @Override
    public void sendMail(TicketEmail ticketEmail) {
        try{
            BufferedImage qrImage = generateQRCodeImage(ticketEmail.toString());
            // chuyen thanh mang byte
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            // upload
            String url = cloudinary.uploader()
                    .upload(imageBytes, Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url").toString();

            //tao string message
            StringBuilder messageData = new StringBuilder("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Ticket Details</title>\n" +
                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                    "    <script src=\"https://unpkg.com/@tailwindcss/browser@4\"></script>\n" +
                    "</head>\n" +
                    "<body class=\"container mt-5\">\n" +
                    "    <h2 class=\"text-center mb-4\">Ticket Details</h2>\n" +
                    "    \n" +
                    "    <div class=\"card p-4\">\n" +
                    "          <h4>Thông tin vé</h4>\n" +
                    "          <p><strong>Tên phim:</strong>" + ticketEmail.getNameFilm() + "</p>\n" +
                    "          <p><strong>Phòng chiếu:</strong> " + ticketEmail.getNameRoom() + "</p>\n" +
                    "          <p><strong>Thời gian:</strong> " + ticketEmail.getTimeStart() + " - " + ticketEmail.getTimeEnd() + " (Ngày:" + ticketEmail.getTimeStampSee() + ")</p>\n" +
                    "          <p><strong>Mã giao dịch:</strong> " + ticketEmail.getTransactionCode() + "</p>\n" +
                    "          <p><strong>Phương thức thanh toán:</strong> " + ticketEmail.getPaymentMethod() + "</p>\n" +
                    "          <p><strong>Tổng tiền:</strong> " + ticketEmail.getTotalPrice() + " VND</p>\n" +
                    "          <p><strong>Khách hàng:</strong> " + ticketEmail.getUserName() + "</p>\n" +
                    "          <p><strong>Email:</strong> " + ticketEmail.getEmail() + "</p>\n" +
                    "          <p><strong>Số điện thoại:</strong> " + ticketEmail.getNumberPhone() + "</p>\n" +
                    "          <div class=\"flex items-end gap-4\">\n" +
                    "             <p><strong>QRcode:</strong></p>\n" +
                    "             <img src=\"" + url +  "\"/>\n" +
                    "          </div>\n" +
                    "    </div>\n" +
                    "    \n" +
                    "    <h4 class=\"mt-4\">Danh sách ghế</h4>\n" +
                    "    <table class=\"table table-bordered\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th>Ghế</th>\n" +
                    "                <th>Loại vé</th>\n" +
                    "                <th>Giá</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>");
            ticketEmail.getChairs().forEach( item -> {
                messageData.append("<tr>\n" +
                        "                <td>" + item.getChairCode() + "</td>\n" +
                        "                <td> " + item.getName() + "</td>\n" +
                        "                <td>" + item.getPrice() + " VND</td>\n" +
                        "            </tr>");
            });
            messageData.append(" </tbody>\n" +
                    "    </table>");
            if( ticketEmail.getDishes().size() > 0 ){
                messageData.append("<h4 class=\"mt-4\">Danh sách món ăn</h4>\n" +
                        "    <table class=\"table table-bordered\">\n" +
                        "        <thead>\n" +
                        "            <tr>\n" +
                        "                <th>Món ăn</th>\n" +
                        "                <th>Loại</th>\n" +
                        "                <th>Số lượng</th>\n" +
                        "                <th>Giá</th>\n" +
                        "            </tr>\n" +
                        "        </thead>\n" +
                        "        <tbody>");
                ticketEmail.getDishes().forEach( item -> {
                    messageData.append("<tr>\n" +
                            "                <td>" + item.getName() + "</td>\n" +
                            "                <td>" + item.getTypeDish() + "</td>\n" +
                            "                <td>" + item.getAmount() + "</td>\n" +
                            "                <td>" + item.getPrice() * item.getAmount() + " VND</td>\n" +
                            "            </tr>");
                });
                messageData.append(" </tbody>\n" +
                        "    </table>");
            }
            messageData.append("  <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n" +
                    "</body>\n" +
                    "</html>");



            //gui mail
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(emailSender);
            helper.setTo(ticketEmail.getEmail());
            helper.setSubject("Vé xem phim của bạn");
            helper.setText(String.valueOf(messageData), true);
            log.info("Sending email.....");
            javaMailSender.send(message);
            log.info("Sended email!!");
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    private static BufferedImage generateQRCodeImage(String data) throws Exception {
        int width = 300;
        int height = 300;
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
