package DBObjetos;

import java.time.LocalDateTime;

public interface IVenta {

  void realizarVenta (int usuarioID, LocalDateTime fechaVenta, double precioTotal);
}
