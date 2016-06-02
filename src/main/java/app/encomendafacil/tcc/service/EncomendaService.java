package app.encomendafacil.tcc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import app.encomendafacil.tcc.entity.Encomenda;
import app.encomendafacil.tcc.service.connection.ConnectionFactory;

public class EncomendaService {

	private Connection conn;
	
	public EncomendaService() {
		conn = new ConnectionFactory().getConnection();
	}
	
	public Encomenda addEncomenda(Encomenda encomenda) {
		Encomenda enc = findEncomendaByCodigoRastreio(encomenda.getCodRastreio());
		if (enc != null) {
			encomenda.setMensagemErro("Encomenda já existe na base de dados");
			return encomenda;
		}
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO encomenda(idEncomenda,codigoRastreio,dataAtualizacao,dataCadastro,nome,houveMudanca,status,idUsuario,local,informacoes) VALUES (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, RandomStringUtils.randomAlphanumeric(12));
			ps.setString(2, encomenda.getCodRastreio());
			ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
			ps.setString(5, encomenda.getNome());
			ps.setBoolean(6, false);
			ps.setString(7, encomenda.getStatus());
			ps.setString(8, encomenda.getIdUsuario());
			ps.setString(9, encomenda.getLocal());
			ps.setString(10, encomenda.getInformacoes());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return encomenda;
	}
	
	public void addEncomendaToHistorico(Encomenda encomenda) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO historico_encomenda(idHistoricoEncomenda,idEncomenda,codigoRastreio,dataAtualizacao,dataCadastro,nome,status,idUsuario,local,informacoes) VALUES (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, RandomStringUtils.randomAlphanumeric(12));
			ps.setString(2, encomenda.getIdEncomenda());
			ps.setString(3, encomenda.getCodRastreio());
			java.sql.Timestamp dataAtualizacao = null;
			if (encomenda.getDataAtualizacao() != null)
				dataAtualizacao = new java.sql.Timestamp(encomenda.getDataAtualizacao().getTime());
			else
				dataAtualizacao = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(4, dataAtualizacao);
			java.sql.Timestamp dataCadastro = null;
			if (encomenda.getDataCadastro() != null)
				dataCadastro = new java.sql.Timestamp(encomenda.getDataCadastro().getTime());
			else
				dataCadastro = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(5, dataCadastro);
			ps.setString(6, encomenda.getNome());
			ps.setString(7, encomenda.getStatus());
			ps.setString(8, encomenda.getIdUsuario());
			ps.setString(9, encomenda.getLocal());
			ps.setString(10, encomenda.getInformacoes());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public Encomenda updateEncomendaByUsuario(Encomenda encomenda) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE encomenda SET codigoRastreio = ?, nome = ? WHERE idEncomenda = ?");
			ps.setString(1, encomenda.getCodRastreio());
			ps.setString(2, encomenda.getNome());
			ps.setString(3, encomenda.getIdEncomenda());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return encomenda;
	}
	
	public void updateEncomendaByMudanca(Encomenda encomenda) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE encomenda SET dataAtualizacao = ?, status = ?, houveMudanca = ?, local = ?, informacoes = ? WHERE idEncomenda = ?");
			ps.setTimestamp(1, new java.sql.Timestamp(encomenda.getDataAtualizacao().getTime()));
			ps.setString(2, encomenda.getStatus());
			ps.setBoolean(3, true);
			ps.setString(4, encomenda.getLocal());
			ps.setString(5, encomenda.getInformacoes());
			ps.setString(6, encomenda.getIdEncomenda());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public void removerEncomenda(Encomenda encomenda) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM encomenda WHERE idEncomenda = ?");
			ps.setString(1, encomenda.getIdEncomenda());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public Encomenda findEncomendaByCodigoRastreio(String codigoRastreio) {
		return findEncomenda(codigoRastreio, "codigoRastreio");
	}
	
	public Encomenda findEncomendaByNome(String nome) {
		return findEncomenda(nome, "nome");
	}
	
	public List<Encomenda> findEncomendasByUsuario(String idUsuario) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM encomenda WHERE idUsuario = ?";
		List<Encomenda> encomendas = new ArrayList<Encomenda>();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, idUsuario);
			rs = ps.executeQuery();
			while (rs.next()) {
				Encomenda encomenda = new Encomenda();
				encomenda.setCodRastreio(rs.getString("codigoRastreio"));
				encomenda.setDataAtualizacao(rs.getDate("dataAtualizacao"));
				encomenda.setDataCadastro(rs.getDate("dataCadastro"));
				encomenda.setHouveMudanca(rs.getBoolean("houveMudanca"));
				encomenda.setIdEncomenda(rs.getString("idEncomenda"));
				encomenda.setIdUsuario(rs.getString("idUsuario"));
				encomenda.setNome(rs.getString("nome"));
				encomenda.setStatus(rs.getString("status"));
				encomenda.setLocal(rs.getString("local"));
				encomenda.setInformacoes(rs.getString("informacoes"));
				encomendas.add(encomenda);
			}
			return encomendas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public List<Encomenda> findHistoricoEncomenda(String idEncomenda) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM historico_encomenda WHERE idEncomenda = ?";
		List<Encomenda> encomendas = new ArrayList<Encomenda>();
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, idEncomenda);
			rs = ps.executeQuery();
			while (rs.next()) {
				Encomenda encomenda = new Encomenda();
				encomenda.setCodRastreio(rs.getString("codigoRastreio"));
				encomenda.setDataAtualizacao(rs.getDate("dataAtualizacao"));
				encomenda.setDataCadastro(rs.getDate("dataCadastro"));
				encomenda.setIdEncomenda(rs.getString("idEncomenda"));
				encomenda.setIdUsuario(rs.getString("idUsuario"));
				encomenda.setNome(rs.getString("nome"));
				encomenda.setStatus(rs.getString("status"));
				encomenda.setLocal(rs.getString("local"));
				encomenda.setInformacoes(rs.getString("informacoes"));
				encomendas.add(encomenda);
			}
			Collections.sort(encomendas, Collections.reverseOrder());
			return encomendas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	private Encomenda findEncomenda(String parametro, String campo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM encomenda WHERE " + campo + " = ?";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, parametro);
			rs = ps.executeQuery();
			if (rs.next()) {
				Encomenda encomenda = new Encomenda();
				encomenda.setCodRastreio(rs.getString("codigoRastreio"));
				encomenda.setDataAtualizacao(rs.getDate("dataAtualizacao"));
				encomenda.setDataCadastro(rs.getDate("dataCadastro"));
				encomenda.setHouveMudanca(rs.getBoolean("houveMudanca"));
				encomenda.setIdEncomenda(rs.getString("idEncomenda"));
				encomenda.setIdUsuario(rs.getString("idUsuario"));
				encomenda.setNome(rs.getString("nome"));
				encomenda.setStatus(rs.getString("status"));
				encomenda.setLocal(rs.getString("local"));
				encomenda.setInformacoes(rs.getString("informacoes"));
				return encomenda;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		
		return null;
	}
	
	public void removerHistoricoEncomenda(String idEncomenda) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM historico_encomenda WHERE idEncomenda = ?");
			ps.setString(1, idEncomenda);
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
