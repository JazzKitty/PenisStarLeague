package com.psl.PenisStarLeague.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.model.PSLUser;
import com.psl.PenisStarLeague.repo.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService   {
    private final UserRepository userRepository;

    public PSLUser getUser(String email, String sub, String name){
        PSLUser user = userRepository.findByEmail(email).orElse(null);

        if(user == null){
                user = new PSLUser();
                user.setName(name);
                user.setEmail(email);
                user.setSub(sub);
                return userRepository.save(user);
        }
        return user;    
    }

    public int getIdUser(Authentication authentication){        
        OAuth2IntrospectionAuthenticatedPrincipal prince = (OAuth2IntrospectionAuthenticatedPrincipal) authentication.getPrincipal();

        PSLUser user = userRepository.findByEmail(prince.getAttribute("email")).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("no user found with this email address: ");
        }
        return user.getIdUser(); 
    }

    /** 
     * return true if user name was able to be created for user
     */
    public boolean createUserName(String userName, String gamerTag, int idUser){
        if(userRepository.findByUserName(userName).orElse(null) == null){
            PSLUser user = userRepository.findByIdUser(idUser).orElse(null);
            if(user == null){
                return false;
            }   
            
            user.setUserName(userName);
            user.setGamerTag(gamerTag);

            if(userRepository.save(user)!=null){
                return true; 
            }

            return false;
        }else{
            return false; // userName already exists 
        }

    }

    /** 
     * return true if user name was able to be created for user
     */
    public boolean editUserName(String userName, int idUser){
        if(userRepository.findByUserName(userName).orElse(null) == null){
            PSLUser user = userRepository.findByIdUser(idUser).orElse(null);
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

    
    /** 
     * return true if user name was able to be created for user
     */
    public boolean editGamerTag(String gamerTag, int idUser){
        PSLUser user = userRepository.findByIdUser(idUser).orElse(null);
        if(user == null){
            return false;
        }   
        
        user.setGamerTag(gamerTag);

        if(userRepository.save(user)!=null){
            return true; 
        }

        return false;
    }

        /** 
     * return true if user name was able to be created for user
     */
    public boolean editBio(String bio, int idUser){
        PSLUser user = userRepository.findByIdUser(idUser).orElse(null);
        if(user == null){
            return false;
        }   
        
        user.setBio(bio);

        if(userRepository.save(user)!=null){
            return true; 
        }

        return false;
    }
}