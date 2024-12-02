package com.pixearth.idle.models.types;

import com.pixearth.core.utils.LocationJsonUtils;
import org.bukkit.Location;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.json.simple.JSONObject;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Type personnalisé qui permet de transformer une Location en un json et vise versa
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class LocationJsonType implements UserType {

    /**
     * Nom du type
     */
    public static final String CLASS_NAME = "com.pixearth.idle.models.types.LocationJsonType";

    /**
     * Permet de connaitre la classe qui est mappé avec ce type
     *
     * @return  Retourne le nom de la classe
     */
    @Override
    public Class returnedClass() {

        return Location.class;
    }

    /**
     * @return Retourne le type de colonne sur laquelle est mappé la propriété
     */
    @Override
    public int[] sqlTypes() {

        return new int[] {Types.LONGVARCHAR};
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int hashCode(Object o) throws HibernateException {

        return o.hashCode();
    }

    /**
     * Fonction qui est appelée pour set la valeur dans l'objet après la requête
     *
     * @param resultSet     Résultat de la requête
     * @param data          (?)
     * @param sessionImp    (?)
     * @param owner         (?)
     * @return              Retourne l'instance du joueur s'il existe
     *
     * @throws HibernateException   Exception
     * @throws SQLException         Exception
     */
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] data, SharedSessionContractImplementor sessionImp, Object owner) throws HibernateException, SQLException {

        String jsonLocation = resultSet.getString(data[0]);
        if(jsonLocation != null && !jsonLocation.isEmpty()) {
            return LocationJsonUtils.formJson(jsonLocation);
        }

        return null;
    }

    /**
     * Fonction qui est appelée pour set la valeur dans la requête lorsqu'elle est exécutée
     *
     * @param statement     Instance de la requête
     * @param value         Instance de l'objet (dans notre cas une instance de Location)
     * @param index         Position du paramètre dans la requête
     * @param arg3          (?)
     *
     * @throws HibernateException   Exeception
     * @throws SQLException         Exception
     */
    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SharedSessionContractImplementor arg3) throws HibernateException, SQLException {

        if(value == null) {
            statement.setNull(index, Types.VARCHAR);
            return;
        }

        final Location location = (Location)value;
        JSONObject json = LocationJsonUtils.toJson(location);

        statement.setString(index, json.toJSONString());
    }


    @Override
    public Object deepCopy(Object value) throws HibernateException {

        final Location recievedParam = (Location) value;

        return recievedParam;
    }

    @Override
    public boolean isMutable() {

        return true;
    }


    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }


    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return null;
    }
}
