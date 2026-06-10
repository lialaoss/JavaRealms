package modelo.ciudad1;

public class Radar extends Elemento {
    
    public Radar() {
        super("Radar de Resonancia");
    }
    
    @Override
    public AdministradorElemento getAdministrador() {
        return new AdministradorRadar();
    }
}