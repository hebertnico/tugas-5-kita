package id.co.mii.serverapp.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import id.co.mii.serverapp.registration.token.ConfirmService;
import id.co.mii.serverapp.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email %s not found";
    private final AppUserRepo aurepo;
    private final BCryptPasswordEncoder encoder;
    private final ConfirmService confService;

    @Override
    public UserDetails loadUserByUsername(String s)
            throws UsernameNotFoundException {
        return aurepo.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, s)));
    }

    public String signUp(AppUser aUser) {
        boolean exists = aurepo.findByEmail(aUser.getEmail()).isPresent();
        if (exists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPass = encoder.encode(aUser.getPassw());
        aUser.setPassw(encodedPass);
        aurepo.save(aUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken conftoken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                aUser);

        confService.saveToken(conftoken);

        // send email
        return token;
    }

    public int enableUser(String email) {
        return aurepo.enableUser(email);
    }
}
