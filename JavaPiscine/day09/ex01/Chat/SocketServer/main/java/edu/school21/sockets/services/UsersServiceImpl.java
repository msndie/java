package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private UsersRepositoryImpl usersRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier("encoder")
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    @Qualifier("usersRep")
    public void setUsersRepository(UsersRepositoryImpl usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean signUp(String name, String password) {
        return usersRepository.save(new User(null, name, bCryptPasswordEncoder.encode(password)));
    }

    @Override
    public boolean signIn(String name, String password) {
        Optional<User> user = usersRepository.findByName(name);
        return user.filter(value -> bCryptPasswordEncoder.matches(password, value.getPassword())).isPresent();
    }

    public Long getIdByName(String name) {
        Optional<User> user = usersRepository.findByName(name);
        return user.map(User::getId).orElse(null);
    }

    public boolean isExist(String name) {
        Optional<User> user;
        user = usersRepository.findByName(name);
        return user.isPresent();
    }
}
