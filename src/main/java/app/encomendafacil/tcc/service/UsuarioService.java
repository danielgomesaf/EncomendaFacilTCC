package app.encomendafacil.tcc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;

import app.encomendafacil.tcc.entity.Usuario;
import app.encomendafacil.tcc.service.connection.ConnectionFactory;

public class UsuarioService {

	private Connection conn;
	
	public UsuarioService() {
		conn = new ConnectionFactory().getConnection();
	}
	
	public Usuario login(String login, String senha) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM usuario WHERE email = ? AND senha = ?");
			ps.setString(1, login);
			ps.setString(2, senha);
			rs = ps.executeQuery();
			if (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setEmail(rs.getString("email"));
				usuario.setIdUsuario(rs.getString("idUsuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setTokenId(rs.getString("tokenId"));
				return usuario;
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
	
	public void addUsuario(Usuario usuario) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO usuario(idUsuario,email,nome,senha) VALUES (?,?,?,?)");
			ps.setString(1, RandomStringUtils.randomAlphanumeric(12));
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getNome());
			ps.setString(4, usuario.getSenha());
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
	
	public void updateUsuario(Usuario usuario) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE usuario SET email = ?, nome = ?, senha=? WHERE idUsuario = ?");
			ps.setString(1, usuario.getEmail());
			ps.setString(2, usuario.getNome());
			ps.setString(3, usuario.getSenha());
			ps.setString(4, usuario.getIdUsuario());
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
	
	public void updateTokenId(Usuario usuario) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE usuario SET tokenId = ? WHERE idUsuario = ?");
			ps.setString(1, usuario.getTokenId());
			ps.setString(2, usuario.getIdUsuario());
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
}
