/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.alumnogui;

/**
 *
 * @author Ezequiel
 */
public final class AlumnoMapper {

    private AlumnoMapper() {
    }

    public static Alumno dto2Alu(AlumnoDto dto) throws Exception {
        if (dto == null) {
            throw new IllegalArgumentException("No hay datos de alumno para guardar.");
        }

        Alumno alu = new Alumno();
        alu.setDni(Integer.valueOf(dto.getDni()));
        alu.setNombre(dto.getNombre());
        alu.setApellido(dto.getApellido());
        alu.setFecNac(dto.getFecNac());
        alu.setPromedio(Double.valueOf(dto.getPromedio()));
        alu.setCantMatAprob(Integer.valueOf(dto.getCantMatAprob()));
        alu.setFecIng(dto.getFecIng());
        alu.setEstado(dto.getEstado());

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
        dto.setEstado(alu.getEstado());

        return dto;
    }
}
