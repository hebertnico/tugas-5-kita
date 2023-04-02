package id.co.mii.serverapp.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String passw;
}
