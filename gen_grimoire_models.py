import os

models_dir = '/Users/dan/Documents/mine mod/dntr/src/main/resources/assets/ancestral_arcane/models/item'

spells = [
    "fire", "fire_friend", "storm", "frost", "frost_walker", "heal", "mend",
    "stabilize", "cleanse", "breathe", "fertilize", "light", "breaker",
    "ward", "stonebind", "reach", "silence"
]

template = """{
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": "ancestral_arcane:item/grimoire_t1"
    }
}"""

for spell in spells:
    filename = f"grimoire_{spell}.json"
    filepath = os.path.join(models_dir, filename)
    if not os.path.exists(filepath):
        with open(filepath, 'w') as f:
            f.write(template)
        print(f"Created {filename}")
