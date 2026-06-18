/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.DniInvalidoException;
import exceptions.NombreInvalidoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persona.Alumno;
import utils.AlumnoUtils;

/**
 *
 * @author g.guzman
 */
public class AlumnoDAOTXT extends DAO<Alumno,Integer> {

    // private static final String CTE = "CTE";
    
    private final RandomAccessFile raf;
    private final String pathfile;
    
    AlumnoDAOTXT(String pathfile) throws DAOException {
        try {
            this.pathfile = pathfile;
            raf = new RandomAccessFile(pathfile, "rws");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }

    @Override
    public void create(Alumno alumno) throws DAOException {
        try {
            if (exist(alumno.getDni())) {
                throw new DAOException("El DNI "+alumno.getDni()+" ya existe");
            }
            raf.seek(raf.length()); // Se posiciona al final del archivo
            raf.writeBytes(AlumnoUtils.alu2Str(alumno)+System.lineSeparator());
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        }
    }

    @Override
    public Alumno read(Integer dni) throws DAOException {
        try {
            raf.seek(0); // e posiciona al inicio del archivo
            String linea;
            Integer dniFile; 
            String[] campos;
            while((linea = raf.readLine())!=null) {
                campos = linea.split(AlumnoUtils.TAB);
                dniFile = Integer.valueOf(campos[0]);
                if (dniFile.equals(dni)) {
                    return AlumnoUtils.str2Alu(campos);
                }
            }
        } catch (IOException | DniInvalidoException | NombreInvalidoException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void update(Alumno alumno) throws DAOException {
        List<Alumno> alumnos = findAll(true);
        boolean actualizado = false;

        for (int i = 0; i < alumnos.size(); i++) {
            if (alumnos.get(i).getDni() == alumno.getDni()) {
                alumnos.set(i, alumno);
                actualizado = true;
                break;
            }
        }

        if (!actualizado) {
            throw new DAOException("No existe alumno con DNI " + alumno.getDni());
        }

        reescribirArchivo(alumnos);
    }

    @Override
    public void delete(Integer dni) throws DAOException {
        Alumno alu = read(dni);
        if (alu == null) {
            throw new DAOException("No existe alumno con DNI " + dni);
        }
        alu.setEstado('B');
        update(alu);
    }

    @Override
    public List<Alumno> findAll(boolean incluirEliminados) throws DAOException {
        List<Alumno> alumnos = new ArrayList<>();
        
        try {
            raf.seek(0);
            String linea;
            while ((linea = raf.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }

                Alumno alumno = AlumnoUtils.str2Alu(linea.split(AlumnoUtils.TAB, -1));
                if (incluirEliminados || alumno.getEstado() != 'B') {
                    alumnos.add(alumno);
                }
            }
        } catch (IOException | DniInvalidoException | NombreInvalidoException | RuntimeException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        }
        
        return alumnos;
    }

    @Override
    public void close() throws DAOException {
        try {
            raf.close();
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        }
    }

    @Override
    public boolean exist(Integer dni) throws DAOException {
        try {
            raf.seek(0); // e posiciona al inicio del archivo
            String linea;
            Integer dniFile; 
            String[] campos;
            while((linea = raf.readLine())!=null) {
//                dniFile = Integer.valueOf(linea.substring(0, 8));

                campos = linea.split(AlumnoUtils.TAB);
                dniFile = Integer.valueOf(campos[0]);
                if (dniFile.equals(dni)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        }
        return false;
    }

    private void reescribirArchivo(List<Alumno> alumnos) throws DAOException {
        try {
            raf.setLength(0);
            raf.seek(0);
            for (Alumno alumno : alumnos) {
                raf.writeBytes(AlumnoUtils.alu2Str(alumno) + System.lineSeparator());
            }
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTXT.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getLocalizedMessage());
        }
    }
    
}
