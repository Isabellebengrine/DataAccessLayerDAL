package org.isa.DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private String url;
    private String username;
    private String password;

    public ClientDAO(){
        this.url = "jdbc:mysql://localhost:3306/hotel?serverTimezone=UTC";
        this.username = "root";
        this.password = "";
    }

    public void insert(Client cli) {
        // méthode d'insertion
        try {
            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement stm = con.prepareStatement("INSERT INTO client (cli_nom, cli_prenom, cli_ville) VALUES (?, ?, ?)");

            stm.setString(1, cli.getNom());
            stm.setString(2, cli.getPrenom());
            stm.setString(3, cli.getVille());

            stm.execute();

            stm.close();
            con.close();
            System.out.println("l'insertion s’est bien effectuée");

        }
        catch (Exception e) {
            System.out.println("Erreur lors de l’insertion 'client'");
            System.out.println(e.getMessage());
        }
    }

    public void update(Client cli) {
        // méthode d'édition
        try {
            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement stm = con.prepareStatement("UPDATE client SET cli_nom = ?, cli_prenom = ?, cli_adresse = ?, cli_ville = ? WHERE cli_id = ?;");

            stm.setString(1, cli.getNom());
            stm.setString(2, cli.getPrenom());
            stm.setString(3, cli.getAdresse());
            stm.setString(4, cli.getVille());
            stm.setInt(5, cli.getId());

            stm.execute();

            stm.close();
            con.close();
            System.out.println("la modification s’est bien effectuée");

        }
        catch (Exception e) {
            System.out.println("Erreur lors de la modification 'client'");
            System.out.println(e.getMessage());
        }


    }

    public void delete(Client cli) {
        // méthode de suppression - req type "DELETE FROM table_name WHERE condition;"
        try {
            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement stm = con.prepareStatement("DELETE FROM client WHERE cli_id = ?;");

            stm.setInt(1, cli.getId());

            stm.execute();

            stm.close();
            con.close();
            System.out.println("la suppression s’est bien effectuée");

        }
        catch (Exception e) {
            System.out.println("Erreur lors de la suppression 'client': ce client a des réservations en cours!");
            System.out.println(e.getMessage());
        }

    }

    public Client find(int id)     {
        // méthode de recherche d'un enregistrement :
        Client c = new Client();

        try {

            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement stm = con.prepareStatement("SELECT * FROM client WHERE cli_id = ?");

            stm.setInt(1, id);

            ResultSet result =stm.executeQuery ();

            while (result.next()) {
                c.setId(result.getInt("cli_id"));
                c.setNom(result.getString("cli_nom"));
                c.setPrenom(result.getString("cli_prenom"));
                c.setAdresse(result.getString("cli_adresse"));
                c.setVille(result.getString("cli_ville"));
            }

            stm.close();
            result.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
        return c;
    }

    public List<Client> list()     {
        // méthode de création de liste
        List<Client> resultat = new ArrayList<Client>();

        try {
            Connection con = DriverManager.getConnection(url, username, password);

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM client");

            while (result.next()) {
                Client c = new Client();
                c.setId(result.getInt("cli_id"));
                c.setNom(result.getString("cli_nom"));
                c.setPrenom(result.getString("cli_prenom"));
                c.setAdresse(result.getString("cli_adresse"));
                c.setVille(result.getString("cli_ville"));
                resultat.add(c);
            }

            stm.close();
            result.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erreur de lecture 'client'");
            System.out.println(e.getMessage());
        }

        return resultat;

    }

}