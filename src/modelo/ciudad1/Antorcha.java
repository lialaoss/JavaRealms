package modelo.ciudad1;

public class Antorcha extends Elemento {
    
    public Antorcha() {
        super("Antorcha de Exploracion");
    }
    
    @Override
    public AdministradorElemento getAdministrador() {
        return new AdministradorAntorcha();
    }
}
