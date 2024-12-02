package com.pixearth.core.options;

import com.pixearth.core.PixEarthCore;

import java.util.HashMap;
import java.util.List;

/**
 * Classe qui permet de gerer les options du plugin
 * Les options sont stockées en base de données
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class OptionManager {

    /**
     * Tableau qui contient le nom de l'option est la valeur associee
     * optionName => option
     */
    private HashMap<String, Option> options = new HashMap<>();

    /**
     * Instance du manager
     * @see #getInstance()
     */
    private static OptionManager instance;

    /**
     * Constructor
     */
    public OptionManager() {

        instance = this;
    }

    /**
     * Charge les options
     */
    public void loadOptions()  {

        List<Option> options = (new OptionRepository()).findAll();
        for (Option option : options) {
            this.options.put(option.getName(), option);
        }

        PixEarthCore.log().info(this.options.size() +" option(s) chargee(s)");
    }

    /**
     * @return Retourne l'instance du manager
     */
    public static OptionManager getInstance() {

        return instance;
    }

    /**
     * Permet de savoir si une option existe
     *
     * @param optionName    Nom de l'option
     * @return  Retourne true si l'option existe ou false dans le cas contraire
     */
    public boolean optionExist(String optionName) {

        return this.options.containsKey(optionName);
    }

    /**
     * Permet de récupérer une option au format String
     *
     * @param optionName    Nom de l'option
     * @return  Retourne la valeur de l'option si elle existe ou null si l'option n'existe pas
     */
    public String getString(String optionName) {

        return this.getString(optionName, null);
    }

    /**
     * Permet de récupérer une option au format String
     *
     * @param optionName    Nom de l'option
     * @param defaultValue  Valeur par défaut si l'option n'existe pas
     * @return  Retourne la valeur de l'option si elle existe ou defaultValue si l'option n'est pas valide
     */
    public String getString(String optionName, String defaultValue) {

        if(this.optionExist(optionName)) {

            return this.options.get(optionName).getValue();
        }

        return defaultValue;
    }

    /**
     * Permet de récupérer une option au format int
     *
     * @param optionName    Nom de l'option
     * @return  Retourne la valeur de l'option si elle existe et si c'est un int ou 0 si l'option n'existe pas ou n'est pas valide
     * @see #getInt(String, int)
     */
    public int getInt(String optionName) {

        return this.getInt(optionName, 0);
    }

    /**
     * Permet de récupérer une option au format int
     *
     * @param optionName    Nom de l'option
     * @param defaultValue  Valeur par défaut de l'option
     * @return  Retourne la valeur de l'option si elle existe et si elle est de type integer ou defaultValue si l'option n'est pas valide
     */
    public int getInt(String optionName, int defaultValue) {

        if(this.isInt(optionName)) {
            return Integer.parseInt(this.options.get(optionName).getValue());
        }

        return defaultValue;
    }

    /**
     * Permet de vérifier si une option est de type integer
     *
     * @param optionName    Nom de l'option a verifier
     * @return  Retourne true si l'option est du type int ou false dans le cas contraire
     * @see #getInt(String, int)
     */
    public boolean isInt(String optionName) {

        if(this.optionExist(optionName)) {

            try {
                Integer.parseInt(this.options.get(optionName).getValue());
                return true;
            } catch (NumberFormatException e) {

            }
        }

        return false;
    }

    /**
     * Permet de récupérer une option de type boolean
     *
     * @param optionName    Nom de l'option
     * @return  Retourne l'option au format boolean si elle existe et si elle existe ou null dans le cas contraire
     * @see #getInt(String, int)
     */
    public boolean getBoolean(String optionName) {

        return this.getBoolean(optionName, null);
    }

    /**
     * Permet de récupérer une option de type boolean
     *
     * @param optionName    Nom de l'option
     * @param defaultValue  Valeur par défaut de l'option
     * @return  Retourne l'option au format boolean si elle existe et si elle est du type boolean ou defaultValue si l'option n'est pas valide
     * @see #isBoolean(String)
     */
    public boolean getBoolean(String optionName, Boolean defaultValue) {

        if(this.isBoolean(optionName)) {

            return Boolean.parseBoolean(this.options.get(optionName).getValue());
        }

        return defaultValue;
    }

    /**
     * Permet de vérifier si une option est du type boolean
     *
     * @param optionName    Nom de l'option
     * @return  Retourne true si l'option est du type boolean ou false dans le cas contraire
     */
    public boolean isBoolean(String optionName) {

        if(this.optionExist(optionName)) {

            return Boolean.parseBoolean(this.options.get(optionName).getValue());
        }

        return false;
    }

    /**
     * Permet de récupérer une option de type double
     *
     * @param optionName    Nom de l'option
     * @return  Retourne l'option au format double si elle existe et si elle est du type double ou null dans le cas contraire
     * @see #getDouble(String, Double)
     */
    public double getDouble(String optionName) {

        return this.getDouble(optionName, null);
    }

    /**
     * Permet de récupérer une option au format double
     *
     * @param optionName    Nom de l'option
     * @param defaultValue  Valeur par défaut
     * @return  Retourne l'option au format double si l'option existe et si elle est au format double ou defaultValue si l'option n'est pas valide
     * @see #isDouble(String)
     */
    public double getDouble(String optionName, Double defaultValue) {

        if(this.isDouble(optionName)) {
            return Double.parseDouble(this.options.get(optionName).getValue());
        }

        return defaultValue;
    }

    /**
     * Permet de vérifier si une option est de type double
     *
     * @param optionName    Nom de l'option
     * @return  Retourne true si l'option est de type double ou false dans le cas contraire
     */
    public boolean isDouble(String optionName) {

        if(this.optionExist(optionName)) {

            try {
                double x = Double.parseDouble(this.options.get(optionName).getValue());

                if(x == (int)x) {
                    return false;
                }

                return true;
            } catch (NumberFormatException e) {

            }
        }

        return false;
    }

    /**
     * Permet de sauvegarder une option
     * Si l'option n'existe pas, elle est créée
     *
     * @param optionName    Nom de l'option
     * @param optionValue   Valeur de l'option
     * @return  Retourne true si l'option a été sauvegardée ou false dans le cas contraire
     */
    public boolean save(String optionName, String optionValue) {

        boolean actionSuccessful = false;

        OptionRepository repo = new OptionRepository();

        // L'option existe donc on la met a jour
        if(this.optionExist(optionName)) {

            Option option = this.options.get(optionName);
            option.setValue(optionValue);

            repo.update(option);
            this.options.replace(optionName, option);
            return true;
        } else {

            Option option = new Option(optionName, optionValue);
            Option optionInsert = repo.insert(option);

            // L'option est valide car elle possede un identifiant suite a l'insert
            if(optionInsert.getId() >= 0) {
                actionSuccessful = true;
                this.options.put(optionName, option);
            }
        }

        return actionSuccessful;
    }

    /**
     * Permet de sauvegarder une option
     *
     * @param optionName    Nom de l'option
     * @param optionValue   Valeur de l'option
     * @return  Retourne true si l'option a été sauvegardée ou false dans le cas contraire
     * @see #save(String, String)
     */
    public boolean save(String optionName, Boolean optionValue){

        return this.save(optionName, String.valueOf(optionValue));
    }

    /**
     * Permet de sauvegarder une option
     *
     * @param optionName    Nom de l'option
     * @param optionValue   Valeur de l'option
     * @return  Retourne true si l'option a été sauvegardée ou false dans le cas contraire
     * @see #save(String, String)
     */
    public boolean save(String optionName, int optionValue) {

        return this.save(optionName, String.valueOf(optionValue));
    }

    /**
     * Permet de sauvegarder une option
     *
     * @param optionName    Nom de l'option
     * @param optionValue   Valeur de l'option
     * @return  Retourne true si l'option a été sauvegardée ou false dans le cas contraire
     * @see #save(String, String)
     */
    public boolean save(String optionName, double optionValue) {

        return this.save(optionName, String.valueOf(optionValue));
    }

}
