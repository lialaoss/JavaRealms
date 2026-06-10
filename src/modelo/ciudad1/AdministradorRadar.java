package modelo.ciudad1;

public class AdministradorRadar implements AdministradorElemento {
    
    @Override
    public void aplicarEfecto(Partida partida) {
        partida.escanearEntorno();
    }
}