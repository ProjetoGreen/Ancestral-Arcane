# Ancestral Arcane Mod - Alignment TODO

This document tracks the missing features in the mod code required to achieve full mechanical alignment with the project Wiki.

---

## 🔴 Priority 1: Core Casting & Passive Bonuses
*High-impact mechanical rules defined in the Wiki but currently missing or incomplete in code.*

- [ ] **Leather Grip Properties**:
  - Update `WandItem` and `CastResolver` to detect if the active item is a `_leather_grip` variant.
  - Implement `-10%` cast time reduction and `+1` reuse (impurity tolerance) for these variants.
- [ ] **Affinity Bonus Expansion**:
  - Extend affinity bonuses beyond just `powerMultiplier`.
  - Implement reductions for: `castTime`, `castCost`, and `dirtyGain` when casting a spell matching the wand's affinity.
- [ ] **Cast Hold/Collapse Mechanic**:
  - Implement `onUseTick` in `WandItem` to track how long a spell is held at "Peak Charge" (Stage D).
  - Add logic to either force-release or "collapse" (cancel with cooldown) if held beyond the allowed window.

---

## 🟡 Priority 2: Exploration & Utility
*Features that define mid-game progression and field usefulness.*

- [ ] **Home Anchor Synchronization & Heartstone Logic**:
  - Update `executeHeartstone` in `SpellExecutor` to check for a specific `Home Anchor` location stored in the wand's/rune's NBT.
  - Add a "synchronization" interaction between the wand and the `Home Anchor` block.
  - Implement special costs: `Heartstone` requires a *full charge* but only consumes `0.5` wear (ignoring normal tier wear rules).
- [ ] **Summon Wolves Expansion**:
  - Implement Tier-based scaling: Tier III (3 wolves, 3m), Tier IV (4, 4m), Tier V (5, 5m).
  - Add "Raw Beef" check: if holding beef, consume only `1` total charge; otherwise `1` charge per wolf.
- [ ] **Fragment of All Knowledge (Rapid Swapping)**:
  - Implement a field-swapping GUI or mechanic when the wand contains the `Fragment of All Knowledge`.
  - This should remove the hard requirement for an Arcane Smithing Table for basic rune swapping.

---

## 🟢 Priority 3: Advanced Progression
*Late-game features that require internal architecture expansion.*

- [ ] **Advanced Modular Wand**:
  - Expand `WandItem` NBT structure to support up to 3 `equipped_runes`.
  - Implement the "Socketed Grimoire" logic (Tier V Empty Grimoires acts as expansion slots).
  - Add a selector mechanism (keybind or scroll) to switch the active rune slot in the field.

---

## ⚪ Priority 4: UX & Polish
*Non-functional improvements for professionalism and player feedback.*

- [ ] **Translatable & Dynamic Tooltips**:
  - Replace `Component.literal` with `Component.translatable` in `WandItem` and `GrimoireSpellItem`.
  - use `toRoman(tier)` for display and add tiered "Uses Left" counters.
- [ ] **NBT Default Consistency**:
  - Ensure all items in the Creative Tab and Registry are pre-initialized with the correct Wiki-defined NBT (Tier, Charges, etc.) to prevent null/fallback usage.
