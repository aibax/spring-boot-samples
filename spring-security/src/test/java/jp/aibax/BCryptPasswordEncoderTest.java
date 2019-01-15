package jp.aibax;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BCryptPasswordEncoderTest
{
    @Test
    public void testEncode()
    {
        String rawPassword = "secret";
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
        assertNotNull(encodedPassword);
        assertTrue(encodedPassword.length() > 0);

        System.out.println(encodedPassword);
    }

    @Test
    public void testValidate()
    {
        String rawPassword = "secret";
        String encodedPassword = "$2a$10$oyWxb7e/R1RgptoOuzDjCexl45WohjGhMEEGlPabKZZWEWYUwaeri";

        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, encodedPassword));
    }
}
