package VentaMemento;

import Venta.Cobro;

public class CobroOriginator {
    private Cobro cobro;

    public CobroOriginator(Cobro cobro) {
        this.cobro = cobro;
    }

    public CobroMemento save() {
        return new CobroMemento(
            cobro.getPre(),
            cobro.getCambio(),
            cobro.getPrecioEnLetras(),
            cobro.getRecibi(),
            cobro.isEfectivoSeleccionado(),
            cobro.isTarjetaSeleccionada(),
            cobro.getCorreo()
        );
    }

    public void restore(CobroMemento memento) {
        cobro.setPre(memento.getPre());
        cobro.setCambio(memento.getCambio());
        cobro.setPrecioEnLetras(memento.getPrecioEnLetras());
        cobro.setRecibi(memento.getRecibi());
        cobro.setEfectivoSeleccionado(memento.isEfectivoSeleccionado());
        cobro.setTarjetaSeleccionada(memento.isTarjetaSeleccionada());
        cobro.setCorreo(memento.getCorreo());
    }
}