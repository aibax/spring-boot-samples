package jp.aibax.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController
{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(name = "name", defaultValue = "World") String name)
    {
        model.addAttribute("name", name);

        return "index";
    }
}
