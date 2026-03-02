import os
import shutil
import json

src_base = "/Users/dan/Documents/mine mod/dntr/src/main/resources/assets/ancestral_arcane/arcane items"
dest_base = "/Users/dan/Documents/mine mod/dntr/src/main/resources/assets/ancestral_arcane"
MOD_ID = "ancestral_arcane"

def copy_and_gen():
    # Directories
    os.makedirs(os.path.join(dest_base, "textures/block"), exist_ok=True)
    os.makedirs(os.path.join(dest_base, "textures/item"), exist_ok=True)
    os.makedirs(os.path.join(dest_base, "textures/gui/arcane_table"), exist_ok=True)
    os.makedirs(os.path.join(dest_base, "models/block"), exist_ok=True)
    os.makedirs(os.path.join(dest_base, "models/item"), exist_ok=True)
    os.makedirs(os.path.join(dest_base, "blockstates"), exist_ok=True)

    # 1. Blocks
    block_src = os.path.join(src_base, "arcane_table")
    for f in os.listdir(block_src):
        if f.startswith("arcane_table_") and f.endswith(".png"):
            shutil.copy(os.path.join(block_src, f), os.path.join(dest_base, "textures/block", f))
        elif f.endswith(".png"): # GUI
            shutil.copy(os.path.join(block_src, f), os.path.join(dest_base, "textures/gui/arcane_table", f))

    # Gen Arcane Table Blockstate
    with open(os.path.join(dest_base, "blockstates/arcane_table.json"), "w") as f:
        json.dump({"variants": {"": {"model": f"{MOD_ID}:block/arcane_table"}}}, f, indent=4)
        
    # Gen Arcane Table Block Model
    with open(os.path.join(dest_base, "models/block/arcane_table.json"), "w") as f:
        json.dump({
            "parent": "minecraft:block/cube",
            "textures": {
                "down": f"{MOD_ID}:block/arcane_table_bottom",
                "up": f"{MOD_ID}:block/arcane_table_top",
                "north": f"{MOD_ID}:block/arcane_table_side",
                "east": f"{MOD_ID}:block/arcane_table_side",
                "south": f"{MOD_ID}:block/arcane_table_side",
                "west": f"{MOD_ID}:block/arcane_table_side",
                "particle": f"{MOD_ID}:block/arcane_table_top"
            }
        }, f, indent=4)
        
    # Gen Arcane Table Item Model
    with open(os.path.join(dest_base, "models/item/arcane_table.json"), "w") as f:
        json.dump({"parent": f"{MOD_ID}:block/arcane_table"}, f, indent=4)


    # 2. Simple Items
    simple_dirs = ["grimoire and scroll", "runes"]
    for d in simple_dirs:
        dir_path = os.path.join(src_base, d)
        for f in os.listdir(dir_path):
            if f.endswith(".png"):
                item_id = f.replace(".png", "")
                
                if item_id == "grimoire_1": item_id = "grimoire_t1"
                elif item_id == "grimoire_2": item_id = "grimoire_t2"
                elif item_id == "grimoire_3": item_id = "grimoire_t3"
                elif item_id == "grimoire_4": item_id = "grimoire_t4"
                elif item_id == "grimoire_5": item_id = "grimoire_t5"
                elif item_id == "grimoire_forgoten": item_id = "forgotten_magicbook"
                
                shutil.copy(os.path.join(dir_path, f), os.path.join(dest_base, "textures/item", f"{item_id}.png"))
                
                if item_id == "rune":
                    with open(os.path.join(dest_base, "models/item", f"{item_id}.json"), "w") as jf:
                        json.dump({
                            "parent": "minecraft:item/generated",
                            "textures": {"layer0": f"{MOD_ID}:item/{item_id}"},
                            "overrides": [
                                { "predicate": { f"{MOD_ID}:rune_state": 0.1 }, "model": f"{MOD_ID}:item/rune_crude" },
                                { "predicate": { f"{MOD_ID}:rune_state": 0.2 }, "model": f"{MOD_ID}:item/rune_generic_inscribed" },
                                { "predicate": { f"{MOD_ID}:rune_state": 0.3 }, "model": f"{MOD_ID}:item/rune_upgraded" },
                                { "predicate": { f"{MOD_ID}:rune_state": 0.4 }, "model": f"{MOD_ID}:item/rune_upgraded_generic_inscribed" }
                            ]
                        }, jf, indent=4)
                else:
                    with open(os.path.join(dest_base, "models/item", f"{item_id}.json"), "w") as jf:
                        json.dump({
                            "parent": "minecraft:item/generated",
                            "textures": {"layer0": f"{MOD_ID}:item/{item_id}"}
                        }, jf, indent=4)


    # 3. Wands
    wand_src = os.path.join(src_base, "wands_cast")
    for f in os.listdir(wand_src):
        if f.endswith(".png"):
            shutil.copy(os.path.join(wand_src, f), os.path.join(dest_base, "textures/item", f))
            
    # Need to generate the intricate wand models base
    wands = [
        "flint_wand", "copper_wand", "iron_wand", "golden_wand", "diamond_wand", "emerald_wand", "netherite_wand",
        "flint_wand_leather_grip", "copper_wand_leather_grip", "iron_wand_leather_grip", 
        "golden_wand_leather_grip", "diamond_wand_leather_grip", "emerald_wand_leather_grip", "netherite_wand_leather_grip"
    ]
    
    for w in wands:
        variants = [
            ("", ""),
            ("_in_hand", ""),
            ("", "_cast_a"), ("", "_cast_b"), ("", "_cast_c"), ("", "_cast_d"),
            ("_in_hand", "_cast_a"), ("_in_hand", "_cast_b"), ("_in_hand", "_cast_c"), ("_in_hand", "_cast_d")
        ]
        
        for hand, cast in variants:
            model_id = f"{w}{hand}{cast}"
            is_base = (hand == "" and cast == "")
            
            with open(os.path.join(dest_base, "models/item", f"{model_id}.json"), "w") as f:
                model_data = {
                    "parent": "minecraft:item/handheld",
                    "textures": {"layer0": f"{MOD_ID}:item/{model_id}"}
                }
                
                if is_base:
                    model_data["overrides"] = [
                        {"predicate": {f"{MOD_ID}:in_hand": 1, f"{MOD_ID}:casting": 1, f"{MOD_ID}:cast_stage": 4}, "model": f"{MOD_ID}:item/{w}_in_hand_cast_d"},
                        {"predicate": {f"{MOD_ID}:in_hand": 1, f"{MOD_ID}:casting": 1, f"{MOD_ID}:cast_stage": 3}, "model": f"{MOD_ID}:item/{w}_in_hand_cast_c"},
                        {"predicate": {f"{MOD_ID}:in_hand": 1, f"{MOD_ID}:casting": 1, f"{MOD_ID}:cast_stage": 2}, "model": f"{MOD_ID}:item/{w}_in_hand_cast_b"},
                        {"predicate": {f"{MOD_ID}:in_hand": 1, f"{MOD_ID}:casting": 1, f"{MOD_ID}:cast_stage": 1}, "model": f"{MOD_ID}:item/{w}_in_hand_cast_a"},
                        {"predicate": {f"{MOD_ID}:in_hand": 1, f"{MOD_ID}:casting": 1}, "model": f"{MOD_ID}:item/{w}_in_hand_cast_a"},
                        {"predicate": {f"{MOD_ID}:in_hand": 1}, "model": f"{MOD_ID}:item/{w}_in_hand"},
                        
                        {"predicate": {f"{MOD_ID}:casting": 1, f"{MOD_ID}:cast_stage": 4}, "model": f"{MOD_ID}:item/{w}_cast_d"},
                        {"predicate": {f"{MOD_ID}:casting": 1, f"{MOD_ID}:cast_stage": 3}, "model": f"{MOD_ID}:item/{w}_cast_c"},
                        {"predicate": {f"{MOD_ID}:casting": 1, f"{MOD_ID}:cast_stage": 2}, "model": f"{MOD_ID}:item/{w}_cast_b"},
                        {"predicate": {f"{MOD_ID}:casting": 1, f"{MOD_ID}:cast_stage": 1}, "model": f"{MOD_ID}:item/{w}_cast_a"},
                        {"predicate": {f"{MOD_ID}:casting": 1}, "model": f"{MOD_ID}:item/{w}_cast_a"},
                    ]
                
                json.dump(model_data, f, indent=4)

if __name__ == "__main__":
    copy_and_gen()
    print("Asset generation complete!")
