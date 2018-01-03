package jp.aibax.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.aibax.data.domain.User;
import jp.aibax.data.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /* アクセスに認証が必要なページと必要なロールの設定 */
        // @formatter:off
        http
            .authorizeRequests()
                // 静的リソースへのアクセスは認証不要
                .antMatchers("/css/**", "/images/**", "/js/**").permitAll()
                // それ以外のアクセスは認証と'USER'ロールが必要
                .anyRequest().hasRole("USER");
        // @formatter:on

        /* ログイン画面設定 */
        // @formatter:off
        http
            .formLogin()
                .loginPage("/login")
                    .usernameParameter("loginId")
                    .passwordParameter("password")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login")
                .permitAll();
        // @formatter:on

        /* ログアウト処理設定 */
        // @formatter:off
        http
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();
        // @formatter:on
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(this).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if ((username == null) || (username.length() == 0))
        {
            throw new UsernameNotFoundException(null);
        }

        User user = userRepository.findByLoginId(username);

        if (user == null)
        {
            throw new UsernameNotFoundException("User is not found. (" + username + ")");
        }

        String password = user.getPassword();
        String displayName = user.getName();

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>()
        {
            {
                /* 一般ユーザー権限 */
                add(new SimpleGrantedAuthority("ROLE_USER"));

                /* ログインIDが 'admin' のユーザーは管理者ユーザーとする */
                if (user.getLoginId().equals("admin"))
                {
                    /* 管理者ユーザー権限 */
                    add(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
                }
            }
        };

        return new jp.aibax.security.User(username, password, displayName, authorities);
    }
}
