package app.encomendafacil.tcc.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import app.encomendafacil.tcc.entity.Encomenda;
import app.encomendafacil.tcc.service.ServiceFactory;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.parser.Parser;
import edu.uci.ics.crawler4j.url.WebURL;

public class CorreioCrawler {

	private final String PAGINA_CORREIO = "http://websro.correios.com.br/sro_bin/txect01$.Inexistente?P_LINGUA=001&P_TIPO=002&P_COD_LIS=";
	private static CorreioCrawler INSTANCE;
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private final Parser parser;
	private final PageFetcher pageFetcher;
	
	private CorreioCrawler() {
		CrawlConfig config = new CrawlConfig();
		parser = new Parser(config);
		pageFetcher = new PageFetcher(config);
	}
	
	
	public static CorreioCrawler getInstance(){
		if (INSTANCE == null)
			return new CorreioCrawler();
		return INSTANCE;
	}
	
	public Encomenda crawl(String codigoRastreio){
		Encomenda encomenda = ServiceFactory.getEncomendaService().findEncomendaByCodigoRastreio(codigoRastreio);
		if (encomenda != null) {
			Page paginaCorreio = getPaginaCorreio(codigoRastreio);
			if (paginaCorreio != null) {
				ParseData parseData = paginaCorreio.getParseData();
				if (parseData != null) {
					if (parseData instanceof HtmlParseData) {
						HtmlParseData htmlParseData = (HtmlParseData) parseData;
						String html = htmlParseData.getHtml();
						Document doc = Jsoup.parse(html);
						Elements table = doc.getElementsByTag("table");
						Elements trList = table.select("tbody tr");
						if (trList != null && !trList.isEmpty()) {
							trList.remove(0);
							for (Element tr : trList) {
								if (tr.children().size() == 3)
									tr.attr("id", "row" + trList.indexOf(tr));
								if (tr.nextElementSibling() != null) {
									if (tr.nextElementSibling().children().size() == 1)
										tr.nextElementSibling().attr("id", "row" + trList.indexOf(tr) + "_aux");
								}
							}
							ServiceFactory.getEncomendaService().removerHistoricoEncomenda(encomenda.getIdEncomenda());;
							for (Element tr2 : trList) {
								Elements cols = tr2.children();
								if (cols.size() == 3) {
									Date dataAtualizacao = getDataAtualizacao(cols.get(0).text());
									if(dataAtualizacao.after(encomenda.getDataAtualizacao())) {
										Encomenda enc = buildEncomenda(encomenda, doc, tr2, cols, dataAtualizacao);
										ServiceFactory.getEncomendaService().updateEncomendaByMudanca(enc);
										ServiceFactory.getEncomendaService().addEncomendaToHistorico(enc);
										encomenda.setDataAtualizacao(dataAtualizacao);
									}
									else {
										Encomenda enc = buildEncomenda(encomenda, doc, tr2, cols, dataAtualizacao);
										ServiceFactory.getEncomendaService().addEncomendaToHistorico(enc);
										encomenda.setDataAtualizacao(dataAtualizacao);
									}
								}
							}
						}
					}
				}
			}
		}
		return encomenda;
	}

	private Encomenda buildEncomenda(Encomenda encomenda, Document doc, Element tr2, Elements cols, Date dataAtualizacao) {
		Encomenda enc = encomenda;
		enc.setLocal(cols.get(1).text());
		enc.setStatus(cols.get(2).text());
		enc.setDataAtualizacao(dataAtualizacao);
		String trId = tr2.id();
		String informacoes = null;
		Element elementInfo = doc.getElementById(trId + "_aux");
		if (elementInfo != null)
			informacoes = elementInfo.select("td").text();
		enc.setInformacoes(informacoes);
		return enc;
	}
	
	private Date getDataAtualizacao(String dateStr) {
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Page getPaginaCorreio(String codigoRastreio) {
		WebURL webURL = new WebURL();
		webURL.setURL(PAGINA_CORREIO + codigoRastreio);
		PageFetchResult fetchResult = null;
		
		try {
			fetchResult = pageFetcher.fetchPage(webURL);
			if (fetchResult.getStatusCode() == HttpStatus.SC_OK) {
				Page page = new Page(webURL);
				fetchResult.fetchContent(page);
				parser.parse(page, webURL.getURL());
				return page;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fetchResult != null)
				fetchResult.discardContentIfNotConsumed();
		}
		return null;
	}
}
