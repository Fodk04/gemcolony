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

    if (textureColor.a < 0.2) {
        discard;
    }

    vec3 tint = vec3(1.0, 0.2, 0.2);
    vec3 tintedColor = mix(textureColor.rgb, tint, 0.65);
    vec4 color = vec4(tintedColor, textureColor.a);

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