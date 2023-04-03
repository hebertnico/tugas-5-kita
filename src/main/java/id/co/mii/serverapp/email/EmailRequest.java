package id.co.mii.serverapp.email;

import java.util.Map;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailRequest {
    private Map<String, Object> props;
}
