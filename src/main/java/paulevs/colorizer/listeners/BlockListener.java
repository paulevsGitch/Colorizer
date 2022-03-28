package paulevs.colorizer.listeners;

public class BlockListener {
	/*@EventListener
	public void onColorsRegister(BlockColoursRegisterEvent event) {
		BlockColourProvider provider = (state, world, pos, tintIndex) -> {
			Chunk chunk = ClientUtil.getClientLevel().getChunk(pos.x, pos.z);
			if (chunk == null) return Color.WHITE.getRGB();
			
			EnumArraySectionData<BlockColor> data = SectionDataHandler.getData(chunk, pos.y >> 4, InitListener.colorDataIndex);
			
			if (data == null) return Color.WHITE.getRGB();
			BlockColor value = data.getData(MathUtil.getIndex16(pos.x & 15, pos.y & 15, pos.z & 15));
			if (value == null) return Color.WHITE.getRGB();
			
			return value.getRGB();
		};
		BlockBase[] blocks = Arrays.stream(BlockBase.BY_ID).filter(block -> block != null).collect(toList()).toArray(new BlockBase[0]);
		//BlockBase[] blocks = BlockRegistry.INSTANCE.stream().toList().toArray(new BlockBase[0]);
		event.getBlockColours().registerColourProvider(provider, blocks);
	}*/
}
