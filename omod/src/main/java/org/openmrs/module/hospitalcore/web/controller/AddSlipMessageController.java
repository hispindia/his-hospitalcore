package org.openmrs.module.hospitalcore.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("AddSlipMessageController")
@RequestMapping("module/hospitalcore/addSlipMessage.form")
public class AddSlipMessageController {
    @RequestMapping(method = RequestMethod.GET)
    public String showForm(HttpServletRequest request, Model model) {
        GlobalProperty slipMessage = Context.getAdministrationService().getGlobalPropertyObject("hospitalcore.slipMessage");
        String message = slipMessage.getPropertyValue();
        model.addAttribute("message", message);
        return("/module/hospitalcore/addSlipMessage");
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestParam String message){
        GlobalProperty slipMessage = Context.getAdministrationService().getGlobalPropertyObject("hospitalcore.slipMessage");
        slipMessage.setPropertyValue(message);
        Context.getAdministrationService().saveGlobalProperty(slipMessage);
        return "success";
    }
}