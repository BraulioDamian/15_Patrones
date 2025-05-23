package DBObjetos;

import java.util.HashMap;
import java.util.Map;

public class ProductoMetadataFactory {
    private static final Map<String, ProductoMetadata> metadataCache = new HashMap<>();
    
    public static ProductoMetadata getMetadata(String nombre, String descripcion, String marca,
                                             String contenido, String tamañoNeto, String codigoBarras) {
        String key = nombre + codigoBarras; // Clave única para identificar el metadata
        
        if (!metadataCache.containsKey(key)) {
            metadataCache.put(key, new ProductoMetadata(
                nombre, descripcion, marca, contenido, tamañoNeto, codigoBarras));
        }
        
        return metadataCache.get(key);
    }
    
    // Método para limpiar la cache si es necesario
    public static void clearCache() {
        metadataCache.clear();
    }
}