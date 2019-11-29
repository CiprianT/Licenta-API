package dto;

import lombok.Data;

/**
 * @author mpuscas
 */
@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}