import java.util.ArrayList;

public class Perfil {

    private String nome;
    private String login;
    private String senha;
    private ArrayList<Perfil> amigo = new ArrayList<>();
    private ArrayList<Perfil> pedido = new ArrayList<>();
    private ArrayList<Mensagem> mensagens = new ArrayList<>();
    private ArrayList<Comunidade> comunidades = new ArrayList<>();

    public Perfil(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<Perfil> getAmigo() {
        return amigo;
    }

    public void setAmigo(Perfil usuario) {
        amigo.add(usuario);
    }

    public ArrayList<Perfil> getPedido() {
        return pedido;
    }

    public void setPedido(Perfil usuario) {
        pedido.add(usuario);
    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(Perfil usuario, String mensagem) {
        this.mensagens.add(new Mensagem(usuario, mensagem));
    }

    public ArrayList<Comunidade> getComunidades() {
        return comunidades;
    }

    public void setComunidades(Comunidade comunidade) {
        comunidades.add(comunidade);
    }

}