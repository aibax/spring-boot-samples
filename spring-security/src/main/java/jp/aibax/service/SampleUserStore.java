package jp.aibax.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SampleUserStore
{
    private static Map<String, User> USERS = new HashMap<String, User>()
    {
        {
            put("admin", createUser("admin", "secret", "ROLE_USER", "ROLE_ADMINISTRATOR"));
            put("manager", createUser("manager", "secret", "ROLE_USER"));
            put("developer", createUser("developer", "secret", "ROLE_USER"));
            put("guest", createUser("guest", "secret", "ROLE_USER"));
        }
    };

    private static User createUser(String username, String rawPassword, String... roles)
    {
        String password = new BCryptPasswordEncoder().encode(rawPassword);

        Set<GrantedAuthority> authorities = new HashSet<>();
        Arrays.asList(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new User(username, password, authorities);
    }

    public User findByLoginId(String loginId)
    {
        if ((loginId == null) || (loginId.length() == 0))
        {
            return null;
        }

        User user = USERS.get(loginId);

        if (user == null)
        {
            return null;
        }

        // Spring Security のログアウト処理でパスワードが初期化されるためコピーを返している
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
