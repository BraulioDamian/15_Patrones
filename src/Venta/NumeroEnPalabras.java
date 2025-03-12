package Venta;


import java.text.DecimalFormat;

public class NumeroEnPalabras {

    private static final String[] UNIDADES = {
        "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve",
        "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"
    };

    private static final String[] DECENAS = {
        "", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };

    private static final String[] CENTENAS = {
        "", "cien", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    private String convertirMenosDeMil(int numero) {
        if (numero == 0) return "cero";

        String palabra = "";

        if (numero % 100 < 20) {
            palabra = UNIDADES[numero % 100];
            numero /= 100;
        } else {
            palabra = UNIDADES[numero % 10];
            numero /= 10;

            palabra = DECENAS[numero % 10] + (palabra.isEmpty() ? "" : " y " + palabra);
            numero /= 10;
        }

        if (numero == 0) return palabra;
        return CENTENAS[numero] + (palabra.isEmpty() ? "" : " " + palabra);
    }

    public String convertir(double numero) {
        if (numero == 0) { return "cero"; }

        String snumber = Double.toString(numero);
        String[] partes = snumber.split("\\.");

        int parteEntera = Integer.parseInt(partes[0]);
        String parteDecimal = partes.length > 1 ? partes[1] : "0";

        String resultado = convertirParteEntera(parteEntera) + " con " + convertirParteDecimal(parteDecimal);
        return resultado.trim();
    }

    private String convertirParteEntera(int numero) {
        if (numero == 0) { return "cero"; }

        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        String snumber = df.format(numero);

        int millones = Integer.parseInt(snumber.substring(0, 6));
        int miles = Integer.parseInt(snumber.substring(6, 9));
        int cientos = Integer.parseInt(snumber.substring(9, 12));

        String tradMillones;
        if (millones == 1) {
            tradMillones = "un millón ";
        } else if (millones > 1) {
            tradMillones = convertirMenosDeMil(millones) + " millones ";
        } else {
            tradMillones = "";
        }

        String tradMiles = miles == 0 ? "" : convertirMenosDeMil(miles) + " mil ";
        String tradCientos = convertirMenosDeMil(cientos);

        return tradMillones + tradMiles + tradCientos;
    }

    private String convertirParteDecimal(String parteDecimal) {
        if (parteDecimal.length() == 1) {
            parteDecimal += "0";
        } else if (parteDecimal.length() > 2) {
            parteDecimal = parteDecimal.substring(0, 2);
        }
        return convertirMenosDeMil(Integer.parseInt(parteDecimal));
    }
}

