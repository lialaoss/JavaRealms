package modelo;

public class Bengala extends Elemento {
    
    public Bengala() {
        super("Bengala de Profundidad");
    }
    
    @Override
    public AdministradorElemento getAdministrador() {
        return new AdministradorBengala();
    }
}
