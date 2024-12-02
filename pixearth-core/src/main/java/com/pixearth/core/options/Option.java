package com.pixearth.core.options;

import javax.persistence.*;

/**
 * Classe qui représente une option en base de données
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="idle_option")
public class Option {

    /**
     * Identifiant unique de l'option (auto_increment)
     *
     * @see #getId()
     * @see #setId(long)
     */
    @Id
    @Column(name = "option_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Nom de l'option
     *
     * @see #getName()
     * @see #setName(String)
     */
    @Column(name = "option_name", unique = true)
    private String name;

    /**
     * Valeur de l'option
     *
     * @see #getValue()
     * @see #setValue(String)
     */
    @Column(name = "option_value")
    private String value;

    /**
     * Constructor
     */
    public Option() {}

    /**
     * Constructor
     *
     * @param optionName    Nom de l'option
     * @param optionValue   Valeur de l'option
     */
    public Option(String optionName, String optionValue) {

        this.name = optionName;
        this.value = optionValue;
        this.id = -1;
    }

    /**
     * @return Retourne l'identifiant de l'option
     */
    public long getId() {

        return  this.id;
    }

    /**
     * Set l'identifiant
     *
     * @param id Identifiant
     */
    public void setId(long id) {

        this.id = id;
    }

    /**
     * @return Retourne le nom de l'option
     */
    public String getName() {

        return this.name;
    }

    /**
     * Set le nom
     *
     * @param name Nom de l'option
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return Retourne la valeur de l'option
     */
    public String getValue() {

        return this.value;
    }

    /**
     * Set la valeur de l'option
     *
     * @param value Valeur
     */
    public void setValue(String value) {

        this.value = value;
    }

    /**
     * @return Retourne les informations de l'option pour l'affichage
     */
    @Override
    public String toString() {

        return "Option: [id : " + this.id + ", name: " + this.name + ", value: " + this.value + "]";
    }

}