# Ancestral Arcane

**Ancestral Arcane** é um mod *standalone* de magia medieval e ritualística para Minecraft Java 1.21.x, construído na plataforma NeoForge. 

O mod foi pensado para ter uma progressão natural (sobrevivência → conhecimento natural → magia), focado nas mecânicas imersivas de runas, pergaminhos, grimórios e canalização de feitiços através de varinhas, sem depender de tecnologia avançada ou mecânicas *overpower*.

## 📜 Conceito e Lore (A Tradição Perdida)

*Eras atrás, antes dos aldeões erguerem suas bibliotecas e dos monstros corromperem as noites, a magia não era uma ferramenta matemática, mas sim uma linguagem. Os Ancestrais não dependiam de máquinas complexas; eles dominavam a pura **Intenção** do mundo, expressa através de corantes vitais, e canalizavam a **Luz Estelar**, que hoje cristaliza nas profundezas abissais como a poeira de Glowstone.*

Como um sobrevivente desta nova era, sua missão não é inventar a mágica, mas sim redescobri-la. Ao moldar poeira brilhante, entalhar pedras cruas com intenção e fabricar ferramentas rústicas baseadas em ecos do passado, você reacende a faísca do **Arcano Ancestral**. Explore o mundo, encontre Fragmentos de Conhecimento, e use a **Mesa de Forja Arcana (Arcane Smithing Table)** para combinar Grimórios e Runas, revelando feitiços antigos.

## ✨ Características Principais

- **Runas e Grimórios:** Descubra, construa e encante runas com diversos feitiços utilitários, mágicos e elementais (Fire, Storm, Heal, Cleanse, Ward, Breach, etc).
- **Varinhas (Wands):** Canalize seus feitiços utilizando varinhas de diversos materiais (Flint, Copper, Iron, Golden, Diamond, Emerald, Netherite), com ou sem grip de couro.
- **Mesa Arcana (Arcane Smithing Table):** Bloco customizado essencial para criar encantamentos, fundir itens mágicos e progredir no mod. Permite reativar grimórios esquecidos (Forgotten Magic Book) consumindo energia (Glowstone).
- **Interface e Efeitos:** Sistema de carregamento dinâmico integrado ao HUD para controle imersivo do tempo de conjuração das magias (cooldowns e casting).
- **Magia Enraizada no Vanilla:** Utiliza tinturas, materiais naturais e incentiva as explorações.
- **Sem Dependências (Standalone):** Todos os recursos de modelos, texturas base e linguagem vêm embutidos dentro do mod sem necessitar de Resource Packs extras.

## 🛠 Instalação e Desenvolvimento

O ambiente utiliza o **NeoForge 1.21.1** (ModDev Gradle Plugin).

**Para compilar o mod localmente:**
```bash
./gradlew build
```

**Para testar o ambiente:**
```bash
./gradlew runClient
```

### Comandos de Debug (Apenas para Ops/Testes)
- `/ancestral_arcane_give_test`: Entrega a mesa arcana, materiais mágicos, runas e varinhas base necessárias para testar os feitiços rapidamente sem precisar do modo de sobrevivência.

## 🐛 Como Criar uma Issue (Reportar Bugs ou Sugerir Melhorias)

Encontrou algum problema, travamento ou tem uma ideia para expandir o mod? Crie uma **Issue** na aba "Issues" do repositório para nos ajudar a melhorar o Ancestral Arcane.

**Siga os passos abaixo para facilitar o atendimento:**

1. **Título Claro:** Seja objetivo. Ex: *"Crash ao conjurar o feitiço Heal usando a Varinha de Cobre"*.
2. **Contexto (Ambiente):** Informe as versões exatas do *Minecraft* (ex: 1.21.1) e do *NeoForge* que você está rodando. Tem outros mods instalados? Se sim, cite-os.
3. **Descrição Detalhada do Bug:** 
   - O que aconteceu de errado? 
   - Quais são os passos exatos de reprodução para chegarmos no mesmo erro?
   - O que era esperado que acontecesse no lugar do erro?
4. **Logs e Prints (Crítico para Bugs):** Sempre anexe o arquivo de crash (`crash-report.txt`) ou o console log (`latest.log`). Utilize o Pastebin ou Gist se for um texto longo. Se for um bug visual, anexe *screenshots* (prints).
5. **Sugestões de Features:** Se for um pedido de nova funcionalidade, detalhe como ela se encaixaria na *Lore* de "Magia Natural/Ritualística" e no balanceamento atual do mod para não quebrar a progressão.

## 📜 Licença
Distribuído sob a licença MIT.
