package paulevs.colorizer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.modificationstation.stationapi.impl.level.chunk.ChunkSection;
import net.modificationstation.stationapi.impl.level.chunk.ChunkSectionsAccessor;
import paulevs.bhcore.rendering.shaders.Shader;
import paulevs.bhcore.rendering.shaders.ShaderProgram;
import paulevs.bhcore.rendering.shaders.ShaderType;
import paulevs.bhcore.rendering.shaders.uniforms.IntUniform;
import paulevs.bhcore.rendering.shaders.uniforms.TextureUniform;
import paulevs.bhcore.rendering.shaders.uniforms.Vec2FUniform;
import paulevs.bhcore.rendering.shaders.uniforms.Vec3IUniform;
import paulevs.bhcore.rendering.shaders.world.WorldShaderData;
import paulevs.bhcore.storage.section.SectionDataHandler;
import paulevs.bhcore.storage.section.arrays.EnumArraySectionData;
import paulevs.colorizer.enums.BlockColor;
import paulevs.colorizer.listeners.InitListener;

@Environment(EnvType.CLIENT)
public class ColorizerClient {
	private static ShaderProgram program;
	private static Vec3IUniform playerPos;
	private static Vec3IUniform sectionPos;
	private static Vec2FUniform dataScale;
	private static IntUniform dataSide;
	private static WorldShaderData data;
	private static TextureUniform dataTexture;
	
	public static void init() {
		Shader vert = new Shader("/assets/colorizer/shaders/terrain.vert", ShaderType.VERTEX);
		Shader frag = new Shader("/assets/colorizer/shaders/terrain.frag", ShaderType.FRAGMENT);
		program = new ShaderProgram(vert, frag);
		playerPos = program.getUniform("playerSectionPos", Vec3IUniform::new);
		sectionPos = program.getUniform("currentSectionPos", Vec3IUniform::new);
		dataScale = program.getUniform("dataScale", Vec2FUniform::new);
		dataSide = program.getUniform("dataSide", IntUniform::new);
		dataTexture = program.getUniform("dataTexture", TextureUniform::new);
		ShaderProgram.unbind();
		
		data = new WorldShaderData(4, 4, ((level, sectionPos, data) -> {
			Chunk chunk = level.getChunkFromCache(sectionPos.x, sectionPos.z);
			ChunkSectionsAccessor accessor = (ChunkSectionsAccessor) chunk;
			ChunkSection[] sections = accessor.getSections();
			ChunkSection section = sections[sectionPos.y];
			if (section == null) return;
			EnumArraySectionData<BlockColor> colorData = SectionDataHandler.getData(section, InitListener.colorDataIndex);
			for (short i = 0; i < 4096; i++) {
				BlockColor color = colorData.getData(i);
				data[i] = color == null ? (byte) 0 : (byte) (color.getIndex() + 1);
			}
		}));
		
		dataScale.set(data.getDataWidth(), data.getDataHeight());
		dataSide.setValue(data.getDataSide());
		dataTexture.setTexture(data.getTexture());
	}
	
	public static void bindTexture() {
		data.getTexture().bind();
	}
	
	public static void updatePlayer(PlayerBase player) {
		int cy = (((int) player.y) >> 4);
		playerPos.set(player.chunkX, cy, player.chunkZ);
		data.update(player);
	}
	
	public static void bindProgram() {
		program.bindWithUniforms();
	}
	
	public static void setSectionPos(int x, int y, int z) {
		sectionPos.set(x, y, z);
		sectionPos.bind();
	}
	
	public static void updateSection(Level level, int x, int y, int z) {
		data.updateSection(level, x, y, z, true);
	}
}
