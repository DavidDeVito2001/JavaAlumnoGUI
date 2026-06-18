/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.alumnogui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import persona.Alumno;

/**
 *
 * @author g.guzman
 */
@SuppressWarnings("serial")
public class AlumnosModel extends AbstractTableModel {

    private static final int COL_DNI = 0;
    private static final int COL_APELLIDO = 1;
    private static final int COL_NOMBRE = 2;
    private static final int COL_ESTADO = 3;

    private static final String[] ENCABEZADOS = {
        "DNI", "APELLIDO", "NOMBRE", "ESTADO"
    };

    private List<Alumno> alumnos = new ArrayList<>();

    public void setAlumnos(List<Alumno> alumnos) {
        if (alumnos == null) {
            this.alumnos = new ArrayList<>();
        } else {
            this.alumnos = alumnos;
        }

        fireTableDataChanged();
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public Alumno getAlumnoAt(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= alumnos.size()) {
            return null;
        }

        return alumnos.get(rowIndex);
    }

    public void limpiar() {
        alumnos.clear();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return alumnos.size();
    }

    @Override
    public int getColumnCount() {
        return ENCABEZADOS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Alumno alumno = alumnos.get(rowIndex);

        return switch (columnIndex) {
            case COL_DNI -> alumno.getDni();
            case COL_APELLIDO -> alumno.getApellido();
            case COL_NOMBRE -> alumno.getNombre();
            case COL_ESTADO -> alumno.getEstado();
            default -> "";
        };
    }

    @Override
    public String getColumnName(int column) {
        return ENCABEZADOS[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case COL_DNI -> Integer.class;
            case COL_APELLIDO, COL_NOMBRE, COL_ESTADO -> String.class;
            default -> Object.class;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
