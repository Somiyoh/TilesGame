package cs351project1;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * The Board class represents the game board for the matching tiles game.
 * It handles the creation of the grid and the initialization of the tiles with colors.
 */
public class Board {
    private final int numCols = 4;
    private final int numRows = 5;


    /**
     * Creates and initializes the game board as a GridPane with colored tiles.
     *
     * @return GridPane representing the game board.
     */
    public GridPane createBoard() {
        GridPane boardGrid = new GridPane();
        boardGrid.setPadding(new Insets(20)); //  padding of 20 pixels on all sides
        boardGrid.setVgap(8);
        boardGrid.setHgap(8);

        List<Color> colorsForSmall = generateColors();
        List<Color> colorsForMedium = new ArrayList<>(colorsForSmall);
        List<Color> colorsForLarge = new ArrayList<>(colorsForSmall);

        Collections.shuffle(colorsForSmall);
        Collections.shuffle(colorsForMedium);
        Collections.shuffle(colorsForLarge);

        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                int position = col * numRows + row;
                Tile tile = new Tile(colorsForSmall.get(position), colorsForMedium.get(position),
                        colorsForLarge.get(position));
                boardGrid.add(tile, col, row);
            }
        }
        return boardGrid;
    }


    /**
     * Generates a list of colors to be used for the tiles on the board.
     * Each color appears twice to create pairs for matching.
     *
     * @return List of Color objects for tile fills.
     */
    private List<Color> generateColors() {
        String[] hexCodes = {
                "d3d3d3", "2f4f4f", "8b4513", "BFFF00", "483d8b", "3cb371",
                "000080", "9acd32", "00ced1", "ff8c00", "ffd700", "00ff00",
                "8a2be2", "00ff7f", "dc143c", "00bfff", "1e90ff", "f0e68c",
                "ff1493", "ee82ee"
        };
        List<Color> colorList = new ArrayList<>();
        for (int i = 0; i < (numCols * numRows) / 2; i++) {
            Color color = Color.valueOf(hexCodes[i % hexCodes.length]);
            colorList.add(color);
            colorList.add(color); // Adding  twice to create pairs
        }
        return colorList;
    }


    /**
     * Creates a highlight effect for a selected tile by creating a border rectangle.
     *
     * @return Rectangle with a stroke to function as a tile highlight.
     */
    public Rectangle createHighlight () {
        Rectangle highlight = new Rectangle(125, 125);
        highlight.setStrokeWidth(8);
        highlight.setStroke(Color.BLACK);
        highlight.setFill(Color.TRANSPARENT);
        return highlight;
    }

    public int getColumnCount() {
        return numCols;
    }

    public int getNumRows() { return numRows;}
}
