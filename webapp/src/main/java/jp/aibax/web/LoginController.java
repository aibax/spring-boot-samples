package jp.aibax.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("login")
public class LoginController
{
    @ModelAttribute
    public LoginForm setUpForm()
    {
        return new LoginForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(LoginForm form, Model model)
    {
        return "login";
    }
}
