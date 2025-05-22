package PatronComposite;

import ConexionDB.Conexion_DB;
import Consultas.CONSULTASDAO;
import DBObjetos.Area;
import DBObjetos.Producto;
import Integracion.CatalogoNotificacionIntegrador;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Gestor del catálogo de productos utilizando el patrón Composite.
 */
public class CatalogoManager {
    
    private static final Logger LOGGER = Logger.getLogger(CatalogoManager.class.getName());
    private static CatalogoManager instance;
    private CategoriaComposite catalogoRaiz;
    private Map<Integer, CategoriaComposite> mapaCategorias;
    private Connection conexion;
    private CONSULTASDAO consultasDao;
    
    /**
     * Constructor privado para Singleton.
     */
    private CatalogoManager() {
        // Crear la raíz del catálogo
        catalogoRaiz = new CategoriaComposite("Catálogo Completo", "Todos los productos disponibles");
        mapaCategorias = new HashMap<>();
        
        try {
            // Inicializar la conexión a la base de datos
            conexion = Conexion_DB.getConexion();
            consultasDao = new CONSULTASDAO(conexion);
            
            // Cargar el catálogo desde la base de datos
            cargarCatalogo();
            LOGGER.log(Level.INFO, "Catálogo cargado correctamente");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar el Catálogo Manager", e);
            JOptionPane.showMessageDialog(null, 
                    "Error al inicializar el Catálogo Manager: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Obtiene la instancia única de CatalogoManager (patrón Singleton).
     * 
     * @return Instancia única de CatalogoManager
     */
    public static synchronized CatalogoManager getInstance() {
        if (instance == null) {
            try {
                instance = new CatalogoManager();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al crear instancia de CatalogoManager: " + e.getMessage(), e);
                // Crear una instancia con datos de ejemplo en caso de error
                instance = new CatalogoManager(true);
            }
        }
        return instance;
    }
    
    /**
     * Constructor privado alternativo para crear una instancia con datos de ejemplo
     * cuando hay problemas con la base de datos.
     * 
     * @param usarEjemplos true para usar datos de ejemplo
     */
    private CatalogoManager(boolean usarEjemplos) {
        // Crear la raíz del catálogo
        catalogoRaiz = new CategoriaComposite("Catálogo Completo", "Todos los productos disponibles");
        mapaCategorias = new HashMap<>();
        
        // Si se solicita usar ejemplos, llenar con datos de ejemplo
        if (usarEjemplos) {
            LOGGER.log(Level.INFO, "Inicializando CatalogoManager con datos de ejemplo");
            agregarCategoriasEjemplo();
        }
    }
    
    /**
     * Carga el catálogo desde la base de datos.
     * 
     * @throws SQLException Si ocurre un error de SQL
     */
    private void cargarCatalogo() throws SQLException {
        // Limpiar el catálogo antes de cargar
        catalogoRaiz = new CategoriaComposite("Catálogo Completo", "Todos los productos disponibles");
        mapaCategorias.clear();
        
        try {
            // Crear una categoría "Sin clasificar" para productos sin área asignada
            CategoriaComposite sinClasificar = new CategoriaComposite("Sin clasificar", "Productos sin área asignada");
            catalogoRaiz.agregar(sinClasificar);
            mapaCategorias.put(0, sinClasificar); // Usamos 0 como ID para productos sin clasificar
            
            // Cargar áreas
            List<Area> areas = consultasDao.obtenerAreas();
            LOGGER.log(Level.INFO, "Áreas obtenidas: {0}", areas.size());
            
            if (areas.isEmpty()) {
                LOGGER.log(Level.WARNING, "No se encontraron áreas en la base de datos");
                // Crear áreas por defecto si no hay ninguna
                agregarAreasEjemplo();
            } else {
                for (Area area : areas) {
                    if (area.getNombreArea() == null || area.getNombreArea().trim().isEmpty()) {
                        LOGGER.log(Level.WARNING, "Se encontró un área sin nombre (ID: {0})", area.getAreaID());
                        continue; // Saltamos áreas sin nombre
                    }
                    
                    CategoriaComposite categoriaArea = new CategoriaComposite(
                            area.getNombreArea(), 
                            "Productos del área " + area.getNombreArea());
                    
                    catalogoRaiz.agregar(categoriaArea);
                    mapaCategorias.put(area.getAreaID(), categoriaArea);
                }
            }
            
            // Cargar productos con sus áreas
            List<Producto> productos = consultasDao.obtenerProductosConNombreArea();
            LOGGER.log(Level.INFO, "Productos obtenidos: {0}", productos.size());
            
            for (Producto producto : productos) {
                if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                    LOGGER.log(Level.WARNING, "Se encontró un producto sin nombre (ID: {0})", producto.getProductoID());
                    continue; // Saltamos productos sin nombre
                }
                
                ProductoLeaf productoLeaf = new ProductoLeaf(producto);
                
                // Agregar el producto a su categoría correspondiente
                int areaId = producto.getAreaID();
                if (mapaCategorias.containsKey(areaId)) {
                    mapaCategorias.get(areaId).agregar(productoLeaf);
                } else {
                    // Si no existe la categoría, agregar a la categoría "Sin clasificar"
                    mapaCategorias.get(0).agregar(productoLeaf);
                    LOGGER.log(Level.WARNING, "Producto {0} asignado a categoría 'Sin clasificar' porque su área (ID: {1}) no existe", 
                            new Object[]{producto.getNombre(), areaId});
                }
            }
            
            // Si no hay productos, cargar ejemplos
            if (productos.isEmpty()) {
                LOGGER.log(Level.WARNING, "No se encontraron productos en la base de datos");
                agregarProductosEjemplo();
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el catálogo: {0}", e.getMessage());
            // Si ocurre un error, agregar categorías de ejemplo para que la interfaz no falle
            agregarCategoriasEjemplo();
            throw e; // Relanzar la excepción para que el llamador sepa que hubo un error
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado al cargar el catálogo: {0}", e.getMessage());
            agregarCategoriasEjemplo();
            throw new SQLException("Error inesperado: " + e.getMessage(), e);
        }
    }
    
    /**
     * Agrega áreas de ejemplo cuando no hay áreas en la base de datos.
     */
    private void agregarAreasEjemplo() {
        LOGGER.log(Level.INFO, "Agregando áreas de ejemplo");
        
        CategoriaComposite lacteos = new CategoriaComposite("Lácteos", "Productos lácteos");
        CategoriaComposite bebidas = new CategoriaComposite("Bebidas", "Todo tipo de bebidas");
        CategoriaComposite limpieza = new CategoriaComposite("Limpieza", "Productos de limpieza");
        
        catalogoRaiz.agregar(lacteos);
        catalogoRaiz.agregar(bebidas);
        catalogoRaiz.agregar(limpieza);
        
        mapaCategorias.put(1, lacteos);
        mapaCategorias.put(2, bebidas);
        mapaCategorias.put(3, limpieza);
    }
    
    /**
     * Agrega productos de ejemplo cuando no hay productos en la base de datos.
     */
    private void agregarProductosEjemplo() {
        LOGGER.log(Level.INFO, "Agregando productos de ejemplo");
        
        // Solo agregar productos si existen las categorías
        if (mapaCategorias.containsKey(1)) {
            mapaCategorias.get(1).agregar(new ProductoLeaf("Leche", "Leche entera 1L", 25.50, 100));
            mapaCategorias.get(1).agregar(new ProductoLeaf("Queso", "Queso fresco 500g", 45.75, 50));
        }
        
        if (mapaCategorias.containsKey(2)) {
            mapaCategorias.get(2).agregar(new ProductoLeaf("Agua", "Agua purificada 1L", 15.00, 200));
            mapaCategorias.get(2).agregar(new ProductoLeaf("Refresco", "Refresco de cola 600ml", 18.50, 150));
        }
        
        if (mapaCategorias.containsKey(3)) {
            mapaCategorias.get(3).agregar(new ProductoLeaf("Jabón", "Jabón para ropa 1kg", 35.90, 80));
            mapaCategorias.get(3).agregar(new ProductoLeaf("Cloro", "Cloro 1L", 22.30, 120));
        }
    }
    
    /**
     * Agrega categorías y productos de ejemplo en caso de error con la base de datos.
     * Esto permite que la interfaz funcione aún sin conexión a la BD.
     */
    private void agregarCategoriasEjemplo() {
        LOGGER.log(Level.INFO, "Agregando categorías de ejemplo por fallo en la conexión a BD");
        catalogoRaiz = new CategoriaComposite("Catálogo Completo", "Todos los productos disponibles");
        mapaCategorias.clear();
        
        // Crear categorías de ejemplo
        CategoriaComposite lacteos = new CategoriaComposite("Lácteos", "Productos lácteos");
        CategoriaComposite bebidas = new CategoriaComposite("Bebidas", "Todo tipo de bebidas");
        CategoriaComposite limpieza = new CategoriaComposite("Limpieza", "Productos de limpieza");
        
        // Crear productos de ejemplo para cada categoría
        lacteos.agregar(new ProductoLeaf("Leche", "Leche entera 1L", 25.50, 100));
        lacteos.agregar(new ProductoLeaf("Queso", "Queso fresco 500g", 45.75, 50));
        
        bebidas.agregar(new ProductoLeaf("Agua", "Agua purificada 1L", 15.00, 200));
        bebidas.agregar(new ProductoLeaf("Refresco", "Refresco de cola 600ml", 18.50, 150));
        
        limpieza.agregar(new ProductoLeaf("Jabón", "Jabón para ropa 1kg", 35.90, 80));
        limpieza.agregar(new ProductoLeaf("Cloro", "Cloro 1L", 22.30, 120));
        
        // Agregar categorías al catálogo
        catalogoRaiz.agregar(lacteos);
        catalogoRaiz.agregar(bebidas);
        catalogoRaiz.agregar(limpieza);
        
        // Guardar referencias en el mapa
        mapaCategorias.put(1, lacteos);
        mapaCategorias.put(2, bebidas);
        mapaCategorias.put(3, limpieza);
    }
    
    /**
     * Recarga el catálogo desde la base de datos.
     */
    public void recargarCatalogo() {
        try {
            cargarCatalogo();
            JOptionPane.showMessageDialog(null, 
                    "Catálogo recargado correctamente", 
                    "Recarga exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al recargar el catálogo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Obtiene la raíz del catálogo.
     * 
     * @return Categoría raíz del catálogo
     */
    public CategoriaComposite getCatalogoRaiz() {
        return catalogoRaiz;
    }
    
    /**
     * Obtiene una categoría por su ID de área.
     * 
     * @param areaId ID del área
     * @return Categoría correspondiente o null si no existe
     */
    public CategoriaComposite getCategoriaPorAreaId(int areaId) {
        return mapaCategorias.get(areaId);
    }
    
    /**
     * Busca productos en todo el catálogo por nombre.
     * 
     * @param nombre Nombre o parte del nombre del producto
     * @return Lista de productos que coinciden
     */
    public List<ProductoLeaf> buscarProductosPorNombre(String nombre) {
        return catalogoRaiz.buscarProductosPorNombre(nombre);
    }
    
    /**
     * Calcula el valor total del inventario.
     * 
     * @return Valor total del inventario
     */
    public double calcularValorTotalInventario() {
        return catalogoRaiz.calcularValorInventario();
    }
    
    /**
     * Obtiene todos los productos del catálogo.
     * 
     * @return Lista de todos los productos
     */
    public List<ProductoLeaf> obtenerTodosLosProductos() {
        return catalogoRaiz.obtenerTodosLosProductos();
    }
    
    /**
     * Obtiene los productos de una categoría específica.
     * 
     * @param areaId ID del área
     * @return Lista de productos de esa categoría
     */
    public List<ProductoLeaf> obtenerProductosPorCategoria(int areaId) {
        CategoriaComposite categoria = mapaCategorias.get(areaId);
        if (categoria != null) {
            return categoria.obtenerTodosLosProductos();
        }
        return new ArrayList<>();
    }
    
    /**
     * Agrega un nuevo producto a una categoría.
     * 
     * @param producto Producto a agregar
     * @param areaId ID del área
     * @return true si se agregó correctamente, false en caso contrario
     */
    public boolean agregarProducto(Producto producto, int areaId) {
        try {
            // Primero insertamos en la base de datos
            boolean exito = consultasDao.insertarProducto(producto);
            
            if (exito) {
                // Crear el componente hoja
                ProductoLeaf productoLeaf = new ProductoLeaf(producto);
                
                // Agregar a la categoría correspondiente
                if (mapaCategorias.containsKey(areaId)) {
                    mapaCategorias.get(areaId).agregar(productoLeaf);
                } else {
                    // Si no existe la categoría, agregar directamente a la raíz
                    catalogoRaiz.agregar(productoLeaf);
                }
                
                // Notificar la adición del producto usando el integrador Bridge-Composite
                try {
                    CatalogoNotificacionIntegrador integrador = CatalogoNotificacionIntegrador.getInstance();
                    integrador.notificarProductoAgregado(producto, areaId);
                } catch (Exception ne) {
                    // Capturar excepciones de notificación para que no afecten la operación principal
                    LOGGER.log(Level.WARNING, "Error al notificar adición de producto", ne);
                }
            }
            
            return exito;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al agregar producto", e);
            return false;
        }
    }
    
    /**
     * Cierra la conexión a la base de datos.
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                LOGGER.log(Level.INFO, "Conexión a la base de datos cerrada");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cerrar la conexión a la base de datos", e);
        }
    }
    
    /**
     * Método para comprobar si la conexión a la base de datos está activa.
     * 
     * @return true si la conexión está activa, false en caso contrario
     */
    public boolean conexionActiva() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}