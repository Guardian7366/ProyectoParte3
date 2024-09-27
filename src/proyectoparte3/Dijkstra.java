package proyectoparte3;

/**
 * Clase Dijkstra : contendra los metodos para ejecutar el algoritmo de Dijkstra
 * @author David Silva
 */
public class Dijkstra {

    /**
     * Metodo dijkstraMethod : Encontrar las distancias mas cortas entre los nodos de un grafo
     * @param graph : el grafo que se pasara al algoritmo para sacar 
     * @param source : desde el nodo donde se desea calcular las distancias mas cortas
     * @return : arreglo que contendra las distancias mas cortas para el nodo de fuente que se quiso encontrar la ruta mas corta hacia los otros nodos
     */
    public int[] dijkstraMethod(int[][] graph, int source) {
        int size = graph.length; //Obtenemos el tamaño de la lista y lo guardamos en la variable size
        int[] distances = new int[size]; //Arreglo que nos funcionara para almacenar las distancias cortas entre el nodo fuente al nodo de destino
        boolean[] visited = new boolean[size]; //Un arreglo de booleanos para mcarcar si un nodo ya ha sido visitado o todavia esta disponible

        // Inicializamos las distancias como infinitas y el nodo fuente como 0
        
        //Creamos un ciclo para rellenar los arreglos anteriormente creados
        //Esto se hara para cada nodo del grafo que tengamos
        //En este ciclo se itera desde 0, la condicion es que sea menor al tamaño del grafo y que se incremente de uno en uno
        for (int i = 0; i < size; i++) {
            //Es importante que dentro del algoritmo de Dijkstra todos los nodos comienzen siendo infinitos
            //Esto porque al inicio no se conoce ninguna distancia corrta entre algunos de los nodos excepto el nodo fuente
            //Al poner el Max_Value estamos haciendo que cualquier distancia que saquemos mas adelante
            //Siempre sera menor que el valor iniciar, lo que ayuda a que podmoas actualizar las distancias en lo que se descruben caminos mas cortos
            distances[i] = Integer.MAX_VALUE; //Actualizamos el valor del nodo a uno infinito de momento
            
            //Tambien para cada nodo se va establecer desde un inicio que no ha sido visitado
            //Esto nos ayuda a mantener un orden de los nodos que ya fueron consultados por asi decirlo
            //Al inicio todos los nodos estan en no visitado, pero con el timepo cada nodo sera visitado
            visited[i] = false; //Establecemos ese nodo como falso
        }
        
        //Despues de que terminemos con el bucle, tenemos que establecer la distancia al nodo fuente que sea de 0
        //Esto es muy importante y logico a la vez, porque la distancia hacia el mismo nodo siempre sera de 0
        //Sera nuestro punto de partida para calcular las distancias mas cortas entre los nodos
        //Y de esta manera daremos comienzo a nuestro algoritmo para empezara encontrar los caminos mas cortos
        distances[source] = 0; //Establecemos el nodo de origen con una distancia hacia el mismo de 0
        
        
        //Lo que sigue ahora es el nucleo del algoritmo de dijkstra
        //En esta parte del codigo se requiere iterar sobre todos los nodos para determinar las distancias mas cortas del nodo fuente hacia los otros nodos
        //Creamos un bucle anidado
        //En este primer bucle se inicia con un contador en 0, con la condicion de que sea menor que el tamaño menos 1 y avanza en uno en uno
        //Esto ayuda a que todos los nodos sean consultados una vez, exceptuando el nodo fuente
        for (int count = 0; count < size - 1; count++) {
            int u = minDistance(distances, visited); //Mediante la llamada a este metodo es que sacamos el nodo con menor distancia al nodo fuente
            //Este nodo que saquemos sera el siguiente que consultaremos ya que es el mas cercano que aun no visitamos
            //Ya que seleccionamos el nodo, lo tenemos que marcar como true para que no se vuelva a procesar
            //Evitaremos calcular las distancias de este nodo para futuras consultas
            visited[u] = true; //Lo establecemos como ya visitado al nodo

            //Este bucle interno recorre todos los nodos del grafo
            //Eate bucle nos ayuda a ver si en x nodo se puede actualizar la distancia minima desdel nodo fuente que pase por el nodo u (osea el de antes)
            //En este bucle iniciamos con el iterador en 0, la condicion que sea menor al tamaño del grafo y avanza en uno en uno
            for (int v = 0; v < size; v++) {
                
                //Ahora nos toca hacer varias verificaciones para actualizar los valores
                //La primera verificación es si actualizamos el nodo solamente si todavia no ha sido visitado
                //De lo contrario no se necesita actualizar su distancia
                //La segunda se trata de actualizar la distancia entre nodos existe un camino directo
                //De lo contrario significa que no existe alguna conexion directa entre los nodos
                //La tercera se trata de ver si el nodo tiene una distancia valida osea que no es infinito
                //De lo contrario significa que todavia no encontramos un camino directo hacie el nodo u hasta el nodo fuente
                //Por lo que no se puede usar para actualizar la distancia
                //La cuarta se trata de verificar si la distancia atra vez del nodo u es menor que la distancia actual del nodo
                //Si todas estas condiciones se cumplen significa que hemos encontrado un camino mas corta y se debe de actualizar
                if (!visited[v] && graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE &&
                    distances[u] + graph[u][v] < distances[v]) {
                    
                    //Como todas las condiciones se cumplieron ahora podemos actualizarlo a una distancia mas corta el nodo
                    distances[v] = distances[u] + graph[u][v]; //Hacemos los calculos para actualizar el nodo que pase por el nodo u
                }
            }
        }
        
        //Una vez que todo el algoritmo principal termine tenemos que retornar el arreglo con las distancias mas cortas del nodo fuente hacia los demas nodos
        return distances; //Retornamos las distancias mas cortas
    }

