package proyectoparte3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Random;

/**
 * Clase ProyectoParte3 : aqui estara todo lo de la parte 3 del proyexto
 * @author David Silva
 */
public class ProyectoParte3 {
    /**
     * Metodo main : ejecutara el main de la parte 3
     * @param args : todos los argumentos posibles
     */
    public static void main(String[] args) {
        int size = 20; //Creamos una variable para el tamaño del grafo nxn
        int[][] graph = generateGraph(size); //Generamos el grafo apartir del tamaño de antes y regresamos un nxn

        printGraph(graph); //Mostramos en pantalla el grafo original antes de los cambios 
                
        System.out.println(""); //Espacio
        System.out.println(""); //Espacio
        System.out.println(""); //Espacio
        
        //Este es un administrador de hilos que nos ayuda a ejecutar tareas al mismo tiempo
        //Para este executor creamos un pool para manejar 2 hilos
        //Lo que se traduce en 2 tareas de manera simultanea
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        //Para usar cada algoritmo de ordenación vamos a usar la interfaz funcional de Callable<Long>
        //Esto nos permite que despues de cada ejecución se nos devuelva un valor
        
        //Creamos el callable del metodo de floyd
        Callable<Long> floydTask = () -> {
            long startTime = System.nanoTime(); //Empezar a registrar el tiempo de arranque del proceso del algoritmo
            Floyd floyd = new Floyd(); //Creamos una instancia del algoritmo de floyd
            int[][] floydResultMatrix = floyd.floydWarshall(graph, size); //Buscamos las rutas mas cortas entre los pares de nodos
            long duration = System.nanoTime() - startTime; //Calculamos el tiempo de ejecución mediente una resta
            return duration; //Retornamos la duración de esta ejecución
        };
        
         //Creamos el callable del metodo de dijkstra
        Callable<Long> dijkstraTask = () -> {
            long startTime = System.nanoTime(); //Empezar a registrar el tiempo de arranque del proceso del algoritmo
            Dijkstra dijkstra = new Dijkstra(); //Creamos una instancia del algoritmo de dijkstra
            int[] dijkstraResultMatriz = dijkstra.dijkstraMethod(graph, 0); //Buscamos las rutas mas cortas para el nodo fuente
            return System.nanoTime() - startTime; //Retornamos la duración de esta ejecución
        };
        
        //Intentamos ejecutar el bloque de codigo
        try {
            //Con los metodos funcionales que creamos anteriormente
            //Vamos a recuperar su resultado en Future<Long>
            //Esto sirve como una representacion del futuro resultado de esa ejecución
            //Lo que hace que puedas continuar con el codigo mientras otras cosas se ejecutan para obtener el resultado mas adelante
            Future<Long> floydResult = executor.submit(floydTask); //Devolvemos el valor del metodo floyd
            Future<Long> dijkstraResult = executor.submit(dijkstraTask); //Devolvemos el valor del metodo dijkstra
            
            Floyd floyd = new Floyd(); //Creamos una instancia del algoritmo de floyd
            int[][] floydResultMatrix = floyd.floydWarshall(graph, size); //Buscamos las rutas mas cortas entre los pares de nodos
            floyd.printSolution(floydResultMatrix, size); //Mostrmaos en pantalla el grafo despues del algoritmo de floyd
            System.out.println("Floyd-Warshall Time: " + floydResult.get() + " ns"); //Mostramos el tiempo de ejecución
            
            System.out.println(""); //Espacio
            System.out.println(""); //Espacio
            
            Dijkstra dijkstra = new Dijkstra(); //Creamos una instancia del algoritmo de dijkstra
            int[] dijkstraResultMatriz = dijkstra.dijkstraMethod(graph, 0); //Buscamos las rutas mas cortas para el nodo fuente
            dijkstra.printDistances(dijkstraResultMatriz); //Mostrmaos en pantalla el arreglo de las distancias del metood de dijsktra
            System.out.println("Dijkstra Time: " + dijkstraResult.get() + " ns"); //Mostramos el tiempo de ejecución
         
        //Agarrar cualquier excepcion que suceda en caso de algun error imprevisto   
        } catch (Exception e) {
            e.printStackTrace(); //Mostramos en pantalla la ruta del error
        } finally { //Finalmente despues del bloque de codigo de Try-catch
            executor.shutdown(); //Cerramos el executor para liberar recursos y terminarlo correctamente 
        }
    }
    
    /**
     * Metodo generateGraph : generar un grafo con nodos aleatorios pero respetando ciertas logicas
     * @param size : el tamaño nxn que se espera del grafo
     * @return : el grafo creado
     */
    private static int[][] generateGraph(int size) {
        Random random = new Random(); //Creamos una instancia de random para los valores aleatorios
        int[][] graph = new int[size][size]; //Creamos la plantilla de la matriz para guardar cada nodo en su lugar
        
        //Creamos un bucle anidado para recorrer coompletamente el grafo
        //Creamos el bucle i para recorrer las filas de la matriz
        for (int i = 0; i < size; i++) {    
            //Creamos el bucle j para recorrer las columnas de la matriz
            for (int j = 0; j < size; j++) {
                //Si tanto i y j coinciden quiere decir que se trata del una posicion como 0 0 1 1 
                if (i == j) {
                    graph[i][j] = 0; //Las distnacias entre si mismos siempre sera de 0 en el nodo
                } else { //De lo contrario si son diferentes....
                    graph[i][j] = random.nextInt(10) + 1; //Asignamos un numero aleatorio entre el 1 al 10
                }
            }
        }

        return graph; //Devolvemos el grafo recien creado con valores aleatorios
    }
    
    /**
     * Metodo printGraph : mostrar en pantalla un grafo
     * @param graph : el grafo que queremos imprimir
     */
    private static void printGraph(int[][] graph) {
        System.out.println("Grafo original (matriz de adyacencia):"); //Cabezera del grafo original
        //Creamos un ciclo for anidado
        //Creamos el ciclo i para recorrer las filas de la matriz
        //Inicia en 0, condicion de menor del tamaño de la matriz y avanza en uno en uno
        for (int i = 0; i < graph.length; i++) {
            System.out.print("Fila " + i + ": "); //Mostramos la fila en la que nos encotramos
            //Creamos un ciclo interno para recorrer las columnas del grafo
            //Inicia en 0 y la condicion es que sea menor al tamaño de la fila y avanza en uno en uno
            for (int j = 0; j < graph[i].length; j++) {
                System.out.print(graph[i][j] + " "); //Mostramos en pantalla el nodo del grafo correspondiente
            }
            System.out.println(); //Espacio para mantener el orden en la impreison
        }
    }
}