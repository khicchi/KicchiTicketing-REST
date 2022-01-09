package com.lespania.implementation;

import com.lespania.entity.User;
import com.lespania.entity.common.UserPrincipal;
import com.lespania.repository.UserRepository;
import com.lespania.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {


    private UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(s);

        if(user==null){
            throw new UsernameNotFoundException("This user does not exists");
        }

        return new UserPrincipal(user);
    }
}
