package id.co.mii.serverapp.email;

import java.util.Map;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private Map<String, Object> props;

    public EmailRequest(String to) {
        this.to = to;
    }

}
