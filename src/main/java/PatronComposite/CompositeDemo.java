package PatronComposite;

/**
 * Clase demostrativa para el uso del patrón Composite.
 */
public class CompositeDemo {
    
    /**
     * Método principal para probar el patrón Composite.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("===== PATRÓN COMPOSITE - CATÁLOGO DE PRODUCTOS =====");
        
        // Obtener la instancia del gestor de catálogo
        CatalogoManager manager = CatalogoManager.getInstance();
        
        // Mostrar el catálogo completo
        System.out.println("\nCatálogo de productos:");
        System.out.println(manager.getCatalogoRaiz().mostrar(0));
        
        // Mostrar los productos de una categoría específica
        int areaId = 1; // Ejemplo: Lácteos
        CategoriaComposite categoria = manager.getCategoriaPorAreaId(areaId);
        if (categoria != null) {
            System.out.println("\nProductos de la categoría " + categoria.getNombre() + ":");
            for (ProductoLeaf producto : manager.obtenerProductosPorCategoria(areaId)) {
                System.out.println("- " + producto.mostrar());
            }
        }
        
        // Buscar productos por nombre
        String busqueda = "leche";
        System.out.println("\nBúsqueda de productos con '" + busqueda + "':");
        for (ProductoLeaf producto : manager.buscarProductosPorNombre(busqueda)) {
            System.out.println("- " + producto.mostrar());
        }
        
        // Calcular el valor total del inventario
        System.out.println("\nValor total del inventario: $" + 
                String.format("%.2f", manager.calcularValorTotalInventario()));
        
        // Cerrar la conexión al finalizar
        manager.cerrarConexion();
    }
}