package controller;

import consumer.ConsumerWithListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsumeController {

    @GetMapping("/consume-events")
    public String startConsumer(Model model) {

        model.addAttribute("eventos", ConsumerWithListener.getEvents());

        return "index";
    }




}