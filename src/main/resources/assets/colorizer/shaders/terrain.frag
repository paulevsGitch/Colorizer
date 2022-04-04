#version 330 core

uniform sampler2D terrainTexture;
uniform sampler2D dataTexture;

uniform int currentSectionPos[3];
uniform int playerSectionPos[3];

uniform vec2 dataScale;
uniform vec2 sectionUV;
uniform int dataSide;

in vec2 textureCoord;
in vec3 vertexColor;
// in vec3 sectionPos;
in vec3 blockPos;

out vec4 color;

const vec3 blockColors[24] = vec3[24] (
	vec3(0.000, 0.000, 0.000),
	vec3(0.329, 0.329, 0.329),
	vec3(0.659, 0.659, 0.659),
	vec3(1.000, 1.000, 1.000),
	vec3(0.424, 0.255, 0.082),
	vec3(0.553, 0.129, 0.129),
	vec3(0.890, 0.373, 0.000),
	vec3(0.894, 0.847, 0.337),
	vec3(0.192, 0.447, 0.133),
	vec3(0.188, 0.647, 0.647),
	vec3(0.176, 0.443, 0.631),
	vec3(0.122, 0.196, 0.620),
	vec3(0.506, 0.161, 0.659),
	vec3(0.627, 0.169, 0.365),
	vec3(0.675, 0.400, 0.125),
	vec3(0.820, 0.098, 0.098),
	vec3(1.000, 0.471, 0.090),
	vec3(1.000, 0.925, 0.106),
	vec3(0.259, 0.776, 0.137),
	vec3(0.251, 0.910, 0.910),
	vec3(0.208, 0.643, 0.953),
	vec3(0.188, 0.294, 0.898),
	vec3(0.718, 0.208, 0.941),
	vec3(0.922, 0.165, 0.494)
);

int getSectionIndex(int sectionX, int sectionY, int sectionZ, int scaleX, int scaleY) {
	//int scale = int(dataScale.x);
	// int sectionX = int(sectionPos.x);
	// int sectionY = int(sectionPos.y);
	// int sectionZ = int(sectionPos.z);
	
	return ((sectionX * scaleY) + sectionY) * scaleX + sectionZ;
}

vec2 indexToUV(int textureSize, int index) {
	float scale = 64.0 / textureSize;
	float x = mod(index, dataSide) * scale;
	float y = int(index / dataSide) * scale;
	return vec2(x, y);
}

int getBlockIndex() {
	int blockX = int(blockPos.x);
	int blockY = int(blockPos.y);
	int blockZ = int(blockPos.z);
	return blockX * 256 + blockY * 16 + blockZ;
}

int wrap(int value, int side) {
	int offset = int(value / side) * side;
	if (offset > value) {
		offset -= side;
	}
	float delta = float(value - offset) / side;
	return int(delta * side);
}

void main() {
	int textureSize = textureSize(dataTexture, 0).x;
	vec4 tex = texture(terrainTexture, textureCoord);
	color = vec4(tex.rgb * vertexColor, tex.a);
	
	int scaleX = int(dataScale.x);
	int scaleY = int(dataScale.y);
	int sectionX = abs(currentSectionPos[0] - playerSectionPos[0]);
	int sectionY = abs(currentSectionPos[1] - playerSectionPos[1]);
	int sectionZ = abs(currentSectionPos[2] - playerSectionPos[2]);
	int sideX = int(scaleX * 0.5);
	int sideY = int(scaleY * 0.5);
	
	if (sectionX <= sideX && sectionY <= sideY && sectionZ <= sideX) {
		sectionX = wrap(currentSectionPos[0], scaleX);
		sectionY = wrap(currentSectionPos[1], scaleY);
		sectionZ = wrap(currentSectionPos[2], scaleX);
	
		int index = getSectionIndex(sectionX, sectionY, sectionZ, scaleX, scaleY);
		vec2 uv = indexToUV(textureSize, index);
		int blockIndex = getBlockIndex();
		float blockU = mod(blockIndex, 64) / textureSize;
		float blockV = floor(blockIndex / 64.0) / textureSize;
		uv += vec2(blockU, blockV);
		
		int colorData = int(texture(dataTexture, uv).r * 255);
		if (colorData > 0) {
			vec3 blockColor = blockColors[colorData - 1];
			//float intensity = (max(color.r, max(color.g, color.b)) - 0.5) * 0.5;
			//color.rgb = blockColor + vec3(intensity);
			float intensity = min(color.r, min(color.g, color.b)) * 3;
			intensity = clamp(intensity, 0.0, 1.0);
			color.rgb = blockColor * intensity;
		}
	}
}