package cs351project1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * Name: Somiyo Rana
 * This is the main class of the matching tiles game. It sets up the JavaFX stage and
 * scene, initializes the game board and the event handler, and starts the JavaFX application.
 */
public class Main extends Application {

    private final Board TILE_BOARD = new Board();
    private TileEventHandler eventHandler;


    /**
     * Starts the JavaFX application, setting up the main game window.
     * This method initializes the grid for the game, the text elements for the current and max chain,
     * and handles the layout and event handling.
     *
     * @param mainStage The primary stage for this application, onto which
     * the application scene can be set.
     */
    @Override
    public void start(Stage mainStage) {
        GridPane tileGrid = TILE_BOARD.createBoard();
        tileGrid.setPadding(new Insets(20)); // Adds padding around the grid
        VBox layout = new VBox(10); // 10 pixels of spacing between elements
        layout.setAlignment(Pos.CENTER);

        Text currentChainText = createStatusText("Current Chain: 0", Color.BLACK);
        Text maxChainText = createStatusText("Max Chain: 0", Color.BLACK);
        Text infoText = createStatusText("", Color.BLACK);
        layout.getChildren().addAll(tileGrid, currentChainText, maxChainText, infoText);

        eventHandler = new TileEventHandler(TILE_BOARD, tileGrid, currentChainText, maxChainText, infoText);
        tileGrid.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler::handleMouseClick);

        mainStage.setTitle("Matching Tiles Game");
        mainStage.setScene(new Scene(layout, 560, 832));
        mainStage.show();
    }



    /**
     * Creates a text element for displaying status information such as the current chain
     * and the maximum chain achieved by the player.
     *
     * @param initialText The initial text to be displayed.
     * @param textColor   The color of the text.
     * @return A Text node with the specified content and color.
     */
    private Text createStatusText(String initialText, Color textColor) {
        Text statusText = new Text(initialText);
        statusText.setFill(textColor);
        statusText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        return statusText;
    }

    /**
     * The main entry point for all JavaFX applications.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
