package se.artcomputer.f1.bingo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.artcomputer.f1.bingo.entity.Fan;
import se.artcomputer.f1.bingo.repository.FanRepository;


import java.util.Optional;

@Configuration
public class FanDetailsService implements UserDetailsService {
    @Autowired
    private FanRepository fanRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Fan> optionalFan = fanRepository.findByName(username);
        return optionalFan.map(FanDetails::new).orElseThrow(()->new UsernameNotFoundException("User Does Not Exist"));
    }
}
