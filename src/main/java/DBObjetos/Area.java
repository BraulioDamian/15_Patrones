package DBObjetos;


public class Area  {
    private int areaID;
    private String nombreArea;
    private String descripcion;

    // Constructor
    public Area(int areaID, String nombreArea, String descripcion) {
        this.areaID = areaID;
        this.nombreArea = nombreArea;
        this.descripcion = descripcion;
    }

    public Area() {

    }
    // Getters
    // Getters
    public int getAreaID() {
        return areaID;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public void setNombreArea(String nombre) {
        this.nombreArea = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // toString

    @Override
    public String toString() {
        return "Area{" +
                "areaID=" + areaID +
                ", nombreArea='" + nombreArea + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

}