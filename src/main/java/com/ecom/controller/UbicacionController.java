package com.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UbicacionController {
	
	  @PostMapping("/guardar-ubicacion")
	    public String guardarUbicacion(@RequestParam("latitude") String latitude,
	                                    @RequestParam("longitude") String longitude,
	                                    Model model) {
	     
	        System.out.println("Latitud: " + latitude);
	        System.out.println("Longitud: " + longitude);
	        model.addAttribute("latitude", latitude);
	        model.addAttribute("longitude", longitude);
	        return "/user/order";
	    }

}
