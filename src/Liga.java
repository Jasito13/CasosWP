import java.util.*;

public class Liga {
    // Diccionario para almacenar los equipos y sus puntos actuales
    private Map<String, Integer> clasificacion;

    // Para crear una liga y añadirle los equipos
    public Liga() {
        this.clasificacion = new HashMap<>();
    }

    // Funcion para agregar equipos con sus puntos iniciales
    public void agregarEquipo(String nombre, int puntos) {
        clasificacion.put(nombre, puntos);
    }

    // Para crear una liga con una clasificación ya hecha
    public Liga(Map<String, Integer> clasificacion) {
        this.clasificacion = clasificacion;
    }

    // Mostrar clasificación ordenada por puntos
    public void mostrarClasificacion() {
        // Ordenar por puntos (de mayor a menor)
        List<Map.Entry<String, Integer>> clasificacionList = new ArrayList<>(clasificacion.entrySet());
        clasificacionList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        System.out.println("\nClasificación:");
        int pos = 1;
        for (Map.Entry<String, Integer> equipo : clasificacionList) {
            System.out.println(pos + ". " + equipo.getKey() + " - " + equipo.getValue() + " puntos");
            pos++;
        }
    }

    // Simular un resultado de partido
    public void simularPartidos(List<Partido> partidos) {
        // Para cada partido, simulamos los resultados posibles
        List<Map<String, Integer>> simulaciones = new ArrayList<>();

        // Copiar la clasificación inicial para empezar a sumar puntos de los partidos progresivamente
        Map<String, Integer> clasificacionBase = new HashMap<>(clasificacion);

        if (partidos.isEmpty()){
            mostrarClasificacion();
        }else {
            Partido partido = partidos.get(0);
            // Validar que ambos equipos existen en el mapa antes de continuar
            if (!clasificacionBase.containsKey(partido.local)) {
                System.err.println("El equipo local '" + partido.local + "' no existe en la liga.");
            }
            if (!clasificacionBase.containsKey(partido.visitante)) {
                System.err.println("El equipo visitante '" + partido.visitante + "' no existe en la liga.");
            }

            // Copiar la clasificación actual para las simulaciones
            Map<String, Integer> simLocalGana = new HashMap<>(clasificacionBase);
            Map<String, Integer> simVisitanteGana = new HashMap<>(clasificacionBase);
            Map<String, Integer> simPenaltisLocal = new HashMap<>(clasificacionBase);
            Map<String, Integer> simPenaltisVisitante = new HashMap<>(clasificacionBase);

            // Obtener los puntos actuales de ambos equipos
            int puntosLocal = simLocalGana.get(partido.local);
            int puntosVisitante = simVisitanteGana.get(partido.visitante);

            // Resultado 1: Gana el equipo local (3 puntos para local)
            simLocalGana.put(partido.local, puntosLocal + 3);
            simulaciones.add(simLocalGana);

            // Resultado 2: Gana el equipo visitante (3 puntos para visitante)
            simVisitanteGana.put(partido.visitante, puntosVisitante + 3);
            simulaciones.add(simVisitanteGana);

            // Resultado 3: Empate en tiempo reglamentario, gana el local en penaltis (2 puntos local, 1 visitante)
            simPenaltisLocal.put(partido.local, puntosLocal + 2);
            simPenaltisLocal.put(partido.visitante, puntosVisitante + 1);
            simulaciones.add(simPenaltisLocal);

            // Resultado 4: Empate en tiempo reglamentario, gana el visitante en penaltis (2 puntos visitante, 1 local)
            simPenaltisVisitante.put(partido.visitante, puntosVisitante + 2);
            simPenaltisVisitante.put(partido.local, puntosLocal + 1);
            simulaciones.add(simPenaltisVisitante);

            List<Partido> partidosCopia = new ArrayList<>(partidos);
            partidosCopia.remove(partido);

            // Calcular todas las simulaciones posibles
            for (Map<String, Integer> simulacion : simulaciones) {
                Liga simulada = new Liga(simulacion);
                simulada.simularPartidos(partidosCopia);
            }

        }

    }

}
