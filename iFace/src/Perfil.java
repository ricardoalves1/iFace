import java.util.ArrayList;

public class Perfil {

    public Perfil(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Perfil() {}

    private String nome;
    private String login;
    private String senha;
    private ArrayList<String> amigo = new ArrayList<>();
    private ArrayList<String> pedido = new ArrayList<>();
    private ArrayList<Mensagem> mensagens = new ArrayList<>();
    private ArrayList<Comunidade> comunidades = new ArrayList<>();


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

    public ArrayList<String> getAmigo() {
        return amigo;
    }

    public void setAmigo(String login) {
        amigo.add(login);
    }

    public ArrayList<String> getPedido() {
        return pedido;
    }

    public void setPedido(String login) {
        pedido.add(login);
    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(String login, String mensagem) {
        this.mensagens.add(new Mensagem(login, mensagem));
    }

    public ArrayList<Comunidade> getComunidades() {
        return comunidades;
    }

    public void setComunidades(Comunidade comunidade) {
        comunidades.add(comunidade);
    }

}
