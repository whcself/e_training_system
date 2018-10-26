package com.csu.etrainingsystem.user.service;

import com.csu.etrainingsystem.user.entity.User;
import com.csu.etrainingsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public void changePassword(HttpSession session,String newPassword){
        User user= (User) session.getAttribute("user");
        user.setPassword(newPassword);
        userRepository.saveAndFlush(user);
    }

    public User getUser(String id){
        return userRepository.getOne(id);
    }


}
