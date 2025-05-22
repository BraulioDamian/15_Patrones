package PatronComposite;

import DBObjetos.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase hoja para el patrón Composite.
 * Representa un producto individual.
 */
public class ProductoLeaf extends CategoriaComponent {
    
    private Producto producto;
    
    /**
     * Constructor que crea un ProductoLeaf desde un objeto Producto.
     * 
     * @param producto Objeto Producto
     * @throws IllegalArgumentException Si el producto es nulo o no tiene nombre
     */
    public ProductoLeaf(Producto producto) {
        super(producto != null ? (producto.getNombre() != null ? producto.getNombre() : "Sin nombre") : "Sin nombre", 
              producto != null ? (producto.getDescripcion() != null ? producto.getDescripcion() : "Sin descripción") : "Sin descripción");
        
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        
        this.producto = producto;
    }
    
    /**
     * Constructor que crea un ProductoLeaf con información básica.
     * 
     * @param nombre Nombre del producto
     * @param descripcion Descripción del producto
     * @param precio Precio del producto
     * @param unidades Unidades disponibles
     * @throws IllegalArgumentException Si el nombre es nulo o vacío
     */
    public ProductoLeaf(String nombre, String descripcion, double precio, int unidades) {
        super(nombre != null && !nombre.trim().isEmpty() ? nombre : "Sin nombre", 
              descripcion != null ? descripcion : "Sin descripción");
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío");
        }
        
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        
        if (unidades < 0) {
            throw new IllegalArgumentException("Las unidades disponibles no pueden ser negativas");
        }
        
        this.producto = new Producto.ProductoBuilder()
            .nombre(nombre)
            .descripcion(descripcion)
            .precio(precio)
            .unidadesDisponibles(unidades)
            .build();
    }
    
    /**
     * Obtiene el objeto Producto asociado.
     * 
     * @return Objeto Producto
     */
    public Producto getProducto() {
        return producto;
    }
    
    /**
     * Obtiene el precio del producto.
     * 
     * @return Precio del producto
     */
    public double getPrecio() {
        return producto != null ? producto.getPrecio() : 0.0;
    }
    
    /**
     * Obtiene las unidades disponibles.
     * 
     * @return Unidades disponibles
     */
    public int getUnidadesDisponibles() {
        return producto != null ? producto.getUnidadesDisponibles() : 0;
    }
    
    @Override
    public boolean esHoja() {
        return true;
    }
    
    @Override
    public String mostrar() {
        if (producto == null) {
            return nombre + " (Producto sin datos)"; 
        }
        return nombre + " - $" + String.format("%.2f", producto.getPrecio()) + " (" + producto.getUnidadesDisponibles() + 
                " unidades disponibles)";
    }
    
    @Override
    public String mostrar(int indentacion) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentacion; i++) {
            sb.append("  ");
        }
        sb.append("- ").append(mostrar());
        return sb.toString();
    }
    
    @Override
    public int contarComponentes() {
        return 1;
    }
    
    @Override
    public void agregar(CategoriaComponent componente) {
        throw new UnsupportedOperationException("No se pueden agregar componentes a un producto");
    }
    
    @Override
    public void eliminar(CategoriaComponent componente) {
        throw new UnsupportedOperationException("No se pueden eliminar componentes de un producto");
    }
    
    @Override
    public CategoriaComponent getHijo(int indice) {
        throw new UnsupportedOperationException("Un producto no tiene hijos");
    }
    
    @Override
    public List<ProductoLeaf> buscarProductosPorNombre(String textoBusqueda) {
        List<ProductoLeaf> resultado = new ArrayList<>();
        
        // Si el texto de búsqueda es nulo o vacío, retornar lista vacía
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            return resultado;
        }
        
        // Si el nombre del producto contiene el texto de búsqueda
        if (this.nombre != null && this.nombre.toLowerCase().contains(textoBusqueda.toLowerCase())) {
            resultado.add(this);
        } 
        // Si el nombre es nulo pero la descripción contiene el texto
        else if (this.nombre == null && this.descripcion != null && 
                 this.descripcion.toLowerCase().contains(textoBusqueda.toLowerCase())) {
            resultado.add(this);
        }
        
        return resultado;
    }
    
    @Override
    public double calcularValorInventario() {
        if (producto == null) {
            return 0.0;
        }
        return producto.getPrecio() * producto.getUnidadesDisponibles();
    }
    
    @Override
    public List<ProductoLeaf> obtenerTodosLosProductos() {
        List<ProductoLeaf> resultado = new ArrayList<>();
        resultado.add(this);
        return resultado;
    }
    
    /**
     * Sobreescribe el método toString para mostrar el nombre del producto
     * en los componentes visuales como JTree.
     * 
     * @return Nombre del producto
     */
    @Override
    public String toString() {
        return nombre != null ? nombre : "Producto sin nombre";
    }
}