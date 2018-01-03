package jp.aibax.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.Data;

@Data
public class UserForm
{
    private int id;

    private String loginId;

    private String password;

    private String name;

    public String getBCryptPassword()
    {
        if ((password == null) || (password.length() == 0))
        {
            return null;
        }

        return new BCryptPasswordEncoder().encode(password);
    }
}
