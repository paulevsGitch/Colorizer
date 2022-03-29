package paulevs.colorizer.enums;

public enum BlockColor {
	BLACK(0, "black", 0x000000),
	DARK_GRAY(1, "dark_gray", 0x545454),
	LIGHT_GRAY(2, "light_gray", 0xa8a8a8),
	WHITE(3, "white", 0xffffff),
	
	DARK_BROWN(4, "dark_brown", 0x6c4115),
	DARK_RED(5, "dark_red", 0x8d2121),
	DARK_ORANGE(6, "dark_orange", 0xe35f00),
	DARK_YELLOW(7, "dark_yellow", 0xe4d856),
	DARK_GREEN(8, "dark_green", 0x317222),
	DARK_CYAN(9, "dark_cyan", 0x30a5a5),
	DARK_PICTONE_BLUE(10, "dark_pictone_blue", 0x2d71a1),
	DARK_ROYAL_BLUE(11, "dark_royal_blue", 0x1f329e),
	DARK_MAGENTA(12, "dark_magenta", 0x8129a8),
	DARK_PINK(13, "dark_pink", 0xa02b5d),
	
	LIGHT_BROWN(14, "light_brown", 0xac6620),
	LIGHT_RED(15, "light_red", 0xd11919),
	LIGHT_ORANGE(16, "light_orange", 0xff7817),
	LIGHT_YELLOW(17, "light_yellow", 0xffec1b),
	LIGHT_GREEN(18, "light_green", 0x42c623),
	LIGHT_CYAN(19, "light_cyan", 0x40e8e8),
	LIGHT_PICTONE_BLUE(20, "light_pictone_blue", 0x35a4f3),
	LIGHT_ROYAL_BLUE(21, "light_royal_blue", 0x304be5),
	LIGHT_MAGENTA(22, "light_magenta", 0xb735f0),
	LIGHT_PINK(23, "light_pink", 0xeb2a7e);
	
	private static final BlockColor[] VALUES = values();
	private static final BlockColor[] BRIGHT = new BlockColor[10];
	private static final BlockColor[] DARK = new BlockColor[10];
	private static final BlockColor[] GRAY = new BlockColor[4];
	private final String name;
	private final byte index;
	private final int rgb;
	
	BlockColor(int index, String name, int rgb) {
		this.index = (byte) index;
		this.name = name;
		this.rgb = rgb;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRGB() {
		return rgb;
	}
	
	public byte getIndex() {
		return index;
	}
	
	public static int getCount() {
		return VALUES.length;
	}
	
	public static final BlockColor getByIndex(int index) {
		return VALUES[index];
	}
	
	public static BlockColor[] getColors() {
		return VALUES;
	}
	
	public static BlockColor[] getGrayColors() {
		return GRAY;
	}
	
	public static BlockColor[] getBrightColors() {
		return BRIGHT;
	}
	
	public static BlockColor[] getDarkColors() {
		return DARK;
	}
	
	static {
		for (byte i = 0; i < 4; i++) {
			GRAY[i] = getByIndex(i);
		}
		
		for (byte i = 0; i < 10; i++) {
			BRIGHT[i] = getByIndex(i + 4);
			DARK[i] = getByIndex(i + 14);
		}
	}
}
