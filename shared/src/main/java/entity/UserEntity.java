package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mpuscas + rmorea
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor

public class UserEntity implements UserDetails {

    public static final String ID = "USER_ID";
    private static final String USERNAME_COLUMN = "USERNAME";
    private static final String FIRST_NAME_COLUMN = "FIRST_NAME";
    private static final String LAST_NAME_COLUMN = "LAST_NAME";
    private static final String EMAIL_COLUMN = "EMAIL";

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GENERATOR")
    @SequenceGenerator(name = "USER_GENERATOR", sequenceName = "seq_users", allocationSize = 1)
    @Column(name = ID)
    private Long id;
    @Column(name = USERNAME_COLUMN)
    private String username;
    @Column(name = FIRST_NAME_COLUMN)
    private String firstName;
    @Column(name = LAST_NAME_COLUMN)
    private String lastName;
    @Column(name = EMAIL_COLUMN)
    private String email;


    @Override
    public Collection getAuthorities() {
        return new ArrayList<>();
    }
    @Override
    public String getPassword() {
        return null;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    @Override
    public boolean isEnabled() {
        return false;
    }

}
