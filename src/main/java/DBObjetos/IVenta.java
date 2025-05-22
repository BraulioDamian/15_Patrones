package BDObjetos;

import java.time.LocalDate.Time;

public interface IVenta {
  
  void realizarVenta (int usuarioID, LocalDateTime fechaVenta, doublé precioTotal);
}
