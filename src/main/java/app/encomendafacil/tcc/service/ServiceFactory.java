package app.encomendafacil.tcc.service;

public class ServiceFactory {

	private static EncomendaService encomendaService;
	private static UsuarioService usuarioService;
	
	public static EncomendaService getEncomendaService(){
		if (encomendaService == null)
			encomendaService = new EncomendaService();
		return encomendaService;
	}
	
	public static UsuarioService getUsuarioService(){
		if (usuarioService == null)
			usuarioService = new UsuarioService();
		return usuarioService;
	}
}
