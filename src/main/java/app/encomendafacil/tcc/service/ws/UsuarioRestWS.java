package app.encomendafacil.tcc.service.ws;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


import app.encomendafacil.tcc.entity.Usuario;
import app.encomendafacil.tcc.service.ServiceFactory;

@Path("usuario")
public class UsuarioRestWS {

	@Context
	private UriInfo context;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login/{login}/{senha}")
	public Usuario login(@NotNull @PathParam("login") String login, @NotNull @PathParam("senha") String senha) {
		return ServiceFactory.getUsuarioService().login(login, senha);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("add")
	public String addUsuario(Usuario usuario) {
		try {
			ServiceFactory.getUsuarioService().addUsuario(usuario);
			return "sucesso";
		} catch (RuntimeException e) {
			return "erro";
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update")
	public String updateUsuario(Usuario usuario) {
		try {
			ServiceFactory.getUsuarioService().updateUsuario(usuario);
			return "sucesso";
		} catch (RuntimeException e) {
			return "erro";
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("updateTokenId")
	public String updateTokenId(Usuario usuario) {
		try {
			ServiceFactory.getUsuarioService().updateTokenId(usuario);
			return "sucesso";
		} catch (Exception e) {
			return "erro";
		}
	}
}
