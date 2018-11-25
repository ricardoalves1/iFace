import java.util.ArrayList;

public class Sistema {

    private static ArrayList<Perfil> usuarios = new ArrayList<>();
    private static ArrayList<Comunidade> comunidades = new ArrayList<>();

    public static ArrayList<Perfil> getUsuarios() {
        return usuarios;
    }

    public static void setUsuarios(Perfil usuario) {
        Sistema.usuarios.add(usuario);
    }

    public static ArrayList<Comunidade> getComunidades() {
        return comunidades;
    }

    public static void setComunidades(Comunidade comunidade) {
        Sistema.comunidades.add(comunidade);
    }

}
