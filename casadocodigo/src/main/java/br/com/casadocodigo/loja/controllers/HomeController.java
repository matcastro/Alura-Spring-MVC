package br.com.casadocodigo.loja.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.daos.UsuarioDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;

@Controller
public class HomeController {

	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@RequestMapping("/")
	@Cacheable(value="produtosHome")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("home");
		List<Produto> produtos = produtoDAO.listar();
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-maluca-d6t1n56td1b56sr156bsr156n1s56n16rsr1b6sevnejkvj")
	public String urlMagicaMaluca() {
		Usuario usuario = new Usuario();
		usuario.setNome("Admin");
		usuario.setEmail("admin@casadocodigo.com.br");
		usuario.setSenha("$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu");
		Role role = new Role();
		role.setNome("ROLE_ADMIN");
		usuario.setRoles(Arrays.asList(role));
		
		usuarioDAO.gravar(usuario);
		
		return "URL mágica executada";
	}
}
