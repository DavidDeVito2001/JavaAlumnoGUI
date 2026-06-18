/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.DniInvalidoException;
import exceptions.NombreInvalidoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persona.Alumno;
import utils.DateUtils;

/**
 *
 * @author g.guzman
 */
public class AlumnoDAOSQL extends DAO<Alumno,Integer> {

    private final Connection connection;
    private final PreparedStatement prepareStatementInsert;
    private final PreparedStatement prepareStatementRead;
    private final PreparedStatement prepareStatementUpdate;
    private final PreparedStatement prepareStatementDelete;
    private final PreparedStatement prepareStatementFindAll;
    private final PreparedStatement prepareStatementFindAllActivos;
    private final PreparedStatement prepareStatementExist;
    
    AlumnoDAOSQL(String url, String user, String pwd) throws DAOException {
        try {
            connection = DriverManager.getConnection(url, user, pwd);
            
            String insertSQL = "INSERT INTO alumnos "
                    + "(DNI, NOMBRE, APELLIDO, FEC_NAC, PROMEDIO, CANT_MAT_APROB, FEC_ING, ESTADO) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            prepareStatementInsert = connection.prepareStatement(insertSQL);
            
            String readSQL = "SELECT * FROM alumnos where DNI = ?;";
            prepareStatementRead = connection.prepareStatement(readSQL);

            String updateSQL = "UPDATE alumnos SET NOMBRE = ?, APELLIDO = ?, FEC_NAC = ?, "
                    + "PROMEDIO = ?, CANT_MAT_APROB = ?, FEC_ING = ?, ESTADO = ? WHERE DNI = ?;";
            prepareStatementUpdate = connection.prepareStatement(updateSQL);

            String deleteSQL = "UPDATE alumnos SET ESTADO = 'B' WHERE DNI = ?;";
            prepareStatementDelete = connection.prepareStatement(deleteSQL);

            String findAllSQL = "SELECT * FROM alumnos ORDER BY APELLIDO, NOMBRE;";
            prepareStatementFindAll = connection.prepareStatement(findAllSQL);

            String findAllActivosSQL = "SELECT * FROM alumnos WHERE ESTADO <> 'B' OR ESTADO IS NULL ORDER BY APELLIDO, NOMBRE;";
            prepareStatementFindAllActivos = connection.prepareStatement(findAllActivosSQL);

            String existSQL = "SELECT 1 FROM alumnos WHERE DNI = ?;";
            prepareStatementExist = connection.prepareStatement(existSQL);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }

    @Override
    public void create(Alumno alumno) throws DAOException {
        try {
            if (exist(alumno.getDni())) {
                throw new DAOException("El DNI " + alumno.getDni() + " ya existe");
            }

            int index = 1;
            prepareStatementInsert.setInt(index++, alumno.getDni());
            prepareStatementInsert.setString(index++, alumno.getNombre());
            prepareStatementInsert.setString(index++, alumno.getApellido());
            prepareStatementInsert.setDate(index++, DateUtils.localDate2SqlDate(alumno.getFecNac()));
            prepareStatementInsert.setDouble(index++, alumno.getPromedio());
            prepareStatementInsert.setShort(index++, alumno.getCantMatAprob());
            prepareStatementInsert.setDate(index++, DateUtils.localDate2SqlDate(alumno.getFecIng()));
            prepareStatementInsert.setString(index++, String.valueOf(estadoOrDefault(alumno.getEstado())));
            
            prepareStatementInsert.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }

    @Override
    public Alumno read(Integer dni) throws DAOException {
        try {
            prepareStatementRead.setInt(1, dni);
            final ResultSet rs = prepareStatementRead.executeQuery();
            if (rs.next()) {
                return rs2Alumno(rs);
            }
        } catch (SQLException | DniInvalidoException | NombreInvalidoException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        
        return null;
    }

    @Override
    public void update(Alumno alumno) throws DAOException {
        try {
            int index = 1;
            prepareStatementUpdate.setString(index++, alumno.getNombre());
            prepareStatementUpdate.setString(index++, alumno.getApellido());
            prepareStatementUpdate.setDate(index++, DateUtils.localDate2SqlDate(alumno.getFecNac()));
            prepareStatementUpdate.setDouble(index++, alumno.getPromedio());
            prepareStatementUpdate.setShort(index++, alumno.getCantMatAprob());
            prepareStatementUpdate.setDate(index++, DateUtils.localDate2SqlDate(alumno.getFecIng()));
            prepareStatementUpdate.setString(index++, String.valueOf(estadoOrDefault(alumno.getEstado())));
            prepareStatementUpdate.setInt(index++, alumno.getDni());

            if (prepareStatementUpdate.executeUpdate() == 0) {
                throw new DAOException("No existe alumno con DNI " + alumno.getDni());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }

    @Override
    public void delete(Integer dni) throws DAOException {
        try {
            prepareStatementDelete.setInt(1, dni);
            if (prepareStatementDelete.executeUpdate() == 0) {
                throw new DAOException("No existe alumno con DNI " + dni);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }

    @Override
    public List<Alumno> findAll(boolean incluirEliminados) throws DAOException {
        List<Alumno> alumnos = new ArrayList<>();
        try {
            PreparedStatement statement = incluirEliminados ? prepareStatementFindAll : prepareStatementFindAllActivos;
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                alumnos.add(rs2Alumno(rs));
            }
        } catch (SQLException | DniInvalidoException | NombreInvalidoException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }

        return alumnos;
    }

    @Override
    public void close() throws DAOException {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }

    @Override
    public boolean exist(Integer id) throws DAOException {
        try {
            prepareStatementExist.setInt(1, id);
            ResultSet rs = prepareStatementExist.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
    }

    private Alumno rs2Alumno(ResultSet rs) throws SQLException, DniInvalidoException, NombreInvalidoException {
        Alumno alu = new Alumno();
        alu.setDni(rs.getInt("DNI"));
        alu.setNombre(rs.getString("NOMBRE"));
        alu.setApellido(rs.getString("APELLIDO"));
        alu.setFecNac(DateUtils.sqlDate2LocalDate(rs.getDate("FEC_NAC")));
        alu.setPromedio(rs.getDouble("PROMEDIO"));
        alu.setCantMatAprob(rs.getShort("CANT_MAT_APROB"));
        alu.setFecIng(DateUtils.sqlDate2LocalDate(rs.getDate("FEC_ING")));
        String estado = rs.getString("ESTADO");
        alu.setEstado(estado == null || estado.isBlank() ? 'A' : estado.charAt(0));

        return alu;
    }

    private char estadoOrDefault(char estado) {
        return estado == '\0' ? 'A' : estado;
    }
    
}
