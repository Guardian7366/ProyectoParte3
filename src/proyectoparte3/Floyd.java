package proyectoparte3;

/**
 * Clase Floyd : contiene el algoritmo de Floyd
 * @author David Silva
 */
public class Floyd {

    /**
     * Metodo floydWarshall : ejecuta el algoritmo de floydWarshall para encontrar los caminos mas cortos entre los nodos en el grafo
     * @param graph : el grafo que se va a analizar para sacar los caminos mas cortos
     * @param size : el numero total de nodos que tiene el grafo
     * @return : e
     */
    public int[][] floydWarshall(int[][] graph, int size) {
        int[][] dist = new int[size][size]; //Creamos una nueva matriz que nos funcionara para guardar las distancias mas cortas que se encuentre entre todos los pares de nodos
        

        //Creamos un bucle anidado que nos ayudara para tener todo el grafo a la mano dentro del metodo
        //Primero hacemos el bucle exterior en el que se recorre cada fila del grafo
        //Iniciamos con el iterador en 0, la condicion es que sea menor al tamaño del grafo y se incremente en uno en uno
        for (int i = 0; i < size; i++) {
            
            //Creamos un buble interior en el que se recorre cada columan del grafo
            //Iniciamos con el iterador en 0, la condicion es que sea menor al tamaño del grafo y se incremente en uno en uno
            for (int j = 0; j < size; j++) {
                // Si no hay camino entre i y j, lo representamos con infinito
                
                //En caso de que tengamos valores infinitos (si pueden ver valores infinitos)
                //La primera verificacion nos sirve para saber si no existen aristas entre los nodos
                //La segunda verificacion nos sirve para saber si estamos tratando con nodos diferentes
                if (graph[i][j] == 0 && i != j) {
                    dist[i][j] = Integer.MAX_VALUE; //A esta casilla decimos que es igual a un valor infinito
                    //Esto se debe a que el algoritmo necesita saber si es posible encontrar un camino para este nodo
                } else { //De lo contrario si todo esta en orden entonces...
                    //Tranferimos los valores del grafo a la matriz del metodo
                    dist[i][j] = graph[i][j]; //Esto se hace con tal de tener indicado que valores son normales positivos y cuales son infinitos
                }
            }
        }

        //Ahora entramos con el algoritmo principal del metodo de floyd
        //Para este algoritmo necesitamos tres bucles anidades que cada uno cumple con algo importante para el algoritmo
        //El bucle de k estara iterando sobre cada nodo que puede servir como nodo intermedio entre el nodo de inicio al nodo destino
        //El bucle de i estara iterando sobre cada nodo que representa el nodo de inicio, el nodo que sera el que busque la distancia mas corta
        //El bucle de j estara iterando sobre cada nodo que representa el nodo de destino, el nodo que  sera el que el nodo de inicia busca
        //Creamos k que inicia de 0, la condición es que sea menos del tamaño y se incremente en uno en uno
        for (int k = 0; k < size; k++) {
            //Creamos j que inicia de 0, la condición es que sea menos del tamaño y se incremente en uno en uno
            for (int i = 0; i < size; i++) {
                //Creamos i que inicia de 0, la condición es que sea menos del tamaño y se incremente en uno en uno
                for (int j = 0; j < size; j++) {
                    
                    //Aqui tenemos que hacer varias verificaciones antes de hacer las asignaciones:
                    
                    //Primera verificación sirve para comprobar si existe alguna distancia valida desde el nodo de inicio al nodo destino
                    //De lo contrario significaria que no existe un camino directo, por lo que ese camino queda descartado
                    
                    //Segunda verificacion sirve para comprobar si existe alguna distancia valida desde el nodo intermedio hasta al nodo destino
                    //De lo contrario significaria que no existe un camino directo, por lo que ese camino queda descartado
                    
                    //Tercera verificacion sirve para saber si ambos caminos son validos y tambien esto estaria verificando si este camino es mas corto
                    //que el actual camino que fue el mas corto durante la iteración
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE 
                        && dist[i][k] + dist[k][j] < dist[i][j]) {
                        
                        //Si las condiciones anteriores se cumplen entonces significa que encontramos un camino mas corto
                        //Entonces tenemos que actualizar ese nodo con sus respectivo nuevo valor
                        dist[i][j] = dist[i][k] + dist[k][j]; //El calculo apartir de la distancia total para ir hasta ese nodo
                        //Tambien puede darse casos en el que algoritmo hace combinaciones multiples, osease  : A a B a C a D para encontrar camino mas cortos
                        //Oh tambien traducido a Nodo 0 a Nodo 1 a Nodo 2 a Nodo 3 para encontrar asi el camino mas corto
                    }
                }
            }
        }
        
        //Al final de que se ejecute el algoritmo principal, terminamos con una matriz con todas las distancias cortas encontradas entre todos los nodos
        return dist; //Retornamos la matriz bidimensional
    }
    
    /**
     * Metodo printSolution : Mostrar en pantalla la mastriz de distancias cortas entre los nodos
     * @param dist : la matriz que se mostrara en pantalla
     * @param size : tamaño de la matriz
     */
    public void printSolution(int[][] dist, int size) {
        System.out.println("Distancias mas cortas (Floyd-Warshall):"); //Especificamos que se trata de una matriz procesada por el algoritmo de Floyd
        
        //Creamos un ciclo anidado
        //El bucle i nos sirve para recorrer cada fila de la matriz
        //EL bucle j nos sirve para recorrer cada columna de la matriz
        //Iniciamos con el iterador en 0, la condición siempre y cuando sea menor al tamaño de la matriz y avanza de uno en uno
        for (int i = 0; i < size; i++) {
            System.out.print("Fila " + i + " : "); //Mostramos en que fila nos encontramos de la matriz
            //Iniciamos con el iterador en 0, la condición siempre y cuando sea menor al tamaño de la matriz y avanza de uno en uno
            for (int j = 0; j < size; j++) {
                //Si durante las iteraciones nos topamos con un valor que sea igual a MAX_VALUE, osease un infinito
                //Esto quiere decir que no existe un camino entre esos nodos por lo que...
                if (dist[i][j] == Integer.MAX_VALUE) {
                    System.out.print("INF "); //Representamos ese valor con un INF de infinito
                } else { //De lo contrario si todo esta en orden entonces...
                    System.out.print(dist[i][j] + " "); //Mostramos el nodo y hacemos un espacio para mantener limpieza en el output
                }
            }
            System.out.println(); //Hacemos una nueva linea para la siguiente fila
        }
    }
}
