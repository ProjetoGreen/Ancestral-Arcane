#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CODE_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"
WIKI_ROOT_DEFAULT="${CODE_ROOT}.wiki"
WIKI_ROOT="${1:-${WIKI_ROOT_DEFAULT}}"

ASSETS_ROOT="${CODE_ROOT}/src/main/resources/assets/ancestral_arcane"
TEXTURES_ROOT="${ASSETS_ROOT}/textures"
ARCANE_ITEMS_ROOT="${ASSETS_ROOT}/arcane items"

copy_flat() {
  local src_dir="$1"
  local dest_dir="$2"
  local pattern="${3:-*.png}"

  [[ -d "${src_dir}" ]] || return 0

  mkdir -p "${dest_dir}"

  while IFS= read -r file; do
    cp "${file}" "${dest_dir}/$(basename "${file}")"
    printf 'copied %s -> %s\n' "${file}" "${dest_dir}/$(basename "${file}")"
  done < <(find "${src_dir}" -maxdepth 1 -type f -name "${pattern}" | sort)
}

copy_named() {
  local src_dir="$1"
  local dest_dir="$2"
  shift 2

  [[ -d "${src_dir}" ]] || return 0

  mkdir -p "${dest_dir}"

  local name
  for name in "$@"; do
    if [[ -f "${src_dir}/${name}" ]]; then
      cp "${src_dir}/${name}" "${dest_dir}/${name}"
      printf 'copied %s -> %s\n' "${src_dir}/${name}" "${dest_dir}/${name}"
    fi
  done
}

copy_grimoire_and_scrolls() {
  local src_dir="${ARCANE_ITEMS_ROOT}/grimoire and scroll"
  local scroll_dest="${WIKI_ROOT}/imgs/scrolls"
  local grimoire_dest="${WIKI_ROOT}/imgs/grimoires"
  local material_dest="${WIKI_ROOT}/imgs/materials"

  [[ -d "${src_dir}" ]] || return 0

  mkdir -p "${scroll_dest}" "${grimoire_dest}" "${material_dest}"

  while IFS= read -r file; do
    local base
    base="$(basename "${file}")"

    case "${base}" in
      scroll*.png)
        cp "${file}" "${scroll_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${scroll_dest}/${base}"
        ;;
      grimoire*.png|forgotten_magicbook.png)
        cp "${file}" "${grimoire_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${grimoire_dest}/${base}"
        ;;
      fragment_of_all_knowledge.png)
        cp "${file}" "${material_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${material_dest}/${base}"
        ;;
    esac
  done < <(find "${src_dir}" -maxdepth 1 -type f -name '*.png' | sort)
}

copy_wand_assets() {
  local generator_dir="${ARCANE_ITEMS_ROOT}/wands_muckups"
  local generated_src="${TEXTURES_ROOT}/item"
  local catalyst_src="${ARCANE_ITEMS_ROOT}/wands_muckups"
  local dest="${WIKI_ROOT}/imgs/wands"
  local catalyst_dest="${dest}/catalysts"

  mkdir -p "${dest}" "${catalyst_dest}"

  if [[ -x "${generator_dir}/generate_wands.sh" ]]; then
    (
      cd "${generator_dir}"
      bash "./generate_wands.sh"
    )
  fi

  while IFS= read -r file; do
    cp "${file}" "${dest}/$(basename "${file}")"
    printf 'copied %s -> %s\n' "${file}" "${dest}/$(basename "${file}")"
  done < <(find "${generated_src}" -maxdepth 1 -type f -name '*wand*.png' | sort)

  copy_named \
    "${catalyst_src}" \
    "${catalyst_dest}" \
    "flint_catalyst.png" \
    "iron_catalyst.png" \
    "gold_catalyst.png" \
    "diamond_catalyst.png" \
    "netherite_catalyst.png" \
    "emerald_catalyst.png" \
    "copper_catalyst.png"
}

copy_items() {
  local src_dir="${TEXTURES_ROOT}/item"
  local dye_dest="${WIKI_ROOT}/imgs/dyes"
  local material_dest="${WIKI_ROOT}/imgs/materials"
  local wand_dest="${WIKI_ROOT}/imgs/wands"
  local scroll_dest="${WIKI_ROOT}/imgs/scrolls"
  local rune_dest="${WIKI_ROOT}/imgs/runes"
  local grimoire_dest="${WIKI_ROOT}/imgs/grimoires"

  [[ -d "${src_dir}" ]] || return 0

  mkdir -p "${dye_dest}" "${material_dest}" "${wand_dest}" "${scroll_dest}" "${rune_dest}" "${grimoire_dest}"

  while IFS= read -r file; do
    local base
    base="$(basename "${file}")"

    case "${base}" in
      *_dye.png)
        cp "${file}" "${dye_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${dye_dest}/${base}"
        ;;
      *wand*.png)
        cp "${file}" "${wand_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${wand_dest}/${base}"
        ;;
      scroll*.png)
        cp "${file}" "${scroll_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${scroll_dest}/${base}"
        ;;
      rune*.png)
        cp "${file}" "${rune_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${rune_dest}/${base}"
        ;;
      grimoire*.png|forgotten_magicbook.png)
        cp "${file}" "${grimoire_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${grimoire_dest}/${base}"
        ;;
      *)
        cp "${file}" "${material_dest}/${base}"
        printf 'copied %s -> %s\n' "${file}" "${material_dest}/${base}"
        ;;
    esac
  done < <(find "${src_dir}" -maxdepth 1 -type f -name '*.png' | sort)
}

if [[ ! -d "${WIKI_ROOT}" ]]; then
  printf 'wiki path not found: %s\n' "${WIKI_ROOT}" >&2
  exit 1
fi

mkdir -p "${WIKI_ROOT}/imgs/blocks" "${WIKI_ROOT}/imgs/gui"

copy_flat "${TEXTURES_ROOT}/block" "${WIKI_ROOT}/imgs/blocks" '*.png'
copy_flat "${TEXTURES_ROOT}/gui/arcane_table" "${WIKI_ROOT}/imgs/gui" '*.png'
copy_items
copy_named "${ARCANE_ITEMS_ROOT}/runes" "${WIKI_ROOT}/imgs/runes" \
  "rune.png" \
  "rune_crude.png" \
  "rune_generic_inscribed.png" \
  "rune_upgraded.png" \
  "rune_upgraded_generic_inscribed.png"
copy_grimoire_and_scrolls
copy_wand_assets

# Cleanup legacy folders if they exist and are empty or redundant
[[ -d "${WIKI_ROOT}/imgs/item" ]] && rm -rf "${WIKI_ROOT}/imgs/item"
[[ -d "${WIKI_ROOT}/imgs/block" ]] && rm -rf "${WIKI_ROOT}/imgs/block"

printf 'texture sync completed.\n'
