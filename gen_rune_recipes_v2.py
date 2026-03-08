import json
import os

recipes_dir = "src/main/resources/data/ancestral_arcane/recipes"

# Fix Consagrate Runes (Blasting)
for tier in range(1, 6):
    file_path = os.path.join(recipes_dir, f"consagrate_rune_t{tier}.json")
    if os.path.exists(file_path):
        with open(file_path, 'r') as f:
            data = json.load(f)
            
        # Update ingredient
        data["ingredient"] = {
            "type": "neoforge:components",
            "items": "ancestral_arcane:rune",
            "components": {
                "minecraft:custom_data": f"{{ancestral_arcane:{{rune:{{tier:{tier},crude:1,empty:1}}}}}}"
            },
            "strict": False
        }
        
        with open(file_path, 'w') as f:
            json.dump(data, f, indent=4)

# Fix Crude Runes (T2-T5 have previous tier rune as an ingredient)
for tier in range(2, 6):
    file_path = os.path.join(recipes_dir, f"crude_rune_t{tier}.json")
    if os.path.exists(file_path):
        with open(file_path, 'r') as f:
            data = json.load(f)
            
        # Find the rune ingredient and update it
        for i, ingredient in enumerate(data["ingredients"]):
            if "item" in ingredient and ingredient["item"] == "ancestral_arcane:rune":
                data["ingredients"][i] = {
                    "type": "neoforge:components",
                    "items": "ancestral_arcane:rune",
                    "components": {
                        "minecraft:custom_data": f"{{ancestral_arcane:{{rune:{{tier:{tier-1},crude:1,empty:1}}}}}}"
                    },
                    "strict": False
                }
                
        with open(file_path, 'w') as f:
            json.dump(data, f, indent=4)

print("Rune recipes updated.")
