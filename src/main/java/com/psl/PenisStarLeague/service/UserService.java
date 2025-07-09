package com.psl.PenisStarLeague.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.model.User;
import com.psl.PenisStarLeague.repo.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String email, String sub, String name){
        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null){
                user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setSub(sub);
                return userRepository.save(user);
        }
        return user;    
    }


    /** 
     * return true if user name was able to be created for user
     */
    public boolean createUserName(String userName, int idUser){
        if(userRepository.findByUserName(userName).orElse(null) == null){
            User user = userRepository.findById(idUser).orElse(null); 
            if(user == null){
                return false;
            }   
            
            user.setUserName(userName);

            if(userRepository.save(user)!=null){
                return true; 
            }

            return false;
        }else{
            return false; // userName already exists 
        }

    }
}