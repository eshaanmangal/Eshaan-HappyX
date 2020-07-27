package com.xebia.happix.client;

import org.springframework.stereotype.Service;

@Service
public class MailClient {

//    private JavaMailSender mailSender;
//    private MailContentBuilder mailContentBuilder;
//
//    @Autowired
//    public MailClient(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
//        this.mailSender = mailSender;
//        this.mailContentBuilder = mailContentBuilder;
//    }
//
//    public void prepareAndSend(List<String> recipients, String message) {
//        CompletableFuture.runAsync(() -> {
//            recipients.stream().forEach(recipient -> {
//                MimeMessagePreparator messagePreparator = mimeMessage -> {
//                    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//                    messageHelper.setTo(recipient);
//                    messageHelper.setSubject("How are you feeling now ??");
//                    String content = mailContentBuilder.build(message, recipient);
//                    messageHelper.setText(content, true);
//                };
//                mailSender.send(messagePreparator);
//            });
//
//        }).exceptionally(exc -> {
//            exc.printStackTrace();
//            return null;
//        });
//
//    }

}
