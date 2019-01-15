package net.pannenko.cicd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Value("${message:Hello default}")
    private String configMessage;

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<Message> home() {
        List<Message> messages = messageService.findAll();
        Message message = null;
        if (!messages.isEmpty()) {
            message = messages.get(0);
            String upperCasedMessage = MessageUtil.toUpperCase(messages.get(0).getMessage());
            message.setMessage(upperCasedMessage + ' ' + configMessage);
        }

        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
