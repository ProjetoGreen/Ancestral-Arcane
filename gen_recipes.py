import os
import json

base_dir = "/Users/dan/Documents/mine mod/dntr/src/main/resources/data/dntr/recipe"
os.makedirs(base_dir, exist_ok=True)

# Early
recipes = {
    "fire": {"tier": "early", "ink": "minecraft:ink_sac", "item": "minecraft:red_dye"},
    "frost": {"tier": "early", "ink": "minecraft:ink_sac", "item": "minecraft:light_blue_dye"},
    "heal": {"tier": "early", "ink": "minecraft:ink_sac", "item": "minecraft:green_dye"},
    "breathe": {"tier": "early", "ink": "minecraft:ink_sac", "item": "minecraft:blue_dye"},
    "fertilize": {"tier": "early", "ink": "minecraft:ink_sac", "item": "minecraft:lime_dye"},
    "light": {"tier": "early", "ink": "minecraft:glow_ink_sac", "item": "minecraft:yellow_dye"},
    # Mid
    "fire_friend": {"tier": "mid", "ink": "minecraft:ink_sac", "item": "minecraft:orange_dye", "mid_item": "minecraft:magma_cream"},
    "storm": {"tier": "mid", "ink": "minecraft:glow_ink_sac", "item": "minecraft:yellow_dye", "mid_item": "minecraft:copper_ingot"},
    "frost_walker": {"tier": "mid", "ink": "minecraft:ink_sac", "item": "minecraft:cyan_dye", "mid_item": "minecraft:packed_ice"},
    "mend": {"tier": "mid", "ink": "minecraft:ink_sac", "item": "minecraft:green_dye", "mid_item": "minecraft:iron_nugget"},
    "stabilize": {"tier": "mid", "ink": "minecraft:ink_sac", "item": "minecraft:cyan_dye", "mid_item": "minecraft:scute"},
    "cleanse": {"tier": "mid", "ink": "minecraft:glow_ink_sac", "item": "minecraft:white_dye", "mid_item": "minecraft:milk_bucket"},
    # Late
    "breaker": {"tier": "late", "ink": "minecraft:ink_sac", "mid_item": "minecraft:flint", "item": "minecraft:iron_nugget"},
    "ward": {"tier": "late", "ink": "minecraft:ink_sac", "mid_item": "minecraft:shield", "item": "minecraft:gray_dye"},
    "stonebind": {"tier": "late", "ink": "minecraft:ink_sac", "mid_item": "minecraft:smooth_stone", "item": "minecraft:brown_dye"},
    "reach": {"tier": "late", "ink": "minecraft:ink_sac", "mid_item": "minecraft:ender_pearl", "item": "minecraft:magenta_dye"},
    "silence": {"tier": "late", "ink": "minecraft:glow_ink_sac", "mid_item": "minecraft:amethyst_shard", "item": "minecraft:black_dye"}
}

for spell, rules in recipes.items():
    tier = rules["tier"]
    ink = rules["ink"]
    
    if tier == "early":
        pattern = [
            "PIF",
            " X "
        ]
        key = {
            "P": {"item": "minecraft:paper"},
            "I": {"item": ink},
            "F": {"item": "minecraft:feather"},
            "X": {"item": rules["item"]}
        }
    elif tier == "mid" or tier == "late":
        pattern = [
            "PIF",
            "LDE",
            " X "
        ]
        key = {
            "P": {"item": "minecraft:paper"},
            "I": {"item": ink},
            "F": {"item": "minecraft:feather"},
            "L": {"item": "minecraft:lapis_lazuli"},
            "D": {"item": "minecraft:glowstone_dust"},
            "E": {"item": rules["mid_item"]},
            "X": {"item": rules["item"]}
        }

    recipe = {
        "type": "minecraft:crafting_shaped",
        "category": "misc",
        "pattern": pattern,
        "key": key,
        "result": {
            "id": f"dntr:scroll_{spell}",
            "count": 1
        }
    }
    
    with open(os.path.join(base_dir, f"scroll_{spell}.json"), "w") as f:
        json.dump(recipe, f, indent=4)

print("Generated all scroll recipes.")