    /**
     * Metodo minDistance : Ayuda al metodo principal a encontrar el nodo no visita y con la distancia mas corta del nodo fuente
     * @param distances : Arreglo de nodos con las distancias actuales desde el nodo fuente hacia cada nodo
     * @param visited : Arreglo con los estados de cada nodo si fue visitado o no todavia
     * @return : devolvemos el indiice del nodo con la distancia mas corta que se logro encontrar
     */
    private int minDistance(int[] distances, boolean[] visited) {
        //Esto nos ayuda a segurar que cualquier distancia de infinito sera consideerado como la nueva distancia minima en la busqueda
        int min = Integer.MAX_VALUE; //Iniciamos la variable minima con un valor infinito
        
        //Esta varaible nos ayuda para almancear el indice del nodo que tenga la menor distancia encontrada
        //Esto se hace para que si al final del bucle no se encuentra ningun nodo no visitado
        //Nos ayudara a indicar que no existen mas nodos por procesar
        int minIndex = -1; //Iniciamos esta variable en -1
        
        //Creamos un bucle que nos ayudara para recorrer todos los nodos que esten en el arreglo de distances
        //Este bucle nos ayudara para ver la distancia minima de un nodo y si aun no ha sido visitado
        //Creamos el ciclo con un iterado de 0, con la condicion de que sea menor que el tamaño de la lista y avanza en uno en uno
        for (int v = 0; v < distances.length; v++) {
            
            //Aqui tenemos que hacer varias verificaciones
            //La primera es checar si es nodo aun no ha sido visitado, si ya fue visitado entonces no se considera la el nodo
            //La segunda es ver si la distancia actual de ese nodo es menor a la distancia que ya tenemos en minim
            //Esto nos ayuda a er los nodos que no han sido visitado y que encima tengan una menor distancia encontrada
            if (!visited[v] && distances[v] <= min) {
                min = distances[v]; //Actualizamos en minimo con el valor que tenga el nodo
                minIndex = v; //Actualizamos el indice del nodo que tenga la distancia mas corta
            }
        }
        //Despues de recorrer todos los nodos, ahora retornamos el indice del nodo con la distancia mas corta
        return minIndex; //Este indice se usara en el metodo principal para actualizar los valores de los nodos vecinos
    }

    // Imprime las distancias calculadas desde el nodo fuente
    /**
     * Metodo printDistances : mostrar las distancias mas cortas desde el nodo fuente
     * @param distances : el arreglo con las distancias que obtuvimos del algoritmo
     */
    public void printDistances(int[] distances) {
        System.out.println("Distancias desde el nodo fuente (Dijkstra):"); //Usamos esto como cabezara para luego mostrar los elementos dela rreglo
        //Creamos un ciclo for simple que va a recorrer la lista de inicio a final
        //Este iterador inicia en 0, la condiciuon es menor al tamaño del arreglo y avanza de uno en uno
        for (int i = 0; i < distances.length; i++) {
            //Si el nodo en el que nos encotnramos se trata de un valor infinito, queire decir que no hay manera de llegara ese nodo
            if (distances[i] == Integer.MAX_VALUE) {
                System.out.println("Nodo " + i + " es inaccesible"); //Mostramos en pantalla que ese nodo no se pudo determinar una distancia del nodo fuente
            } else { //De lo contrario si todo esta en orden
                System.out.println("Nodo " + i + ": " + distances[i]); //Mostramos el nodo en el que nos contramos y la distancia respectiva al nodo fuente
            }
        }
    }
}