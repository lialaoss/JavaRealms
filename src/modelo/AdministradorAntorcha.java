package modelo;

public class AdministradorAntorcha implements AdministradorElemento {
    
    @Override
    public void aplicarEfecto(Partida partida) {
        int radioActual = partida.getRadioVision();
        
        if (radioActual < 10) {
            partida.setRadioVision(radioActual + 1);
        }
    }
}