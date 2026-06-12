package ui;

public final class ConfiguracionPantalla {
	/**
	 * Configuracion de pantalla:
	 * originalTileSize : tamaño de tiles
	 * maxScreenCol : max cantidad de columnas para nuestra ventana (ancho)
	 * maxScreenRow : max cantidad de filas para nuestra ventana (altura)
	 * screenWidth : ancho de pantalla
	 * screenHeight : alto pantalla
	 */

    private ConfiguracionPantalla() {}

    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 3;

    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48

    public static final int MAX_SCREEN_COL = 24;
    public static final int MAX_SCREEN_ROW = 12;

    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 1152 px
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 px

    public static final int FPS = 60;
}
