package mensagem;

import usuario.Perfil;

import java.util.Scanner;

public class Mensagem {

    private Perfil usuario;
    private String mensagem;

    public Mensagem(Perfil usuario, String mensagem) {
        this.usuario = usuario;
        this.mensagem = mensagem;
    }

    public Mensagem() {}

    Scanner input = new Scanner(System.in);

    public Perfil getUsuario() {
        return usuario;
    }

    public void setUsuario(Perfil usuario) {
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
