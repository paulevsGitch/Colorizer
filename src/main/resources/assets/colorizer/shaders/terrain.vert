#version 120

varying out vec2 textureCoord;
varying out vec3 vertexColor;
varying out vec3 blockPos;
varying out vec4 fogColor;

float getFogDensity(float z) {
	return clamp((z - gl_Fog.start) * gl_Fog.scale, 0.0, 1.0);
}

void main() {
	vec4 vertex = gl_Vertex;//vec4(gl_Vertex);
	gl_Position = gl_ModelViewProjectionMatrix * vertex;
	textureCoord = gl_MultiTexCoord0.st;
	vertexColor = gl_Color.rgb;
	blockPos = vertex.xyz; // 0.5
	fogColor.rgb = gl_Fog.color.rgb;
	fogColor.a = getFogDensity(gl_Position.z);
}