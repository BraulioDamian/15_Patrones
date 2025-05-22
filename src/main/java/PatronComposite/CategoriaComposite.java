package PatronComposite;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase compuesta para el patrón Composite.
 * Representa una categoría que puede contener otras categorías o productos.
 */
public class CategoriaComposite extends CategoriaComponent {
    
    private List<CategoriaComponent> hijos;
    
    /**
     * Constructor que recibe nombre y descripción.
     * 
     * @param nombre Nombre de la categoría
     * @param descripcion Descripción de la categoría
     */
    public CategoriaComposite(String nombre, String descripcion) {
        super(nombre != null ? nombre : "Categoría sin nombre", 
              descripcion != null ? descripcion : "Sin descripción");
        this.hijos = new ArrayList<>();
    }
    
    @Override
    public void agregar(CategoriaComponent componente) {
        hijos.add(componente);
    }
    
    @Override
    public void eliminar(CategoriaComponent componente) {
        hijos.remove(componente);
    }
    
    @Override
    public CategoriaComponent getHijo(int indice) {
        if (indice >= 0 && indice < hijos.size()) {
            return hijos.get(indice);
        }
        return null;
    }
    
    /**
     * Obtiene todos los hijos de esta categoría.
     * 
     * @return Lista de todos los hijos
     */
    public List<CategoriaComponent> getHijos() {
        return new ArrayList<>(hijos);
    }
    
    @Override
    public boolean esHoja() {
        return false;
    }
    
    @Override
    public String mostrar() {
        return nombre + " (" + hijos.size() + " elementos)";
    }
    
    @Override
    public String mostrar(int indentacion) {
        StringBuilder result = new StringBuilder();
        
        // Añadir indentación
        for (int i = 0; i < indentacion; i++) {
            result.append("  ");
        }
        
        // Añadir esta categoría
        result.append("+ ").append(mostrar()).append("\n");
        
        // Añadir hijos con indentación aumentada
        for (CategoriaComponent hijo : hijos) {
            result.append(hijo.mostrar(indentacion + 1));
            if (!hijo.esHoja()) {
                result.append("\n");
            }
        }
        
        return result.toString();
    }
    
    @Override
    public int contarComponentes() {
        int total = 1; // Contar esta categoría
        
        // Sumar el conteo de todos los hijos
        for (CategoriaComponent hijo : hijos) {
            total += hijo.contarComponentes();
        }
        
        return total;
    }
    
    @Override
    public List<ProductoLeaf> buscarProductosPorNombre(String textoBusqueda) {
        List<ProductoLeaf> resultado = new ArrayList<>();
        
        // Si el texto de búsqueda es nulo o vacío, retornar lista vacía
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            return resultado;
        }
        
        try {
            // Buscar en todos los hijos
            for (CategoriaComponent hijo : hijos) {
                if (hijo != null) {
                    List<ProductoLeaf> resultadoHijo = hijo.buscarProductosPorNombre(textoBusqueda);
                    if (resultadoHijo != null) {
                        resultado.addAll(resultadoHijo);
                    }
                }
            }
        } catch (Exception e) {
            // Registrar el error pero continuar con la búsqueda
            System.err.println("Error al buscar productos por nombre: " + e.getMessage());
        }
        
        return resultado;
    }
    
    @Override
    public double calcularValorInventario() {
        double total = 0.0;
        
        try {
            // Sumar el valor de inventario de todos los hijos
            for (CategoriaComponent hijo : hijos) {
                if (hijo != null) {
                    total += hijo.calcularValorInventario();
                }
            }
        } catch (Exception e) {
            // Registrar el error pero continuar con el cálculo
            System.err.println("Error al calcular valor de inventario: " + e.getMessage());
        }
        
        return total;
    }
    
    @Override
    public List<ProductoLeaf> obtenerTodosLosProductos() {
        List<ProductoLeaf> resultado = new ArrayList<>();
        
        try {
            // Obtener productos de todos los hijos
            for (CategoriaComponent hijo : hijos) {
                if (hijo != null) {
                    List<ProductoLeaf> productosHijo = hijo.obtenerTodosLosProductos();
                    if (productosHijo != null) {
                        resultado.addAll(productosHijo);
                    }
                }
            }
        } catch (Exception e) {
            // Registrar el error pero continuar con la operación
            System.err.println("Error al obtener todos los productos: " + e.getMessage());
        }
        
        return resultado;
    }
    
    /**
     * Sobreescribe el método toString para mostrar solo el nombre de la categoría
     * en los componentes visuales como JTree.
     * 
     * @return Nombre de la categoría
     */
    @Override
    public String toString() {
        return nombre != null ? nombre : "Categoría sin nombre";
    }
}