package modelo;

public class AdministradorBengala implements AdministradorElemento {
    
    @Override
    public void aplicarEfecto(Partida partida) {
        partida.despejarNieblaActual();
    }
}
