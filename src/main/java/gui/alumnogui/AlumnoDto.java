/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.alumnogui;

/**
 *
 * @author Ezequiel
 */
public class AlumnoDto {

    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fecNac;
    private String promedio;
    private String cantMatAprob;
    private LocalDate fecIng;
    private String estado;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFecNac() {
        return fecNac;
    }

    public void setFecNac(LocalDate fecNac) {
        this.fecNac = fecNac;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    public String getCantMatAprob() {
        return cantMatAprob;
    }

    public void setCantMatAprob(String cantMatAprob) {
        this.cantMatAprob = cantMatAprob;
    }

    public LocalDate getFecIng() {
        return fecIng;
    }

    public void setFecIng(LocalDate fecIng) {
        this.fecIng = fecIng;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
