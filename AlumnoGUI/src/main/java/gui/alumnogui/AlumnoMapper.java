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

        Alumno alu = new Alumno();
        alu.setDni(Integer.parseInt(dto.getDni()));
        alu.setNombre(dto.getNombre());
        alu.setApellido(dto.getApellido());
        alu.setFecNac(dto.getFecNac());
        alu.setPromedio(parseDouble(dto.getPromedio()));
        alu.setCantMatAprob(parseShort(dto.getCantMatAprob()));
        alu.setFecIng(dto.getFecIng());
        alu.setEstado(parseChar(dto.getEstado(), ESTADO_POR_DEFECTO));

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

    private static double parseDouble(String value) {
        return isBlank(value) ? 0d : Double.parseDouble(value);
    }

    private static short parseShort(String value) {
        return isBlank(value) ? 0 : Short.parseShort(value);
    }

    private static char parseChar(String value, char porDefecto) {
        return isBlank(value) ? porDefecto : value.charAt(0);
    }
}
