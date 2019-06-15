package application;

//importaciones de las librerias a usar
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import application.CrudClient;
import java.sql.SQLException;
import java.util.ArrayList;

//Clase Main
public class Main extends Application {

	//Cuadros de texto,alert y boolean en privado para utilizarse en la clase MAin
    private TextField txfNombre = new TextField();
    private TextField txfApellido = new TextField();
    private TextField txfDireccion = new TextField();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Boolean bandera = true;

    //Sirve para navegar entre pestañas
    private TabPane tabpane = new TabPane();
    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane border = new BorderPane();//Pantalla
        primaryStage.setTitle("Venta Boletos");//Titulo de la ventana

        //Insertarla imagen de fondo
        border.setStyle("-fx-background-image: url('fondo.jpg');-fx-background-position: Default;-fx-background-size: cover;");

        TabPane tabpane = addPanel();
        border.setTop(tabpane);

        primaryStage.setScene(new Scene(border, 680, 400));//tamaño
        primaryStage.show();

    }
    //Insertar los datos que corresponden a la base de datos
    public GridPane addGrindPane (){
        GridPane grid = new GridPane();
        Label lNombre = new Label("Nombre");//Decalracion de los labels
        Label lApellido = new Label("Apellido");
        Label lDireccion = new Label("Direccion");
        Button btnEnviar = new Button("Aceptar");

        grid.setHgap(10);//Establece el valor de la propiedad hgap.
        grid.setVgap(10);//Establece el valor de la propiedad vgap.
        grid.setPadding(new Insets(0,10,0,10));

        //Configuracion de letra y titulo
        Text Unidades = new Text("Ingresar los datos");
        Unidades.setFill(Color.CORAL);
        Unidades.setFont(Font.font("Algerian", FontWeight.BOLD, 20));
        grid.add(Unidades, 1,0);

        //Añadir label en el grid y la posicion
        grid.add(lNombre, 1,1);
        grid.add(lApellido, 1,2);
        grid.add(lDireccion, 1,3);

      //Posicion de los textfied y nombre de que aparecera en los cuadros
        txfNombre.setPromptText("Nombre");
        GridPane.setConstraints(txfNombre, 2, 1);
        grid.getChildren().add(txfNombre);

        txfApellido.setPromptText("Apellido");
        GridPane.setConstraints(txfApellido, 2, 2);
        grid.getChildren().add(txfApellido);

        txfDireccion.setPromptText("Direccion");
        GridPane.setConstraints(txfDireccion, 2, 3);
        grid.getChildren().add(txfDireccion);

        //Botones y de la acción
        btnEnviar.setPrefSize(150,20);
        btnEnviar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;//Conexion a la base de datos
                try {
                    accesoBD = new Conexion();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }


                //Llamada al crud
                    CrudClient opCliente = new CrudClient(accesoBD.getConnection());
                    opCliente.insertCliente(txfNombre.getText(), txfApellido.getText(), txfDireccion.getText());
                    bandera= true;

                    //Alertas que se registro bien
                    alert.setTitle("Registro");
                    alert.setHeaderText("Se registro exitosamente");
                    alert.showAndWait();

            }
        });

        grid.add(btnEnviar,1,4);
        return grid;
    }
    //Visualizar
    public GridPane addGrindPane2 (){
        GridPane grid = new GridPane();
        //Muestra el contenido de la tabla
        TableView tabla = new TableView();
        TableColumn idNombre = new TableColumn("Id cliente");
        idNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("clienteId"));//método que se utiliza para obtener los datos

        TableColumn colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("Nombre"));

        TableColumn colApellido = new TableColumn ("Apellidos");
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Apellidos"));

        TableColumn colDireccion = new TableColumn ("Direccion");
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente,String>("Direccion"));

        tabla.getColumns().addAll(idNombre,colNombre,colApellido,colDireccion);

        Label lBuscar = new Label("BUSCAR");

        TextField txfID = new TextField();
        Button btnBuscar = new Button("Buscar");

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));

        txfID.setPromptText("Nombre");
        GridPane.setConstraints(txfID, 1, 0);

        grid.getChildren().add(txfID);
        grid.getChildren().add(tabla);

        btnBuscar.setPrefSize(150,20);
        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String nombre1 = txfID.getText();
                CrudClient opCliente = new CrudClient(accesoBD.getConnection());
                ArrayList<Cliente> arrayClientes;
                arrayClientes = opCliente.getClientes(nombre1);

                ObservableList<Cliente> data = FXCollections.observableArrayList();
                data.removeAll();
                data.addAll(arrayClientes);
                tabla.setItems(data);
                txfID.setText(" ");

                if(arrayClientes.size()==0){
                    alert.setTitle("No se encontro");
                    alert.setHeaderText("No existe");
                    alert.showAndWait();
                }else{
                    alert.setTitle("Busqueda exitosa");
                    alert.setHeaderText("Si existe");
                    alert.showAndWait();
                }
            }
        });

        grid.add(btnBuscar,2,0);

        return grid;
    }

    //Actualizar los datos
    private GridPane addGrindPane3() {

        GridPane grid = new GridPane();
        TextField txfNombre = new TextField();
        TextField txfNombreA = new TextField();
        TextField txfApellido = new TextField();
        TextField txfDireccion = new TextField();
        Button btnBuscar = new Button ("Buscar");
        Button btnActualizar = new Button("Actualizar");
        Label Buscar = new Label ("Ingrese el Id para modificar los datos");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));

        Text Unidades= new Text("Actualizar los datos");
        Unidades.setFill(Color.CORAL);
        Unidades.setFont(Font.font("Algerian", FontWeight.BOLD, 20));
        grid.add(Unidades, 1,1);
        grid.add(Buscar, 1,2);

        txfNombre.setPromptText("Id");
        GridPane.setConstraints(txfNombre, 2, 2);
        grid.getChildren().add(txfNombre);

        txfNombreA.setPromptText("Nombre");
        GridPane.setConstraints(txfNombreA, 1, 4);
        grid.getChildren().add(txfNombreA);

        txfApellido.setPromptText("Apellido");
        GridPane.setConstraints(txfApellido, 1, 5);
        grid.getChildren().add(txfApellido);

        txfDireccion.setPromptText("Direccion");
        GridPane.setConstraints(txfDireccion, 1, 6);
        grid.getChildren().add(txfDireccion);

        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String nombre2 = txfNombre.getText();
                int id1 = Integer.parseInt(nombre2);
                CrudClient opCliente = new CrudClient(accesoBD.getConnection());
                Cliente clt;
                clt = opCliente.getCliente(id1);

                alert.setTitle("Buscar");
                alert.setHeaderText("Busqueda realizada");
                alert.showAndWait();
                txfNombreA.setText(clt.getNombre());
                txfApellido.setText(clt.getApellidos());
                txfDireccion.setText(clt.getDireccion());

            }
        });

        btnActualizar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String ID = txfNombre.getText();
                int id = Integer.parseInt(ID);
                String nombre = txfNombreA.getText();
                String apellido = txfApellido.getText();
                String direccion = txfDireccion.getText();
                CrudClient opCliente = new CrudClient(accesoBD.getConnection());

                opCliente.updateCliente(id,nombre,apellido,direccion);
                Cliente clt;
                clt = opCliente.getCliente(id);

                alert.setTitle("Actualizar");
                alert.setHeaderText("Actualización Exitosa");
                alert.showAndWait();
                txfNombre.setText(" ");
                txfNombreA.setText(" ");
                txfApellido.setText(" ");
                txfDireccion.setText(" ");
            }
        });

        grid.add(btnBuscar, 3,2);
        grid.add(btnActualizar, 1,7);

        return grid;
    }
    //Borrar datos
    private GridPane addGrindPane4() {
        GridPane grid = new GridPane();
        TextField txfNombre = new TextField();
        Label lbNombreA = new Label("Nombre");
        Label lbApellido = new Label("Apellido");
        Label lbDireccion = new Label("Dirección");
        Button btnBuscar = new Button ("Aceptar");
        Button btnEliminar = new Button ("Eliminar");
        Label Buscar = new Label ("Ingrese el Id del cliente");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));

        Text Unidades= new Text("Eliminar los datos");
        Unidades.setFill(Color.CORAL);
        Unidades.setFont(Font.font("Algerian", FontWeight.BOLD, 20));
        grid.add(Unidades, 1,1);
        grid.add(Buscar, 1,2);

        txfNombre.setPromptText("Id Cliente");
        grid.add(txfNombre,2,2);

        grid.add(lbNombreA,1,4);
        grid.add(lbApellido,1,5);
        grid.add(lbDireccion,1,6);

        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String ID = txfNombre.getText();
                int id = Integer.parseInt(ID);
                CrudClient opCliente = new CrudClient(accesoBD.getConnection());
                Cliente clt;
                clt = opCliente.getCliente(id);

                alert.setTitle("Buscar");
                alert.setHeaderText("Busqueda realizada");
                alert.showAndWait();
                lbNombreA.setText(clt.getNombre());//extracion de datos
                lbApellido.setText(clt.getApellidos());
                lbDireccion.setText(clt.getDireccion());

            }
        });
        btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;//Acceso a la BD
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String ID = txfNombre.getText();
                int id = Integer.parseInt(ID);//Pedir el Id
                CrudClient opCliente = new CrudClient(accesoBD.getConnection());

                opCliente.deleteCliente(id);

                alert.setTitle("Eliminar");//Mensajes de alerta
                alert.setHeaderText("Eliminado");
                alert.showAndWait();

                lbNombreA.setText("---");
                lbApellido.setText("---");
                lbDireccion.setText("---");

            }
        });
        grid.add(btnBuscar, 3,2);
        grid.add(btnEliminar, 2,4);

        return grid;
    }

    //Creación donde se dividira los menús del Crud y se le asignara nombre y el gridpane para trabajar dichos menus
    public TabPane addPanel() {
        BorderPane border = new BorderPane();

        //Detarminacion de las tab con su respectivos nombres
        Tab tab1 = new Tab("Insertar");
        tabpane.getTabs().add(tab1);
        Button botton = new Button("Boton");
        tab1.setContent(botton);

        Tab tab2 = new Tab("Visualizar");
        tabpane.getTabs().add(tab2);

        Tab tab3 = new Tab("Actualizar");
        tabpane.getTabs().add(tab3);

        Tab tab4 = new Tab("Borrar");
        tabpane.getTabs().add(tab4);

        GridPane gridp1 = addGrindPane();
        tab1.setContent(gridp1);
        tab1.setId("pane");
        GridPane gridp2 = addGrindPane2();
        tab2.setContent(gridp2);

        GridPane gridp3 = addGrindPane3();
        tab3.setContent(gridp3);

        GridPane gridp4 = addGrindPane4();
        tab4.setContent(gridp4);

        return tabpane;

    }


    public static void main(String[] args) {
        launch(args);
    }
}