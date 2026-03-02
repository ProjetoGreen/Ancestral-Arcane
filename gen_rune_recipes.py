import os
import json

base_path = "/Users/dan/Documents/mine mod/dntr/src/main/resources/data/ancestral_arcane/recipe"
os.makedirs(base_path, exist_ok=True)

craft_recipes = [
    {
        "id": "crude_rune_t1",
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
            {"item": "minecraft:clay_ball"},
            {"item": "minecraft:glowstone_dust"}
        ],
        "result": {
            "id": "ancestral_arcane:rune",
            "count": 1,
            "components": {
                "minecraft:custom_data": "{ancestral_arcane:{tier:1,crude:1,empty:1}}"
            }
        }
    },
    {
        "id": "crude_rune_t2",
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
            {"item": "minecraft:amethyst_shard"},
            {
                "item": "ancestral_arcane:rune",
                "components": {
                    "minecraft:custom_data": "{ancestral_arcane:{tier:1,crude:1,empty:1}}"
                }
            }
        ],
        "result": {
            "id": "ancestral_arcane:rune",
            "count": 1,
            "components": {
                "minecraft:custom_data": "{ancestral_arcane:{tier:2,crude:1,empty:1}}"
            }
        }
    },
    {
        "id": "crude_rune_t3",
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
            {"item": "minecraft:echo_shard"},
            {
                "item": "ancestral_arcane:rune",
                "components": {
                    "minecraft:custom_data": "{ancestral_arcane:{tier:2,crude:1,empty:1}}"
                }
            }
        ],
        "result": {
            "id": "ancestral_arcane:rune",
            "count": 1,
            "components": {
                "minecraft:custom_data": "{ancestral_arcane:{tier:3,crude:1,empty:1}}"
            }
        }
    },
    {
        "id": "crude_rune_t4",
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
            {"item": "minecraft:chorus_fruit"},
            {
                "item": "ancestral_arcane:rune",
                "components": {
                    "minecraft:custom_data": "{ancestral_arcane:{tier:3,crude:1,empty:1}}"
                }
            }
        ],
        "result": {
            "id": "ancestral_arcane:rune",
            "count": 1,
            "components": {
                "minecraft:custom_data": "{ancestral_arcane:{tier:4,crude:1,empty:1}}"
            }
        }
    },
    {
        "id": "crude_rune_t5",
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
            {"item": "minecraft:netherite_scrap"},
            {"item": "minecraft:echo_shard"},
            {
                "item": "ancestral_arcane:rune",
                "components": {
                    "minecraft:custom_data": "{ancestral_arcane:{tier:4,crude:1,empty:1}}"
                }
            }
        ],
        "result": {
            "id": "ancestral_arcane:rune",
            "count": 1,
            "components": {
                "minecraft:custom_data": "{ancestral_arcane:{tier:5,crude:1,empty:1}}"
            }
        }
    }
]

smelt_recipes = []
for tier in range(1, 6):
    recipe = {
        "type": "minecraft:blasting",
        "ingredient": {
            "item": "ancestral_arcane:rune",
            "components": {
                "minecraft:custom_data": f"{{ancestral_arcane:{{tier:{tier},crude:1,empty:1}}}}"
            }
        },
        "result": {
            "id": "ancestral_arcane:rune",
            "components": {
                "minecraft:custom_data": f"{{ancestral_arcane:{{rune:{{tier:{tier},crude:0,empty:1}}}}}}"
            }
        },
        "experience": 0.2,
        "cookingtime": 100
    }
    smelt_recipes.append({"id": f"consagrate_rune_t{tier}", "data": recipe})

for rec in craft_recipes:
    name = rec.pop("id")
    # Wrap in "rune: {}" nested struct to match previous java impl CustomDataUtil
    comp = rec["result"]["components"]["minecraft:custom_data"]
    comp = comp.replace("{ancestral_arcane:{", "{ancestral_arcane:{rune:{").replace("}}", "}}}")
    rec["result"]["components"]["minecraft:custom_data"] = comp
    
    # Ingredient should match
    for ing in rec["ingredients"]:
        if "components" in ing:
            comp_ing = ing["components"]["minecraft:custom_data"]
            comp_ing = comp_ing.replace("{ancestral_arcane:{", "{ancestral_arcane:{rune:{").replace("}}", "}}}")
            ing["components"]["minecraft:custom_data"] = comp_ing

    with open(os.path.join(base_path, f"{name}.json"), "w") as f:
        json.dump(rec, f, indent=4)

for rec in smelt_recipes:
    name = rec["id"]
    ing = rec["data"]["ingredient"]
    comp_ing = ing["components"]["minecraft:custom_data"]
    comp_ing = comp_ing.replace("{ancestral_arcane:{", "{ancestral_arcane:{rune:{").replace("}}", "}}}")
    ing["components"]["minecraft:custom_data"] = comp_ing
    
    with open(os.path.join(base_path, f"{name}.json"), "w") as f:
        json.dump(rec["data"], f, indent=4)

print("Rune recipes generated.")
