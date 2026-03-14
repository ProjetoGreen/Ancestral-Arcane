#!/bin/bash

# Diretório de destino para as texturas prontas
TARGET_DIR="../../textures/item"

# Mapeamento dos nomes base para os nomes finais (Bash 3.2 compatível)
MATERIALS=(
  "cooper:copper"
  "diamond:diamond"
  "emerold:emerald"
  "flint:flint"
  "gold:golden"
  "iron:iron"
  "netherite:netherite"
)

# Arquivos base
WOOD="wood_base.png"
LEATHER="leather_grip.png"
ACTIVE_RUNE="active_rune_base.png"

echo "Gerando texturas das varinhas (Wands)..."

for entry in "${MATERIALS[@]}"; do
  mat="${entry%%:*}"
  out_name="${entry##*:}"
  mat_file="${mat}_base.png"

  echo "Processando material: $out_name..."

  # 1. Vars sem leather grip
  # 1.1 Base silenciosa
  convert "$WOOD" "$mat_file" -composite "$TARGET_DIR/${out_name}_wand.png"

  # 1.2 Versões carregando (Cast states)
  for state in a b c d; do
    convert "$WOOD" "$mat_file" -composite "$ACTIVE_RUNE" -composite "${state}_base.png" -composite "$TARGET_DIR/${out_name}_wand_cast_${state}.png"
  done

  # 2. Vars com leather grip
  # 2.1 Base silenciosa
  convert "$WOOD" "$LEATHER" -composite "$mat_file" -composite "$TARGET_DIR/${out_name}_wand_leather_grip.png"

  # 2.2 Versões carregando (Cast states)
  for state in a b c d; do
    convert "$WOOD" "$LEATHER" -composite "$mat_file" -composite "$ACTIVE_RUNE" -composite "${state}_base.png" -composite "$TARGET_DIR/${out_name}_wand_leather_grip_cast_${state}.png"
  done
done

echo "Geração concluída com sucesso! Limpando texturas in_hand antigas..."
rm -f "$TARGET_DIR"/*_in_hand*.png

echo "Tudo pronto!"
