# Ancestral Arcane Mod - Alignment TODO

This document tracks the status of mod features compared to the project Wiki. Many Core (P1) and Exploration (P2) features are now implemented.

---

## ✅ Completed (Mechanical Alignment Phase)
- [x] **Leather Grip Properties**: implemented -10% cast time and +1 reuse tolerance.
- [x] **Cast Hold/Collapse Mechanic**: implemented 1s Stage D window with auto-fizzle and penalties.
- [x] **Affinity Bonus Expansion**: implemented -15% cast time, -20% costs, and elemental particles.
- [x] **Home Anchor Registration**: wands link to anchors with visual portal effects.
- [x] **Heartstone Logic**: teleports to linked anchors with 0.5 wear cost.
- [x] **Summon Wolves Scaling**: Tier-based counts and Raw Beef charge discount.
- [x] **Fragment of All Knowledge**: implemented sneak+right-click rapid rune swapping in the field.
- [x] **Translatable UX**: all wands/grimoires now use translatable tooltips and Roman numeral tiers.
- [x] **Impurity Enforcement**: spells now fail if the "Dirty" meter reaches 100%.

---

## 🔴 Priority 1: High Impact Mechanics
- [ ] **Dirty Side-Effects**: Add more dynamic negative effects (e.g., occasional random sparks or weak slowness) when Impurity is > 75%.

---

## 🟡 Priority 2: Mid-Game Utility
- [ ] **Advanced Home Anchor Visuals**: Implement a visible "Beam" or visual tether between linked anchors and the player when holding the wand.

---

## 🟢 Priority 3: Advanced Progression
- [ ] **Advanced Modular Wand (Multi-Slot)**:
  - Expand `WandItem` NBT structure to support up to 3 `equipped_runes`.
  - Implement Tier V "Socketed Grimoire" expansion logic.
  - Add a selector mechanism (keybind or scroll) to switch active runes in the field.

---

## ⚪ Priority 4: UX & Polish
- [x] **Translatable & Dynamic Tooltips**
- [ ] **NBT Default Consistency**:
  - Pre-initialize all Registry/Creative Tab items with correct NBT.
- [ ] **Code Polish (Technical Debt)**:
  - Resolve remaining 100+ "Null type safety" warnings in registry and UI classes.
