package dev.eder.qhatu_api.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Los precios YA incluyen IGV (como en cualquier tienda). De ahí se saca la base y el IGV.
public final class CalculadoraIgv {

    public static final BigDecimal TASA_IGV = new BigDecimal("0.18");
    public static final BigDecimal FACTOR_IGV = BigDecimal.ONE.add(TASA_IGV);   // 1.18
    public static final int DECIMALES_DINERO = 2;
    public static final int DECIMALES_CANTIDAD = 3;

    private CalculadoraIgv(){}
    
    // importe = precio x cantidad - descuento: 8.50 x 1.750 = 14.875 -> 14.88
    public static BigDecimal calcularImporte(BigDecimal precioUnitario, BigDecimal cantidad, BigDecimal descuento){
        return redondear(precioUnitario.multiply(cantidad).subtract(descuento));
    }


    // Base sin IGV: 14.88 / 1.18 = 12.61
    public static BigDecimal calcularBase(BigDecimal importeConIgv){
        return importeConIgv.divide(FACTOR_IGV,DECIMALES_DINERO, RoundingMode.HALF_UP);
    }

    // IGV por resta, para que base + igv = importe exacto
    public static BigDecimal calcularIgv(BigDecimal importConIgv, BigDecimal base){
        return redondear(importConIgv.subtract(base));
    }

    /* --------------------------------------------------------------------------- */

    public static BigDecimal redondear(BigDecimal monto){
        return monto.setScale(DECIMALES_DINERO, RoundingMode.HALF_UP);
    }
    public static BigDecimal redondearCantidad(BigDecimal cantidad){
        return  cantidad.setScale(DECIMALES_CANTIDAD, RoundingMode.HALF_UP);
    }

    // 2.000 -> true | 2.500 -> false
    public static  boolean esEntero(BigDecimal valor){
        return  valor.stripTrailingZeros().scale()<=0;
    }

    public static BigDecimal cero(){
        return BigDecimal.ZERO.setScale(DECIMALES_DINERO);
    }

}
