package service;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean inserir(Usuario usuario) {
        if (usuario.getUsuario() == null || usuario.getUsuario().isBlank()) {
            return false;
        }
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            return false;
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            return false;
        }
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            return false;
        }

        usuarioDAO.inserir(usuario);
        return true;
    }

    public boolean atualizar(Usuario usuario) {
        if (usuario.getIdUsuario() <= 0) {
            return false;
        }
        usuarioDAO.atualizar(usuario);
        return true;
    }

    public boolean excluir(int idUsuario) {
        if (idUsuario <= 0) {
            return false;
        }
        usuarioDAO.excluir(idUsuario);
        return true;
    }

    public Usuario buscarPorId(int idUsuario) {
        return usuarioDAO.buscarPorId(idUsuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public Usuario login(String usuario, String senha) {
        if (usuario == null || usuario.isBlank() || senha == null || senha.isBlank()) {
            return null;
        }
        return usuarioDAO.buscarPorLoginESenha(usuario, senha);
    }
}