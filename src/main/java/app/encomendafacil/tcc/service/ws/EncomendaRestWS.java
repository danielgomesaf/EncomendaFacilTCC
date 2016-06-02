package app.encomendafacil.tcc.service.ws;

import java.util.List;

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

import app.encomendafacil.tcc.crawler.CorreioCrawler;
import app.encomendafacil.tcc.entity.Encomenda;
import app.encomendafacil.tcc.service.ServiceFactory;

@Path("encomenda")
public class EncomendaRestWS {

	@Context
	private UriInfo context;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("rastrearEncomenda/{codigoRastreio}")
	public Encomenda rastrearEncomenda(@NotNull @PathParam("codigoRastreio") String codigoRastreio) {
		return CorreioCrawler.getInstance().crawl(codigoRastreio);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findByCodigoRastreio/{codigoRastreio}")
	public Encomenda findByEncomendaCodigoRastreio(@NotNull @PathParam("codigoRastreio") String codigoRastreio) {
		return ServiceFactory.getEncomendaService().findEncomendaByCodigoRastreio(codigoRastreio);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findByNome/{nome}")
	public Encomenda findByEncomendaNome(@NotNull @PathParam("nome") String nome) {
		return ServiceFactory.getEncomendaService().findEncomendaByNome(nome);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findEncomendasByUsuario/{idUsuario}")
	public List<Encomenda> findEncomendasByUsuario(@NotNull @PathParam("idUsuario") String idUsuario) {
		return ServiceFactory.getEncomendaService().findEncomendasByUsuario(idUsuario);
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findHistoricoEncomenda/{idEncomenda}")
	public List<Encomenda> findHistoricoEncomenda(@NotNull @PathParam("idEncomenda") String idEncomenda) {
		return ServiceFactory.getEncomendaService().findHistoricoEncomenda(idEncomenda);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("add")
	public Encomenda addEncomenda(Encomenda encomenda) throws Exception {
		return ServiceFactory.getEncomendaService().addEncomenda(encomenda);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update")
	public Encomenda updateEncomenda(Encomenda encomenda) {
		return ServiceFactory.getEncomendaService().updateEncomendaByUsuario(encomenda);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("delete")
	public void deleteEncomenda(Encomenda encomenda) {
		ServiceFactory.getEncomendaService().removerEncomenda(encomenda);
	}
}
