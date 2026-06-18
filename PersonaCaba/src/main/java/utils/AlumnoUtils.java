/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import exceptions.DniInvalidoException;
import exceptions.NombreInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import persona.Alumno;

/**
 *
 * @author g.guzman
 */
public final class AlumnoUtils {

    public static final String TAB = "\t";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private AlumnoUtils() {
    }
    
    public static String alu2Str(Alumno alu) {
        String dni = StringUtils.leftPad(String.valueOf(alu.getDni()), 8, '0');

        return dni + TAB
                + safe(alu.getNombre()) + TAB
                + safe(alu.getApellido()) + TAB
                + formatDate(alu.getFecNac()) + TAB
                + alu.getPromedio() + TAB
                + alu.getCantMatAprob() + TAB
                + formatDate(alu.getFecIng()) + TAB
                + estadoOrDefault(alu.getEstado());
    }
    
    public static Alumno str2Alu(String[] campos) throws DniInvalidoException, NombreInvalidoException {
        Alumno alu = new Alumno();
        int index = 0;
        alu.setDni(Integer.valueOf(campos[index++]));
        alu.setNombre(campos[index++].trim());
        alu.setApellido(campos[index++].trim());

        if (campos.length == 4) {
            alu.setFecIng(parseDate(campos[index++]));
            alu.setEstado('A');
            return alu;
        }

        alu.setFecNac(parseDate(campos[index++]));
        alu.setPromedio(Double.parseDouble(campos[index++].trim()));
        alu.setCantMatAprob(Short.parseShort(campos[index++].trim()));
        alu.setFecIng(parseDate(campos[index++]));
        alu.setEstado(campos[index++].trim().charAt(0));
        
        return alu;
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private static String formatDate(LocalDate date) {
        return date == null ? "" : date.format(DATE_FORMATTER);
    }

    private static LocalDate parseDate(String value) {
        String cleanValue = value == null ? "" : value.trim();
        return cleanValue.isEmpty() ? null : LocalDate.parse(cleanValue, DATE_FORMATTER);
    }

    private static char estadoOrDefault(char estado) {
        return estado == '\0' ? 'A' : estado;
    }
}
