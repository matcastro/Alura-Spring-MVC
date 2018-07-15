package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;
import br.com.casadocodigo.loja.models.Usuario;

@Controller
@RequestMapping("/pagamento")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class PagamentoController {

	@Autowired
	CarrinhoCompras carrinho;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	MailSender mailSender;
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public ModelAndView finalizar(RedirectAttributes model, @AuthenticationPrincipal Usuario usuario) {
		System.out.println(carrinho.getTotal());
		String uri = "http://book-payment.herokuapp.com/payment";
		
		try {
			restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
			enviaEmailCompraProduto(usuario);
			model.addFlashAttribute("sucesso", "Pagamento realizado com sucesso!");

			carrinho.esvaziar();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			model.addFlashAttribute("falha", "Valor maior que o permitido");
		} finally {
			return new ModelAndView("redirect:/produtos");
		}
		
	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra efetuada com sucesso!!!");
		//email.setTo(usuario.getEmail());
		email.setTo("matcastro2004@yahoo.com.br");
		email.setText("Compra realizada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("contato@casadocodigo.com.br");
		mailSender.send(email);
	}
}
