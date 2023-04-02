package id.co.mii.serverapp.registration;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistCtrl {

    private final RegistService rService;

    @PostMapping
    public String register(@RequestBody RegistRequest r) {
        return rService.register(r);
    }

    @GetMapping(value = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return rService.confirmtoken(token);
    }

}
