/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.alumnogui;

import exceptions.DniInvalidoException;
import exceptions.NombreInvalidoException;
import java.time.LocalDate;
import java.time.Period;
import persona.Alumno;

/**
 *
 * @author Ezequiel
 */
public final class AlumnoMapper {

    private static final char ESTADO_POR_DEFECTO = 'A';
    private static final int EDAD_MINIMA = 18;
    private static final double PROMEDIO_MIN = 0;
    private static final double PROMEDIO_MAX = 10;

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
        if (dto.getFecNac() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        if (dto.getFecIng() == null) {
            throw new IllegalArgumentException("La fecha de ingreso es obligatoria.");
        }

        double promedio = parseDouble(dto.getPromedio(), "Promedio");
        if (promedio < PROMEDIO_MIN || promedio > PROMEDIO_MAX) {
            throw new IllegalArgumentException("El promedio debe estar entre 0 y 10.");
        }

        short cantMatAprob = parseShort(dto.getCantMatAprob(), "Cantidad de materias aprobadas");
        if (cantMatAprob < 0) {
            throw new IllegalArgumentException("La cantidad de materias aprobadas no puede ser negativa.");
        }

        LocalDate fecNac = dto.getFecNac();
        LocalDate fecIng = dto.getFecIng();
        if (fecIng.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser posterior a la fecha actual.");
        }
        if (fecIng.isBefore(fecNac)) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser anterior a la fecha de nacimiento.");
        }
        if (Period.between(fecNac, fecIng).getYears() < EDAD_MINIMA) {
            throw new IllegalArgumentException("El alumno debe tener al menos " + EDAD_MINIMA + " años al momento de ingresar.");
        }

        Alumno alu = new Alumno();
        alu.setDni(parseInt(dto.getDni(), "DNI"));
        alu.setNombre(dto.getNombre());
        alu.setApellido(dto.getApellido());
        alu.setFecNac(fecNac);
        alu.setPromedio(promedio);
        alu.setCantMatAprob(cantMatAprob);
        alu.setFecIng(fecIng);
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
