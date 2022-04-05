#version 330

layout(triangles) in;
layout(triangle_strip, max_vertices=3) out;

in vec2 textureCoord[];
in vec3 vertexColor[];
in vec3 blockPos[];
in vec4 fogColor[];

out vec2 geo_textureCoord;
out vec3 geo_vertexColor;
out vec3 geo_blockPos;
out vec3 geo_normal;
out vec4 geo_fogColor;

bool hasCardinalAxis(vec3 vec) {
	return vec.x < -0.99 || vec.x > 0.99 || vec.y < -0.99 || vec.y > 0.99 || vec.z < -0.99 || vec.z > 0.99;
}

void main() {
	vec3 a = blockPos[1] - blockPos[0];
	vec3 b = blockPos[2] - blockPos[0];
	vec3 normal = normalize(cross(b, a));
	bool hasCardinal = hasCardinalAxis(normal);
	vec3 halfNormal = normal * 0.5;
	
	for (int i = 0; i < gl_in.length(); ++i) {
		gl_Position = gl_in[i].gl_Position;
		geo_textureCoord = textureCoord[i];
		geo_vertexColor = vertexColor[i];
		geo_fogColor = fogColor[i];
		if (hasCardinal) {
			geo_blockPos = blockPos[i] + halfNormal;
		}
		else {
			geo_blockPos = blockPos[i];
		}
		geo_normal = normal;
		EmitVertex();
	}
	
	EndPrimitive();
}