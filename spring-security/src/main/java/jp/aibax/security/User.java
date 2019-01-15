package jp.aibax.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import lombok.Getter;
import lombok.Setter;

public class User extends org.springframework.security.core.userdetails.User
{
    @Getter
    @Setter
    private String displayName;

    public User(String username, String password, String displayName,
        Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, authorities);

        this.displayName = displayName;
    }

    public User(String username, String password, String displayName, boolean enabled, boolean accountNonExpired,
        boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.displayName = displayName;
    }
}
