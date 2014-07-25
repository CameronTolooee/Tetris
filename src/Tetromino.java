import java.awt.Color;
import java.util.Random;

public enum Tetromino{

	/**
	 * Piece TypeI.
	 */
	TypeI(Color.CYAN, 4, 4, 1, new boolean[][] {
		{
			false,	false,	false,	false,
			true,	true,	true,	true,
			false,	false,	false,	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	true,	false,
			false,	false,	true,	false,
			false,	false,	true,	false,
			false,	false,	true,	false,
		},
		{
			false,	false,	false,	false,
			false,	false,	false,	false,
			true,	true,	true,	true,
			false,	false,	false,	false,
		},
		{
			false,	true,	false,	false,
			false,	true,	false,	false,
			false,	true,	false,	false,
			false,	true,	false,	false,
		}
	}),

	/**
	 * Piece TypeJ.
	 */
	TypeJ(Color.BLUE, 3, 3, 2, new boolean[][] {
		{
			true,	false,	false,
			true,	true,	true,
			false,	false,	false,
		},
		{
			false,	true,	true,
			false,	true,	false,
			false,	true,	false,
		},
		{
			false,	false,	false,
			true,	true,	true,
			false,	false,	true,
		},
		{
			false,	true,	false,
			false,	true,	false,
			true,	true,	false,
		}
	}),

	/**
	 * Piece TypeL.
	 */
	TypeL(Color.ORANGE, 3, 3, 2, new boolean[][] {
		{
			false,	false,	true,
			true,	true,	true,
			false,	false,	false,
		},
		{
			false,	true,	false,
			false,	true,	false,
			false,	true,	true,
		},
		{
			false,	false,	false,
			true,	true,	true,
			true,	false,	false,
		},
		{
			true,	true,	false,
			false,	true,	false,
			false,	true,	false,
		}
	}),

	/**
	 * Piece TypeO.
	 */
	TypeO(Color.YELLOW, 2, 2, 2, new boolean[][] {
		{
			true,	true,
			true,	true,
		},
		{
			true,	true,
			true,	true,
		},
		{	
			true,	true,
			true,	true,
		},
		{
			true,	true,
			true,	true,
		}
	}),

	/**
	 * Piece TypeS.
	 */
	TypeS(Color.GREEN, 3, 3, 2, new boolean[][] {
		{
			false,	true,	true,
			true,	true,	false,
			false,	false,	false,
		},
		{
			false,	true,	false,
			false,	true,	true,
			false,	false,	true,
		},
		{
			false,	false,	false,
			false,	true,	true,
			true,	true,	false,
		},
		{
			true,	false,	false,
			true,	true,	false,
			false,	true,	false,
		}
	}),

	/**
	 * Piece TypeT.
	 */
	TypeT(new Color(138, 43, 226), 3, 3, 2, new boolean[][] {
		{
			false,	true,	false,
			true,	true,	true,
			false,	false,	false,
		},
		{
			false,	true,	false,
			false,	true,	true,
			false,	true,	false,
		},
		{
			false,	false,	false,
			true,	true,	true,
			false,	true,	false,
		},
		{
			false,	true,	false,
			true,	true,	false,
			false,	true,	false,
		}
	}),

	/**
	 * Piece TypeZ.
	 */
	TypeZ(Color.RED, 3, 3, 2, new boolean[][] {
		{
			true,	true,	false,
			false,	true,	true,
			false,	false,	false,
		},
		{
			false,	false,	true,
			false,	true,	true,
			false,	true,	false,
		},
		{
			false,	false,	false,
			true,	true,	false,
			false,	true,	true,
		},
		{
			false,	true,	false,
			true,	true,	false,
			true,	false,	false,
		}
	});

	/**
	 * The base color of tiles of this type.
	 */
	private Color baseColor;

	/**
	 * The light shading color of tiles of this type.
	 */
	private Color lightColor;

	/**
	 * The dark shading color of tiles of this type.
	 */
	private Color darkColor;

	/**
	 * The column that this type spawns in.
	 */
	private int spawnCol;

	/**
	 * The row that this type spawns in.
	 */
	private int spawnRow;

	/**
	 * The dimensions of the array for this piece.
	 */
	private int rotation;

	/**
	 * The number of rows in this piece. (Only valid when rotation is 0 or 2,
	 * but it's fine since we're only using it for displaying the next piece
	 * preview, which uses rotation 0).
	 */
	private int rows;

	/**
	 * The number of columns in this piece. (Only valid when rotation is 0 or 2,
	 * but it's fine since we're only using it for displaying the next piece
	 * preview, which uses rotation 0).
	 */
	private int cols;

	/**
	 * The tiles for this piece. Each piece has an array of tiles for each rotation.
	 */
	private boolean[][] tiles;

	/**
	 * Creates a new TileType.
	 * @param color The base color of the tile.
	 * @param dimension The dimensions of the tiles array.
	 * @param cols The number of columns.
	 * @param rows The number of rows.
	 * @param tiles The tiles.
	 */
	private Tetromino(Color color, int rotation, int cols, int rows, boolean[][] tiles) {
		this.baseColor = color;
		this.lightColor = color.brighter();
		this.darkColor = color.darker();
		this.rotation = rotation;
		this.tiles = tiles;
		this.cols = cols;
		this.rows = rows;
	}
	
	public boolean[][] getTiles(){
		return tiles;
	}
	
	public Color getColor(){
		return baseColor;
	}
	
	public static Tetromino generatePiece() {
		Random r = new Random();
		Tetromino t;
		switch (r.nextInt(7)) {
		case 0:
			t = TypeI;
			break;
		case 1:
			t = TypeL;
			break;
		case 2:
			t = TypeJ;
			break;
		case 3:
			t = TypeO;
			break;
		case 4:
			t = TypeT;
			break;
		case 5:
			t = TypeZ;
			break;
		case 6:
			t = TypeS;
			break;
		default:
			t = null;
		}
		return t;
	}

	public int bufferLeft(int ori) {
		int buff = 0;
		int width = getTiles()[0].length == 9 ? 3 : 4; // get width (3 or 4) of piece
		width = getTiles()[0].length == 4 ? 2 : width;	
		for(int j = 0; j < width; ++j){
			boolean tmp = true;
			for(int i = 0; i < width; ++i){
				if(tiles[ori][i*width+j]){
					tmp = false;
				}
			}
			if(tmp){
				++buff;
			} else break;
		}
		return buff;
	}
	
	public int bufferRight(int ori) {
		int buff = 0;
		int width = getTiles()[0].length == 9 ? 3 : 4; // get width (3 or 4) of piece
		width = getTiles()[0].length == 4 ? 2 : width;	
		for(int j = width-1; j >= 0; --j){
			boolean tmp = true;
			for(int i = 0; i < width; ++i){
				if(tiles[ori][i*width+j]) {
					tmp = false;
				}
			}
			if(tmp){
				++buff;
			} else break;
		}
		return buff;
	}
	
	public static void main(String args[]){
		Tetromino t = Tetromino.TypeZ;
		System.out.println(t.bufferLeft(1));
				
	}
	
	
	
	
}