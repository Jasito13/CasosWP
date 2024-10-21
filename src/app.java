import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class app {

    public static List<Partido> cargarDatosDesdeArchivo(String archivo, Liga liga) throws IOException {
        List<Partido> partidos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(archivo));

        String linea;
        boolean leerPartidos = false;

        // Leer línea por línea
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) {
                // Si encontramos una línea vacía, comenzamos a leer los partidos
                leerPartidos = true;
                continue;
            }

            if (!leerPartidos) {
                // Leer la clasificación
                String[] partes = linea.split(" ");
                String equipo = partes[0].trim();
                int puntos = Integer.parseInt(partes[1].trim());
                liga.agregarEquipo(equipo, puntos);
            } else {
                // Leer los partidos
                String[] partes = linea.split(" vs ");
                String local = partes[0].trim();
                System.out.println(local);
                String visitante = partes[1].trim();
                System.out.println(visitante);
                partidos.add(new Partido(local, visitante));
            }
        }
        br.close();
        return partidos;
    }

    public static void main(String[] args) {
        try {
            // Crear la liga
            Liga liga = new Liga();

            // Leer los datos desde el archivo
            List<Partido> partidos = cargarDatosDesdeArchivo("assets/data.txt", liga);

            // Mostrar la clasificación actual
            liga.mostrarClasificacion();

            // Simular los resultados de los partidos
            liga.simularPartidos(partidos);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}