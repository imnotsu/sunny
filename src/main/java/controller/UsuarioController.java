package controller;

import model.Usuario;
import service.UsuarioService;
import java.util.List;

public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    public boolean salvarUsuario(Usuario usuario) {
        return usuarioService.inserir(usuario);
    }

    public boolean atualizarUsuario(Usuario usuario) {
        return usuarioService.atualizar(usuario);
    }

    public boolean excluirUsuario(int idUsuario) {
        return usuarioService.excluir(idUsuario);
    }

    public Usuario buscarUsuarioPorId(int idUsuario) {
        return usuarioService.buscarPorId(idUsuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    public Usuario login(String usuario, String senha) {
        return usuarioService.login(usuario, senha);
    }
}