package cs351project1;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



/**
 * This class represents a single tile in the matching tiles game.
 * A tile consists of stacked rectangles with different colors.
 */
public class Tile extends StackPane {
    private Color[] colors;
    private Rectangle[] rectangles;


    /**
     * Constructs a Tile with the given colors.
     * Each color is used to create a rectangle, which is added to the stack pane.
     *
     * @param colors An array of colors for each layer of the tile.
     */
    public Tile(Color... colors) {
        this.colors = colors;
        this.rectangles = new Rectangle[colors.length];

        for (int i = 0; i < colors.length; i++) {
            int size = 125 - 32 * i;
            rectangles[i] = new Rectangle(size, size);
            rectangles[i].setFill(colors[i]);
            rectangles[i].setMouseTransparent(true);
            rectangles[i].setStroke(Color.BLACK);
            rectangles[i].setStrokeWidth(2.8);
            this.getChildren().add(rectangles[i]);
        }
    }
}
