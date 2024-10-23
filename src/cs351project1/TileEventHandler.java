package cs351project1;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;



/**
 * This class handles all tile events for the matching tiles game.
 * It processes mouse clicks, tile selections, and checks for game over conditions.
 */
public class TileEventHandler {

    private final Board TILE_BOARD;
    private final GridPane tileGrid;
    private Pane firstSelection;
    private Pane secondSelection;
    private int currentChain = 0;
    private int maxChain = 0;
    private final Text currentChainText;
    private final Text maxChainText;
    private final Text infoText;


    /**
     * Constructs a TileEventHandler with the game board, UI components, and texts.
     *
     * @param board          The game board logic.
     * @param tileGrid       The grid that contains the tiles.
     * @param currentChainText Text display for the current chain count.
     * @param maxChainText   Text display for the max chain count.
     * @param infoText       Text display for informational messages.
     */
    public TileEventHandler(Board board, GridPane tileGrid, Text currentChainText, Text maxChainText, Text infoText) {
        this.TILE_BOARD = board;
        this.tileGrid = tileGrid;
        this.currentChainText = currentChainText;
        this.maxChainText = maxChainText;
        this.infoText = infoText;
    }


    /**
     * Handles mouse click events on the tile grid.
     *
     * @param event The MouseEvent object that contains information about the mouse click.
     */
    public void handleMouseClick(MouseEvent event) {
        infoText.setText("");
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (!(clickedNode instanceof Pane)) {
            return;
        }
        Pane selectedTile = (Pane) clickedNode;
        if (selectedTile.getChildren().size() < 4 && !isTransparent(selectedTile)) {
            processTileSelection(selectedTile);
        }
    }


    /**
     * Processes the selection of a tile. If it is the first tile selected, it is highlighted.
     * If it is the second tile, it checks for a match with the first tile.
     *
     * @param selectedTile The tile that has been selected by the user.
     */
    private void processTileSelection(Pane selectedTile) {
        if (firstSelection == null) {
            firstSelection = selectedTile;
            addHighlight(firstSelection);
        } else {
            secondSelection = selectedTile;
            removeHighlight(firstSelection);
            if (!tilesMatch()) {
                infoText.setText("Tiles do not match!");
                infoText.setFill(Color.BLACK);
                resetCurrentChain();
            } else {
                updateCurrentChain(currentChainText, 1);
                if (currentChain > maxChain) {
                    updateMaxChain(maxChainText, 1);
                }
                handleTransparentTile();
            }
        }
    }


    /**
     * Resets the current chain to zero and clears the selections when the tiles do not match.
     */
    private void resetCurrentChain() {
        updateCurrentChain(currentChainText, -currentChain);
        firstSelection = null;
        secondSelection = null;
    }


    /**
     * Handles the scenario when a tile is transparent and can be selected again.
     * It also triggers a check for game over conditions.
     */
    private void handleTransparentTile() {
        if (isTransparent(secondSelection)) {
            infoText.setText("Select any tile!");
            firstSelection = null;
        } else {
            addHighlight(secondSelection);
            firstSelection = secondSelection;
        }
        secondSelection = null;
        checkGameOver();
    }

    private void checkGameOver() {
        if (isGameOver(tileGrid)) {
            infoText.setText("Game Over");
            infoText.setFill(Color.BLACK);
            removeHighlights();
        }
    }

    /**
     * Checks if the game is over, which is when all tiles are transparent.
     * If the game is over, it triggers the game over alert.
     */
    private boolean isGameOver(GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (node instanceof Pane) {
                Pane tile = (Pane) node;
                if (!isTransparent(tile)) {
                    // If any tile is not transparent, the game is not over
                    return false;
                }
            }
        }
        // If all tiles are transparent, the game is over
        Platform.runLater(this::showGameOverAlert);
        return true;
    }


    /**
     * Displays a game over alert and closes the application when the user acknowledges it.
     */
    private void showGameOverAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("All tiles matched! Game Over!");
        alert.setOnHidden(evt -> Platform.exit());
        alert.show();
    }

    private void addHighlight(Pane tile) {
        tile.getChildren().add(1, TILE_BOARD.createHighlight());
    }

    private void removeHighlight(Pane tile) {
        tile.getChildren().remove(1);
    }

    private void removeHighlights() {
        for (Node node : tileGrid.getChildren()) {
            if (node instanceof Pane) {
                Pane pane = (Pane) node;
                if (pane.getChildren().size() > 1) {
                    pane.getChildren().remove(1);
                }
            }
        }
    }

    private void updateCurrentChain(Text text, int increment) {
        currentChain += increment;
        text.setText("Current Chain: " + currentChain);
    }

    private void updateMaxChain(Text text, int increment) {
        maxChain += increment;
        text.setText("Max Chain: " + maxChain);
    }


    /**
     * Checks if a given tile (Pane) is fully transparent, which implies the tile is matched.
     * This is determined by checking if all three Rectangle children of the Pane have a fill
     * of Color.TRANSPARENT.
     *
     * @param tile The Pane that represents a tile in the game.
     * @return true if all three Rectangles in the tile are transparent, false otherwise.
     */
    private boolean isTransparent(Pane tile) {
        long transparentCount = tile.getChildren().stream()
                .filter(child -> child instanceof Rectangle)
                .map(child -> (Rectangle) child)
                .filter(rect -> rect.getFill().equals(Color.TRANSPARENT))
                .count();
        return transparentCount == 3;
    }


    /**
     * Compares the currently selected tiles for a match. Tiles match if all the Rectangles
     * within them have the same fill color and it's not transparent. If they match,
     * the method sets their fill to transparent, effectively removing them from the game.
     *
     * @return true if the tiles match, false otherwise.
     */
    private boolean tilesMatch() {
        boolean isMatch = false;
        for (int i = 0; i < 3; i++) {
            Rectangle rect1 = (Rectangle) firstSelection.getChildren().get(i);
            Rectangle rect2 = (Rectangle) secondSelection.getChildren().get(i);
            if (rect1.getFill().equals(rect2.getFill()) && !rect1.getFill().equals(Color.TRANSPARENT)) {
                rect1.setFill(Color.TRANSPARENT);
                rect1.setStroke(Color.TRANSPARENT);
                rect2.setFill(Color.TRANSPARENT);
                rect2.setStroke(Color.TRANSPARENT);
                isMatch = true;
            }
        }
        return isMatch;
    }

}
