package com.ecom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 // Ruta para las imágenes de productos
	    String productImgDir = "file:./uploads/product_img/";
	    registry.addResourceHandler("/img/product_img/**")
	            .addResourceLocations(productImgDir);
		//Ruta User
		String categoryImgDir = "file:./uploads/category_img/";
		registry.addResourceHandler("/img/category_img/**")
				.addResourceLocations(categoryImgDir);

	    // Ruta para las imágenes de administración
	    String adminImgDir = "file:./uploads/admin_img/";
	    registry.addResourceHandler("/admin/img/admin_img/**")
	            .addResourceLocations(adminImgDir);
		//Ruta User
		String userImgDir = "file:./uploads/user_img/";
		registry.addResourceHandler("/user/img/user_img/**")
				.addResourceLocations(userImgDir);
    }
	
}
