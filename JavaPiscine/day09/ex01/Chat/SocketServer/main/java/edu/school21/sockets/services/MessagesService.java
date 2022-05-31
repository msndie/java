package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {
    private MessagesRepository repository;

    @Autowired
    @Qualifier("messagesRep")
    public void setRepository(MessagesRepository repository) {
        this.repository = repository;
    }

    public boolean save(Message message) {
        return repository.save(message);
    }
}
