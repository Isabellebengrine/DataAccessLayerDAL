package org.isa.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.isa.DAL.Client;
import org.isa.DAL.ClientDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionClientsController implements Initializable {
    public Button btnAjouter;
    public Button btnModifier;
    public Button btnSupprimer;
    public TableColumn<Client, String> colNom;
    public TableColumn<Client, String> colPrenom;
    public TableView<Client> listeClients;
    public VBox detailsForm;
    public TextField inputNom;
    public TextField inputPrenom;
    public TextField inputVille;
    public Button btnOk;
    public Button btnAnnuler;
    public Label msgErreur;
    ObservableList<Client> model = FXCollections.observableArrayList();
    public String nomBouton;


    /**
     * Fills all text fields to show details about a client.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showDetails(Client person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            inputNom.setText(person.getNom());
            inputPrenom.setText(person.getPrenom());
            inputVille.setText(person.getVille());

        } else {
            // Person is null, so remove all the text.
            inputNom.setText("");
            inputPrenom.setText("");
            inputVille.setText("");
        }
    }

    public void ajouter(ActionEvent actionEvent) {
        detailsForm.setVisible(true);
        //stocker nom bouton dans variable :
        nomBouton = ((Button) actionEvent.getSource()).getText();
        //remettre champs input à zéro:
        showDetails(null);
    }

    public void modifier(ActionEvent actionEvent) {
        detailsForm.setVisible(true);
        nomBouton = ((Button) actionEvent.getSource()).getText();
        Client c = listeClients.getSelectionModel().getSelectedItem();
        //afficher message erreur si rien de sélectionné dans le tableau :
        if (listeClients.getSelectionModel().getSelectedItem() == null) {
            msgErreur.setVisible(true);
        } else {
            msgErreur.setVisible(false);
            //remplir champs avec infos:
            showDetails(c);
        }
    }

    public void supprimer(ActionEvent actionEvent) {
        detailsForm.setVisible(true);
        nomBouton = ((Button) actionEvent.getSource()).getText();
        Client c = listeClients.getSelectionModel().getSelectedItem();
        //afficher message erreur si rien de sélectionné dans le tableau :
        if (listeClients.getSelectionModel().getSelectedItem() == null) {
            msgErreur.setVisible(true);
        } else {
            msgErreur.setVisible(false);
            //remplir champs avec infos:
            showDetails(c);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialisation du modèle
        ClientDAO repo = new ClientDAO();
        model.addAll(repo.list());

        //On rend le tableau non-éditable
        listeClients.setEditable(false);

        // Jonction du tableau avec les données
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        // On indique au TableView quel modèle observer pour trouver les données
        listeClients.setItems(model);
    }

    public void enregistrer(ActionEvent actionEvent) {
        detailsForm.setVisible(false);
        ClientDAO repo = new ClientDAO();

        //if clic sur ajouter alors ajout en bdd: 10/12 ok
        if (nomBouton.equals("Ajouter")) {

            Client c = new Client();
            c.setNom(inputNom.getText());
            c.setPrenom(inputPrenom.getText());
            c.setVille(inputVille.getText());
            repo.insert(c);
            //mise à jour du tableview pour afficher nvo client :
            model.add(c);
            listeClients.setItems(model);

        } else if (nomBouton.equals("Modifier")) {
            Client c = listeClients.getSelectionModel().getSelectedItem();
            //modifier dans la bdd :
            c.setNom(inputNom.getText());
            c.setPrenom(inputPrenom.getText());
            c.setVille(inputVille.getText());
            repo.update(c);

            //mise à jour du tableview pour montrer modification :
            int selectedIndex = listeClients.getSelectionModel().getSelectedIndex();
            listeClients.getItems().set(selectedIndex, c);

        } else if (nomBouton.equals("Supprimer")) {

            Client c = listeClients.getSelectionModel().getSelectedItem();
            //supprimer de la bdd :
            repo.delete(c);
            //mise à jour du tableview pour montrer suppression :
            model.remove(c);
            listeClients.setItems(model);

        }
    }

    public void annuler(ActionEvent actionEvent) {
        detailsForm.setVisible(false);
        inputNom.clear();
        inputPrenom.clear();
        inputVille.clear();
    }

}