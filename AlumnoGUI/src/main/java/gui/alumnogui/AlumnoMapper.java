/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.alumnogui;

import exceptions.DniInvalidoException;
import exceptions.NombreInvalidoException;
import persona.Alumno;

/**
 *
 * @author Ezequiel
 */
public final class AlumnoMapper {

    private static final char ESTADO_POR_DEFECTO = 'A';

    private AlumnoMapper() {
    }

    public static Alumno dto2Alu(AlumnoDto dto) throws DniInvalidoException, NombreInvalidoException {
        if (dto == null) {
            throw new IllegalArgumentException("No hay datos de alumno para guardar.");
        }

        validarObligatorio(dto.getDni(), "DNI");
        validarObligatorio(dto.getNombre(), "Nombre");
        validarObligatorio(dto.getApellido(), "Apellido");
        validarObligatorio(dto.getPromedio(), "Promedio");
        validarObligatorio(dto.getCantMatAprob(), "Cantidad de materias aprobadas");
        validarObligatorio(dto.getEstado(), "Estado");
        if (dto.getFecNac() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        if (dto.getFecIng() == null) {
            throw new IllegalArgumentException("La fecha de ingreso es obligatoria.");
        }

        Alumno alu = new Alumno();
        alu.setDni(parseInt(dto.getDni(), "DNI"));
        alu.setNombre(dto.getNombre());
        alu.setApellido(dto.getApellido());
        alu.setFecNac(dto.getFecNac());
        alu.setPromedio(parseDouble(dto.getPromedio(), "Promedio"));
        alu.setCantMatAprob(parseShort(dto.getCantMatAprob(), "Cantidad de materias aprobadas"));
        alu.setFecIng(dto.getFecIng());
        alu.setEstado(parseEstado(dto.getEstado()));

        return alu;
    }

    public static AlumnoDto alu2Dto(Alumno alu) {
        AlumnoDto dto = new AlumnoDto();
        dto.setDni(String.valueOf(alu.getDni()));
        dto.setNombre(alu.getNombre());
        dto.setApellido(alu.getApellido());
        dto.setFecNac(alu.getFecNac());
        dto.setPromedio(String.valueOf(alu.getPromedio()));
        dto.setCantMatAprob(String.valueOf(alu.getCantMatAprob()));
        dto.setFecIng(alu.getFecIng());
        dto.setEstado(String.valueOf(alu.getEstado()));

        return dto;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static void validarObligatorio(String value, String campo) {
        if (isBlank(value)) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
    }

    private static int parseInt(String value, String campo) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser numerico.", ex);
        }
    }

    private static double parseDouble(String value, String campo) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser numerico.", ex);
        }
    }

    private static short parseShort(String value, String campo) {
        try {
            return Short.parseShort(value.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser numerico entero.", ex);
        }
    }

    private static char parseEstado(String value) {
        char estado = isBlank(value) ? ESTADO_POR_DEFECTO : Character.toUpperCase(value.trim().charAt(0));
        if (estado != 'A' && estado != 'B') {
            throw new IllegalArgumentException("El estado debe ser A o B.");
        }

        return estado;
    }
}
