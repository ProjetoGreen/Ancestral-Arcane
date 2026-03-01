# Ancestral Arcane

**Ancestral Arcane** é um mod *standalone* de magia medieval e ritualística para Minecraft Java 1.21.x, construído na plataforma NeoForge. 

O mod foi pensado para ter uma progressão natural (sobrevivência → conhecimento natural → magia), sendo totalmente focado em limitações, runas, pergaminhos e a canalização de feitiços através de uma Lança Arcana, sem depender de tecnologia avançada ou mecânicas overpower.

## ✨ Características Principais

- **Runas e Grimoires:** Descubra, construa e encante runas com feitiços.
- **Feitiços Determinísticos:** 8 magias úteis para o mundo (Fire, Storm, Heal, Breathe, Heartstone, Breaker, Fertilize, Wolves).
- **Mesa Arcana (Arcane Smithing Table):** Bloco customizado com 5 níveis de progressão para recarregar, inscrever e fazer upgrades em suas runas e ferramentas.
- **HUD Próprio:** Uma barra de carregamento dinâmica exibida ao usar a Lança Arcana, indicando progresso e cooldowns dos feitiços de forma fluida.
- **Magia Enraizada no Vanilla:** Utiliza tinturas base para intenção, Glowstone como energia arcana central, e integrando exploração recompensadora (Loot de Witches, End Cities, Chests, e Trades de Villagers).
- **Sem Dependências Externas (Standalone):** Todos os modelos, texturas base e arquivos de linguagem vêm embutidos dentro do mod sem precisar de Resource Packs adicionais.

## 🛠 Instalação e Desenvolvimento

O ambiente utiliza o **NeoForge 1.21.1** (ModDev Gradle Plugin 2.0.74).

**Para compilar o mod:**
```bash
./gradlew build
```

**Para testar localmente:**
```bash
./gradlew runClient
```

### Comandos de Debug (Apenas para Ops/Testes)
- `/ancestral_arcane_give_test`: Entrega a mesa arcana no nível máximo (5), runas ativadas, grimoires, lança focada e materiais necessários para explorar os feitiços sem precisar passar pela progressão do jogo durante testes de desenvolvimento.

## 📜 Licença
Distribuído sob a licença MIT.
