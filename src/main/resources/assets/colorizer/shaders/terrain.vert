#version 120

// uniform int currentSectionPos[3];
// uniform int playerSectionPos[3];

varying out vec2 textureCoord;
varying out vec3 vertexColor;
// varying out vec3 sectionPos;
varying out vec3 blockPos;

void main() {
	vec4 vertex = gl_Vertex;//vec4(gl_Vertex);
	gl_Position = gl_ModelViewProjectionMatrix * vertex;
	textureCoord = gl_MultiTexCoord0.st;
	vertexColor = gl_Color.rgb;
	blockPos = vertex.xyz - normalize(gl_Normal.xyz) * 0.001; // 0.5
	
	// int sectionX = currentSectionPos[0] - playerSectionPos[0];
	// int sectionY = currentSectionPos[1] - playerSectionPos[1];
	// int sectionZ = currentSectionPos[2] - playerSectionPos[2];
	// sectionPos = vec3(sectionX, sectionY, sectionZ);
	
	// int blockX = int(vertex.x - gl_Normal.x * 0.5);
	// int blockY = int(vertex.y - gl_Normal.y * 0.5);
	// int blockZ = int(vertex.z - gl_Normal.z * 0.5);
	// blockPos = ivec3(blockX, blockY, blockZ);
}