package jp.aibax.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.aibax.data.domain.User;
import jp.aibax.data.repository.UserRepository;

@Controller
@RequestMapping("user")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public UserForm setUpForm()
    {
        return new UserForm();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model)
    {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "user/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model)
    {
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(UserForm form, BindingResult result)
    {
        String loginId = form.getLoginId();
        String password = form.getBCryptPassword();
        String name = form.getName();

        User user = new User(null, loginId, password, name);
        userRepository.save(user);

        return "redirect:/user/";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String edit(UserForm form, @PathVariable("id") Integer id)
    {
        User user = userRepository.findOne(id);

        if (user != null)
        {
            form.setId(user.getId());
            form.setLoginId(user.getLoginId());
            form.setName(user.getName());
        }

        return "user/edit";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.POST)
    public String edit(UserForm form, @PathVariable("id") Integer id, BindingResult result)
    {
        User user = userRepository.findOne(id);

        if (user != null)
        {
            user.setLoginId(form.getLoginId());
            user.setName(form.getName());

            if (form.getBCryptPassword() != null)
            {
                user.setPassword(form.getBCryptPassword());
            }

            userRepository.save(user);
        }

        return "redirect:/user/";
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
    public String delete(UserForm form, @PathVariable("id") Integer id)
    {
        userRepository.delete(id);

        return "redirect:/user/";
    }
}
