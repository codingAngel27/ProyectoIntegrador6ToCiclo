package com.ecom.util;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;

	public Boolean sendMail(String url, String reciepentEmail) throws UnsupportedEncodingException, MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("sumacperusac@gmail.com", "Sumaq Peru");
		helper.setTo(reciepentEmail);

		String content = "<p>Hola,</p>" + "<p>\n" +
				"Ha solicitado restablecer su contraseña.</p>"
				+ "<p>\n" +
				"Haga clic en el siguiente enlace para cambiar su contraseña:</p>" + "<p><a href=\"" + url
				+ "\">\n" +
				"cambiar mi contraseña</a></p>";
		helper.setSubject("Password Reset");
		helper.setText(content, true);
		mailSender.send(message);
		return true;
	}

	public static String generateUrl(HttpServletRequest request) {

		// http://localhost:8080/forgot-password
		String siteUrl = request.getRequestURL().toString();

		return siteUrl.replace(request.getServletPath(), "");
	}
	
	String msg=null;;
	
	public Boolean sendMailForProductOrder(ProductOrder  productOrder, String status) throws Exception
	{
		List<ProductOrder> list = new ArrayList<>();
		list.add(productOrder);
		return sendMailForProductOrder(list, status);
	}
	
	public Boolean sendMailForProductOrder(List<ProductOrder>  listOrder,String status) throws Exception
	{
		
		msg = "<p>Hola " + listOrder.get(0).getOrderAddress().getFirstName() + "</p>";
		msg += "<p>Gracias por su Compra, su orden se esta procesando</p>"
				+ "<p><b>Product Details:</b></p>";
		
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("sumacperusac@gmail.com", "Sumaq Peru");
		helper.setTo(listOrder.get(0).getOrderAddress().getEmail());
				
	
		

		for(int i = 0; i<listOrder.size(); i++) {
			
			ProductOrder order =listOrder.get(i);
			
			msg += "</br>";
			msg += "<p>Producto : " + order.getProduct().getTitle() + "</p>";
			msg += "<p>Categoria : " + order.getProduct().getCategory() + "</p>";
			msg += "<p>Cantidad : " + order.getQuantity().toString() + "</p>";
			msg += "<p>Precio : " + order.getPrice().toString() + "</p>";
			msg += "<p>Tipo de Pago: " + order.getPaymentType() + "</p>";
			msg += "<p>---------------------------</p>";
			
			
		
		
			
			
		}
		
		
		helper.setSubject("Product Order Status");
		helper.setText(msg, true);
		mailSender.send(message);
		return true;
	}
	
	public Boolean sendMailForProductOrderPayment(String email,Double totalOrder,String nombre) throws Exception
	{
		
		msg= "<p>Hola: "+ nombre +"</p>"
				+"<p>Su pago fue procesado con èxito </p>";
				
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("sumacperusac@gmail.com", "Sumaq Peru");
		helper.setTo(email);

		msg += "<p>Monto: " + totalOrder + "</p>";
		
		helper.setText(msg, true);
		mailSender.send(message);
		return true;
	}
	
	public UserDtls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}
	

}
