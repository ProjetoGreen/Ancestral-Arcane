import os
import json
import base64

ASSETS_DIR = "src/main/resources/assets/dntr"

def mk(path):
    os.makedirs(os.path.join(ASSETS_DIR, path), exist_ok=True)

mk("lang")
mk("textures/item")
mk("textures/block")
mk("models/item")
mk("models/block")
mk("blockstates")

# Tiny 16x16 purple/black png (fallback texture)
png_b64 = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAIElEQVQ4T2NkYGD4z8DAwMgw0oDxowEjaBqNBoDABH8AACR5BAsYV42/AAAAAElFTkSuQmCC"
png_data = base64.b64decode(png_b64)

items = [
    "rune", "intent_ink_fire", "intent_ink_storm", "intent_ink_heal", "intent_ink_breathe",
    "intent_ink_heartstone", "intent_ink_breaker", "intent_ink_fertilize", "intent_ink_wolves",
    "scroll", "grimoire_t1", "grimoire_t2", "grimoire_t3", "grimoire_t4", "forgotten_magicbook",
    "end_sigil", "arcane_residue", "arcane_spear_focus"
]

blocks = ["arcane_smithing_table", "home_anchor"]

for item in items:
    with open(f"{ASSETS_DIR}/textures/item/{item}.png", "wb") as f:
        f.write(png_data)
    with open(f"{ASSETS_DIR}/models/item/{item}.json", "w") as f:
        json.dump({
            "parent": "item/generated",
            "textures": {"layer0": f"ancestral_arcane:item/{item}"}
        }, f, indent=2)

for block in blocks:
    with open(f"{ASSETS_DIR}/textures/block/{block}.png", "wb") as f:
        f.write(png_data)
    with open(f"{ASSETS_DIR}/models/block/{block}.json", "w") as f:
        json.dump({
            "parent": "block/cube_all",
            "textures": {"all": f"ancestral_arcane:block/{block}"}
        }, f, indent=2)
    with open(f"{ASSETS_DIR}/models/item/{block}.json", "w") as f:
        json.dump({
            "parent": f"ancestral_arcane:block/{block}"
        }, f, indent=2)
    with open(f"{ASSETS_DIR}/blockstates/{block}.json", "w") as f:
        json.dump({
            "variants": {"": {"model": f"ancestral_arcane:block/{block}"}}
        }, f, indent=2)

lang = {}
for item in items:
    lang[f"item.dntr.{item}"] = item.replace("_", " ").title()
for block in blocks:
    lang[f"block.dntr.{block}"] = block.replace("_", " ").title()

lang["itemGroup.dntr"] = "AncestralArcane Magic"
lang["container.dntr.arcane_smithing"] = "Arcane Smithing Table"

with open(f"{ASSETS_DIR}/lang/en_us.json", "w") as f:
    json.dump(lang, f, indent=2)

with open(f"{ASSETS_DIR}/lang/pt_br.json", "w") as f:
    json.dump(lang, f, indent=2)

print("Assets generated.")
