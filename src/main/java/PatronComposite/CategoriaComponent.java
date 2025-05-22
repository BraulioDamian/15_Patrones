package PatronComposite;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que define la estructura base para el patrón Composite.
 */
public abstract class CategoriaComponent {
    
    protected String nombre;
    protected String descripcion;
    
    /**
     * Constructor con nombre y descripción.
     * 
     * @param nombre Nombre del componente (no puede ser nulo)
     * @param descripcion Descripción del componente
     */
    public CategoriaComponent(String nombre, String descripcion) {
        this.nombre = (nombre != null) ? nombre : "Sin nombre";
        this.descripcion = (descripcion != null) ? descripcion : "Sin descripción";
    }
    
    /**
     * Obtiene el nombre del componente.
     * 
     * @return Nombre del componente
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene la descripción del componente.
     * 
     * @return Descripción del componente
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Agrega un componente hijo.
     * Este método debe ser implementado por las subclases.
     * 
     * @param componente Componente a agregar
     */
    public abstract void agregar(CategoriaComponent componente);
    
    /**
     * Elimina un componente hijo.
     * Este método debe ser implementado por las subclases.
     * 
     * @param componente Componente a eliminar
     */
    public abstract void eliminar(CategoriaComponent componente);
    
    /**
     * Obtiene un componente hijo por su índice.
     * Este método debe ser implementado por las subclases.
     * 
     * @param indice Índice del componente a obtener
     * @return Componente hijo
     */
    public abstract CategoriaComponent getHijo(int indice);
    
    /**
     * Verifica si es una hoja del árbol.
     * 
     * @return true si es una hoja, false si es un compuesto
     */
    public abstract boolean esHoja();
    
    /**
     * Muestra la representación como cadena del componente.
     * 
     * @return Representación como cadena
     */
    public abstract String mostrar();
    
    /**
     * Muestra la representación como cadena del componente con indentación.
     * 
     * @param indentacion Nivel de indentación
     * @return Representación como cadena con indentación
     */
    public abstract String mostrar(int indentacion);
    
    /**
     * Cuenta el número de componentes en el árbol.
     * 
     * @return Número de componentes
     */
    public abstract int contarComponentes();
    
    /**
     * Busca productos por nombre en el componente y sus hijos.
     * 
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de productos que coinciden
     */
    public List<ProductoLeaf> buscarProductosPorNombre(String nombre) {
        return new ArrayList<>();
    }
    
    /**
     * Calcula el valor total del inventario en el componente y sus hijos.
     * 
     * @return Valor total del inventario
     */
    public double calcularValorInventario() {
        return 0.0;
    }
    
    /**
     * Obtiene todos los productos en el componente y sus hijos.
     * 
     * @return Lista de todos los productos
     */
    public List<ProductoLeaf> obtenerTodosLosProductos() {
        return new ArrayList<>();
    }
}