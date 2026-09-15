#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;

in vec4 vertexColor;

in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 textureColor = texture(Sampler0, texCoord0);

    if(textureColor.a < 0.2){
        discard;
    }
    // Keep the texture's alpha.
    // Completely replace its RGB with white.
    vec4 color = vec4(1.0, 1.0, 1.0, textureColor.a);

    // Apply the entity's color modulator/alpha.
    color *= ColorModulator;

    fragColor = apply_fog(
            color,
            sphericalVertexDistance,
            cylindricalVertexDistance,
            FogEnvironmentalStart,
            FogEnvironmentalEnd,
            FogRenderDistanceStart,
            FogRenderDistanceEnd,
            FogColor
    );
}