package dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mpuscas
 */
@Data
@EqualsAndHashCode
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}